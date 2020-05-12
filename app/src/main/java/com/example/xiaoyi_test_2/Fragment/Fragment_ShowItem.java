package com.example.xiaoyi_test_2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Adapter.ShowItemAdatper;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.R;

import java.util.ArrayList;

public class Fragment_ShowItem extends Fragment {
    private ShowItemAdatper.ShowDetail showDetail;
    public static Fragment_ShowItem newInstance(ArrayList<ItemBean> arrayList) {

        Bundle args = new Bundle();
        args.putSerializable("arr",arrayList);
        Fragment_ShowItem fragment = new Fragment_ShowItem();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_frag_show,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.frag_recy_show);
        recyclerView.setAdapter(new ShowItemAdatper((ArrayList<ItemBean>) getArguments().getSerializable("arr"),getContext(),showDetail));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ShowItemAdatper.ShowDetail){
            this.showDetail= (ShowItemAdatper.ShowDetail) context;
        }else throw new RuntimeException("context没有实现showdetail接口");
    }
}
