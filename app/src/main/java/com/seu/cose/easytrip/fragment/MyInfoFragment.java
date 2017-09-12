package com.seu.cose.easytrip.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_myinfo)
public class MyInfoFragment extends BaseAppFragment {

    @ViewInject(R.id.set_photo)
    private LinearLayout userSet;
    @ViewInject(R.id.article)
    private LinearLayout showArticle;
    @ViewInject(R.id.followee)
    private LinearLayout showFollowee;
    @ViewInject(R.id.fans)
    private LinearLayout showFans;
    @ViewInject(R.id.set_message)
    private LinearLayout messageSet;
    @ViewInject(R.id.friend)
    private LinearLayout showFriends;
    @ViewInject(R.id.collection)
    private LinearLayout showCollection;
    @ViewInject(R.id.settings)
    private LinearLayout settingsLayout;
    @ViewInject(R.id.about)
    private LinearLayout aboutLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);

        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoFragment.this.getActivity())
                        .setTitle("关于RUA团队")
                        .setMessage(getString(R.string.aboutinfo))
                        .setPositiveButton("确定",null);
                builder.show();
            }
        });

    }

    public void quitApp(){
        onStop();
    }

}
