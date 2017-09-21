package com.seu.cose.easytrip.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.seu.cose.xutils3.BaseAppFragment;

import java.util.List;

/**
 * Created by Hins on 2017/9/7,007.
 */

public class FragAdapter extends FragmentStatePagerAdapter {

    private List<BaseAppFragment> mFragments;
    private FragmentManager fragmentManager;

    public FragAdapter(FragmentManager fm, List<BaseAppFragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
        fragmentManager = fm;
    }

    @Override
    public BaseAppFragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        BaseAppFragment fragment = mFragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

}
