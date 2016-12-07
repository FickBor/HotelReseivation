package com.example.hotelreservation.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/19.
 */

public class Room extends BmobObject {
    private  String name;
    private  String breakfast;
    private  String windows;
    private  String price;
    public void setName(String name) {
        this.name = name;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getBreakfast() {
        return breakfast;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getWindows() {
        return windows;
    }


}
