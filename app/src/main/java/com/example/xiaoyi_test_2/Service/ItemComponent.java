package com.example.xiaoyi_test_2.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Activity.ShowDetailActivity;
import com.example.xiaoyi_test_2.Bean.SayToItemBean;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemComponent {
    public static void saveFirstSay(final SayToItemBean sayToItemBean, final Context context){

        new Thread(new Runnable() {
            @Override
            public void run() {

                MediaType mediaType=MediaType.parse("application/json; charset=utf-8");
                Request request=new Request.Builder()
                        .url("http://123.56.137.134/XiaoYiServer/AddItemComp")
                        .post(RequestBody.create(mediaType, JSONObject.toJSONString(sayToItemBean))).build();
                OkHttpClient okHttpClient=new OkHttpClient();

                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                        ((ShowDetailActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context, "+_+ 发布成功 +_+", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                });
            }
        }).start();
    }

    public static void getAllAbout(final int id, final SendAboutC sendAboutC){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
                String request_body=""+id;
                Request request=new Request.Builder().
                        url("http://123.56.137.134/XiaoYiServer/GetCompent")
                        .post(RequestBody.create(mediaType,request_body)).build();
                OkHttpClient okHttpClient=new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //response.body只能调一次
                        String body=response.body().string();
                        Log.d("IC-------",body);
                        List<SayToItemBean> list=JSONObject.parseArray(body,SayToItemBean.class);
                        sendAboutC.sendAC(list);
                    }
                });
            }
        }).start();
    }
    public interface SendAboutC{
        public void sendAC(List<SayToItemBean> list);
    }
}
