package com.seu.cose.easytrip.fragment.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppFragment;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.fragment_my_collection)
public class MyCollectionFragment extends BaseAppFragment {

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
