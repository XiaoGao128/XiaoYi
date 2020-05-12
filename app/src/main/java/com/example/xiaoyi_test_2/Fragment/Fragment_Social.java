package com.example.xiaoyi_test_2.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.Activity.AddSocialActivity;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Adapter.SocialAdapter;
import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.AddComp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Social extends Fragment implements View.OnClickListener, AddComp.SendSC {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_frag_social,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        recyclerView=view.findViewById(R.id.social_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AddComp.getComp(this);

        floatingActionButton=view.findViewById(R.id.social_float);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.social_float:{
                Intent intent=new Intent(getContext(),AddSocialActivity.class);
                startActivityForResult(intent,0);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void sendSC(List<SocialBean> list) {
        ((MainActivity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new SocialAdapter(getContext(), (ArrayList<SocialBean>) list));
            }
        });


    }
}
