package com.example.xiaoyi_test_2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Activity.LoginActivity;
import com.example.xiaoyi_test_2.Activity.MainActivity;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;
import com.example.xiaoyi_test_2.CommentToItem.SayToItemClass;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.io.File;
import java.util.zip.Inflater;

public class Fragment_Mysession extends Fragment implements View.OnClickListener {
    private TextView tv_like, tv_likeme, tv_depatch;
    private LinearLayout linearLayout;
    private int like, likeme, depatch;
    private Context context;
    private boolean flag = false;
    private TextView tv_name;
    private ImageView iv_head;
    private Dialog mCameraDialog;
    private int type;
    private String nowusername="defult";
    private Button button_log;
    public Fragment_Mysession() {
//        this.context=context;
    }
    //    public static String getNowusername(){
//        return nowusername;
//    }
    //在activity中设置数字
    public void Set(int like, int likeme, int depatch) {
        this.like = like;
        this.likeme = likeme;
        this.depatch = depatch;
        flag = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view = inflater.inflate(R.layout.layout_frag_mysession, container, false);
        tv_like = view.findViewById(R.id.myfrag_likeone);
        tv_likeme = view.findViewById(R.id.myfrag_likeme);
        tv_depatch = view.findViewById(R.id.myfrag_patch);
        iv_head=view.findViewById(R.id.myfrag_head);
        tv_name=view.findViewById(R.id.frag_tv_name);
        button_log=view.findViewById(R.id.myfrag_log);
        if (Integer.parseInt(SPUtils.get(context,"userid",1).toString())==1) {
            iv_head.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.INVISIBLE);
            button_log.setVisibility(View.VISIBLE);
            button_log.setOnClickListener(this);
        }
        else {
            iv_head.setVisibility(View.VISIBLE);
            if (SPUtils.get(context,"head","1").toString().length()>6) {
                Glide.with(context).load(SPUtils.get(context, "head", "1")).into(iv_head);
            }
            else {
                iv_head.setImageResource(R.drawable.unuser);
            }
            iv_head.setOnClickListener(this);
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(SPUtils.get(context,"username","用户名").toString());
            button_log.setVisibility(View.INVISIBLE);
        }

        linearLayout=view.findViewById(R.id.myfrag_linear_out);
        if (flag) {
            tv_like.setText(like);
            tv_likeme.setText(likeme);
            tv_depatch.setText(depatch);
        }
        return view;
    }

    public void setHead(File file){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myfrag_log:{
                Intent intent=new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.myfrag_head:{
                mCameraDialog = new Dialog(context, R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(
                        R.layout.layout_changeimg, null);
                root.findViewById(R.id.tv_changeimg).setOnClickListener(Fragment_Mysession.this);
                mCameraDialog.setContentView(root);
                Window dialogWindow = mCameraDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setWindowAnimations(R.style.DialogAnimation); // 添加动画
                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                lp.x = 0; // 新位置X坐标
                lp.y = 0; // 新位置Y坐标
                lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
                root.measure(0, 0);
                lp.height = root.getMeasuredHeight();
//              lp.alpha = 9f; // 透明度
                dialogWindow.setAttributes(lp);
                mCameraDialog.show();
                break;
            }
            case R.id.tv_changeimg:{
                mCameraDialog.dismiss();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                AlertDialog alertDialog;
                View view=View.inflate(context,R.layout.layout_headclick,null);
                builder.setView(view);
                alertDialog=builder.create();
                view.findViewById(R.id.changeimg_tv_pz).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                view.findViewById(R.id.changeimg_tv_xc).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 7);
                    }
                });

                alertDialog.show();
                break;
            }
        }
    }
}
