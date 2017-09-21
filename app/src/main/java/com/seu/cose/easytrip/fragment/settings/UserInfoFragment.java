package com.seu.cose.easytrip.fragment.settings;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seu.cose.easytrip.Override.FileMethods;
import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

@ContentView(R.layout.fragment_user_info)
public class UserInfoFragment extends BaseAppFragment {

    @ViewInject(R.id.account_userInfo)
    private TextView account;
    @ViewInject(R.id.userName_userInfo)
    private EditText userName;
    @ViewInject(R.id.sex_userInfo)
    private TextView sex;
    @ViewInject(R.id.age_userInfo)
    private EditText age;
    @ViewInject(R.id.userPhotoView)
    private ImageView userPhoto;
    @ViewInject(R.id.address_userInfo)
    private EditText address;

    private static final String TAG = "UserInfoFragment";

    private Intent intent;
    private Bitmap bitmap;
    private String newPhotoUrl;
    private String sexValue;
    private Button confirmButton;
    private static EasyTripApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);
        app = (EasyTripApplication) getActivity().getApplication();

        account.setText(app.getUser().getAccount());
        userName.setHint(app.getUserInfo().getUserName());
        sex.setText(app.getUserInfo().getSex().toString());

        if(app.getUserInfo().getSex()){
            sexValue = "true";
            sex.setText("女");
        }
        else {
            sexValue = "false";
            sex.setText("男");
        }

        address.setHint(app.getUserInfo().getAddress());

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("请选择性别")
                        .setMessage("请选择性别")
                        .setPositiveButton("男", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sexValue = "false";
                                sex.setText("男");
                            }
                        })
                        .setNegativeButton("女", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sexValue = "true";
                                sex.setText("女");
                            }
                        });
                builder.show();
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

        confirmButton = getActivity().findViewById(R.id.confirmModifyButton);
        confirmButton.setVisibility(View.VISIBLE);
        confirmButton.setClickable(true);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        applyModify();
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserPhoto(app.getUser().getId());
            }
        }).start();

    }

    public void getImage() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0) {
            ContentResolver resolver = getActivity().getContentResolver();
            // 获得图片的uri
            final Uri originalUri = data.getData();
            String url = FileMethods.getPath(getContext(), originalUri);
            newPhotoUrl = url;
            Log.i(TAG, originalUri.toString());
            bitmap = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                if (userPhoto.getWidth() < originalBitmap.getWidth()){
                    int bmpWidth = userPhoto.getWidth();
                    bitmap = changeBitmapSize(originalBitmap, bmpWidth);
                } else {
                    bitmap = originalBitmap;
                }

                String fileName = FileMethods.getFileName(url);
                userPhoto.setImageURI(originalUri);
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

    public void applyModify(){
        Map<String, File> params = new HashMap<>();
        params.put("profilePhoto", new File(newPhotoUrl));
        XUtilsTools.UpLoadFile(getResources().getString(R.string.server_url) + "/file/upload", params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result){
                Log.i(TAG, result);
                GsonPaserUtils gsonPaserUtils = new GsonPaserUtils();
                Map<String, String> bean = (Map<String, String>) gsonPaserUtils.convertToObj(result, Map.class);
                if (bean.get("result").equals("1")){
                    Toast.makeText(getContext(), "上传头像成功 ", Toast.LENGTH_LONG).show();

                    Map<String, String> params = new HashMap<String, String>();
                    Log.i(TAG, app.getUser().getId().toString());
                    params.put("id", app.getUser().getId().toString());
                    params.put("userName", userName.getText().toString());
                    params.put("age", age.getText().toString());
                    params.put("sex", sexValue);
                    params.put("address", address.getText().toString());
                    XUtilsTools.Post(getResources().getString(R.string.server_url) + "/user/modifyInfo", params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result){
                            Log.i(TAG, result);
                            GsonPaserUtils gsonPaserUtils = new GsonPaserUtils();
                            Map<String, String> bean = (Map<String, String>) gsonPaserUtils.convertToObj(result, Map.class);
                            if (bean.get("result").equals("1")){
                                Toast.makeText(getContext(), "修改资料成功 ", Toast.LENGTH_LONG).show();
                                // 跳转返回
                                getActivity().onBackPressed();
                            } else {
                                Toast.makeText(getContext(), "修改资料失败！", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(CancelledException cex) {
                            Toast.makeText(getContext(), "修改资料取消！ ", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(getContext(), "修改资料出错！ ", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFinished() {}
                    });
                } else {
                    Toast.makeText(getContext(), "上传头像失败！", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(CancelledException cex) {   }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {   }
            @Override
            public void onFinished() {}
        });

    }

    public void getUserPhoto(int userId){
        try{
            String userPhotoPath = app.getAbsolutePath() + "/EasyTrip/user/" + String.valueOf(userId);
            File userPhotoFile = FileMethods.getDownLoadFile(getResources().getString(R.string.server_url) + "/file/download?id=" + String.valueOf(app.getUser().getId()) + "&type=1", userPhotoPath);
            userPhoto.setImageURI(Uri.fromFile(userPhotoFile));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
