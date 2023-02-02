package com.runcode.tasbee7.ui.Azkar;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.runcode.tasbee7.data.repository.AzkarRepositoryImpl;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class AzkarViewModel extends ViewModel {
    LiveData<List<Zikr>> morningAzkar;
    private final MutableLiveData<List<Zikr>> _morningAzkar = new MutableLiveData<>();
    LiveData<List<Zikr>> eveningAzkar;
    private final MutableLiveData<List<Zikr>> _eveningAzkar = new MutableLiveData<>();
    private static final String TAG = "AzkarViewModel";
    private final AzkarRepositoryImpl mAzkarRepository;

    public AzkarViewModel(AzkarRepositoryImpl azkarRepository) {
        mAzkarRepository = azkarRepository;
        morningAzkar = _morningAzkar;
        eveningAzkar = _eveningAzkar;
    }


    public void insertZikr(Zikr zikr) {
        mAzkarRepository.insertZikr(zikr);
    }

    @SuppressLint("CheckResult")
    public void getMorningAZkar() {
        mAzkarRepository.getMorningAzkar()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    _morningAzkar.postValue(result);
                    morningAzkar = _morningAzkar;
                }, error -> Log.d(TAG, "getMorningAZkar: " + error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getEveningAZkar() {
        mAzkarRepository.getEveningAzkar()
                .subscribeOn(Schedulers.io())
                .subscribe(result ->
                        {
                            _eveningAzkar.postValue(result);
                            eveningAzkar = _eveningAzkar;
                        }
                        , error -> Log.d(TAG, "getEveningAZkar: " + error.getMessage()));
    }
}
