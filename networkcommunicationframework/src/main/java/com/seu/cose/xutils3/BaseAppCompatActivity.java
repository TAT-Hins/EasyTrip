package com.seu.cose.xutils3;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by Hins on 2017/9/8,008.
 */

public class BaseAppCompatActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 仅竖屏显示
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*
          启动当前窗体的注入模式，从而实现对布局资源文件中的交互组件进行自动实例化
          工程模块中的所有Activity需要继承当前BaseAppCompatActivity父类，从而继承视图注入模式
        */
        x.view().inject(this);
    }

}
