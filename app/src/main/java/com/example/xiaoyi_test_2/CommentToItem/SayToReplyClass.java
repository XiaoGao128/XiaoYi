package com.example.xiaoyi_test_2.CommentToItem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.Activity.LoginActivity;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SayToReplyClass {
    private LinearLayout root;
    private Context context;
    private Button btn_send;
    private EditText et;
    private ImageView face;
    private SayToItemBean itemBean;
    private Dialog dialog;
    private SayToReplyClass.sendAdd sendAdd;
    private int posi;

    public SayToReplyClass(LinearLayout root, Context context, SayToItemBean itemBean, Dialog mdialog, sendAdd sendAdd, int posi) {
        this.root = root;
        this.context = context;
        this.itemBean = itemBean;
        this.dialog = mdialog;
        this.posi = posi;
        this.sendAdd = sendAdd;
        bindViews(root);
    }

    private void bindViews(LinearLayout root) {
        btn_send = root.findViewById(R.id.saytoitem_btn);
        et = root.findViewById(R.id.saytoitem_et);
        face = root.findViewById(R.id.saytoitem_face);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintKbTwo();
                if (SPUtils.get(context, "username", "111") == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    ((ShowDetailActivity) context).startActivityForResult(intent, 0);
                    Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                } else {
                    SayToItemBean sayToItemBean = new SayToItemBean();
                    sayToItemBean.setUserid((Integer) SPUtils.get(context, "userid", 1));
                    sayToItemBean.setSaywhat(et.getText().toString());
                    sayToItemBean.setTouerid(itemBean.getUserid());
                    sayToItemBean.setItemid(itemBean.getItemid());
                    sayToItemBean.setAbovecomment(itemBean.getId());
                    sayToItemBean.save(context);
                    sendAdd.send(sayToItemBean, posi);
                }
                dialog.dismiss();
            }
        });
    }

    //隐藏键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && dialog.getCurrentFocus() != null) {
            if (dialog.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public interface sendAdd {
        public void send(SayToItemBean itemBean, int posi);
    }
}
