package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.Bill;
import com.example.hotelreservation.bean.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Plix on 2016/12/6.
 */

public class BillReviseActivity extends Activity {
    @InjectView(R.id.recise_ok)
    Button reciseOk;
    @InjectView(R.id.recise_hotel_name)
    TextView reciseHotelName;
    @InjectView(R.id.recise_hotel_tell)
    TextView reciseHotelTell;
    @InjectView(R.id.recise_hotel_other_hanzao)
    TextView reciseHotelOtherHanzao;
    @InjectView(R.id.recise_hotel_other_windows)
    TextView reciseHotelOtherWindows;
    @InjectView(R.id.recise_calendar_ed_ruzhu)
    EditText reciseCalendarEdRuzhu;
    @InjectView(R.id.recise_calendar_btn_ruzhu)
    Button reciseCalendarBtnRuzhu;
    @InjectView(R.id.recise_calendar_ed_lidian)
    EditText reciseCalendarEdLidian;
    @InjectView(R.id.recise_calendar_btn_lidian)
    Button reciseCalendarBtnLidian;
    @InjectView(R.id.recise_hotel_address)
    TextView reciseHotelAddress;
    @InjectView(R.id.recise_hotel_price)
    TextView reciseHotelPrice;
    @InjectView(R.id.recise_out)
    Button reciseOut;
    @InjectView(R.id.recise_del)
    Button reciseDel;
    @InjectView(R.id.recise_type_spinner)
    Spinner reciseTypeSpinner;
    private String name;
    private String tell;
    private String address;
    private String type;
    private String price;
    private String hanzao;
    private String windows;
    private String ruzhu;
    private String lidian;
    public final static int Calendarruzhu = 1;
    public final static int Calendarlidian = 2;
    private String time1, time2;
    private static int days = 0;
    private int jiage;
    private List<Map<String,String>> listroom= new ArrayList<Map<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_recise);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        tell = intent.getStringExtra("tell");
        address = intent.getStringExtra("address");
        type = intent.getStringExtra("type");
        price = intent.getStringExtra("price");
        hanzao = intent.getStringExtra("hanzao");
        windows = intent.getStringExtra("windows");
        ruzhu = intent.getStringExtra("ruzhu");
        lidian = intent.getStringExtra("lidian");
        reciseHotelName.setText(name);
        reciseHotelTell.setText(tell);
        reciseHotelAddress.setText(address);
        reciseHotelPrice.setText(price);
        reciseHotelOtherHanzao.setText(hanzao);
        reciseHotelOtherWindows.setText(windows);
        reciseCalendarEdRuzhu.setText(ruzhu);
        reciseCalendarEdLidian.setText(lidian);
        time1 = reciseCalendarEdRuzhu.getText().toString();
        roomfind();



    }

    @OnClick({R.id.recise_ok, R.id.recise_calendar_btn_ruzhu, R.id.recise_calendar_btn_lidian, R.id.recise_out, R.id.recise_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recise_ok:
                billrevise();
                break;
            case R.id.recise_calendar_btn_ruzhu:
                showDialog(Calendarruzhu);
                break;
            case R.id.recise_calendar_btn_lidian:
                showDialog(Calendarlidian);
                time();
                int fangjia = Integer.parseInt(price.trim());
//                            //Integer.valueOf(price).intValue();
                jiage = fangjia * days;
                reciseHotelPrice.setText(jiage + "");

                break;
            case R.id.recise_out:
                startActivity(new Intent(BillReviseActivity.this, BillDataActivity.class));
                finish();
                break;
            case R.id.recise_del:
                break;
        }
    }
    private  void calender(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar1= Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH,+1);
        String lidianriqi=sdf.format(calendar1.getTime());
        reciseCalendarEdLidian.setText(lidianriqi);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case Calendarruzhu:
                final Calendar calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp,  int year, int month, int dayOfMonth) {
                        month=month+1;
                        String yue=(month+"").length()==1?"0"+month:month+"";
                        String ri=(dayOfMonth+"").length()==1?"0"+dayOfMonth:dayOfMonth+"";

                        reciseCalendarEdRuzhu.setText(year + "/" + yue + "/" + ri);


                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)

                );
                break;
            case Calendarlidian:
                final Calendar calendar1 = Calendar.getInstance();
                dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String yue=(month+"").length()==1?"0"+month:month+"";
                        String ri=(dayOfMonth+"").length()==1?"0"+dayOfMonth:dayOfMonth+"";
                        reciseCalendarEdLidian.setText(year + "/" + yue + "/" + ri);
                        time2 = reciseCalendarEdLidian.getText().toString();


                    }
                }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH) + 1, calendar1.get(Calendar.DAY_OF_MONTH));
        }
        return dialog;
    }

    private void time() {
        SimpleDateFormat num1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat num2 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = num1.parse(time1);
            Date date2 = num2.parse(time2);
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date, date2));
            Toast.makeText(BillReviseActivity.this, "两个日期的差距：" + differentDaysByMillisecond(date, date2), Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));

        return days;
    }

    private void billrevise() {
        final ProgressDialog progressDialog = new ProgressDialog(BillReviseActivity.this);
        progressDialog.setMessage("正在修改中....");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        Bill bill = new Bill();
        bill.setRuzhu(reciseCalendarEdRuzhu.getText().toString());
        bill.setLidian(reciseCalendarEdLidian.getText().toString());
        bill.setPrice(jiage + "");
        bill.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(BillReviseActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(BillReviseActivity.this, BillDataActivity.class));
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(BillReviseActivity.this, "修改错误" + i + s, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  int  spfind(List<Map<String,String>> listroom){
        for (int i= 0;i<listroom.size();i++)
        {
            if (listroom.get(i).get("spname").equals(type))
            {
                return i;
            }

        }
        return 0;

    }
    private  void roomfind(){

        BmobQuery<Room> room= new BmobQuery<Room>();
        room.order("-createdAt");
        room.findObjects(this, new FindListener<Room>() {
            @Override
            public void onSuccess(List<Room> list) {
                for (Room r:list)
                {
                    Map map= new HashMap();
                    map.put("spname",r.getName());
                    map.put("spprice",r.getPrice());
                    listroom.add(map);

                }
                SimpleAdapter simpleAdapter= new SimpleAdapter(BillReviseActivity.this,listroom,android.R.layout.simple_list_item_1,new String[]{"spname"},new int[]{android.R.id.text1});
                simpleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                reciseTypeSpinner.setAdapter(simpleAdapter);
                reciseTypeSpinner.setSelection(spfind(listroom));
                reciseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Map m= (Map) reciseTypeSpinner.getSelectedItem();
                        String a= m.get("spprice").toString();
                       // Map map1= (Map) parent.getItemAtPosition(position);
                        Toast.makeText(BillReviseActivity.this,a,Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(BillReviseActivity.this, "房型查询失败，请稍后再试....." + i + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
