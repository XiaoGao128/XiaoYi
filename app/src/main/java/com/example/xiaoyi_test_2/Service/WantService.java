package com.example.xiaoyi_test_2.Service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WantService {
    public static void addWang(String phone, int id, final SendSuccWant sendSuccWant){
        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        Request request=new Request.Builder()
                .addHeader("phone",phone)
                .addHeader("id",""+id)
                .url("http://123.56.137.134/XiaoYiServer/AddWant")
                .post(RequestBody.create(mediaType,"111"))
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccWant.sendSW();
            }
        });

    }
    public static void addLike(String phone, int id, final SendSuccLike sendSuccLike){
        MediaType mediaType=MediaType.parse("text/x-markdown; charset=utf-8");
        Request request=new Request.Builder()
                .addHeader("phone",phone)
                .addHeader("id",""+id)
                .url("http://123.56.137.134/XiaoYiServer/AddLike")
                .post(RequestBody.create(mediaType,"111"))
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccLike.sendSL();
            }
        });

    }
    public interface SendSuccWant{
        public void sendSW();
    }
    public interface SendSuccLike{
        public void sendSL();
    }
}
