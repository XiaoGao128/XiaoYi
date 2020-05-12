package com.example.xiaoyi_test_2.Utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class PageTransFormer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {

    }
//    #AlphaPageTransformer

    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public void pageTransform(View view, float position)
    {
        if (position < -1)
        {
            view.setAlpha(mMinAlpha);
        } else if (position <= 1)
        { // [-1,1]

            if (position < 0) //[0，-1]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else
        { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
