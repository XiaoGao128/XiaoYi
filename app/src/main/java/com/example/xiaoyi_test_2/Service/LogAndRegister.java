package com.example.xiaoyi_test_2.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Utils.Base36Utils;
import com.example.xiaoyi_test_2.Utils.RangeUtil;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogAndRegister{

    public static void uploadHead(File file, Context context){
        String s= Base36Utils.imageToBase64(file);
        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        String request_body = s;

        String name = (String) SPUtils.get(context, "userid", "") + RangeUtil.getRange();

        Request request = new Request.Builder()
                .url("http://123.56.137.134/XiaoYiServer/UploadHead")
                .addHeader("name", name)
                .addHeader("userid",(String) SPUtils.get(context, "userid", ""))
                .post(RequestBody.create(mediaType, request_body)).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //response.body只能调一次

            }
        });
    }

    public static boolean snetenceHaveRegist(final String phone, final sendRgRes sendRgRes){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
                String request_body=phone;
                Request request=new Request.Builder().
                        url("http://123.56.137.134/XiaoYiServer/sentenceRegd")
                        .post(RequestBody.create(mediaType,request_body)).build();
                OkHttpClient okHttpClient=new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("LogAndRegister-------","phone查询请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //response.body只能调一次
                        String body=response.body().string();
                        Log.d("LogAndRegister-------",body);
                        sendRgRes.sendRgRes(body);
                    }
                });
            }
        }).start();
        return false;
    }

    public static void Login(final JSONObject jsonObject, final SendRegSucc sendRegSucc){
        new Thread(new Runnable() {
            @Override
            public void run() {

                MediaType mediaType=MediaType.parse("application/json; charset=utf-8");
                Request request=new Request.Builder()
                        .url("http://123.56.137.134/XiaoYiServer/Regis")
                        .post(RequestBody.create(mediaType,jsonObject.toJSONString())).build();
                OkHttpClient okHttpClient=new OkHttpClient();

                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        sendRegSucc.sendRegSucc(false);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        sendRegSucc.sendRegSucc(true);
                    }
                });
            }
        }).start();
    }
    public interface sendRgRes{
        public void sendRgRes(String body);
    }
    public interface SendRegSucc{
        public void sendRegSucc(boolean succ);
    }
}
