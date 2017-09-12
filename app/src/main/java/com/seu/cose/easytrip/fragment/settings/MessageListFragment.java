package com.seu.cose.easytrip.fragment.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.cose.easytrip.Override.MessageViewAdapter;
import com.seu.cose.easytrip.Override.MessageViewContainer;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_message_list)
public class MessageListFragment extends BaseAppFragment {

    private List<MessageViewContainer> mDatas=new ArrayList<>();
    private MessageViewAdapter recycleAdapter;

    @ViewInject(R.id.messagelist_recyclerview)
        private RecyclerView mRecyclerView;

    private int[] mImageId = new int[]{R.drawable.card_a, R.drawable.card_b, R.drawable.card_c, R.drawable.card_d, R.drawable.card_e};
    private String[] mUserName = new String[]{"李竞1", "李竞2", "李竞3", "李竞4", "李竞5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);

        for (int i=0; i<5; i++){
            mDatas.add(new MessageViewContainer(mUserName[i],mImageId[i]));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MessageListFragment.this.getActivity()));
        recycleAdapter = new MessageViewAdapter(MessageListFragment.this.getActivity(), mDatas);
        //设置Adapter
        mRecyclerView.setAdapter(recycleAdapter);

    }

}
