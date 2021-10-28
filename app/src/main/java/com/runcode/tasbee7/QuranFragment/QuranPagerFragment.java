package com.runcode.tasbee7.QuranFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runcode.tasbee7.R;


public class QuranPagerFragment extends Fragment {

    private final int pageNumber;

    public QuranPagerFragment(int pageNumber) {
        this.pageNumber = pageNumber;
    }

   // public QuranPagerFragment() {
      //  pageNumber = 0;
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quran_pager, container, false);
        ImageView image = view.findViewById(R.id.QuranImageContainer);
        TextView fixedText = view.findViewById(R.id.textView_fixedQuran);
        TextView personName = view.findViewById(R.id.person_name_quranCover);
        if (pageNumber == 0) {
            fixedText.setVisibility(View.VISIBLE);
            personName.setVisibility(View.VISIBLE);
            image.setScaleType(ImageView.ScaleType.CENTER);
            image.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            image.requestLayout();
        }
        switch (pageNumber) {
            case 1:
                image.setImageResource(R.drawable.yasin1);
                break;
            case 2:
                image.setImageResource(R.drawable.yasin2);
                break;
            case 3:
                image.setImageResource(R.drawable.yasin3);
                break;
            case 4:
                image.setImageResource(R.drawable.yasin4);
                break;
            case 5:
                image.setImageResource(R.drawable.yasin5);
                break;
            case 6:
                image.setImageResource(R.drawable.yasin6);
                break;
            default: {
                image.setImageResource(R.drawable.quran_cover_png);
            }
        }
        return view;
    }
}