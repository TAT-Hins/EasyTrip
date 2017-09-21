package com.seu.cose.easytrip.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seu.cose.easytrip.Override.MainViewPager;
import com.seu.cose.easytrip.Override.ToolsChoosingFragment;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_tools)
public class ToolsFragment extends BaseAppFragment {

    @ViewInject(R.id.tools_tab)
        private TabLayout mTab;
    @ViewInject(R.id.viewpager_tools)
    private MainViewPager mViewPager ;

    public static final int TAB_SHORT = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);
        initData();

    }

    private void initData() {
        ShortPagerAdapter adapter = new ShortPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(mViewPager);
    }

    private class ShortPagerAdapter extends FragmentPagerAdapter {

        public String[] mTilte;

        public ShortPagerAdapter(FragmentManager fm) {
            super(fm);
            mTilte = getResources().getStringArray(R.array.tab_short_Title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTilte[position];
        }

        @Override
        public BaseAppFragment getItem(int position) {
            BaseAppFragment fragment = ToolsChoosingFragment.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_SHORT;
        }
    }
}
