package com.runcode.tasbee7.hadith_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.runcode.tasbee7.R
import com.runcode.tasbee7.data.database.AzkarDatabase
import com.runcode.tasbee7.data.model.Hadith
import com.runcode.tasbee7.data.remote.ApiClient
import com.runcode.tasbee7.databinding.ActivityHadithBinding
import kotlinx.coroutines.flow.collect

class HadithActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityHadithBinding
    lateinit var viewModel: HadithViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadith)
        mBinding = ActivityHadithBinding.inflate(layoutInflater)

        val factory = ViewModelFactory.getInstance(
            ApiClient.buildRetrofit(),
            AzkarDatabase.getInstance(this).hadithDao
        )
        viewModel = ViewModelProvider(this, factory)[HadithViewModel::class.java]


        lifecycleScope.launchWhenResumed {
            displayHadith(viewModel.getRandomHadith())
        }
    }


    private fun displayHadith(hadith: Hadith) {
        mBinding.hadith.text = hadith.Ar_Text
    }
}