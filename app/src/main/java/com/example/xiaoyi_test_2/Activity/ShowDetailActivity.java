package com.example.xiaoyi_test_2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.example.xiaoyi_test_2.Adapter.SayToItemAdapter;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.CommentToItem.SayToItemClass;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.ItemComponent;
import com.example.xiaoyi_test_2.Service.WantService;
import com.example.xiaoyi_test_2.Utils.PageTransFormer;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

//在fragment_item中点击相应item后来到这个activity展示商品的详情
public class ShowDetailActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, SayToItemClass.sendAdd, WantService.SendSuccWant
        , WantService.SendSuccLike, ItemComponent.SendAboutC {
    private static final String Clas = "ShowDetailActivity-----";
    private ImageView Iv;
    private TextView tv_img, tv_bigtyp, tv_smtyp, tv_posi, tv_describ, tv_price;
    private ImageView iv_return;
    private ViewPager imgviewpager; //这个viewpager用来展示商品的图片
    private ItemBean itemBean;
    private Button btn_want, btn_like, btn_say;
    private Boolean LogState;
    private String username;
    private RecyclerView recyclerView;
    private SayToItemAdapter sayToItemAdapter;
    public ProgressBar progressBar;
    private String[] res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        //传进来的itembean
        itemBean = (ItemBean) getIntent().getExtras().getSerializable("item");

        //登录状态
        LogState = getIntent().getExtras().getBoolean("LogState");

        //username，后期打算直接在初次登陆的时候把username直接存到sharedprefence中
        username = getIntent().getExtras().getString("username");

        initViews();

    }

    private void initViews() {
        progressBar = findViewById(R.id.showde_progress);
        progressBar.setVisibility(View.VISIBLE);
        if (itemBean.getImgpath() != null)
            res = itemBean.getImgpath().split("\\|");
        //这里是展示图片的viewpager初始化
        imgviewpager = findViewById(R.id.shoudetail_viewpag);
        imgviewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (res != null)
                    return res.length;
                else return 0;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView view = new ImageView(ShowDetailActivity.this);
                if (res != null && res[position].length() > 2)
                    Glide.with(ShowDetailActivity.this).load(res[position]).into(view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);

            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        imgviewpager.addOnPageChangeListener(this);
        imgviewpager.setPageMargin(10);
        imgviewpager.setOffscreenPageLimit(3);
        imgviewpager.setPageTransformer(false, new PageTransFormer());

        //这些控件点进去了解
        tv_img = findViewById(R.id.tv_img);
        iv_return = findViewById(R.id.shoudetail_iv_return);
        iv_return.setOnClickListener(this);
        tv_bigtyp = findViewById(R.id.showdet_bigtype);
        tv_smtyp = findViewById(R.id.showdet_smaltype);
        tv_describ = findViewById(R.id.showdetail_describ);
        tv_posi = findViewById(R.id.showde_posi);
        tv_posi.setText(itemBean.getPosi());
        tv_describ.setText(itemBean.getDescribe());
        tv_smtyp.setText(itemBean.getType());
        tv_bigtyp.setText(itemBean.getBigtype());
        tv_price = findViewById(R.id.showdet_price);
        tv_price.setText(itemBean.getMoney());
        btn_want = findViewById(R.id.showde_want);
        btn_want.setOnClickListener(this);
        btn_say = findViewById(R.id.showde_say);
        btn_say.setOnClickListener(this);
        btn_like = findViewById(R.id.btn_like);
        btn_like.setOnClickListener(this);
        recyclerView = findViewById(R.id.showde_recy_sayto);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowDetailActivity.this,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.requestFocus();
        progressBar.setVisibility(View.INVISIBLE);
        ItemComponent.getAllAbout(itemBean.getId(), ShowDetailActivity.this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shoudetail_iv_return: {
                Intent intent = new Intent(ShowDetailActivity.this, MainActivity.class);
                setResult(0, intent);
                finish();
                break;
            }
            case R.id.btn_like: {
                if (!SPUtils.get(ShowDetailActivity.this,"userid",90909).toString().equals("90909")) {
                    //想要时添加想要的人，这里用的是bmob的函数，后续我们还是自己写吧
                    WantService.addLike((String) SPUtils.get(ShowDetailActivity.this, "phone", "111"), itemBean.getId(), ShowDetailActivity.this);
                } else {
                    Intent intent = new Intent(ShowDetailActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
                break;
            }
            case R.id.showde_want: {
                //想要时判断，如果现未在登录状态则提示登录
//                if (!SPUtils.get(ShowDetailActivity.this,"userid",90909).toString().equals("90909")) {
////                    WantService.addWang((String) SPUtils.get(ShowDetailActivity.this, "phone", "13393111416"), itemBean.getId(), ShowDetailActivity.this);
//                    Toast.makeText(this, "已向卖家发送消息！", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(ShowDetailActivity.this, LoginActivity.class);
//                    startActivityForResult(intent, 0);
//                }
//                break;
                Toast.makeText(this, "已向卖家发送消息！", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.showde_say: {

                //点击留言的时候会从下方弹出的输入框
                Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                        R.layout.layout_saysmoetoitem, null);
                SayToItemClass sayToItemClass = new SayToItemClass(root, ShowDetailActivity.this, itemBean, mCameraDialog, this);
//                root = sayToItemClass.bindandset(root);
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
                showKeyboard(root.findViewById(R.id.saytoitem_et));
                break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            LogState = true;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            tv_img.setText("" + (imgviewpager.getCurrentItem() + 1) + "/" + res.length);
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
        }, 998);

    }

    /**
     * 此方法只是关闭软键盘
     * <p>
     * //     * @param et 输入焦点
     */
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //在saytoitemadaper中添加评论评论接口
    @Override
    public void send(SayToItemBean itemBean) {
        sayToItemAdapter.addComment(itemBean);
    }

    @Override
    public void sendSW() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShowDetailActivity.this, "已向卖家发送信息！", Toast.LENGTH_SHORT).show();

            }
        });
        //跳转消息界面
    }

    @Override
    public void sendSL() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShowDetailActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void sendAC(final List<SayToItemBean> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                Log.d(Clas, "1");
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
                        Log.d(Clas, "1111111111111");
                        for (int i = 0; i < list_reply.size(); i++) {
                            Log.d(Clas, "1111111111112");
                            if (list_reply.get(i).size() > 0)
                                if (list_reply.get(i).get(0).getUserid() == bean.getTouerid()) {
                                    list_reply.get(i).add(bean);
                                }
                        }
                    }
                }
                sayToItemAdapter = new SayToItemAdapter(ShowDetailActivity.this, list_coment, list_reply);
                recyclerView.setAdapter(sayToItemAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
       }
}
