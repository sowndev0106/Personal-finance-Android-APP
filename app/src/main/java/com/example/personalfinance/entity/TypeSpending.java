package com.example.personalfinance.entity;

import android.content.Context;
import android.content.res.Resources;

import com.example.personalfinance.R;
import com.example.personalfinance.activity.HomeActivity;

import java.io.Serializable;

public class TypeSpending implements Serializable {
    private String name;
    private int img;
    private  int type; // 0: spending 1: add


    public TypeSpending(String name, int img) {
        this.name = name;
        this.img = img;
    }
    public TypeSpending(String name, int img,int type) {
        this.name = name;
        this.img = img;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgName(){
        Resources resources = HomeActivity.getContext().getResources();
        String name = resources.getResourceEntryName(this.img);
        return name;
    }


    @Override
    public String toString() {
        return "TypeSpending{" +
                "name='" + name + '\'' +
                ", img=" + img +
                '}';
    }
}
