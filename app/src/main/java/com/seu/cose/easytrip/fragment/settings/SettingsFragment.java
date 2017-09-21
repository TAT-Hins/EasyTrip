package com.seu.cose.easytrip.fragment.settings;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seu.cose.easytrip.Override.GsonPaserUtils;
import com.seu.cose.easytrip.R;
import com.seu.cose.easytrip.activity.LoginActivity;
import com.seu.cose.xutils3.BaseAppFragment;
import com.seu.cose.xutils3.EasyTripApplication;
import com.seu.cose.xutils3.XUtilsTools;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.fragment_settings)
public class SettingsFragment extends BaseAppFragment {

    @ViewInject(R.id.change_psd)
    private LinearLayout changePswd;
    @ViewInject(R.id.change_account)
    private  LinearLayout changeAccount;
    @ViewInject(R.id.voice_close)
    private LinearLayout voiceClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void  onActivityCreated(@Nullable Bundle saveInstanceState) {

        super.onActivityCreated(saveInstanceState);
        final EasyTripApplication app = (EasyTripApplication) getActivity().getApplication();
        final EditText tv = new EditText(getContext());

        changePswd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("修改密码")
                        .setMessage(getString(R.string.psdChange))
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                changePswdToServer(app.getUser().getId().toString(), tv.getText().toString());
                            }
                        })
                        .setView(tv);
                builder.show();
            }
        });

        changeAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void changePswdToServer(String id,String password){
        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        params.put("password",password);
        XUtilsTools.Post(getResources().getString(R.string.server_url)+"/user/modifyPassword", params,
                new Callback.CommonCallback<String>(){
                    @Override
                    public void onSuccess(String result) {
                        GsonPaserUtils gsonPaserUtils = new GsonPaserUtils();
                        int changedpsdbean= (int) gsonPaserUtils.convertToObj(result, Integer.class);
                        if(changedpsdbean!=0){
                            Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(), "修改失败", Toast.LENGTH_LONG).show();
                        }
                        getActivity().onBackPressed();
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {   }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {   }
                    @Override
                    public void onFinished() {}

                });


    }

}
