package com.runcode.tasbee7

import android.app.Application
import android.util.Log
import androidx.work.*
import com.runcode.tasbee7.workers.GetHadithFromServerWorker

private const val TAG = "TasbeehApp"
class TasbeehApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: the work started ")
        val myWorkRequest = OneTimeWorkRequest.from(GetHadithFromServerWorker::class.java)
        WorkManager.getInstance(applicationContext)
                .enqueue(myWorkRequest)
        val getHadithWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<GetHadithFromServerWorker>()
                // Additional configuration
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build())
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(getHadithWorkRequest)
    }

}