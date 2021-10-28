package com.runcode.tasbee7.Azkar;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.runcode.tasbee7.Repository;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class AzkarViewModel extends ViewModel {
    LiveData<List<Zikr>> morningAzkar;
    private final MutableLiveData<List<Zikr>> _morningAzkar = new MutableLiveData<>();
    LiveData<List<Zikr>> eveningAzkar;
    private final MutableLiveData<List<Zikr>> _eveningAzkar = new MutableLiveData<>();
    private static final String TAG = "AzkarViewModel";
    private final Repository mRepository;

    public AzkarViewModel(Repository repository) {
        mRepository = repository;
        morningAzkar = _morningAzkar;
        eveningAzkar = _eveningAzkar;
    }


    public void insertZikr(Zikr zikr) {
        mRepository.insertZikr(zikr);
    }

    @SuppressLint("CheckResult")
    public void getMorningAZkar() {
        mRepository.getMorningAZkar()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    _morningAzkar.postValue(result);
                    morningAzkar = _morningAzkar;
                }, error -> Log.d(TAG, "getMorningAZkar: " + error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getEveningAZkar() {
        mRepository.getEveningAZkar()
                .subscribeOn(Schedulers.io())
                .subscribe(result ->
                        {
                            _eveningAzkar.postValue(result);
                            eveningAzkar = _eveningAzkar;
                        }
                        , error -> Log.d(TAG, "getEveningAZkar: " + error.getMessage()));
    }
}
