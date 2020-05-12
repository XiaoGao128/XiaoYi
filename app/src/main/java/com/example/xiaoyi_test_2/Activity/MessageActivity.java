package com.example.xiaoyi_test_2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xiaoyi_test_2.Adapter.ChatAdapter;
import com.example.xiaoyi_test_2.Bean.ChatBean;
import com.example.xiaoyi_test_2.R;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

//聊天activity，只是简单写了个界面
public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button sayButton;
    private EditText sayEt;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Conversation.createSingleConversation("13393111416");
        initViews();
    }

    private void initViews() {
        recyclerView=findViewById(R.id.recy_chat);
        ArrayList<ChatBean> arrayList=new ArrayList<>();
        ChatBean chatBean=new ChatBean();
        chatBean.setType(ChatBean.TYPE_YOU);
        chatBean.setContent("小高");
        chatBean.setHead(R.drawable.xingy);
        arrayList.add(chatBean);
        chatBean=new ChatBean();
        chatBean.setType(ChatBean.TYPE_YOU);
        chatBean.setContent("我想买你的劳力士");
        chatBean.setHead(R.drawable.xingy);
        arrayList.add(chatBean);
        chatAdapter=new ChatAdapter(arrayList,MessageActivity.this);
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));

        sayEt=findViewById(R.id.sayto_et);
        sayButton=findViewById(R.id.sayto_btn);
        sayButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sayto_btn:{
                if (sayEt.getText().toString()!=""||sayEt.getText().toString()!=null){
                    ChatBean chatBean=new ChatBean();
                    chatBean.setType(ChatBean.TYPE_ME);
                    chatBean.setContent(sayEt.getText().toString());
                    chatBean.setName("18软工小高");
                    chatBean.setHead(R.drawable.mengt);
                    chatAdapter.addMessage(chatBean);
                    JMessageClient.createSingleTextMessage("13393111416",sayEt.getText().toString());
                    sayEt.setText("");
                }
                break;
            }
        }
    }
}
