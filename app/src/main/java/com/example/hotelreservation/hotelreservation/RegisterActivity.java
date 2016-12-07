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
import cn.bmob.v3.listener.SaveListener;


/**
 * 登注册
 * Created by Plix on 2016/11/22.
 *   
 */
public class RegisterActivity extends LoginBaseActivity {
	
	@InjectView(R.id.iv_left)
	ImageView iv_left;
	@InjectView(R.id.tv_title)
	TextView tv_title;
	
	@InjectView(R.id.et_account)
	EditText et_account;
	@InjectView(R.id.et_password)
	EditText et_password;
	
	@InjectView(R.id.et_pwd_again)
	EditText et_pwd_again;
	
	@InjectView(R.id.btn_register)
	Button btn_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ButterKnife.inject(this);
		iv_left.setVisibility(View.VISIBLE);
		tv_title.setText("注册");
	}

	@OnClick(R.id.iv_left)
	public void back() {
		finish();
	}
	
	
	@OnClick(R.id.btn_register)
	public void register() {
		registerUser();
	}
	
	/**
	 */
	private void registerUser(){
		String account = et_account.getText().toString();
		String password = et_password.getText().toString();
		String pwd = et_pwd_again.getText().toString();
		if (TextUtils.isEmpty(account)) {
			showToast("用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showToast("密码不能为空");
			return;
		}
		if (!password.equals(pwd)) {
			showToast("两次密码不一样");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册，请稍后" + "...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final User user = new User();
		user.setUsername(account);
		user.setPassword(password);
		user.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				//toast("注册成功---用户名："+user.getUsername()+"，年龄："+user.getAge());
				toast("注册成功---用户名："+user.getUsername()+"正在返回登录页面");
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				//intent.putExtra("from", "login");
				startActivity(intent);
				finish();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				toast("注册失败：code="+arg0+"，错误描述："+arg1);
				System.out.println("注册失败：code="+arg0+"，错误描述："+arg1);
			}
		});
	}
	
	
}
