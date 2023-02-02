package com.runcode.tasbee7.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.runcode.tasbee7.domain.repository.HadithRepository
import com.runcode.tasbee7.data.repository.HadithRepositoryImpl
import com.runcode.tasbee7.data.database.AzkarDatabase
import com.runcode.tasbee7.data.model.hadith.Chapter
import com.runcode.tasbee7.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "GetHadithFromServerWork"

class GetHadithFromServerWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    private lateinit var repository: HadithRepository

    override fun doWork(): Result {
        runBlocking {
            repository = HadithRepositoryImpl(
                ApiClient.buildRetrofit(),
                AzkarDatabase.getInstance(applicationContext).hadithDao
            )
        }
        return try {
            Log.d(TAG, "doWork: hadith started loading...")
            getRandomHadith(repository)
            Log.d(TAG, "doWork: hadith loaded successfully ")
            Result.success()

        } catch (e: Exception) {
            Log.d(TAG, "doWork: error while loading hadith hadith ")
            Result.retry()
        }
    }

    private fun getRandomHadith(repository: HadithRepository) {

        GlobalScope.launch(Dispatchers.IO) {
            // Coroutine that will be canceled when the ViewModel is cleared.
            val count = repository.hadithCount()
            if (count == 0) {
                //get from network
                val chapters = getFromApi(repository).AllChapters
                //insert to db
                repository.insertAll(chapters)
            }
        }
    }

    private suspend fun getFromApi(repository: HadithRepository): Chapter {
        return repository.getChapter()
    }
}
