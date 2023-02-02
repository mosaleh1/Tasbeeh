package com.runcode.tasbee7.ui.hadith

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.runcode.tasbee7.R
import com.runcode.tasbee7.data.database.AzkarDatabase
import com.runcode.tasbee7.data.model.hadith.Hadith
import com.runcode.tasbee7.data.remote.ApiClient
import com.runcode.tasbee7.workers.DailyHadithWorkManager
import com.runcode.tasbee7.workers.HADITH_ID
import java.util.*
import java.util.concurrent.TimeUnit

class HadithActivity : AppCompatActivity() {

    private lateinit var viewModel: HadithViewModel
    private lateinit var hadithTextView: TextView
    private lateinit var esnadTextView: TextView
    var hadith : Hadith? = null
    private val stackAhadith = Stack<Hadith>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadith)
        hadithTextView = findViewById(R.id.hadithTV)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val factory = ViewModelFactory.getInstance(
            ApiClient.buildRetrofit(),
            AzkarDatabase.getInstance(this).hadithDao
        )
        viewModel = ViewModelProvider(this, factory)[HadithViewModel::class.java]



        lifecycleScope.launchWhenStarted {
            //viewModel.getRandomHadith()
            if(intent.hasExtra(HADITH_ID)) {
                viewModel.getHadithByID(intent.getIntExtra(HADITH_ID,1))
            }else{
                viewModel.getRandomHadith()
            }
        }

        val dailyHadithWorkManager =
            PeriodicWorkRequestBuilder<DailyHadithWorkManager>(12, TimeUnit.HOURS)
                // Additional configuration
                .addTag("Hadith")
                .setInitialDelay(20,TimeUnit.SECONDS)
                .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            "DailyHadith",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyHadithWorkManager
        )

        viewModel.hadithLiveData.observe(this) { hadith ->
            Log.d(TAG, "onCreate: ${hadith.Ar_Text}")
            if (hadith != null) {
                this.hadith = hadith
                displayHadith(hadith)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = ""
    }

    private val TAG = "HadithActivity"
    private fun displayHadith(hadith: Hadith) {
        findViewById<ProgressBar>(R.id.hadith_bar).apply {
            visibility = View.INVISIBLE
        }
        Log.d(TAG, "displayHadith: $hadith us working ")
        hadithTextView.text = hadith.toString()
        hadithTextView.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hadith, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_next -> {
                lifecycleScope.launchWhenCreated {
                    stackAhadith.push(hadith)
                    viewModel.getRandomHadith()
                }
            }
            R.id.action_previous -> {
                if (stackAhadith.size > 1) {
                    displayHadith(stackAhadith.pop())
                } else {
                    Toast.makeText(this, "no data to show", Toast.LENGTH_LONG).show()
                }
            }
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.action_copy->{
                val textToCopy = hadithTextView.text
                val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("hadith", textToCopy)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(this, getString(R.string.copied), Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}