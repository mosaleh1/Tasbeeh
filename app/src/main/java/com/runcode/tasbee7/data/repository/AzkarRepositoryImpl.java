package com.runcode.tasbee7.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.runcode.tasbee7.domain.repository.AzkarRepository;
import com.runcode.tasbee7.ui.Azkar.Zikr;
import com.runcode.tasbee7.data.database.AzkarDao;
import com.runcode.tasbee7.data.database.AzkarDatabase;

import java.util.List;

import io.reactivex.Observable;


public class AzkarRepositoryImpl implements AzkarRepository {
    AzkarDao mDao;

    public AzkarRepositoryImpl(Context context) {
        mDao = AzkarDatabase.getInstance(context).getAzkarDao();
    }

    public void insertZikr(@NonNull Zikr zikr) {
        mDao.insertZikr(zikr);
    }


    @NonNull
    public Observable<List<Zikr>> getMorningAzkar() {
        return mDao.getMorningAZkar();
    }


    public Observable<List<Zikr>> getEveningAzkar() {
        return mDao.getEveningAZkar();
    }

}
