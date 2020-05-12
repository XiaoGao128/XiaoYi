package com.example.xiaoyi_test_2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.xiaoyi_test_2.Adapter.ListViewAdapter;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_clock;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_cloth;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_huaz;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_page;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_sentic;
import com.example.xiaoyi_test_2.Fragment.Fragment_type_study;
import com.example.xiaoyi_test_2.R;

public class AddTypeActivity extends AppCompatActivity implements ListViewAdapter.sendClick,Fragment_type_study.send,
        Fragment_type_cloth.send,Fragment_type_clock.send,Fragment_type_huaz.send,Fragment_type_page.send,Fragment_type_sentic.send{
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private FrameLayout frameLayout;
    boolean isfirstreplace = true;
    private String[] typee;
    private Fragment[] fragments;
    private String[] type = {"学习用品", "衣物", "箱包", "数码", "化妆品", "其他"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_type);
        initView();
    }

    private void initView() {
        frameLayout = findViewById(R.id.type_frame);
        typee = type;
        fragments = new Fragment[]{new Fragment_type_study(), new Fragment_type_cloth(),new Fragment_type_page(),new Fragment_type_sentic(),new Fragment_type_huaz(),new Fragment_type_clock()};
        listViewAdapter = new ListViewAdapter(type, this, this);
        listView = findViewById(R.id.type_list);
        listView.setAdapter(listViewAdapter);
    }

    @Override
    public void sendClick(int position) {
        if (isfirstreplace) {
            getSupportFragmentManager().beginTransaction().add(R.id.type_frame, fragments[position], ""+position).commitAllowingStateLoss();
            isfirstreplace=false;
        } else
            getSupportFragmentManager().beginTransaction().replace(R.id.type_frame,fragments[position], ""+position).commitAllowingStateLoss();
    }

    //返回键时返回结果
    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        String s="全部分类";
        bundle.putString("type",s);
        bundle.putString("bigtype","all");
        intent.putExtras(bundle);
        setResult(0,intent);
        finish();
    }

    @Override
    public void send(String btext,String text) {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("btype",btext);
        bundle.putString("type",text);
        intent.putExtras(bundle);
        setResult(0,intent);
        finish();
    }
}
