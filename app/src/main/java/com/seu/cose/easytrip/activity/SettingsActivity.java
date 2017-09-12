package com.seu.cose.easytrip.activity;

import android.os.Bundle;
import android.view.Window;

import com.seu.cose.easytrip.Override.FragAdapter;
import com.seu.cose.easytrip.Override.MainViewPager;
import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.fragment.settings.MessageListFragment;
import com.seu.cose.easytrip.fragment.settings.MyCollectionFragment;
import com.seu.cose.easytrip.fragment.settings.MyFriendsFragment;
import com.seu.cose.easytrip.fragment.settings.UserInfoFragment;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_settings)
public class SettingsActivity extends BaseAppCompatActivity {

    @ViewInject(R.id.viewpager_settings)
        private MainViewPager vp;

    private static int currentPage = 4;

    public SettingsActivity(){super();}

    public SettingsActivity(int page){
        super();
        this.currentPage = page;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        List<BaseAppFragment> fragmentList = new ArrayList<>();
        switch(currentPage){
            case 0:
                fragmentList.add(new UserInfoFragment());
                setTitle("修改资料");
                break;
            case 1:
                fragmentList.add(new MyFriendsFragment());
                setTitle("我的文章");
                break;
            case 2:
                fragmentList.add(new MyCollectionFragment());
                setTitle("我的关注");
                break;
            case 3:
                fragmentList.add(new UserInfoFragment());
                setTitle("我的粉丝");
                break;
            case 4:
                fragmentList.add(new MessageListFragment());
                setTitle("消息列表");
                break;
            case 5:
                fragmentList.add(new MyFriendsFragment());
                setTitle("我的好友");
                break;
            case 6:
                fragmentList.add(new MyCollectionFragment());
                setTitle("我的收藏");
                break;
            case 7:
                fragmentList.add(new UserInfoFragment());
                setTitle("设置");
                break;
        }

        FragAdapter mAdapter = new FragAdapter(getSupportFragmentManager(), fragmentList);
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(0);
    }
}
