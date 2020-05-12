package com.example.xiaoyi_test_2.Utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base36Utils {
    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(File file){
        FileInputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(file);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_CLOSE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
