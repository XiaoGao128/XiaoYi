package com.example.xiaoyi_test_2.Bean;

import android.content.Context;

import com.example.xiaoyi_test_2.Service.Component;
import com.example.xiaoyi_test_2.Service.ItemComponent;

import java.io.Serializable;
import java.sql.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class SayToItemBean implements Serializable {
    private int itemid,userid,touerid,id,abovecomment;
    private String saywhat,username,head;
    private Date date;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemid() {
        return itemid;
    }

    public int getUserid() {
        return userid;
    }

    public int getTouerid() {
        return touerid;
    }

    public String getSaywhat() {
        return saywhat;
    }

    public void setSaywhat(String saywhat) {
        this.saywhat = saywhat;
    }

    public int getAbovecomment() {
        return abovecomment;
    }

    public void setAbovecomment(int abovecomment) {
        this.abovecomment = abovecomment;
    }

    public Date getDate() {
        return date;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setTouerid(int touerid) {
        this.touerid = touerid;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public void save(Context context){
        ItemComponent.saveFirstSay(this,context);
    }

    public void savesoc(Context context) {
        Component.saveSay(context,this);
    }
}
