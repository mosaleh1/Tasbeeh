package com.runcode.tasbee7.domain.repository

import androidx.lifecycle.LiveData
import com.runcode.tasbee7.data.model.hadith.Chapter
import com.runcode.tasbee7.data.model.hadith.Hadith

interface HadithRepository  {

    fun insertAll(ahadith: List<Hadith>)

    suspend fun getRandom(): Hadith

    suspend fun getChapter(): Chapter

    fun hadithCount (): Int

    fun getHadithById(id:Int): LiveData<Hadith>

}