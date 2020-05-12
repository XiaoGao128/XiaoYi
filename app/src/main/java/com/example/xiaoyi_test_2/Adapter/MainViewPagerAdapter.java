package com.example.xiaoyi_test_2.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.xiaoyi_test_2.Fragment.Fragment_Item;
import com.example.xiaoyi_test_2.Fragment.Fragment_Social;
import com.example.xiaoyi_test_2.Fragment.Fragment_message;
import com.mob.tools.gui.ViewPagerAdapter;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment_Item fragment_item;
    private Fragment_message fragment_message;
    private Fragment_Social fragment_social;

    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragment_item=new Fragment_Item();
        fragment_message=new Fragment_message();
        fragment_social=new Fragment_Social();
    }


    @Override
    public int getCount() {
        return 3;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragment_item;
            case 1:
                return fragment_message;
            case 2:
                return fragment_social;
            default:return fragment_item;
        }
    }
}
