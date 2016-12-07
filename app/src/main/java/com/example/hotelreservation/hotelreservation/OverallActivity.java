package com.example.hotelreservation.hotelreservation;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.adapter.DrawerLayoutAdapter;
import com.example.hotelreservation.bean.OverallDrawer;
import com.example.hotelreservation.tools.ImageLoaderConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

import static com.example.hotelreservation.tools.ImageLoaderConstants.IMAGES;

/**
 * Created by Plix on 2016/10/24.
 */

public class OverallActivity extends ImageBaseActivity implements Runnable {
    public ImageLoader imageLoader = ImageLoader.getInstance();
    //banner
    private static final String TAG = "MainActivity";
    private ViewPager mBanner;
    private BannerAdapter mBannerAdapter;
    private ImageView[] mIndicators;
    private Timer mTimer = new Timer();
    private int mBannerPosition = 0;
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 5;
    private boolean mIsUserTouched = false;
    private int[] mImagesSrc = {R.drawable.imghotel1, R.drawable.imghotel2, R.drawable.imghotel3, R.drawable.imghotel4, R.drawable.imghotel5};
    //drawerlayout
    private DrawerLayout drawerLayout;
    private RelativeLayout leftLayout;
    private RelativeLayout rightLayout;
    private List<OverallDrawer> list;
    private DrawerLayoutAdapter adapter;
//    private AnimationDrawable animationDrawable;
//    private ActionBarDrawerToggle actionBarDrawerToggle;
   // private  ImageView imgback;
private  ListView listView;
    private    boolean a=false;
    private View showView;
    private boolean cehua=false;
    private Button back;
    //yemian
    private TextView tv_citylocation;
    private ImageButton imgbtn_jiudian,imgbtn_dingwei,imgbtn_dingdan,imgbtn_pinglun,imgbtn_yonghu,imgbtn_guanyu;
    private  Intent intent = null;
    private  String user_name;
    private static final String TEST_FILE_NAME = "Universal Image Loader @#&=+-_.,!()~'%20.png";


    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!mIsUserTouched) {
                mBannerPosition = (mBannerPosition + 1) % FAKE_BANNER_SIZE;
                runOnUiThread(OverallActivity.this);
                Log.d(TAG, "tname:" + Thread.currentThread().getName());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overall_view);
        intent=getIntent();
       user_name=intent.getStringExtra("user_name");
        initView();
        initovreallview();
        mTimer.schedule(mTimerTask, 5000, 5000);
        drawerLayout = (DrawerLayout) findViewById(R.id.ovreall_main);
        //drawerLayout.openDrawer(Gravity.LEFT);
        leftLayout = (RelativeLayout) findViewById(R.id.left);
        rightLayout = (RelativeLayout) findViewById(R.id.right);
        back=(Button) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cehua)
                {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    back.setBackgroundResource(R.drawable.qianjin);
                }else {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    back.setBackgroundResource(R.drawable.imgback);
                }
                cehua=!cehua;



            }
        });
        //image
        File testImageOnSdCard = new File("/mnt/sdcard", TEST_FILE_NAME);
        if (!testImageOnSdCard.exists()) {	// 如果文件不存在
            // 把文件复制到SD卡
            copyTestImageToSdCard(testImageOnSdCard);
        }
//        Button a= (Button) findViewById(R.id.btnasd);
//        a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
       // imgback = (ImageView) findViewById(R.id.hotelreserva_action_head_imgback);
//        toolbar=(Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Toolbar");//设置Toolbar标题
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        imgback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
         listView = (ListView) leftLayout.findViewById(R.id.left_listview);
        initData();
        adapter = new DrawerLayoutAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerOnItemClickListener());
        this.showView = listView;
//        actionBarDrawerToggle = new ActionBarDrawerToggle(OverallActivity.this, drawerLayout,toolbar, R.string.open, R.string.close) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                animationDrawable.stop();
//            }
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                animationDrawable.start();
//            }
//        };
//        actionBarDrawerToggle.syncState();
//        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//    }

    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.imgback:
//                if (showView == list) {
//
//                    if (!a) { // 左边栏菜单关闭时，打开
//                        drawerLayout.openDrawer(listView);
//                    } else {// 左边栏菜单打开时，关闭
//                        drawerLayout.closeDrawer( listView);
//                    }
//                }
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
            @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void copyTestImageToSdCard(final File testImageOnSdCard) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = getAssets().open(TEST_FILE_NAME);
                    FileOutputStream fos = new FileOutputStream(testImageOnSdCard);
                    byte[] buffer = new byte[8192];
                    int read;
                    try {
                        while ((read = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, read);	// 写入输出流
                        }
                    } finally {
                        fos.flush();		// 写入SD卡
                        fos.close();		// 关闭输出流
                        is.close();			// 关闭输入流
                    }
                } catch (IOException e) {
                    L.w("Can't copy test image onto SD card");
                }
            }
        }).start();		// 启动线程
    }


    //drawerlayout
    private void initData() {
        list=new ArrayList<OverallDrawer>();

        list.add(new OverallDrawer(R.drawable.overliuser, "用户"));
        list.add(new OverallDrawer(R.drawable.dingdan1, "订单"));
        list.add(new OverallDrawer(R.drawable.pingun, "评论"));
        list.add(new OverallDrawer(R.drawable.qingchu, "清除内存缓存"));
        list.add(new OverallDrawer(R.drawable.qingchusd, "清除sd卡图片缓存"));
        list.add(new OverallDrawer(R.drawable.about, "关于"));
        list.add(new OverallDrawer(R.drawable.tuichu,"退出"));

    }
    //banner
    private void initView() {
        mIndicators = new ImageView[] {
                (ImageView)findViewById(R.id.indicator1),
                (ImageView)findViewById(R.id.indicator2),
                (ImageView)findViewById(R.id.indicator3),
                (ImageView)findViewById(R.id.indicator4),
                (ImageView)findViewById(R.id.indicator5)
        };
        mBanner = (ViewPager) findViewById(R.id.banner);
        mBannerAdapter = new BannerAdapter(this);
        mBanner.setAdapter(mBannerAdapter);
        mBanner.setOnPageChangeListener(mBannerAdapter);
        mBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
    }
    private  void initovreallview(){
        tv_citylocation= (TextView) findViewById(R.id.ovreall_tv_location);
        imgbtn_jiudian= (ImageButton) findViewById(R.id.ovreall_imagebtn_jiudian);
        imgbtn_dingwei= (ImageButton)findViewById(R.id.ovreall_imagebtn_dingwei);
        imgbtn_dingdan= (ImageButton)findViewById(R.id.ovreall_imagebtn_dingdan);
        imgbtn_pinglun= (ImageButton)findViewById(R.id.ovreall_imagebtn_pinglun);
        imgbtn_yonghu= (ImageButton)findViewById(R.id.ovreall_imagebtn_yonghu);
        imgbtn_guanyu= (ImageButton)findViewById(R.id.ovreall_imagebtn_guanyu);
        imgbtn_jiudian.setOnClickListener(new OverallImgBtnOnClickListener());
        imgbtn_yonghu.setOnClickListener( new OverallImgBtnOnClickListener());
        imgbtn_dingwei.setOnClickListener(new OverallImgBtnOnClickListener());
        imgbtn_dingdan.setOnClickListener(new OverallImgBtnOnClickListener());
        imgbtn_pinglun.setOnClickListener(new OverallImgBtnOnClickListener());
        imgbtn_yonghu.setOnClickListener(new OverallImgBtnOnClickListener());
        imgbtn_guanyu.setOnClickListener(new OverallImgBtnOnClickListener());
        tv_citylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(OverallActivity.this,SelectCityActivity.class);
                startActivityForResult(intent,99);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            switch (resultCode) {
                case 99:
                    tv_citylocation.setText(data.getStringExtra("lngCityName"));
                    break;
                default:
                    break;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public  class  OverallImgBtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.ovreall_imagebtn_jiudian:
                     intent= new Intent(OverallActivity.this,ImageListActivity.class);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("city",tv_citylocation.getText());
                    intent.putExtra(ImageLoaderConstants.Extra.IMAGES, IMAGES);
                    startActivity(intent);
                    break;
                case R.id.ovreall_imagebtn_dingwei:
                   intent= new Intent(OverallActivity.this,LocationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ovreall_imagebtn_dingdan:
                    intent= new Intent(OverallActivity.this,BillDataActivity.class);
                    intent.putExtra("user_name",user_name);
                    startActivity(intent);
                    break;
                case R.id.ovreall_imagebtn_pinglun:
                     intent= new Intent(OverallActivity.this,CommentActivity.class);
                    intent.putExtra("user_name",user_name);
                    startActivity(intent);
                    break;
                case R.id.ovreall_imagebtn_yonghu:
                    intent= new Intent(OverallActivity.this,UserActivity.class);
                    intent.putExtra("user_name",user_name);
                    startActivity(intent);
                    break;
                case R.id.ovreall_imagebtn_guanyu:
                     intent= new Intent(OverallActivity.this,HotelAboutActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        for(ImageView indicator : mIndicators) {
            indicator.setImageResource(R.drawable.indicator_unchecked);
        }
        mIndicators[position].setImageResource(R.drawable.indicator_checked);
    }

    @Override
    public void run() {
        if (mBannerPosition == FAKE_BANNER_SIZE - 1) {
            mBanner.setCurrentItem(DEFAULT_BANNER_SIZE - 1, false);
        } else {
            mBanner.setCurrentItem(mBannerPosition);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }
    private class  DrawerOnItemClickListener implements  ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {
//                intent = new Intent(OverallActivity.this, ImageListActivity.class);
//                intent.putExtra(ImageLoaderConstants.Extra.IMAGES, IMAGES);
//                startActivity(intent);
                startActivity(new Intent(OverallActivity.this,UserActivity.class));
            }
            if (position == 1)
            {
                startActivity(new Intent(OverallActivity.this,BillDataActivity.class));
            }
            if (position==2){
                startActivity(new Intent(OverallActivity.this,CommentActivity.class));
            }
            if (position==3){
                imageLoader.clearMemoryCache();
                Toast.makeText(OverallActivity.this,"清楚内存缓存成功！",Toast.LENGTH_SHORT).show();
            }
            if (position==4){
                imageLoader.clearDiscCache();
                Toast.makeText(OverallActivity.this,"清楚内存缓存成功！",Toast.LENGTH_SHORT).show();
            }
            if (position==5){
                startActivity(new Intent(OverallActivity.this,HotelAboutActivity.class));
            }
            if (position==6)
            {
                AlertDialog.Builder builder= new AlertDialog.Builder(OverallActivity.this);
                builder.setTitle("警告！");
                builder.setMessage("是否退出？");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut(OverallActivity.this);
                        BmobUser Current= BmobUser.getCurrentUser(OverallActivity.this);
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        }
    }


    private class BannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private LayoutInflater mInflater;

        public BannerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return FAKE_BANNER_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= DEFAULT_BANNER_SIZE;
            View view = mInflater.inflate(R.layout.overall_banner_item, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(mImagesSrc[position]);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OverallActivity.this, "click banner item :" + pos, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mBanner.getCurrentItem();
            Log.d(TAG, "finish update before, position=" + position);
            if (position == 0) {
                position = DEFAULT_BANNER_SIZE;
                mBanner.setCurrentItem(position, false);
            } else if (position == FAKE_BANNER_SIZE - 1) {
                position = DEFAULT_BANNER_SIZE - 1;
                mBanner.setCurrentItem(position, false);
            }


            Log.d(TAG, "finish update after, position=" + position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mBannerPosition = position;
            setIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


}
