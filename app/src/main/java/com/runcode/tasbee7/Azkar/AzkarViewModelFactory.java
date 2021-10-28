package com.runcode.tasbee7.Azkar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.runcode.tasbee7.Repository;

import java.lang.reflect.InvocationTargetException;

public class AzkarViewModelFactory implements ViewModelProvider.Factory {

    Application mApplication;

    public AzkarViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Repository repository = new Repository(mApplication);
        try {
            return modelClass.getConstructor(Repository.class).newInstance(repository);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot create an instance of " + modelClass, e);
        }
    }
}
