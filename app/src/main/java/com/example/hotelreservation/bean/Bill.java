package com.example.hotelreservation.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Plix on 2016/11/20.
 */

public class Bill extends BmobObject {
    private  String name;
    private  String tell;
    private  String address;
    private  String price;
    private  String hanzao;
    private  String type;
    private  String windows;
    private  User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }



    public void setLidian(String lidian) {
        this.lidian = lidian;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public void setRuzhu(String ruzhu) {
        this.ruzhu = ruzhu;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHanzao(String hanzao) {
        this.hanzao = hanzao;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public void setName(String name) {
        this.name = name;
    }

    private  String ruzhu;

    public String getLidian() {
        return lidian;
    }

    public String getTell() {
        return tell;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getHanzao() {
        return hanzao;
    }

    public String getType() {
        return type;
    }

    public String getWindows() {
        return windows;
    }

    public String getRuzhu() {
        return ruzhu;
    }

    private  String lidian;

}
