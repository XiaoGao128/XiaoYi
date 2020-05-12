package com.example.xiaoyi_test_2.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.AddComp;
import com.example.xiaoyi_test_2.Service.ItemService;
import com.example.xiaoyi_test_2.Service.ZipService;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.io.File;

public class AddSocialActivity extends AppCompatActivity implements ZipService.SendZipd {
    private Button btn_retrun,btn_out;
    private ImageView im_1,im_2,im_3;
    private EditText edit_title,edit_describe;
    public ProgressBar progressBar;
    private int clickone;
    private ImageView clickimg;
    private SocialBean socialBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_social);
        initView();
    }

    private void initView() {
        btn_out=findViewById(R.id.addsoc_btn_out);
        btn_retrun=findViewById(R.id.addsoc_btn_return);
        im_1=findViewById(R.id.addsoc_img);
        im_2=findViewById(R.id.addsoc_img_2);
        im_3=findViewById(R.id.addsoc_img_3);
        edit_title=findViewById(R.id.addsoc_et_title);
        edit_describe=findViewById(R.id.addsoc_et_describe);
        progressBar=findViewById(R.id.addsoc_progers);
        progressBar.setVisibility(View.INVISIBLE);
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                socialBean=new SocialBean();
                socialBean.setName((String) SPUtils.get(AddSocialActivity.this,"username","11"));
                socialBean.setHead((String) SPUtils.get(AddSocialActivity.this,"head","11"));
                socialBean.setDescrib(edit_describe.getText().toString());
                socialBean.setTitle(edit_title.getText().toString());
                socialBean.setUserid((Integer) SPUtils.get(AddSocialActivity.this,"userid",1));
                socialBean.save(AddSocialActivity.this);
            }
        });
        im_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 1;
                clickimg = im_1;
            }
        });
        im_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 2;
                clickimg = im_2;
            }
        });
        im_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                clickone = 3;
                clickimg = im_3;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
            ZipService.zipPhoto(AddSocialActivity.this,imagePath,AddSocialActivity.this);
        } else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(AddSocialActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendZip(File file) {
        clickimg.setImageURI(Uri.parse(file.getPath()));
        if (clickone==1){
            socialBean.setImg1(file);
        }
        if (clickone==2){
            socialBean.setImg2(file);
        }
        if (clickone==3){
            socialBean.setImg3(file);
        }
    }
}
