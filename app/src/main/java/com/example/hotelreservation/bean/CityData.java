package com.example.hotelreservation.bean;

/**
 * Created by Administrator on 2016/11/8.
 */

public class CityData {
    public String name;
    public String pinyi;

    public CityData(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public CityData() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }
}
