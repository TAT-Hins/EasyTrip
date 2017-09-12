package com.seu.cose.easytrip.fragment.settings;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_user_info)
public class UserInfoFragment extends BaseAppFragment {

    @ViewInject(R.id.userinfo_head)
        private LinearLayout headLayout;
    @ViewInject(R.id.userinfo_account)
        private LinearLayout accountLayout;
    @ViewInject(R.id.userinfo_username)
        private LinearLayout usernameLayout;
    @ViewInject(R.id.userinfo_gender)
        private LinearLayout genderLayout;
    @ViewInject(R.id.userinfo_age)
        private LinearLayout ageLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);

    }

}
