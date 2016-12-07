/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.hotelreservation.hotelreservation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.Room;
import com.example.hotelreservation.tools.ImageLoaderConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Plix on 2016/11/10.
 */
public class ImagePagerActivity extends ImageBaseActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	private  String name;
	private  String tell;
	private  String address;
	private  String type;
	private String price;
	private String hanzao;
	private  String windows;
	private String imgurl;
	private TextView hotel_name;
	private  TextView hotel_address;
	private  TextView hotel_tell;
	private ListView detail_lv;
	//private  String price;
	DisplayImageOptions options;
	ViewPager pager;
	private  String user_name;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		Intent intent= getIntent();
		name= intent.getStringExtra("name");
		//type= intent.getStringExtra("type");
		user_name= intent.getStringExtra("user_name");
		address= intent.getStringExtra("address");
		tell= intent.getStringExtra("tell");
		//imgurl=intent.getStringExtra("imgurl");

		findbyid();
		hotel_name.setText(name);
		hotel_address.setText(address);
		hotel_tell.setText(tell);
		getData();
		Bundle bundle = getIntent().getExtras();

		String[] imageUrls = bundle.getStringArray("imgurl");


		// 当前显示View的位置
		int pagerPosition = bundle.getInt(ImageLoaderConstants.Extra.IMAGE_POSITION, 0);

		// 如果之前有保存用户数据
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls));
		pager.setCurrentItem(pagerPosition);	// 显示当前位置的View
	}
	private  void findbyid(){
		hotel_name=(TextView) findViewById(R.id.hotel_detail_linear_tell_name);
		hotel_address=(TextView) findViewById(R.id.hotel_detail_address);
		hotel_tell=(TextView) findViewById(R.id.hotel_detail_linear_tell_num);
		detail_lv=(ListView) findViewById(R.id.hotel_detail_listview);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// 保存用户数据
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;

		ImagePagerAdapter(String[] images) {
			this.images = images;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return images.length;
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			imageLoader.displayImage(images[position], imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {		// 获取图片失败类型
						case IO_ERROR:				// 文件I/O错误
							message = "Input/Output error";
							break;
						case DECODING_ERROR:		// 解码错误
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:		// 网络延迟
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:		    // 内存不足
							message = "Out Of Memory error";
							break;
						case UNKNOWN:				// 原因不明
							message = "Unknown error";
							break;
					}
					Toast.makeText(ImagePagerActivity.this, message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);		// 不显示圆形进度条
				}
			});

			((ViewPager) view).addView(imageLayout, 0);		// 将图片增加到ViewPager
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}
	/*
	查询表格数据
	 */
	public void GetData(List<Room> roomdata)
	{
		detail_lv.setAdapter(new DetailAdapter(this,roomdata));
	}
	public  void getData() {
		final List<Room> roomdata = new ArrayList<>();

		BmobQuery<Room> query = new BmobQuery<Room>();
		query.order("-createdAt");
		query.findObjects(this, new FindListener<Room>() {
			@Override
			public void onSuccess(List<Room> roomList) {
				for (Room r:roomList)
				{
					roomdata.add(r);
				}
				GetData(roomdata);
			}

			@Override
			public void onError(int i, String s) {
				System.out.println("55555");
			}
		});
	}
	class DetailAdapter extends BaseAdapter {
		private List<Room> data;
		private   Context context;
		private  DetailAdapter(Context context,List<Room> data){
			this.context= context;
			this.data=data;

		}



		private class ViewHolder {
			public TextView name;
			public TextView breakfast;
			public TextView windows;
			public  TextView price;
			public Button yuding;

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
				convertView = getLayoutInflater().inflate(R.layout.hotel_list_item, parent, false);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.hotel_detail_list_name);
				holder.breakfast = (TextView) convertView.findViewById(R.id.hotel_detail_list_hanzao);
				holder.windows= (TextView) convertView.findViewById(R.id.hotel_detail_list_window);
				holder.price = (TextView) convertView.findViewById(R.id.hotel_detail_list_price);
				holder.yuding=(Button)convertView.findViewById(R.id.hotel_detail_list_btn_yuding);
				convertView.setTag(holder);		// 给View添加一个格外的数据
			} else {
				holder = (ViewHolder) convertView.getTag(); // 把数据取出来
			}
			holder.name.setText(((Room)getItem(position)).getName() );
			holder.breakfast.setText(((Room)getItem(position)).getBreakfast());
			holder.windows.setText(((Room)getItem(position)).getWindows());
			holder.price.setText("¥"+((Room)getItem(position)).getPrice());
			holder.yuding.setTag(position);
			holder.yuding.setOnClickListener(new  MyOnClickListener());

			return convertView;
		}
	}
	class MyOnClickListener implements View.OnClickListener {
//		    private    MyOnClickListener instance = null;
//		    private MyOnClickListener() {}
//		    public MyOnClickListener getInstance() {
//			        if (instance == null)
//						instance = new MyOnClickListener();
//			       return instance;
//			    }
		    @Override
		    public void onClick(View view) {
				int position = (Integer) ((Button) view).getTag();
				Toast.makeText(ImagePagerActivity.this,"按钮被点击"+position,Toast.LENGTH_SHORT).show();
				 Room room=(Room)detail_lv.getItemAtPosition(position);
				type=room.getName();
				price=room.getPrice();
				hanzao=room.getBreakfast();
				windows=room.getWindows();
				Intent intent = new Intent(ImagePagerActivity.this,OrdersActivity.class);
				intent.putExtra("user_name",user_name);
				intent.putExtra("name",name);
				intent.putExtra("tell",tell);
				intent.putExtra("address",address);
				intent.putExtra("type",type);
				intent.putExtra("price",price);
				intent.putExtra("hanzao",hanzao);
				intent.putExtra("windows",windows);
				startActivity(intent);

			     }
		 }
}