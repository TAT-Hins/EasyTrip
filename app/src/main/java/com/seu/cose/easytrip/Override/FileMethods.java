package com.seu.cose.easytrip.Override;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.XUtilsTools;

import org.xutils.common.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Hins on 2017/9/20,020.
 */

public class FileMethods {

    private static final String TAG = "FileMethods";

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getFileName(String pathandname){

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1) ;
        }else{
            return null;
        }

    }

    public static String getFileParentPath(String pathandname){

        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(0, start) ;
        }else{
            return null;
        }

    }

    public static File getDownLoadFile(String pathStream, String filepath) throws Exception {
        URL url = new URL(pathStream);//
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//从网络获得链接
        connection.setRequestMethod("GET");//获得
        connection.setConnectTimeout(5000);//设置超时时间为5s
        connection.setRequestProperty("api-version", "1.0");
        if (connection.getResponseCode() == 200)//检测是否正常返回数据请求 详情参照http协议
        {
            InputStream is = connection.getInputStream();//获得输入流
            File fileParentPath = new File(filepath);
            if(!fileParentPath.exists())
                fileParentPath.mkdirs();
            String fileName = connection.getHeaderField("Content-Disposition").toString().replace("attachment;fileName=", "");
            File file = new File(filepath, fileName);//新建一个file文件
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);//对应文件建立输出流
            byte[] buffer = new byte[1024];//新建缓存  用来存储 从网络读取数据 再写入文件
            int len = 0;
            while ((len = is.read(buffer)) != -1) {//当没有读到最后的时候
                fos.write(buffer, 0, len);//将缓存中的存储的文件流file文件
            }
            fos.flush();//将缓存中的写入file
            fos.close();
            is.close();//将输入流 输出流关闭
            Log.e("========", "++++++file++++" + file);
            return file;
        } else {
            Log.e("======", "-----getResponseCode----" + connection.getResponseCode());
            return null;
        }
    }

    public static File getDownLoadTXT(String pathStream, String filepath) throws Exception {
        URL url = new URL(pathStream);//
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//从网络获得链接
        connection.setRequestMethod("GET");//获得
        connection.setConnectTimeout(5000);//设置超时时间为5s
        connection.setRequestProperty("api-version", "1.0");
        if (connection.getResponseCode() == 200)//检测是否正常返回数据请求 详情参照http协议
        {
            InputStream is = connection.getInputStream();//获得输入流
            File fileParentPath = new File(filepath);
            if(!fileParentPath.exists())
                fileParentPath.mkdirs();
            String fileName = "text.txt";
            File file = new File(filepath, fileName);//新建一个file文件
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);//对应文件建立输出流
            byte[] buffer = new byte[1024];//新建缓存  用来存储 从网络读取数据 再写入文件
            int len = 0;
            while ((len = is.read(buffer)) != -1) {//当没有读到最后的时候
                fos.write(buffer, 0, len);//将缓存中的存储的文件流file文件
            }
            fos.flush();//将缓存中的写入file
            fos.close();
            is.close();//将输入流 输出流关闭
            Log.e("========", "++++++file++++" + file);
            return file;
        } else {
            Log.e("======", "-----getResponseCode----" + connection.getResponseCode());
            return null;
        }
    }

}
