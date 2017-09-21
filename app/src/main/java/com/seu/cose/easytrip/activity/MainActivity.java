package com.seu.cose.easytrip.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
//import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seu.cose.easytrip.Override.CardViewContainer;
import com.seu.cose.easytrip.Override.FileMethods;
import com.seu.cose.easytrip.adapter.FragAdapter;
import com.seu.cose.easytrip.Override.BottomNavigationViewHelper;
import com.seu.cose.easytrip.Override.MainViewPager;
import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.fragment.main.CommunityFragment;
import com.seu.cose.easytrip.fragment.main.HomeFragment;
import com.seu.cose.easytrip.fragment.main.MineFragment;
import com.seu.cose.easytrip.fragment.main.ToolsFragment;
import com.seu.cose.easytrip.fragment.settings.MyArticleFragment;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;
import com.seu.cose.xutils3.pojo.Article;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @ViewInject(value = R.id.viewpager_main)
        private MainViewPager vp;
    //private MenuItem menuItem;

    private boolean mIsExit;
    private ImageView photoImageView;
    private SearchView searchView;
    private EasyTripApplication app;
    private static File previewPhoto;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (EasyTripApplication) getApplication();

        List<BaseAppFragment> fragmentsList = new ArrayList<>();
        //添加Fragment到集合
        fragmentsList.add(new HomeFragment());
        fragmentsList.add(new CommunityFragment());
        fragmentsList.add(new BaseAppFragment());
        fragmentsList.add(new ToolsFragment());
        fragmentsList.add(new MineFragment());

        //设置适配器
        FragAdapter fragAdapter = new FragAdapter(getSupportFragmentManager(), fragmentsList);
        vp.setAdapter(fragAdapter);
        vp.setCurrentItem(0);
        vp.setOffscreenPageLimit(4);

        //底部导航栏初始化
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton post = (FloatingActionButton) findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ArticleEditActivity.class);
                startActivity(intent);
            }
        });

        vp.addOnPageChangeListener(new MainViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                /*
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
                */

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //初始化侧边栏视图并传入数据
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView usernameTextView = navigationView.getHeaderView(0).findViewById(R.id.userName_drawer);
        usernameTextView.setText(app.getUserInfo().getUserName());
        photoImageView = navigationView.getHeaderView(0).findViewById(R.id.imageView_drawer);
        ColorStateList csl = getResources().getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        navigationView.setNavigationItemSelectedListener(this);

        getUserPhoto(app.getUser().getId());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vp.setCurrentItem(0);
                    return true;
                case R.id.navigation_community:
                    vp.setCurrentItem(1);
                    return true;
                case R.id.navigation_blank:
                    vp.setCurrentItem(2);
                    return true;
                case R.id.navigation_tools:
                    vp.setCurrentItem(3);
                    return true;
                case R.id.navigation_myinfo:
                    vp.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//指定Toolbar上的视图文件
        MenuItem searchItem = (MenuItem) menu.findItem(R.id.search_toolbar_home);
        View view = MenuItemCompat.getActionView(searchItem);
        if (view != null){
            searchView = (SearchView) view;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    Toast.makeText(MainActivity.this, "Submit" + query, Toast.LENGTH_SHORT).show();
                    searchView.clearFocus();
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }
        return true;
    }


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Activity newActivity;
        Intent intent;
        switch (item.getItemId()){
            case R.id.nav_collection:
                newActivity = new SettingsActivity(6);
                intent = new Intent(this, newActivity.getClass());
                startActivity(intent);
                break;
            case R.id.nav_personal:
                newActivity = new SettingsActivity(0);
                intent = new Intent(this, newActivity.getClass());
                startActivity(intent);
                break;
            case R.id.nav_setting:
                newActivity = new SettingsActivity(7);
                intent = new Intent(this, newActivity.getClass());
                startActivity(intent);
                break;
            case R.id.nav_message:
                newActivity = new SettingsActivity(4);
                intent = new Intent(this, newActivity.getClass());
                startActivity(intent);
                break;
            case R.id.nav_quit:
                for(Activity act:app.activities) {
                    act.finish();//显式结束
                }
                Toast.makeText(getApplicationContext(), "退出成功！", Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    /**
     * 双击返回键退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                for(Activity act:app.activities) {
                    act.finish();//显式结束
                }
                Toast.makeText(getApplicationContext(), "退出成功！", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void getUserPhoto(final int userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userPhotoPath = app.getAbsolutePath() + "/EasyTrip/user/" + String.valueOf(userId);
                    File userPhotoFile = FileMethods.getDownLoadFile(getResources().getString(R.string.server_url) + "/file/download?id=" + String.valueOf(app.getUser().getId()) + "&type=1", userPhotoPath);
                    photoImageView.setImageURI(Uri.fromFile(userPhotoFile));
                    previewPhoto = userPhotoFile;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
