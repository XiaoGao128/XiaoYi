package com.example.xiaoyi_test_2.Bean;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Service.ItemService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ItemBean implements Serializable {
    private String describe, tag, type, posi, username, bigtype,money;
    private File img, img_2, img_3;
    private String imgpath,head;
    private String phone;
    private String wantter;
    private int id;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getWantter() {
        return wantter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWantter(String wantter) {
        this.wantter = wantter;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = "￥"+money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBigtype() {
        return bigtype;
    }

    public void setBigtype(String bigtype) {
        this.bigtype = bigtype;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPosi() {
        return posi;
    }

    public void setPosi(String posi) {
        this.posi = posi;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public File getImg_2() {
        return img_2;
    }

    public void setImg_2(File img_2) {
        this.img_2 = img_2;
    }

    public File getImg_3() {
        return img_3;
    }

    public void setImg_3(File img_3) {
        this.img_3 = img_3;
    }


    public void save(Context context, ItemService.SendAddItem sendAddItem){

        ItemService.addItem(this,context,sendAddItem);
    }
}
