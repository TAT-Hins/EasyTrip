package com.seu.cose.easytrip.activity;

import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.seu.cose.easytrip.Override.ChatAdapter;
import com.seu.cose.xutils3.pojo.PersonChat;
import com.seu.cose.easytrip.R;
import com.seu.cose.xutils3.BaseAppCompatActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseAppCompatActivity {

    private ChatAdapter chatAdapter;
    /**
     * 声明ListView
     */
    @ViewInject(R.id.lv_chat_dialog)
        private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<PersonChat> personChats = new ArrayList<PersonChat>();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(personChats.size());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_chat);

        /**
         * 虚拟4条发送方的消息
         */
        for (int i = 0; i <= 3; i++) {
            PersonChat personChat = new PersonChat();
            personChat.setMeSend(false);
            personChats.add(personChat);
        }
        Button btn_chat_message_send = (Button) findViewById(R.id.btn_chat_message_send);
        Button button3 = (Button) findViewById(R.id.button3);
        final EditText et_chat_message = (EditText) findViewById(R.id.et_chat_message);
        /**
         *setAdapter
         */
        chatAdapter = new ChatAdapter(this.getApplicationContext(), personChats);
        lv_chat_dialog.setAdapter(chatAdapter);
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonChat personChat = new PersonChat();
                //代表自己发送
                personChat.setMeSend(true);
                //得到发送内容
                personChat.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(ChatActivity.this, "发送内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonChat personChat = new PersonChat();
                //代表自己发送
                personChat.setMeSend(false);
                //得到发送内容
                personChat.setChatMessage(et_chat_message.getText().toString());
                //加入集合
                personChats.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });
    }

}
