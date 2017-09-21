package com.seu.cose.xutils3;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.baidu.mapapi.SDKInitializer;
import com.seu.cose.xutils3.pojo.UserInfo;
import com.seu.cose.xutils3.pojo.UserLogin;

import org.xutils.*;

import java.util.ArrayList;

/**
 * Created by Hins on 2017/9/8,008.
 */

public class EasyTripApplication extends Application {

    private UserLogin user;
    private UserInfo userInfo;

    public ArrayList<Activity> activities = new ArrayList<>();

    public UserLogin getUser(){
        return user;
    }
    public UserInfo getUserInfo(){ return userInfo; }

    public void setUser(UserLogin user){
        this.user = user;
    }

    public void setUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(org.xutils.BuildConfig.DEBUG);
        SDKInitializer.initialize(getApplicationContext());
    }

    public String getAbsolutePath(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else return null;
    }

}
