package com.example.xiaoyi_test_2.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Activity.AddActivity;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.ItemService;
import com.example.xiaoyi_test_2.Utils.SPUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_Item extends Fragment implements View.OnClickListener, ItemService.SendAll{
    private FloatingActionButton btn_float;
    private ImageView imageView;
    private Fragment_ShowItem fragment_showItem;
    private RadioGroup rg_type;
    private ProgressBar loadprogress;
    private TextView tv_all;
    private boolean LogState = false;
    private String username = "xiaoGao";
    private Button btn_book,btn_search;
    private EditText et_search;
    private ImageView head;
    private boolean first=true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frag_item, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        //初始化商品界面
        loadprogress = view.findViewById(R.id.main_show_progress);
        loadprogress.setVisibility(View.INVISIBLE);
        head = view.findViewById(R.id.item_head);
        if (SPUtils.get(getContext(),"head","111").toString().length()>6) {
            Glide.with(getContext()).load(SPUtils.get(getContext(), "head", "http://123.56.137.134/photo_XY/13393111416gp653r2rsgetuzthzdos.jpg")).into(head);
        }
        else {
            head.setImageResource(R.drawable.unuser);
        }
        head.setOnClickListener(this);
        tv_all = view.findViewById(R.id.main_tv_all);
        btn_float = view.findViewById(R.id.main_btn_float);
        btn_float.setOnClickListener(this);
        et_search = view.findViewById(R.id.mian_et_search);
        rg_type = view.findViewById(R.id.main_rd_type);
        btn_book = view.findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);
        btn_search=view.findViewById(R.id.mian_btn_search);
        et_search=view.findViewById(R.id.mian_et_search);
        btn_search.setOnClickListener(this);

        ItemService.getAll(this);
        loadprogress.setVisibility(View.VISIBLE);

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadprogress.setVisibility(View.VISIBLE);
                ItemService.getAll(Fragment_Item.this);

            }
        });
        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                loadprogress.setVisibility(View.VISIBLE);
                ItemService.getType(Fragment_Item.this,((RadioButton) (getActivity().findViewById(rg_type.getCheckedRadioButtonId()))).getText().toString());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_float: {
                Intent intent = new Intent(getContext(), AddActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.btn_book: {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, Fragment_Book.newInstance()).commitAllowingStateLoss();
                break;
            }
            case R.id.item_head: {
                ((MainActivity) getActivity()).drawerLayout.openDrawer(Gravity.LEFT);
                break;
            }
            case R.id.mian_btn_search:{
                if ((!et_search.getText().toString().equals(""))&&et_search.getText().toString()!=null){
                    ItemService.searchKey(Fragment_Item.this,et_search.getText().toString());
                }
                else {
                    Toast.makeText(getContext(),"请输入关键词",Toast.LENGTH_SHORT);
                }
            }
        }

    }

    @Override
    public void sendAll(JSONArray jsonArray) {
        final ArrayList<ItemBean> arrayList;
        if (jsonArray!=null) {
             arrayList= (ArrayList<ItemBean>) jsonArray.toJavaList(ItemBean.class);
        }else {
            arrayList=new ArrayList<>();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadprogress.setVisibility(View.INVISIBLE);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frag, Fragment_ShowItem.newInstance((ArrayList<ItemBean>) arrayList)).commitAllowingStateLoss();

            }
        });
    }

    public interface HeadClick {
        public void headClick();
    }

}
