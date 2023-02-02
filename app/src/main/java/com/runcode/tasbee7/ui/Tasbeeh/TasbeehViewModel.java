package com.runcode.tasbee7.ui.Tasbeeh;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.runcode.tasbee7.R;

import static android.content.Context.MODE_PRIVATE;

public class TasbeehViewModel extends ViewModel {
    Application mApplication;

    public int getMaxValue() {
        return mMaxValue;
    }

    private int totalTasbee7Number, counterTotalNumber;
    //    private int counter = 0;
    private static final String SHARED_PREF = "shared_pref";
    private static final String TOTAL_NUMBER = "totalTasbeehNumber";
    private static final String TOTAL_COUNT = "totalCount";
    private static final String sColor_primary = "color_primary";
    private static final String sColorPrimaryDark = "ColorPrimaryDark";

    //total count
    private final MutableLiveData<Integer> _counterTotalNumberLiveDate = new MutableLiveData<>();
    LiveData<Integer> counterTotalNumberLive;
    //tasbeeh count
    private final MutableLiveData<Integer> _totalTasbee7NumberMutable = new MutableLiveData<>();
    LiveData<Integer> totalTasbee7NumberLive;

    private final MutableLiveData<Integer> _counterMutableLivedata = new MutableLiveData<>();
    public LiveData<Integer> counterMutableLivedata;
    private int mCounter = 0;
    private int mMaxValue;

    //
//    private final MutableLiveData<Integer> _colorMutableLiveData = new MutableLiveData<>();
//    public LiveData<Integer> colorGetterLiveData;

    public TasbeehViewModel(Application application) {
        this.mApplication = application;
    }


    public void LoadData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        counterTotalNumber = sp.getInt(TOTAL_COUNT, 0);
        totalTasbee7Number = sp.getInt(TOTAL_NUMBER, 0);
        _counterTotalNumberLiveDate.postValue(counterTotalNumber);
        _totalTasbee7NumberMutable.postValue(totalTasbee7Number);
        counterTotalNumberLive = _counterTotalNumberLiveDate;
        totalTasbee7NumberLive = _totalTasbee7NumberMutable;
    }

    public void saveData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(TOTAL_COUNT, counterTotalNumber);
        editor.putInt(TOTAL_NUMBER, totalTasbee7Number);
        editor.apply();
    }

    public void increaseValues() {
        _totalTasbee7NumberMutable.postValue(++totalTasbee7Number);
        _counterTotalNumberLiveDate.postValue(++counterTotalNumber);
        //counter++;
    }


    public Integer getColorPrimary(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sp.getInt(sColor_primary,
                context.getResources().getColor(R.color.colorPrimary));
    }


    public Integer getColorPrimaryDark(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sp.getInt(sColorPrimaryDark, ContextCompat.getColor(context, R.color.colorPrimaryDark));
    }


    public void saveColorsToSharedPrefs(int color, Context context) {

        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(sColor_primary, color);
        int colorPrimaryDark = getDarkerColor(color, 0.8f);
        editor.putInt(sColorPrimaryDark, colorPrimaryDark);
        editor.apply();
    }

    public static int getDarkerColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }

    public void reset() {
        counterTotalNumber = 0;
        _counterTotalNumberLiveDate.postValue(counterTotalNumber);
    }

    public void applyCounter(int maxValue) {
        mCounter = 0;
        mMaxValue = maxValue;
    }

    public void upgradeCounter() {
        mCounter++;
        if (mCounter <= mMaxValue) {
            _counterMutableLivedata.postValue(mCounter);
            counterMutableLivedata = _counterMutableLivedata;
        }
    }

    public void resetAll() {
        counterTotalNumber = 0;
        _counterTotalNumberLiveDate.postValue(counterTotalNumber);
        totalTasbee7Number = 0;
        _totalTasbee7NumberMutable.postValue(totalTasbee7Number);
    }

    public void notifyCounterReset() {
        mCounter = 0;
        _counterMutableLivedata.postValue(mCounter);
        counterMutableLivedata = _counterMutableLivedata;
    }
}
