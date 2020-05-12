package com.example.xiaoyi_test_2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyi_test_2.Adapter.MsgAdapter;
import com.example.xiaoyi_test_2.Bean.MsgBean;
import com.example.xiaoyi_test_2.R;

import java.util.ArrayList;

public class Fragment_message extends Fragment {
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_message,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView=view.findViewById(R.id.msg_recy);

        recyclerView.setAdapter(new MsgAdapter(getContext(),new ArrayList<MsgBean>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
