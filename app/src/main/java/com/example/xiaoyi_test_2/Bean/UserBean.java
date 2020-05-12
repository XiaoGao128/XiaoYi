package com.example.xiaoyi_test_2.Bean;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import cn.bmob.v3.BmobUser;

public class UserBean implements Serializable {

    private String position;
    private String username;
    private String phone;
    private String head;
    private String sex;
    private String age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void signUp(final Context context){
        final JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket=new Socket("123.56.137.134",12306);
                    OutputStream os=socket.getOutputStream();
                    os.write(jsonArray.toJSONString().getBytes());
                    os.flush();
                    os.close();
                    InputStream is=socket.getInputStream();
                    InputStreamReader reader=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(reader);
                    String s="";
                    StringBuffer stringBuffer=new StringBuffer();
                    while ((s=br.readLine())!=null){
                        stringBuffer.append(s);
                    }
                    br.close();
                    reader.close();
                    is.close();
                    socket.close();
                    JSONObject jsonObject=JSONObject.parseObject(stringBuffer.toString());
                    String LogState=jsonObject.getString("LogState");
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
