package com.runcode.tasbee7.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.runcode.tasbee7.ui.Azkar.Zikr;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface AzkarDao {

    @Insert
    Completable insertZikr(Zikr zikr);

    @Query("select * from zikr_table where isMorningZikr = 1 order by zikrId")
    io.reactivex.Observable<List<Zikr>> getMorningAZkar();

    @Query("select * from zikr_table where isMorningZikr = 0 order by zikrId")
    Observable<List<Zikr>> getEveningAZkar();
}
