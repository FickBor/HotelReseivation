package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.Bill;
import com.example.hotelreservation.bean.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/11/19.
 */

public class OrdersActivity extends Activity {
    private  String name;
    private  String tell;
    private  String address;
    private  String type;
    private  String price;
    private String hanzao;
    private  String windows;
    public   final  static  int Calendarruzhu= 1;
    public   final  static  int Calendarlidian= 2;
    private String tianshu1;
    private String tianshu2;
    private int jiage;
    public static int days=0;
    private  String user_name;




    private TextView orders_name,orders_tell,orders_address,orders_type,orders_hanzao,orders_windows,orders_price;
    private EditText orders_et_ruzhu,orders_et_lidian;
    private Button orders_btn_ruzhu,orders_btn_lidian ,orders_btn_ok,orders_btn_noerror,orders_btn_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_main);
        IntentData();
        findbyid();
        Intent intent= getIntent();
        user_name=intent.getStringExtra("user_name");
        orders_name.setText(name);
        orders_tell.setText(tell);
        orders_address.setText(address);
        orders_type.setText(type);
        orders_hanzao.setText(hanzao);
        orders_price.setText(price);
        orders_windows.setText(windows);
       // orders_price.setText(jiage);
        calender();
        tianshu1= orders_et_ruzhu.getText().toString();





    }
    private  void  IntentData(){
        Intent intent =getIntent();
        name= intent.getStringExtra("name");
        tell= intent.getStringExtra("tell");
        address=intent.getStringExtra("address");
        type=intent.getStringExtra("type");
        price=intent.getStringExtra("price");
        hanzao=intent.getStringExtra("hanzao");
        windows= intent.getStringExtra("windows");
    }
    private  void findbyid(){
//        orders_name= (TextView) findViewById(R.id.orders_hotel_name);
        orders_name=(TextView) findViewById(R.id.orders_hotel_name);
        orders_tell  =(TextView)findViewById(R.id.orders_hotel_tell);
        orders_address=(TextView)findViewById(R.id.orders_hotel_address);
        orders_hanzao=(TextView)findViewById(R.id.orders_hotel_other_hanzao);
        orders_windows=(TextView)findViewById(R.id.orders_hotel_other_windows);
        orders_type=(TextView)findViewById(R.id.orders_hotel_type);
        orders_price=(TextView) findViewById(R.id.orders_hotel_price);
       orders_et_ruzhu=(EditText) findViewById(R.id.orders_calendar_ed_ruzhu);
        orders_et_lidian=(EditText)findViewById(R.id.orders_calendar_ed_lidian);
        orders_btn_ruzhu=(Button) findViewById(R.id.orders_calendar_btn_ruzhu);
        orders_btn_lidian=(Button) findViewById(R.id.orders_calendar_btn_lidian);
       orders_btn_ok=(Button) findViewById(R.id.orders_ok);
        orders_btn_out=(Button) findViewById(R.id.orders_out);
        orders_btn_out.setOnClickListener(new onclick());
        orders_btn_ok.setOnClickListener(new onclick());
        orders_btn_ruzhu.setOnClickListener( new onclick());
        orders_btn_lidian.setOnClickListener(new onclick());

    }
    private  void calender(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String ruzhushijian= sdf.format(calendar.getTime());
        orders_et_ruzhu.setText(ruzhushijian);
        Calendar calendar1= Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH,+1);
        String lidianriqi=sdf.format(calendar1.getTime());
        orders_et_lidian.setText(lidianriqi);
    }
    private void time(){
        SimpleDateFormat num1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat num2 = new SimpleDateFormat("yyyy/MM/dd");
        try
        {
            Date date = num1.parse(tianshu1);
            Date date2 = num2.parse(tianshu2);
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
            Toast.makeText(OrdersActivity.this,"两个日期的差距：" + differentDaysByMillisecond(date,date2),Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
         days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));

        return days;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case Calendarruzhu:
                final Calendar calendar= Calendar.getInstance();
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp,   int nian, int month,  int dayOfMonth) {
                        month= month+1;
                        String yue=(month+"").length()==1?"0"+month:month+"";
                        String tian=(dayOfMonth+"").length()==1?"0"+dayOfMonth:dayOfMonth+"";

                        orders_et_ruzhu.setText(nian+"/"+yue+"/"+tian);
                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH)

                );
                break;
            case  Calendarlidian:
                final  Calendar calendar1= Calendar.getInstance();
                dialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month,int dayOfMonth) {
                        month= month+1;
                        String yue=(month+"").length()==1?"0"+month:month+"";
                        String ri= (dayOfMonth+"").length()==1?"0"+dayOfMonth:dayOfMonth+"";
                        orders_et_lidian.setText(year+"/"+yue+"/"+ri);
                        tianshu2= orders_et_lidian.getText().toString();
                        time();
                        int fangjia=Integer.parseInt(price.trim());
//                            //Integer.valueOf(price).intValue();
                        jiage=fangjia*days;
                        orders_price.setText(jiage+"");


                }
                },calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH)+1, calendar1.get(Calendar.DAY_OF_MONTH));
        }
        return dialog;
    }

    class onclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.orders_calendar_btn_ruzhu:
                    showDialog(Calendarruzhu);
                    break;
                case R.id.orders_calendar_btn_lidian:
                    showDialog(Calendarlidian);

                    break;
                case R.id.orders_ok:
                    billdataadd();
                    break;

                case R.id.orders_out:
                    Intent intent = new Intent(OrdersActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    }
    private  void billdataadd(){
        final ProgressDialog progressDialog= new ProgressDialog(OrdersActivity.this);
        progressDialog.setMessage("正在添加，请稍后.....");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        User user= BmobUser.getCurrentUser(this,User.class);
        Bill bill= new Bill();
        bill.setOwner(user);
        bill.setName(name);
        bill.setAddress(address);
        bill.setTell(tell);
        bill.setType(type);
        bill.setRuzhu(orders_et_ruzhu.getText().toString());
        bill.setLidian(orders_et_lidian.getText().toString());
        bill.setHanzao(hanzao);
        bill.setWindows(windows);
        bill.setPrice(orders_price.getText().toString());
        bill.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(OrdersActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OrdersActivity.this,ImageListActivity.class);
//                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(OrdersActivity.this,"添加失败",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
