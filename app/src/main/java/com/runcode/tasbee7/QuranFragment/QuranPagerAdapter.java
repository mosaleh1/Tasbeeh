package com.runcode.tasbee7.QuranFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class QuranPagerAdapter extends FragmentPagerAdapter {

    private final QuranPagerFragment[] mFragmentList;

    public QuranPagerAdapter(@NonNull FragmentManager fm, int behavior, QuranPagerFragment[] fragments) {
        super(fm, behavior);
        this.mFragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList[position];
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }


}
