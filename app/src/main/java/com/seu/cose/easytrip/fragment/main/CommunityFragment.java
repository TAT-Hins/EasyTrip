package com.seu.cose.easytrip.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seu.cose.easytrip.Override.CardViewContainer;
import com.seu.cose.easytrip.Override.FileMethods;
import com.seu.cose.easytrip.Override.SpacesItemDecoration;
import com.seu.cose.easytrip.Override.WaterFallAdapter;
import com.seu.cose.easytrip.activity.MainActivity;
import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.fragment.settings.MyArticleFragment;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;
import com.seu.cose.xutils3.pojo.Article;
import com.seu.cose.xutils3.pojo.ImageCardView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.fragment_community)
public class CommunityFragment extends BaseAppFragment {

    private static final String TAG = "CommunityFragment";

    @ViewInject(R.id.recyclerView_community)
        private RecyclerView mRecyclerView;

    private WaterFallAdapter mAdapter;
    private List<ImageCardView> imageCardView = new ArrayList<>();
    private static Article article;
    public static File previewPhoto;
    private static EasyTripApplication app;

    private int[] mCardImageId = new int[]{R.drawable.card_a, R.drawable.card_b, R.drawable.card_c, R.drawable.card_d, R.drawable.card_e};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);
        app = (EasyTripApplication) getActivity().getApplication();
        Log.i(TAG, "onActivityCreated -> MainActivity窗体创建Fragment实例……");

        /*
        for (int i=0; i<5; i++){
            imageCardView.add(new ImageCardView(mCardImageId[i], "第"+ (i+1) + "张"));
            imageCardView.add(new ImageCardView(mCardImageId[i], "第"+ (2+i*2) + "张"));
        }
        */

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new WaterFallAdapter(this.getActivity(), imageCardView);
        mRecyclerView.setAdapter(mAdapter);
        getArticle(getContext(), 24);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

    }

    private void getArticle(final Context context, final int articleId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(articleId));
        XUtilsTools.Post(getResources().getString(R.string.server_url) + "/article/getArticle", params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, result);
                Gson gson = new Gson();
                try{
                    article = gson.fromJson(result, Article.class);
                } catch (Exception e){
                    e.printStackTrace();
                }
                if (article != null) {
                    Toast.makeText(getContext(), "获取文章成功！", Toast.LENGTH_LONG).show();
                    final Article spe = article;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String filePath = app.getAbsolutePath() + "/EasyTrip/article/" + String.valueOf(articleId);
                            try{
                                previewPhoto = FileMethods.getDownLoadFile(getResources().getString(R.string.server_url) + "/file/download?id=" + spe.getId().toString() + "&type=4", filePath);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override public void run() {
                            mAdapter.addItem(imageCardView);
                            mRecyclerView.notifyAll();
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "注册失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {}

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {}

            @Override
            public void onFinished() {}

        });
    }

}
