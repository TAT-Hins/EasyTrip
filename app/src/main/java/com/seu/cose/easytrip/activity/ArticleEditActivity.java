package com.seu.cose.easytrip.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seu.cose.easytrip.Override.FileMethods;
import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppCompatActivity;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_article_edit)
public class ArticleEditActivity extends BaseAppCompatActivity {

    @ViewInject(R.id.titleSet_article)
        private EditText titleSet;
    @ViewInject(R.id.contentSet_article)
        private EditText contentSet;
    @ViewInject(R.id.imageAddButton_article)
        private ImageButton imageAdd;
    @ViewInject(R.id.labelOneSet_article)
        private EditText labelOne;
    @ViewInject(R.id.labelTwoSet_article)
        private EditText labelTwo;
    @ViewInject(R.id.labelThreeSet_article)
        private EditText labelThree;
    @ViewInject(R.id.toolbar_articleEdit)
        private Toolbar toolbar;
    @ViewInject(R.id.sendArticleButton)
        private Button sendArticle;

    private Intent intent;
    private Bitmap bitmap;
    private List<String> images = new ArrayList<>();
    private List<String> contents = new ArrayList<>();
    private static final String TAG = "ArticleEditActivity";
    private static EasyTripApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (EasyTripApplication) getApplication();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //为添加图片按钮设置监听
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

        //为发送文章按钮设置监听
        sendArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyArticle();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getImage() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0) {
            ContentResolver resolver = getContentResolver();
            // 获得图片的uri
            final Uri originalUri = data.getData();
            String url = FileMethods.getPath(getApplicationContext(), originalUri);
            images.add(url);
            Log.i(TAG, originalUri.toString());
            bitmap = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                if (contentSet.getWidth() < originalBitmap.getWidth()){
                    int bmpWidth = contentSet.getWidth();
                    bitmap = changeBitmapSize(originalBitmap, bmpWidth);
                } else {
                    bitmap = originalBitmap;
                }
                // 将原始图片的bitmap转换为文件
                String fileName = FileMethods.getFileName(url);
                insertPic(bitmap, fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private Bitmap changeBitmapSize(Bitmap bitmap, int bmpWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.e("width","width:"+width);
        Log.e("height","height:"+height);
        //设置想要的大小
        int newWidth = bmpWidth;

        //计算压缩的比率
        float scaleWidth = ((float)newWidth) / width;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleWidth);

        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        bitmap.getWidth();
        bitmap.getHeight();
        Log.e("newWidth","newWidth"+bitmap.getWidth());
        Log.e("newHeight","newHeight"+bitmap.getHeight());
        return bitmap;
    }

    private void insertPic(Bitmap bmp, String url){
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(ArticleEditActivity.this, bmp);
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        String tempUrl = "@@" + url + "##";
        SpannableString spannableString = new SpannableString(tempUrl);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        int index = contentSet.getSelectionStart(); // 获取光标所在位置
        Editable edit_text = contentSet.getEditableText();

        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }

        System.out.println("插入的图片：" + spannableString.toString());

    }

    public File writeFileSdcardFile(String write_str) {
        File file = null;
        try{
            File fileDir = new File(app.getAbsolutePath(), "/EasyTrip/temp/");
            if (!fileDir.exists())
                fileDir.mkdirs();
            file = new File(fileDir, "text.txt");
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file);
            byte [] bytes = write_str.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onPostResume() { super.onPostResume();}

    private void applyArticle(){

        //文本、图片内容上传
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, File> params = new HashMap<>();
                contents.add(writeFileSdcardFile(contentSet.getText().toString()).getAbsolutePath());
                for (String image: images)
                    contents.add(image);
                for (String filePath : contents)
                    params.put(FileMethods.getFileName(filePath), new File(filePath));
                XUtilsTools.UpLoadFile(getResources().getString(R.string.server_url) + "/file/upload", params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(getApplicationContext(), "文件上传成功！", Toast.LENGTH_LONG).show();
                        Log.i(TAG, result);
                        GsonPaserUtils gsonPaserUtils = new GsonPaserUtils();
                        Map<String, String> bean = (Map<String, String>) gsonPaserUtils.convertToObj(result, Map.class);
                        if (bean.get("result").equals("1")){
                            //文章信息上传
                            Map<String, String> params = new HashMap<>();
                            params.put("authorId", app.getUser().getId().toString());
                            params.put("title", titleSet.getText().toString());
                            XUtilsTools.Post(getResources().getString(R.string.server_url) + "/article/addArticle", params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    Log.i(TAG,result);
                                    Gson gson = new Gson();
                                    Map<String, String> resultBean = gson.fromJson(result, HashMap.class);
                                    if(resultBean.get("id") != null){
                                        //EasyTripApplication application = (EasyTripApplication)getApplication();
                                        //application.setLoginUser(loginUsers);
                                        Toast.makeText(getApplicationContext(), "发送成功！", Toast.LENGTH_LONG).show();
                                        // 跳转返回
                                        onBackPressed();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {   }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {   }

                                @Override
                                public void onFinished() { }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "读取文章出错！", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(getApplicationContext(), "文件上传终止！", Toast.LENGTH_LONG).show();
                        return;
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(getApplicationContext(), "文件上传出错！", Toast.LENGTH_LONG).show();
                        return;
                    }

                    @Override
                    public void onFinished() {}
                });
            }
        }).start();

    }

}
