package com.runcode.tasbee7;

import android.content.Context;

import com.runcode.tasbee7.Azkar.Zikr;
import com.runcode.tasbee7.database.AzkarDao;
import com.runcode.tasbee7.database.AzkarDatabase;

import java.util.List;

import io.reactivex.Observable;


public class Repository {
    AzkarDao mDao;

    public Repository(Context context) {
        mDao = AzkarDatabase.getInstance(context).getAzkarDao();
    }

    public void insertZikr(Zikr zikr) {
        mDao.insertZikr(zikr);
    }


    public Observable<List<Zikr>> getMorningAZkar() {
        return mDao.getMorningAZkar();
    }


    public Observable<List<Zikr>> getEveningAZkar() {
        return mDao.getEveningAZkar();
    }

}
