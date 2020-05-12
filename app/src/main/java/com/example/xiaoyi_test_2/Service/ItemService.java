package com.example.xiaoyi_test_2.Service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Activity.AddActivity;
import com.example.xiaoyi_test_2.Bean.ItemBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Utils.Base36Utils;
import com.example.xiaoyi_test_2.Utils.RangeUtil;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemService {
    public static void addItem(ItemBean itemBean, final Context context, final SendAddItem sendAddItem) {
        String imgpath = "";

        if (itemBean.getImg() != null) {
            String s = Base36Utils.imageToBase64(itemBean.getImg());
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
            itemBean.setImg(null);

        }
        if (itemBean.getImg_2() != null) {
            String s = Base36Utils.imageToBase64(itemBean.getImg_2());
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
            itemBean.setImg_2(null);
        }
        if (itemBean.getImg_3() != null) {
            String s = Base36Utils.imageToBase64(itemBean.getImg_3());
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
            itemBean.setImg_3(null);
        }
        itemBean.setImgpath(imgpath);
        final boolean[] flag = {false};
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url("http://123.56.137.134/XiaoYiServer/AddItem")
                .post(RequestBody.create(mediaType, JSON.toJSONString(itemBean))).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                sendAddItem.sendAddItem(s);
                Log.d("ItemService------",s);
            }
        });

    }
    public static void getAll(final SendAll sendAll){
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        final Request request=new Request.Builder()
                .url("http://123.56.137.134/XiaoYiServer/sendItem")
                .post(RequestBody.create(mediaType,"1"))
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String s=response.body().string();

                    Log.d("ItemService------","--+"+s);
                    JSONArray jsonArray=JSONArray.parseArray(s);
                    sendAll.sendAll(jsonArray);
            }
        });
    }
    public static void searchKey(final SendAll sendAll,String key){
     OkHttpClient okHttpClient=new OkHttpClient();
     MediaType mediaType=MediaType.parse("ext/x-markdown; charset=utf-8");
     Request request=new Request.Builder()
             .url("http://123.56.137.134/XiaoYiServer/SearchKey")
             .post(RequestBody.create(mediaType,key))
             .build();
     Call call=okHttpClient.newCall(request);
     call.enqueue(new Callback() {
         @Override
         public void onFailure(Call call, IOException e) {

         }

         @Override
         public void onResponse(Call call, Response response) throws IOException {
             String s=response.body().string();

             Log.d("ItemService------","--+"+s);
             JSONArray jsonArray=JSONArray.parseArray(s);
             sendAll.sendAll(jsonArray);
         }
     });
    }
    public static void getType(final SendAll sendAll,String key){
        OkHttpClient okHttpClient=new OkHttpClient();
        MediaType mediaType=MediaType.parse("ext/x-markdown; charset=utf-8");
        Request request=new Request.Builder()
                .url("http://123.56.137.134/XiaoYiServer/GetType")
                .post(RequestBody.create(mediaType,key))
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();

                Log.d("ItemService------","--+"+s);
                JSONArray jsonArray=JSONArray.parseArray(s);
                sendAll.sendAll(jsonArray);
            }
        });
    }
    public interface SendAddItem{
        public void sendAddItem(String s);
    }
    public interface SendAll{
        public void sendAll(JSONArray jsonArray);
    }
}
