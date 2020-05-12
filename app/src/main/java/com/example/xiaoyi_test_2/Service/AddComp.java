package com.example.xiaoyi_test_2.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Activity.AddSocialActivity;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;
import com.example.xiaoyi_test_2.Bean.SocialBean;
import com.example.xiaoyi_test_2.Utils.Base36Utils;
import com.example.xiaoyi_test_2.Utils.RangeUtil;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddComp {
    public static void Acomp(SocialBean socialBean, final Context context){
        String imgpath = "";

        if (socialBean.getImg1() != null) {
            String s = Base36Utils.imageToBase64(socialBean.getImg1());
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
            String request_body = s;

            String name = (String) SPUtils.get(context, "phone", "") + RangeUtil.getRange();
            imgpath += ("http://123.56.137.134/photo_XY/" + name + ".jpg|");
            Request request = new Request.Builder()
                    .url("http://123.56.137.134/XiaoYiServer/AddPhoto")
                    .addHeader("name", name)
                    .post(RequestBody.create(mediaType, request_body)).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("LogAndRegister-------", "phone查询请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    //response.body只能调一次

                }
            });
            socialBean.setImg1(null);

        }
        if (socialBean.getImg2() != null) {
            String s = Base36Utils.imageToBase64(socialBean.getImg2());
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
            String request_body = s;
            String name = (String) SPUtils.get(context, "phone", "") + RangeUtil.getRange();
            imgpath += ("http://123.56.137.134/photo_XY/" + name + ".jpg|");
            Request request = new Request.Builder().
                    url("http://123.56.137.134/XiaoYiServer/AddPhoto")
                    .addHeader("name", name)
                    .post(RequestBody.create(mediaType, request_body)).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("LogAndRegister-------", "phone查询请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    //response.body只能调一次
                }
            });
            socialBean.setImg2(null);
        }
        if (socialBean.getImg3() != null) {
            String s = Base36Utils.imageToBase64(socialBean.getImg3());
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
            String request_body = s;
            String name = (String) SPUtils.get(context, "phone", "") + RangeUtil.getRange();
            imgpath += ("http://123.56.137.134/photo_XY/" + name + ".jpg|");
            Request request = new Request.Builder().
                    url("http://123.56.137.134/XiaoYiServer/AddPhoto")
                    .addHeader("name", name)
                    .post(RequestBody.create(mediaType, request_body)).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("itemService-------", "存储失败！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //response.body只能调一次
                }
            });
            socialBean.setImg3(null);
        }
        socialBean.setImgpath(imgpath);
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("application/json; charset=utf-8");
        String request_body=JSONObject.toJSONString(socialBean);
        Request request=new Request.Builder().
                url("http://123.56.137.134/XiaoYiServer/AddComp")
                .post(RequestBody.create(mediaType,request_body)).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //response.body只能调一次
                String body=response.body().string();
                Log.d("IC-------",body);

                ((AddSocialActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((AddSocialActivity)context).progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "发布成功！", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent();
                        ((AddSocialActivity)context).setResult(0,intent);
                    }
                });
            }
        });
    }
    public static void getComp(final SendSC sendSC){
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        Request request=new Request.Builder().
                url("http://123.56.137.134/XiaoYiServer/GetComp")
                .post(RequestBody.create(mediaType,"1")).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //response.body只能调一次
                String body=response.body().string();
                Log.d("IC-------",body);
                List<SocialBean> list= com.alibaba.fastjson.JSONObject.parseArray(body,SocialBean.class);
                sendSC.sendSC(list);
            }
        });
    }
    public interface SendSC{
        public void sendSC(List<SocialBean> list);
    }
}
