package com.seu.cose.easytrip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;
import com.seu.cose.xutils3.pojo.Article;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_article)
public class ArticleActivity extends BaseAppCompatActivity {

    @ViewInject(R.id.titleShow_article)
        private TextView titleShow;
    @ViewInject(R.id.titleSet_article)
        private EditText titleSet;
    @ViewInject(R.id.contentShow_article)
        private TextView contentShow;
    @ViewInject(R.id.contentSet_article)
        private EditText contentSet;

    private Intent intent;
    private ImageSpan imageSpan;
    private static final String TAG = "ArticleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getImage() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onPostResume() { super.onPostResume();}

    /*
    private void applyArticle(Article article){
        Map<String, String> params = new HashMap<>();
        params.put("password", password);
        params.put("account", name);
        XUtilsTools.Post(getResources().getString(R.string.server_url) + "/userLogin/login", params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG,result);
                GsonPaserUtils gsonPaserUtils = new GsonPaserUtils();
                UserLogin userBean = (UserLogin) gsonPaserUtils.convertToObj(result, UserLogin.class);
                if(userBean != null){
                    //EasyTripApplication application = (EasyTripApplication)getApplication();
                    //application.setLoginUser(loginUsers);
                    Toast.makeText(getApplicationContext(), "发送成功！", Toast.LENGTH_LONG).show();
                    // 跳转返回
                    Intent intent = new Intent(ArticleActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {   }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {   }

            @Override
            public void onFinished() {

            }
        });
    }*/

}
