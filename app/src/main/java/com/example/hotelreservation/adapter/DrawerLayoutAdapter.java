package com.example.hotelreservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservation.bean.OverallDrawer;
import com.example.hotelreservation.hotelreservation.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/5.
 */

public class DrawerLayoutAdapter extends BaseAdapter {
    private Context context;
    private List<OverallDrawer> list;

    public DrawerLayoutAdapter(Context context, List<OverallDrawer> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.overall_drawer_item, null);
            hold.image_overall_drawer=(ImageView) convertView.findViewById(R.id.item_imageview);
            hold.tv_overall_drawer=(TextView) convertView.findViewById(R.id.item_textview);
            convertView.setTag(hold);
        }else {
            hold=(ViewHold) convertView.getTag();
        }
        hold.image_overall_drawer.setImageResource(list.get(position).getImage_overall_drawer());
        hold.tv_overall_drawer.setText(list.get(position).getTv_overall_drawer());
        return convertView;
    }

    static class ViewHold {
        public ImageView image_overall_drawer;
        public TextView tv_overall_drawer;
    }
}
