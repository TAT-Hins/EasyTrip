package com.seu.cose.easytrip.fragment.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.activity.SettingsActivity;
import com.seu.cose.easytrip.fragment.settings.MessageListFragment;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseAppFragment {

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
    @ViewInject(R.id.quit)
        private LinearLayout quitApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = MineFragment.this.getActivity();
                Activity newActivity;
                Intent intent;
                switch (view.getId()){
                    case R.id.set_photo:
                        newActivity = new SettingsActivity(0);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.article:
                        newActivity = new SettingsActivity(1);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.followee:
                        newActivity = new SettingsActivity(2);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.fans:
                        newActivity = new SettingsActivity(3);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.set_message:
                        newActivity = new SettingsActivity(4);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.friend:
                        newActivity = new SettingsActivity(5);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.collection:
                        newActivity = new SettingsActivity(6);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.settings:
                        newActivity = new SettingsActivity(7);
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                        break;
                    case R.id.about:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MineFragment.this.getActivity())
                                .setTitle("关于RUA团队")
                                .setMessage(getString(R.string.aboutinfo))
                                .setPositiveButton("确定",null);
                        builder.show();
                        break;
                    case R.id.quit:
                        EasyTripApplication app = (EasyTripApplication) activity.getApplication();
                        for(Activity act:app.activities) {
                            act.finish();//显式结束
                        }
                        Toast.makeText(MineFragment.this.getContext(), "退出成功！", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        userSet.setOnClickListener(listener);
        showArticle.setOnClickListener(listener);
        showFollowee.setOnClickListener(listener);
        showFans.setOnClickListener(listener);
        messageSet.setOnClickListener(listener);
        showFriends.setOnClickListener(listener);
        showCollection.setOnClickListener(listener);
        settingsLayout.setOnClickListener(listener);
        aboutLayout.setOnClickListener(listener);
        quitApp.setOnClickListener(listener);

    }

}
