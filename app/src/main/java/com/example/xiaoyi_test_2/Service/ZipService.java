package com.example.xiaoyi_test_2.Service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.xiaoyi_test_2.R;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ZipService {

    public static void zipPhoto(Context context, String imgpath, final SendZipd sendZipd){
        String[] ss=imgpath.split("/");
        ss[ss.length-1]="";
        String imgpath2="";
        for(int i=0;i<ss.length-1;i++){
            imgpath2+="/";
            imgpath2+=ss[i];

        }
        ss.toString();
        Luban.with(context)
                .load(imgpath)
                .ignoreBy(100)
                .setTargetDir(imgpath2)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                Log.d("ZipService--------","success");
                sendZipd.sendZip(file);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ZipService-------erro--",e.getMessage());
            }
        }).launch();
    }

    public interface SendZipd{
        public void sendZip(File file);
    }
}
