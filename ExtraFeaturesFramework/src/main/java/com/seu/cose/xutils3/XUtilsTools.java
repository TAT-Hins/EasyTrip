package com.seu.cose.xutils3;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

public class XUtilsTools {

    private static final String TAG = "XutilsTools";

    /**
     * 发送get请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Get(String url, Map<String,String> map, Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        if(null!=map){
            for(Map.Entry<String, String> entry : map.entrySet()){
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, Map<String, String> map, Callback.CommonCallback<T> callback){
        RequestParams params = new RequestParams(url);
        Log.i(TAG, "POST请求地址:> " + url);
        if(null != map){
            for(Map.Entry<String, String> entry : map.entrySet()){
                Log.i(TAG, "POST请求参数:> " + entry.getKey() + ":> " + entry.getValue());
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
            params.setAsJsonContent(true);
        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 上传文件
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(String url, Map<String,File> map, Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        // 设置Multipart参数类型
        params.setMultipart(true);
        if(null!=map){
            for(Map.Entry<String, File> entry : map.entrySet()){
                // 这里使用addBodyParameter方法
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback){
        RequestParams params=new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    public static void LoadImage(String imageUrl){
    }

}
