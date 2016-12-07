package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.Lost;
import com.example.hotelreservation.bean.person;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/11.
 */

public class AddActivity extends BaseActivity implements View.OnClickListener {
    EditText edit_title, edit_manyi, edit_describe;
    private AutoCompleteTextView buquan;
    private Spinner sp_manyi;
    Button btn_back, btn_true;
    private List<String> queryname= new ArrayList<>();
    private  String[] name={};
    private String[] sp={"非常满意","比较满意","一般满意","不满意"};
    private String option;

    TextView tv_add;
    String from = "";

    String old_title = "";
    String old_describe = "";

    String old_phone = "";


       @Override
    public void setContentView() {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_add);
    }

    @Override
    public void initViews() {
        // TODO Auto-generated method stub
        tv_add = (TextView) findViewById(R.id.tv_add);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_true = (Button) findViewById(R.id.btn_true);
//        edit_manyi = (EditText) findViewById(R.id.edit_manyi);
        edit_describe = (EditText) findViewById(R.id.edit_describe);
        buquan=(AutoCompleteTextView) findViewById(R.id.auto_buquan);
        sp_manyi=(Spinner) findViewById(R.id.sp_manyi);

    }

    @Override
    public void initListeners() {
        // TODO Auto-generated method stub
        btn_back.setOnClickListener(this);
        btn_true.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        from = getIntent().getStringExtra("from");
        old_title = getIntent().getStringExtra("title");
        old_phone = getIntent().getStringExtra("phone");
        old_describe = getIntent().getStringExtra("describe");
      ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,queryname);
        buquan.setAdapter(adapter);
        ArrayAdapter<String> spadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sp);
        spadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_manyi.setAdapter(spadapter);
        sp_manyi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option= (String) parent.getItemAtPosition(position);
                Toast.makeText(AddActivity.this,option,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        query();
       // edit_title.setText(old_title);
        edit_describe.setText(old_describe);
//        edit_manyi.setText(old_phone);

        if (from.equals("Lost")) {
            tv_add.setText("添加评论信息");
        } else {
            tv_add.setText("添加评论信息");
        }
    }

    private  void query(){
        BmobQuery<person> personBmobQuery= new BmobQuery<person>();
        personBmobQuery.addQueryKeys("name");
        personBmobQuery.findObjects(this, new FindListener<person>() {
            @Override
            public void onSuccess(List<person> list) {
                for (person q:list)
                {
                    queryname.add(q.getName());

                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btn_true) {
            addByType();
        } else if (v == btn_back) {
            finish();
        }
    }
    String title = "";
    String describe = "";
    String pleased="";


    private void addByType(){
        title = buquan.getText().toString();
        describe = edit_describe.getText().toString();
//        photo = edit_manyi.getText().toString();
        pleased=option;

        if(TextUtils.isEmpty(title)){
            ShowToast("请填写酒店名");
            return;
        }
        if(TextUtils.isEmpty(describe)){
            ShowToast("请填写评论");
            return;
        }
        if(TextUtils.isEmpty(pleased)){
            ShowToast("请填写满意程度");
            return;
        }
        if(from.equals("Lost")){
            addLost();
        }
    }

    private void addLost(){
        Lost lost = new Lost();
        lost.setDescribe(describe);
        lost.setPhone(pleased);
        lost.setTitle(title);
        lost.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                ShowToast("评论信息添加成功!");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub
                ShowToast("添加失败:"+arg0);
            }
        });
    }



}
