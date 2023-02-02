package com.runcode.tasbee7.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.runcode.tasbee7.data.model.hadith.Hadith

@Dao
interface HadithDao {

    @Insert
    fun insertAll(Ahadith: List<Hadith>)

    @Query("SELECT * FROM Hadith ORDER BY RANDOM() LIMIT 1 ")
    suspend fun getRandom(): Hadith

    @Query("SELECT COUNT(*) from Hadith")
    fun hadithCount():Int

    @Query("SELECT * FROM HADITH WHERE Hadith_ID = :id")
    fun getHadithById(id:Int):LiveData<Hadith>
}