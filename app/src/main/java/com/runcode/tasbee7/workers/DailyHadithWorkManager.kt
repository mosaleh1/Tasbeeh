package com.runcode.tasbee7.workers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.runcode.tasbee7.R
import com.runcode.tasbee7.data.database.AzkarDatabase
import com.runcode.tasbee7.ui.hadith.HadithActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.runcode.tasbee7.notification.*
import com.runcode.tasbee7.ui.notification.notify

const val HADITH_ID = "HADITH_ID_EXTRA"

class DailyHadithWorkManager(
    val context: Context,
    param: WorkerParameters
) : Worker(context, param) {
    @SuppressLint("UnspecifiedImmutableFlag")
    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        val data = AzkarDatabase.getInstance(context).hadithDao
        GlobalScope.launch {
            val hadith = data.getRandom()
            val intent = Intent(context, HadithActivity::class.java)
                .apply {
                    putExtra(HADITH_ID,hadith.Hadith_ID)
                }
            val pending =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notify(
                context,
                context.getString(R.string.dailyHadith),
                hadith.Ar_Text,
                R.drawable.person_img,
                1, pending
            )
        }
        return Result.success()
    }
}