package com.example.hotelreservation.hotelreservation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.base.AbsListViewBaseActivity;
import com.example.hotelreservation.bean.person;
import com.example.hotelreservation.tools.ImageLoaderConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ImageListActivity extends AbsListViewBaseActivity {
    DisplayImageOptions options;
    @InjectView(R.id.btn_back)
    Button btnBack;
    @InjectView(R.id.hotelreserva_action_head_title)
    TextView hotelreservaActionHeadTitle;
    private String hotel_name;
    private String hotel_type;
    private String hotel_address;
    private String hotel_tell;
    private String[] imgurl=new String[1];
    // DisplayImageOptions是用于设置图片显示的类
    String[] imageUrls;
    private  String user_name;
    private String city;
    // 图片路径

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_listview);
        ButterKnife.inject(this);
        Intent intent= getIntent();
        city=intent.getStringExtra("city");
        user_name= intent.getStringExtra("user_name");
        hotelreservaActionHeadTitle.setText("酒店");
        // Bmob.initialize(this, "53bf922fdd6253628f9b82ea10ecafd0");
        Bundle bundle = getIntent().getExtras();
        imageUrls = bundle.getStringArray(ImageLoaderConstants.Extra.IMAGES);

        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))    // 设置成圆角图片
                .build();                                    // 创建配置过得DisplayImageOption对象

        listView = (ListView) findViewById(R.id.list);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击列表项转入ViewPager显示界面
                person pers = (person) listView.getItemAtPosition(position);
                Toast.makeText(ImageListActivity.this, pers.getName(), Toast.LENGTH_SHORT).show();
                hotel_name = pers.getName();
                hotel_tell = pers.getTell();
                hotel_type = pers.getType();
                imgurl[0]=pers.getImgurl();
                hotel_address = pers.getAddress();
                startImagePagerActivity(position);

            }
        });

    }

    public void getdata(List<person> data) {

        listView.setAdapter(new ItemAdapter(this, data));
    }

    public void getData() {
        final List<person> data = new ArrayList<>();
        BmobQuery<person> query = new BmobQuery<person>();
        query.addWhereEqualTo("address",city);
        query.setLimit(20);
        query.order("-createdAt");
        query.findObjects(this, new FindListener<person>() {
            @Override
            public void onSuccess(List<person> lists) {
                for (person p : lists) {

                    data.add(p);
                }
                getdata(data);
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("55555");
            }
        });
    }

    @Override
    public void onBackPressed() {
        AnimateFirstDisplayListener.displayedImages.clear();
        super.onBackPressed();
    }

    private void startImagePagerActivity(int position) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra("imgurl",imgurl);
//        intent.putExtra(ImageLoaderConstants.Extra.IMAGES, );
        intent.putExtra(ImageLoaderConstants.Extra.IMAGE_POSITION, position);
        intent.putExtra("user_name",user_name);
        intent.putExtra("name", hotel_name);
        intent.putExtra("type", hotel_type);
        intent.putExtra("address", hotel_address);
        intent.putExtra("tell", hotel_tell);
        startActivity(intent);
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        Intent intent= new Intent(this,OverallActivity.class);
        startActivity(intent);
    }


    /**
     * 自定义列表项适配器
     */
    class ItemAdapter extends BaseAdapter {

        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        private List<person> data;
        private Context context;

        private ItemAdapter(Context context, List<person> data) {
            this.context = context;
            this.data = data;

        }

        private class ViewHolder {
            public TextView address;
            public TextView type;
            public TextView name;
            public TextView price;
            public ImageView image;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.xiangqing_list_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.hotel_jiudian_name);
                holder.type = (TextView) convertView.findViewById(R.id.hotel_jiudian_type);
                holder.address = (TextView) convertView.findViewById(R.id.hotel_jiudian_address);
                holder.price = (TextView) convertView.findViewById(R.id.hotel_jiudian_price);
                holder.image = (ImageView) convertView.findViewById(R.id.hotel_jiudian_image);
                convertView.setTag(holder);        // 给View添加一个格外的数据
            } else {
                holder = (ViewHolder) convertView.getTag(); // 把数据取出来
            }
            //System.out.println(((person)getItem(position)).getName());
            holder.name.setText(((person) getItem(position)).getName());
            holder.type.setText(((person) getItem(position)).getType());
            holder.address.setText(((person) getItem(position)).getAddress());
            holder.price.setText("¥" + ((person) getItem(position)).getPrice());


            // TextView设置文本

            /**
             * 显示图片
             * 参数1：图片url
             * 参数2：显示图片的控件
             * 参数3：显示图片的设置
             * 参数4：监听器
             */
            imageLoader.displayImage(((person) getItem(position)).getImgurl(), holder.image, options, animateFirstListener);

            return convertView;
        }
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
