package com.seu.cose.xutils3;

import android.app.Application;

import com.seu.cose.xutils3.pojo.UserInfo;

import org.xutils.*;

/**
 * Created by Hins on 2017/9/8,008.
 */

public class EasyTripApplication extends Application {

    private UserInfo userInfo;

    public UserInfo getUserInfo(){
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    public boolean checkUserInfo(){
        return userInfo == null ? false : true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(org.xutils.BuildConfig.DEBUG);
    }

}
