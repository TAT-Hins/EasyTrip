package com.seu.cose.easytrip.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.activity.MapActivity;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.fragment_tools)
public class ToolsFragment extends BaseAppFragment {

    @ViewInject(R.id.tools_map)
        private LinearLayout map;

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
                Activity activity = ToolsFragment.this.getActivity();
                Activity newActivity;
                Intent intent;
                switch (view.getId()){
                    case R.id.tools_map:
                        newActivity = new MapActivity();
                        intent = new Intent(activity, newActivity.getClass());
                        startActivity(intent);
                }
            }
        };

        map.setOnClickListener(listener);

    }
}
