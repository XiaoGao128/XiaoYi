package com.example.xiaoyi_test_2.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xiaoyi_test_2.Adapter.MainViewPagerAdapter;
import com.example.xiaoyi_test_2.Adapter.ShowItemAdatper;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.Fragment.Fragment_Item;
import com.example.xiaoyi_test_2.Fragment.Fragment_Mysession;
import com.example.xiaoyi_test_2.Fragment.Fragment_Social;
import com.example.xiaoyi_test_2.Fragment.Fragment_message;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.LogAndRegister;
import com.example.xiaoyi_test_2.Service.ZipService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.jpush.im.android.api.JMessageClient;

//主界面，上面是一个framlayout，最下面是一组radiobutton，点击相应的radiobutton切换到相应的fragment

public class MainActivity extends AppCompatActivity implements Serializable, ShowItemAdatper.ShowDetail, RadioGroup.OnCheckedChangeListener, Fragment_Item.HeadClick, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, ZipService.SendZipd {
    private String username = "游客";
    private Boolean LogState = false;
    private FrameLayout frameLayout;
    private RadioGroup radioGroup;
    public DrawerLayout drawerLayout;
    private BottomNavigationView bottmenu;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private ImageView iv_head;
    private ChangeHead changeHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化bomb
        Bmob.initialize(this, "4268bcc7a8211cfbefeebd79e5d7200a");
        //极光im
        JMessageClient.init(MainActivity.this,true);
//        初始化控件
        initViews();
        getWindow().setEnterTransition(new Fade().setDuration(500));
        //这里我也不太看得懂了
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("username") != null) {
                username = getIntent().getExtras().getString("username");
                LogState = true;
            }
        }

    }

    private void initViews() {
        //初始化界面
       iv_head=findViewById(R.id.fg_left_menu).findViewById(R.id.myfrag_head);
        //这个是有侧滑菜单的布局
        drawerLayout = findViewById(R.id.main_drawerlayout);
        viewPager = findViewById(R.id.main_viewp);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        //显示fragment的framlayout
//        frameLayout=findViewById(R.id.fram_main);
        bottmenu = findViewById(R.id.menu_bottom);
        //设置切换事件
        bottmenu.setOnNavigationItemSelectedListener(this);
        bottmenu.setSelectedItemId(R.id.bottom_mian);
//        //framlayout里最初放的是商品展示的fragment
//        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in,
//                R.anim.slide_left_out,
//                R.anim.slide_left_in,
//                R.anim.slide_right_out).add(R.id.fram_main,Fragment_Item.newInstance(this),"111").commitAllowingStateLoss();
//        getSupportFragmentManager().beginTransaction().add(R.id.fg_left_menu,new Fragment_Mysession(MainActivity.this));
    }


    //ShowItemAdatper中的接口，点击相应的item之后会传到主界面跳转到ShowDetail展示该商品的详情
    @Override
    public void showDetail(ItemBean itemBean) {
        Intent intent = new Intent(this, ShowDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("LogState", LogState);
        bundle.putString("username", username);
        bundle.putSerializable("item", itemBean);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7: {
                if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    c.close();
                    iv_head.setImageURI(Uri.parse(imagePath));
                    ZipService.zipPhoto(MainActivity.this, imagePath, MainActivity.this);
                } else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

    //在Fragment_Item中的接口，点击头像之后，在mainactivity中展开侧滑菜单，即个人信息
    @Override
    public void headClick() {
        drawerLayout.openDrawer(Gravity.LEFT);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_mian: {
                viewPager.setCurrentItem(0);
                break;
            }
            case R.id.bottom_msg: {
                viewPager.setCurrentItem(1);
                break;
            }
            case R.id.bottom_social: {
                viewPager.setCurrentItem(2);
                break;
            }
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottmenu.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottmenu.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void sendZip(File file) {
        LogAndRegister.uploadHead(file,MainActivity.this);

        Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();

    }

    public interface ChangeHead{
        public void changeHead(File file);
    }
}
