package com.example.xiaoyi_test_2.CommentToItem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xiaoyi_test_2.Activity.LoginActivity;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SayToSocial {
    private LinearLayout root;
    private Dialog dialog;
    private SocialBean socialBean;
    private Context context;
    private Button btn_send;
    private EditText et;
    private SendAdd sendAdd;
    public SayToSocial(LinearLayout root, Dialog mdialog, SocialBean socialBean, Context context,SendAdd sendAdd) {
        this.root = root;
        this.dialog = mdialog;
        this.socialBean = socialBean;
        this.context = context;
        this.sendAdd=sendAdd;
        bindViews(root);

    }

    private void bindViews(LinearLayout root) {
        btn_send = root.findViewById(R.id.saytoitem_btn);
        et = root.findViewById(R.id.saytoitem_et);
        showKeyboard(et);
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
                    sayToItemBean.setItemid(socialBean.getId());
                    sayToItemBean.savesoc(context);
                    sendAdd.sendAdd(sayToItemBean);
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
    //展示软键盘
    public static void showKeyboard(final View view) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    view.requestFocus();
                    imm.showSoftInput(view, 0);
                }
            }
        }, 1998);

    }
    public interface SendAdd{
        public void sendAdd(SayToItemBean sayToItemBean);
    }
}
