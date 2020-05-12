package com.example.xiaoyi_test_2.Bean;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Activity.AddSocialActivity;
import com.example.xiaoyi_test_2.Service.AddComp;

import java.io.File;
import java.io.Serializable;

public class SocialBean implements Serializable {
    private String name,time,head,goodnum,compnum,title,describ;
    private int userid,id;
    private String imgpath;
    private File img1,img2,img3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getGoodnum() {
        return goodnum;
    }

    public void setGoodnum(String goodnum) {
        this.goodnum = goodnum;
    }

    public String getCompnum() {
        return compnum;
    }

    public void setCompnum(String compnum) {
        this.compnum = compnum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public File getImg1() {
        return img1;
    }

    public void setImg1(File img1) {
        this.img1 = img1;
    }

    public File getImg2() {
        return img2;
    }

    public void setImg2(File img2) {
        this.img2 = img2;
    }

    public File getImg3() {
        return img3;
    }

    public void setImg3(File img3) {
        this.img3 = img3;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void save(Context context) {
        AddComp.Acomp(this,context);
    }
}
