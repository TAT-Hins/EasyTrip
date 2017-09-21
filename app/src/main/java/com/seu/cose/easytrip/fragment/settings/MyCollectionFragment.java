package com.seu.cose.easytrip.fragment.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.cose.easytrip.Override.CardViewContainer;
import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.adapter.CRecyclerViewAdapter;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_my_collection)
public class MyCollectionFragment extends BaseAppFragment {

    @ViewInject(R.id.collection_recyclerview)
        private RecyclerView mRecyclerView;

    private List<CardViewContainer> cards = new ArrayList<>();
    private int[] mCardImageId = new int[]{R.drawable.card_a, R.drawable.card_b, R.drawable.card_c, R.drawable.card_d, R.drawable.card_e};
    private String[] mCardIntro = new String[]{"李时珍的皮", "今年国庆放8天", "没想到吧", "嘻嘻，SB", "你老婆正吃辣条呢"};
    private CRecyclerViewAdapter crecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);

        /*for (int i=0; i<5; i++){
            cards.add(new CardViewContainer(mCardIntro[i], "作者" + i, mCardImageId[i]));
        }*/

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        crecyclerViewAdapter = new CRecyclerViewAdapter(this.getActivity(), cards);
        mRecyclerView.setAdapter(crecyclerViewAdapter);

    }

}
