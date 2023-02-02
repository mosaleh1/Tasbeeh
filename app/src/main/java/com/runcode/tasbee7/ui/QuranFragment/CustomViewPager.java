package com.runcode.tasbee7.ui.QuranFragment;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    onClickListener mListener;
    private int mCount=0;

    public void setListener(onClickListener listener) {
        mListener = listener;
    }

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isClicked = false;
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mCount += 1;
//            mListener.onClick();
            int finalCount = mCount;
            new Handler().postDelayed(() -> {
                if (finalCount >= 2) {
                    mListener.onClick();
                    mCount = 0;
                }
                mCount = 0;

            }, 300);
        }
        return super.onTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
//            mListener.onClick();
//            return true ;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }


    public interface onClickListener {
        void onClick();
    }
}
