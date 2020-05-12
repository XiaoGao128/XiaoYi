package com.example.xiaoyi_test_2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Adapter.SayToItemAdapter;
import com.example.xiaoyi_test_2.Adapter.SocialAdapter;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.CommentToItem.SayToItemClass;
import com.example.xiaoyi_test_2.CommentToItem.SayToSocial;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.Component;
import com.example.xiaoyi_test_2.View.GoodView;

import java.util.ArrayList;
import java.util.List;


public class CompDetailActivity extends AppCompatActivity implements Component.SendAC, SayToSocial.SendAdd {
    private TextView tv_name, tv_time, tv_title, tv_desc, tv_num, tv_say;
    private ImageView im_1, im_2, im_3, im_dz, im_return,im_head;
    private RecyclerView recyclerView;
    private SayToItemAdapter sayToItemAdapter;

    private SocialBean socialBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("----------aaa","5");
        setContentView(R.layout.activity_comp_detail);
        tv_name = findViewById(R.id.cd_name);
        im_head=findViewById(R.id.cd_head);
        socialBean = (SocialBean) getIntent().getExtras().getSerializable("soc");
        tv_time = findViewById(R.id.cd_time);
        tv_title = findViewById(R.id.cd_title);
        tv_desc = findViewById(R.id.cd_desc);
        im_return = findViewById(R.id.cd_return);
        tv_num = findViewById(R.id.cd_im_goodnum);
        tv_say = findViewById(R.id.cd_tv_say);
        im_1 = findViewById(R.id.cd_img_1);
        im_2 = findViewById(R.id.cd_img_2);
        im_3 = findViewById(R.id.cd_img_3);
        im_dz = findViewById(R.id.cd_im_good);
        tv_time = findViewById(R.id.cd_time);
        recyclerView = findViewById(R.id.cd_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Component.getAllAbout(socialBean.getId(), this);
        Glide.with(CompDetailActivity.this).load(socialBean.getHead()).into(im_head);

        tv_time.setText(socialBean.getTime());
        tv_desc.setText(socialBean.getDescrib());
        tv_name.setText(socialBean.getName());
        tv_title.setText(socialBean.getTitle());
        tv_num.setText(socialBean.getGoodnum());
//        String[] imgs = socialBean.getImgpath().split("|");
//        ImageView[] imageView = {im_1, im_2, im_3};
//        for (int i = 0; i < imgs.length; i++) {
//            if (!imgs[i].equals("") && imgs[i] != null) {
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView[i].getLayoutParams();
//                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//                imageView[i].setLayoutParams(layoutParams);
//                Glide.with(CompDetailActivity.this).load(imgs[i]).into(imageView[i]);
//                imageView[i].setVisibility(View.VISIBLE);
//            }
//        }
        Log.d("----------aaa","6");
        im_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });
        tv_say.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击留言的时候会从下方弹出的输入框
                Dialog mCameraDialog = new Dialog(CompDetailActivity.this, R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(CompDetailActivity.this).inflate(
                        R.layout.layout_saysmoetoitem, null);
//                SayToItemClass sayToItemClass = new SayToItemClass(root, CompDetailActivity.this, socialBean, mCameraDialog, this);
//                root = sayToItemClass.bindandset(root);
                SayToSocial sayToSocial = new SayToSocial(root, mCameraDialog, socialBean, CompDetailActivity.this, CompDetailActivity.this);
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
//                showKeyboard(root.findViewById(R.id.saytoitem_et));
            }
        });
        im_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodView goodView = new GoodView(CompDetailActivity.this);
                if (im_dz.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.dz_gray).getConstantState())) {
                    im_dz.setBackground(getResources().getDrawable(R.drawable.dz_pink));
                    goodView.setImage(R.drawable.dz_pink);
                    goodView.show(im_dz);
                    tv_num.setText("" + (Integer.parseInt(tv_num.getText().toString()) + 1));
                    Component.socGood(socialBean.getId(),"1");
                } else {
                    im_dz.setBackground(getResources().getDrawable(R.drawable.dz_gray));
                    goodView.setImage(R.drawable.dz_gray);
                    goodView.show(im_dz);
                    tv_num.setText("" + (Integer.parseInt(tv_num.getText().toString()) - 1));
                    Component.socGood(socialBean.getId(),"0");
                }
            }
        });

    }

    @Override
    public void sendAC(List<SayToItemBean> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //选出此商品的评论后筛选根评论和子回复,查询效率有待提升
                ArrayList<ArrayList<SayToItemBean>> list_reply = new ArrayList<>();
                ArrayList<SayToItemBean> list_coment = new ArrayList<>();
                for (SayToItemBean bean : list) {
                    if (bean.getAbovecomment() == 0) {
                        list_coment.add(bean);
                        list_reply.add(new ArrayList<SayToItemBean>());
                    }
                }
                //评论的回复，评论的回复无tousername有abovecomment
                for (SayToItemBean bean : list) {
                    if (bean.getAbovecomment() != 0) {
                        for (int i = 0; i < list_coment.size(); i++) {
                            if (bean.getAbovecomment() == (list_coment.get(i).getId())) {
                                list_reply.get(i).add(bean);
                            }
                        }
                    }
                }
                //评论回复的回复的回复，有abovecomment有tousername
                for (SayToItemBean bean : list) {
                    if (bean.getUserid() != 0) {
                        for (int i = 0; i < list_reply.size(); i++) {
                            if (list_reply.get(i).size() > 0)
                                if (list_reply.get(i).get(0).getUserid() == bean.getTouerid()) {
                                    list_reply.get(i).add(bean);
                                }
                        }
                    }
                }
                sayToItemAdapter = new SayToItemAdapter(CompDetailActivity.this, list_coment, list_reply);
                recyclerView.setAdapter(sayToItemAdapter);
            }
        });
    }

    @Override
    public void sendAdd(SayToItemBean sayToItemBean) {
        sayToItemAdapter.addComment(sayToItemBean);
    }
}
