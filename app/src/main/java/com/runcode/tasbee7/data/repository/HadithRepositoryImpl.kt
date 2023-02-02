package com.runcode.tasbee7.data.repository

import androidx.lifecycle.LiveData
import com.runcode.tasbee7.domain.repository.HadithRepository
import com.runcode.tasbee7.data.database.HadithDao
import com.runcode.tasbee7.data.model.hadith.Chapter
import com.runcode.tasbee7.data.model.hadith.Hadith
import com.runcode.tasbee7.data.remote.HadithApi

class HadithRepositoryImpl(
    private val api: HadithApi,
    private val database: HadithDao
) :
    HadithRepository {
    override fun insertAll(Ahadith: List<Hadith>) {
        database.insertAll(Ahadith)
    }

    override fun hadithCount(): Int =
        database.hadithCount()

    override fun getHadithById(id:Int):LiveData<Hadith> {
        return database.getHadithById(id)
    }

    override suspend fun getRandom(): Hadith = database.getRandom()


    override suspend fun getChapter(): Chapter =
        api.getChapter()
}