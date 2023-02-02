package com.runcode.tasbee7.ui.Tasbeeh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.runcode.tasbee7.R;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;


public class TasbeehFragment extends Fragment implements CounterDialog.CounterDialogListener {
    // FragmentTasbeehBinding mBinding;
    // declare views
    private static final String TAG = "TasbeehFragment";


    private TextView totalTasbee7, counterTotal;
    private FloatingActionButton resetButton, incrementButton;

    private SeekBar mSeekBar;
    private TasbeehViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasbeeh, container, false);
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(requireActivity(),
                new TasbeehViewModelProvider(requireActivity().getApplication()))
                .get(TasbeehViewModel.class);
        initViews(view);

        defaultColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary);
        setHasOptionsMenu(true);
        return view;
    }

    private void initViews(View view) {
        totalTasbee7 = view.findViewById(R.id.text_total_number_of_tasbee7);
        counterTotal = view.findViewById(R.id.counter_text);
        resetButton = view.findViewById(R.id.reset_button);
        incrementButton = view.findViewById(R.id.counter_button);
        //personName = view.findViewById(R.id.person_name);
        mSeekBar = view.findViewById(R.id.seekBar);
    }

    int defaultColor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSeekBar.setOnTouchListener((v, event) -> true);
        mAdView = view.findViewById(R.id.adView);
        setListenersToIncrement();

        try {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        } catch (Exception e) {
            Log.d(TAG, "onViewCreated: can't change title");
        }
        //setListenersToNav();
//        requireActivity().getActionBar().setTitle("");


    }


    private void openDialog() {
        CounterDialog counterDialog = new CounterDialog();
        counterDialog.setListener(this);
        counterDialog.show(requireActivity().getSupportFragmentManager(), "counter_dialog");
    }
    private AdView mAdView;
    @Override
    public void onResume() {
        super.onResume();
        LoadData();
        loadColorsFromSharedPref();

        MobileAds.initialize(requireActivity(), initializationStatus -> {
        });
        //ads

        AdView adView = new AdView(requireActivity());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1035381369536866/5123280616");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void LoadData() {
        mViewModel.LoadData(requireActivity());
        mViewModel.counterTotalNumberLive.observe(requireActivity(), counterTotalNumber ->
                counterTotal.setText(String.valueOf(counterTotalNumber)));
        mViewModel.totalTasbee7NumberLive.observe(this, totalTasbee7Number ->
                totalTasbee7.setText(String.valueOf(totalTasbee7Number)));
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.saveData(requireActivity());
    }

    private void setListenersToIncrement() {
        resetButton.setOnClickListener(v -> mViewModel.reset());

        incrementButton.setOnClickListener(v -> {

            mViewModel.increaseValues();
            if (mSeekBar.getVisibility() == View.VISIBLE) {
                mViewModel.upgradeCounter();
                mViewModel.counterMutableLivedata.observe(getViewLifecycleOwner(), counter -> {
                    Log.d(TAG, "setListenersToIncrement: "+counter);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mSeekBar.setProgress(counter, true);
                    } else {
                        mSeekBar.setProgress(counter);
                    }
                    if (mSeekBar.getProgress() == mSeekBar.getMax()) {
                        mViewModel.notifyCounterReset();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mSeekBar.setProgress(0,true);
                        }else {
                            mSeekBar.setProgress(0);
                        }
                        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibrator.vibrate(500);
                        }
                    }
                });
                //mViewModel.upgradeCounter();
            }

        });
    }

    private void vibrate(int defaultAmplitude){
        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, defaultAmplitude));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    private void changeTheme() {
        new ColorPickerDialog.Builder(requireActivity())
                .setTitle(getString(R.string.color_picker_dialog))
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton(getString(R.string.confirm),
                        (ColorEnvelopeListener) (envelope, fromUser) -> {
                            int color = envelope.getColor();
                            //mOnColorChangeListener.onColorChange(color);
                            mViewModel.saveColorsToSharedPrefs(color, requireActivity());
                            loadColorsFromSharedPref();
                        })
                .setNegativeButton(getString(R.string.canceled),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(true) // the default value is true.
                .attachBrightnessSlideBar(true)  // the default value is true.
                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                .show();
    }

//    public void changeThemeForAr() {
//        new ColorPickerDialog.Builder(requireActivity().getApplicationContext())
//                .setTitle("ColorPicker Dialog")
//                .setPreferenceName("MyColorPickerDialog")
//                .setPositiveButton(getString(R.string.confirm),
//                        (ColorEnvelopeListener) (envelope, fromUser) -> {
//                            int color = envelope.getColor();
//                            mViewModel.saveColorsToSharedPrefs(color, requireActivity());
//                            loadColorsFromSharedPref();
//                        })
//                .setNegativeButton(getString(R.string.canceled),
//                        (dialogInterface, i) -> dialogInterface.dismiss())
//                .attachAlphaSlideBar(true) // the default value is true.
//                .attachBrightnessSlideBar(true)  // the default value is true.
//                .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
//                .show();
//    }

    private void loadColorsFromSharedPref() {
        // TODO: 4/1/2021 color observation
        int colorPrimary = mViewModel.getColorPrimary(requireActivity());
        int colorPrimaryDark = mViewModel.getColorPrimaryDark(requireActivity());
        defaultColor = colorPrimary;
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) requireActivity().findViewById(R.id.main_activity_toolbar);
        toolbar.setBackgroundColor(colorPrimary);
        counterTotal.setTextColor(colorPrimary);
        totalTasbee7.setTextColor(colorPrimary);
        requireActivity().getWindow().setStatusBarColor(colorPrimaryDark);

        //Buttons
        incrementButton.setRippleColor(colorPrimaryDark);
        incrementButton.setSupportBackgroundTintList(ColorStateList.valueOf(colorPrimary));
        resetButton.setRippleColor(colorPrimaryDark);
        resetButton.setSupportBackgroundTintList(ColorStateList.valueOf(colorPrimary));
        mSeekBar.getProgressDrawable().setColorFilter(
                mViewModel.getColorPrimary(requireActivity())
                , PorterDuff.Mode.MULTIPLY);

        setSeekBarColor(mSeekBar,mViewModel.getColorPrimary(requireActivity()));
    }
    public static void setSeekBarColor(SeekBar seekBar, int color) {
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public void applyCounter(int number) {
        Context context = getContext();
        if (context != null) {
            mViewModel.applyCounter(number);
            mSeekBar.getProgressDrawable().setColorFilter(
                    mViewModel.getColorPrimary(requireActivity())
                    , PorterDuff.Mode.MULTIPLY);
            mSeekBar.setVisibility(View.VISIBLE);
            mSeekBar.setMax(number);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tasbeeh_options_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_theme:
//                if (Resources.getSystem().getConfiguration().locale.getLanguage().equals("ar")) {
//                    Toast.makeText(requireActivity(), "Arabic", Toast.LENGTH_SHORT).show();
//                    changeThemeForAr();
//                } else {
                changeTheme();
                //}
                break;
            case R.id.action_set_prayer_counter:
                openDialog();
                break;
            case R.id.action_reset_all:
                mViewModel.resetAll();
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setTitle("");
    }

}