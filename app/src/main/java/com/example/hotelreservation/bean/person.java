package com.example.hotelreservation.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/18.
 */

public class person extends BmobObject {
    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    private String imgurl;
    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    private  String tell;
    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    private  String  price;

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private  String type;
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    private  String name;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    private  String address;

}
