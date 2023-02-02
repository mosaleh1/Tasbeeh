package com.runcode.tasbee7.ui.Tasbeeh;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TasbeehViewModelProvider implements ViewModelProvider.Factory {
    Application mApplication;

    public TasbeehViewModelProvider(Application application) {
        this.mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        try {
//            return modelClass.getConstructor(TasbeehViewModel.class).newInstance(mApplication);
//        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Cannot create an instance of me " + modelClass, e);
//        }
        return (T) new TasbeehViewModel(mApplication);
    }
}
