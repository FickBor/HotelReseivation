package com.example.hotelreservation.bean;

/**
 * Created by Administrator on 2016/11/5.
 */

public class OverallDrawer {
    private int image_overall_drawer;
    private String tv_overall_drawer;

    public void setImage_overall_drawer(int image_overall_drawer) {
        this.image_overall_drawer = image_overall_drawer;
    }

    public void setTv_overall_drawer(String tv_overall_drawer) {
        this.tv_overall_drawer = tv_overall_drawer;
    }

    public int getImage_overall_drawer() {

        return image_overall_drawer;
    }

    public String getTv_overall_drawer() {
        return tv_overall_drawer;
    }

    public OverallDrawer(int draw_image, String draw_text) {
        super();
        this.image_overall_drawer = draw_image;
        this.tv_overall_drawer = draw_text;
    }


}
