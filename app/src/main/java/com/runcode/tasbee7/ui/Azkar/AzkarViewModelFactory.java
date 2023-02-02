package com.runcode.tasbee7.ui.Azkar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.runcode.tasbee7.data.repository.AzkarRepositoryImpl;

import java.lang.reflect.InvocationTargetException;

public class AzkarViewModelFactory implements ViewModelProvider.Factory {

    Application mApplication;

    public AzkarViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AzkarRepositoryImpl azkarRepository = new AzkarRepositoryImpl(mApplication);
        try {
            return modelClass.getConstructor(AzkarRepositoryImpl.class).newInstance(azkarRepository);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
