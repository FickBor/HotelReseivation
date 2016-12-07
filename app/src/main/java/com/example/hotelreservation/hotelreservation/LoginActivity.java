package com.example.hotelreservation.hotelreservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelreservation.bean.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**
 * 登录
 * Created by Plix on 2016/11/22.
 */

public class LoginActivity extends LoginBaseActivity {


    //priv  ate static final String BMOB_APP_KEY="46c730e7e33eabeb3ec790b3fb0a02d7";
    //private static final String BMOB_APP_KEY = "53bf922fdd6253628f9b82ea10ecafd0";

    @InjectView(R.id.iv_left)
    ImageView iv_left;
    @InjectView(R.id.et_account)
    EditText et_account;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.btn_login)
    Button btn_login;
    @InjectView(R.id.btn_onekey)
    Button btn_onekey;
//    @InjectView(R.id.btn_register)
//    Button btn_register;
    @InjectView(R.id.tv_register)
    TextView tvRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        //初始化Bmob sdk
//        Bmob.initialize(this, "53bf922fdd6253628f9b82ea10ecafd0");

    }

    @OnClick(R.id.iv_left)
    public void back() {
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login(View view) {
        login();
    }
//
//    @OnClick(R.id.btn_register)
//    public void register() {
//        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//        startActivity(intent);
//    }

    @OnClick(R.id.btn_onekey)
    public void oneKey() {
        Intent intent = new Intent(LoginActivity.this, LoginOneKeyActivity.class);
        startActivity(intent);
    }

    /**
     * 登陆操作
     *
     * @return void
     * @throws
     * @method login
     */
    private void login() {
        String account = et_account.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showToast("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }
        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("正在登录中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        //V3.3.9提供的新的登录方式，可传用户名/邮箱/手机号码
        BmobUser.loginByAccount(this, account, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException ex) {
                // TODO Auto-generated method stub
                progress.dismiss();
                if (ex == null) {
                    toast("登录成功---用户名：" + user.getUsername() + "，年龄：" + user.getAge());
                    Intent intent = new Intent(LoginActivity.this, OverallActivity.class);
                    intent.putExtra("user_name",user.getUsername());
                    intent.putExtra("from", "login");
                    startActivity(intent);
                    finish();
                } else {
                    toast("登录失败：code=" + ex.getErrorCode() + "，错误描述：" + ex.getLocalizedMessage());
                    System.out.println("登录失败：code=" + ex.getErrorCode() + "，错误描述：" + ex.getLocalizedMessage());
                }


            }
        });
    }

    @OnClick(R.id.tv_register)
    public void onClick() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
