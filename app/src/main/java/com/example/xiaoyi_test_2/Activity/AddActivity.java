package com.example.xiaoyi_test_2.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.ItemService;
import com.example.xiaoyi_test_2.Service.ZipService;
import com.example.xiaoyi_test_2.Utils.AutoAskPermission;
import com.example.xiaoyi_test_2.Utils.BitmapUtils;
import com.example.xiaoyi_test_2.Utils.SPUtils;
import com.example.xiaoyi_test_2.Utils.ZipStringUtils;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddActivity extends AppCompatActivity implements View.OnClickListener,ZipService.SendZipd, ItemService.SendAddItem {
    private Button btn_addtype;
    private TextView textView_type;
    private EditText et_describ, et_tag, et_posi, et_money;
    private ImageView clickimg, img, img_2, img_3;
    private ProgressBar mprogers;
    private String bigtype;
    private Button btn_cancel, btn_confirm;
    private   ItemBean itemBean = new ItemBean();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private RecyclerView recyclerView;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int clickone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        AutoAskPermission.verifyStoragePermissions(this);
        initView();
    }


    private void initView() {
        textView_type = findViewById(R.id.add_tv_type);
        textView_type.setText("全部分类");
        bigtype="all";
        mprogers = findViewById(R.id.add_progers);
        img = findViewById(R.id.add_img);
        img.setOnClickListener(this);
        img_2 = findViewById(R.id.add_img_2);
        img_2.setOnClickListener(this);
        img_3 = findViewById(R.id.add_img_3);
        img_3.setOnClickListener(this);
        et_money = findViewById(R.id.add_et_money);
        et_describ = findViewById(R.id.add_et_describe);
        et_posi = findViewById(R.id.add_et_posi);
        et_tag = findViewById(R.id.add_et_tag);
        btn_cancel = findViewById(R.id.add_btn_return);
        btn_confirm = findViewById(R.id.add_btn_out);
        btn_cancel.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        mprogers.setVisibility(View.INVISIBLE);

    }

    public void addType(View view) {
        Intent intent = new Intent(AddActivity.this, AddTypeActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            textView_type.setText(data.getExtras().getString("type"));
            bigtype = data.getExtras().getString("btype");
        }
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
            ZipService.zipPhoto(AddActivity.this,imagePath,AddActivity.this);
        } else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(AddActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //三张图片的选取
            case R.id.add_img_2: {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 2;
                clickimg = img_2;
                break;
            }
            case R.id.add_img_3: {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 3;
                clickimg = img_3;
                break;
            }
            case R.id.add_img: {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 1;
                clickimg = img;
                break;
            }
            case R.id.add_btn_return: {
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                setResult(0,intent);
                break;
            }
            case R.id.add_btn_out: {
             if (et_describ.getText().length() == 0) {
                    Toast.makeText(this, "请输入描述", Toast.LENGTH_SHORT).show();
                    break;
                } else if (et_money.getText().length() == 0) {
                    Toast.makeText(this, "请输入价格", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    mprogers.setVisibility(View.VISIBLE);

                    itemBean.setDescribe(et_describ.getText().toString());
                    itemBean.setTag(et_tag.getText().toString());
                    itemBean.setMoney(et_money.getText().toString());
                    itemBean.setBigtype(bigtype);
                    itemBean.setPhone(SPUtils.get(AddActivity.this,"phone","13393111416").toString());
                    itemBean.setPosi(et_posi.getText().toString());
                    itemBean.setType(textView_type.getText().toString());
                    itemBean.setImgpath("http://123.56.137.134/photo_XY/mm.png");
                    itemBean.save(AddActivity.this,AddActivity.this);
                    break;
                }
            }
        }
    }

    @Override
    public void sendZip(File file) {
        clickimg.setImageURI(Uri.parse(file.getPath()));
        if (clickone==1){
            itemBean.setImg(file);
        }
        if (clickone==2){
            itemBean.setImg_2(file);
        }
        if (clickone==3){
            itemBean.setImg_3(file);
        }
    }

    @Override
    public void sendAddItem(String s) {
        if (s!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddActivity.this,"上传成功！",Toast.LENGTH_SHORT).show();
                    mprogers.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    finish();
                    setResult(0,intent);
                }
            });

        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(AddActivity.this,"上传失败！请检查网络设置！",Toast.LENGTH_LONG).show();
                    mprogers.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
