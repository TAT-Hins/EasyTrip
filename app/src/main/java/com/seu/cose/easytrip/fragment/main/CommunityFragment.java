package com.seu.cose.easytrip.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.cose.easytrip.Override.SpacesItemDecoration;
import com.seu.cose.easytrip.Override.WaterFallAdapter;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.pojo.ImageCardView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_community)
public class CommunityFragment extends BaseAppFragment {

    private static final String TAG = "CommunityFragment";

    @ViewInject(R.id.recyclerView_community)
        private RecyclerView mRecyclerView;

    private WaterFallAdapter mAdapter;
    private List<ImageCardView> imageCardView = new ArrayList<>();

    private int[] mImageId = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
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
        Log.i(TAG, "onActivityCreated -> MainActivity窗体创建Fragment实例……");

        for (int i=0; i<5; i++){
            imageCardView.add(new ImageCardView(mImageId[i], "第"+ (i+1) + "张"));
            imageCardView.add(new ImageCardView(mCardImageId[i], "第"+ (2+i*2) + "张"));
        }

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new WaterFallAdapter(this.getActivity(), imageCardView);
        mRecyclerView.setAdapter(mAdapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

    }


}
