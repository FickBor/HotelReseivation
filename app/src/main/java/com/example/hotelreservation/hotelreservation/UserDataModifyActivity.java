package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/11/23.
 */

public class UserDataModifyActivity extends Activity {
    @InjectView(R.id.btn_back)
    Button btnBack;
    @InjectView(R.id.hotelreserva_action_head_title)
    TextView hotelreservaActionHeadTitle;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.et_user_age)
    EditText etUserAge;

    @InjectView(R.id.btn_confirm)
    Button btnConfirm;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.radio_nan)
    RadioButton radioNan;
    @InjectView(R.id.radio_nv)
    RadioButton radioNv;
    private    String radio_option,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_datamodify);
        ButterKnife.inject(this);
        hotelreservaActionHeadTitle.setText("用户资料");
        Intent intent = getIntent();
        radioNan.setChecked(true);
         name = intent.getStringExtra("name");
        tvUserName.setText(name);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioNan.getId()) {
                    radio_option =radioNan.getText().toString();

                } else if (checkedId == radioNv.getId()) {
                    radio_option =radioNv.getText().toString();

                }
            }
        });
    }

    @OnClick({R.id.btn_back, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                startActivity(new Intent(this, UserActivity.class));
                break;
            case R.id.btn_confirm:
                modifyUser();

                break;
        }
    }

    private void modifyUser() {

        User user = BmobUser.getCurrentUser(this, User.class);
        if (user != null) {
            Toast.makeText(this, user.getObjectId() + "", Toast.LENGTH_SHORT).show();
        }


        int age = Integer.parseInt(etUserAge.getText().toString());
        if (TextUtils.isEmpty(age + "")) {
            Toast.makeText(this, "年龄不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (radio_option.equals("")) {
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progress = new ProgressDialog(UserDataModifyActivity.this);
        progress.setMessage("正在完善，请稍后" + "...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        final User user1 = new User();
//        User user= new User();
        user1.setAge(age);
        user1.setSet(radio_option);
        Toast.makeText(this,radio_option,Toast.LENGTH_SHORT).show();
//        user.signUp(this, new SaveListener() {
//
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//                progress.dismiss();
//                Intent intent = new Intent(UserDataModifyActivity.this,UserActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFailure(int arg0, String arg1) {
//                // TODO Auto-generated method stub
//                Toast.makeText(UserDataModifyActivity.this,"注册失败：code="+arg0+"，错误描述："+arg1,Toast.LENGTH_SHORT).show();
//                System.out.println("注册失败：code="+arg0+"，错误描述："+arg1);
//            }
//        });
        System.out.println();
        user1.update(this, user.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                progress.dismiss();
                Intent intent = new Intent(UserDataModifyActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserDataModifyActivity.this, "修改错误：code=" + i + "，错误描述：" + s, Toast.LENGTH_SHORT).show();
                System.out.println("修改错误失败：code=" + i + "，错误描述：" + s);
            }
        });
    }
}
