package com.runcode.tasbee7.ui.Azkar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.runcode.tasbee7.R;

import java.util.List;
import java.util.Objects;

public class AzkarFragment extends Fragment {

    private AzkarRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Zikr> mData;
    private Button mMorning;
    private Button mEvening;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_azkar, container, false);
        setUpRecyclerView(view);

        AzkarViewModel azkarViewModel =
                new ViewModelProvider(this, new AzkarViewModelFactory(requireActivity().getApplication()))
                        .get(AzkarViewModel.class);
        mMorning = view.findViewById(R.id.MorningAzkarButton);
        mMorning.setOnClickListener(
                view1 -> {
                    view.findViewById(R.id.azkarButtonContainer).setVisibility(View.GONE);
                    azkarViewModel.getMorningAZkar();
                });
        mEvening = view.findViewById(R.id.EveningAzkarButton);
        mEvening.setOnClickListener(view12 -> {
            view.findViewById(R.id.azkarButtonContainer).setVisibility(View.GONE);
            azkarViewModel.getEveningAZkar();
        });
        azkarViewModel.morningAzkar.observe(getViewLifecycleOwner(), morningAzkar -> {
            Log.d(TAG, "onCreate: zikr " + morningAzkar.size());
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.setData(morningAzkar);
            mData = morningAzkar;
        });
        azkarViewModel.eveningAzkar.observe(getViewLifecycleOwner(), eveningAzkar -> {
            Log.d(TAG, "onCreate: zikr we got" + eveningAzkar.size() + " Azkar");
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.setData(eveningAzkar);
            mData = eveningAzkar;
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.azkar_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mAdapter = new AzkarRecyclerAdapter(requireActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private static final String TAG = "AzkarFragment";

    @Override
    public void onStart() {
        super.onStart();
        try {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("");
        } catch (Exception e) {
            Log.d(TAG, "onViewCreated: can't change title");
        }
        changeTheme();
    }


    public Integer getColorPrimary(Context context) {
        final String sColor_primary = "color_primary";
        final String SHARED_PREF = "shared_pref";
        SharedPreferences sp = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sp.getInt(sColor_primary,
                context.getResources().getColor(R.color.colorPrimary));
    }

    private void changeTheme() {
        int color = getColorPrimary(requireContext());
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) requireActivity().findViewById(R.id.main_activity_toolbar);
        toolbar.setBackgroundColor(color);
        GradientDrawable gbm = (GradientDrawable) mMorning.getBackground().mutate();
        gbm.setColor(color);
        GradientDrawable gbe = (GradientDrawable) mEvening.getBackground().mutate();
        gbe.setColor(color);
    }

}