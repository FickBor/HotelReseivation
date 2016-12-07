package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/8.
 */

public class UserActivity extends Activity {
    public ImageLoader imageLoader = ImageLoader.getInstance();

    private TextView tvName;

    private TextView tvAge;

    private TextView tvSet;

    private TextView tvBangding;

    private TextView tvZiliao;

    private TextView tvMima;

    private TextView tvDingdan;

    private TextView tvNeicun;
    private  TextView title;
    private Button back;

    private TextView tvSd;
    private TextView tvAbout;
    private TextView tvTell;
    private TextView tvTuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_mian);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvSet = (TextView) findViewById(R.id.tv_set);
        tvZiliao = (TextView) findViewById(R.id.tv_ziliao);
        tvDingdan = (TextView) findViewById(R.id.tv_dingdan);
        tvNeicun = (TextView) findViewById(R.id.tv_neicun);
        tvSd = (TextView) findViewById(R.id.tv_sd);
        tvAbout = (TextView) findViewById(R.id.tv_about);
        tvTell = (TextView) findViewById(R.id.tv_tell);
        tvMima = (TextView) findViewById(R.id.tv_mima);
        tvBangding = (TextView) findViewById(R.id.tv_bangding);
        tvTuichu= (TextView) findViewById(R.id.tv_tuichu);
        back=(Button)findViewById(R.id.btn_back);
        title=(TextView)findViewById(R.id.hotelreserva_action_head_title);
        ButterKnife.inject(this);
        title.setText("用户");
        seleUser();
    }

    private void UpdateUser() {
       User user = BmobUser.getCurrentUser(this, User.class);
        if (user != null && user.getMobilePhoneNumberVerified() != null && user.getMobilePhoneNumberVerified()) {
            tvBangding.setText("修改绑定手机号码");
            tvTell.setText(user.getMobilePhoneNumber().toString());
            tvMima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this, ResetPasswordActivity.class));
                }
            });
        } else {
            tvMima.setText("未绑定手机，不能使用手机重置密码".toString());
        }
        tvName.setText(user.getUsername().toString());


        //tv_user.setText(user.getUsername() + "-" + user.getAge() + "-" + user.getMobilePhoneNumberVerified() + "-" + user.getMobilePhoneNumber());
    }
    private  void seleUser(){
        User user = BmobUser.getCurrentUser(this, User.class);
        if (user!=null&&user.getAge()!=null&&user.getSet()!=null){
            tvAge.setText(user.getAge().toString());
            tvSet.setText(user.getSet().toString());
            tvZiliao.setText("修改用户资料");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        UpdateUser();
    }

    @OnClick({R.id.tv_bangding,R.id.tv_ziliao, R.id.tv_dingdan, R.id.tv_neicun, R.id.tv_sd, R.id.tv_about,R.id.btn_back,R.id.tv_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.tv_bangding:
                startActivity(new Intent(this,UserBindPhoneActivity.class));
                break;
            case R.id.tv_ziliao:
               Intent intent = new Intent(this,UserDataModifyActivity.class);
                intent.putExtra("name",tvName.getText()+"");
                startActivity(intent);
                break;
            case R.id.tv_dingdan:
                startActivity(new Intent(this,BillDataActivity.class));
                break;
            case R.id.tv_neicun:
                imageLoader.clearMemoryCache();
                Toast.makeText(this,"清楚内存缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_sd:
                imageLoader.clearDiscCache();
                Toast.makeText(this,"清楚sd卡缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_about:
                startActivity(new Intent(this,HotelAboutActivity.class));
                break;
            case R.id.btn_back:
                startActivity(new Intent(this,OverallActivity.class));
                break;
            case R.id.tv_tuichu:
                AlertDialog.Builder builder= new AlertDialog.Builder(UserActivity.this);
                builder.setTitle("警告！");
                builder.setMessage("是否退出？");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser.logOut(UserActivity.this);
                        BmobUser Current= BmobUser.getCurrentUser(UserActivity.this);
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
                break;
        }
    }
}
