package com.example.hotelreservation.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View>list;
    public MyPagerAdapter(List<View> list)
    {
        this.list=list;
    }
    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1)
    {
        return arg0==arg1;
    }
    @Override
    public Object instantiateItem(View container, int position)
    {
        ((ViewPager)container).addView(list.get(position), 0);
        return list.get(position);
    }
    @Override
    public void destroyItem(View container, int position, Object object)
    {
        ((ViewPager)container).removeView(list.get(position));
    }
}
