package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelreservation.bean.Bill;
import com.example.hotelreservation.bean.User;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Plix on 2016/11/20.
 */

public class BillDataActivity extends Activity {
    @InjectView(R.id.btn_back)
    Button btnBack;
   // @InjectView(R.id.hotelreserva_action_head_title)
    TextView hotelreservaActionHeadTitle;
    private  String user_name;
    private String name;
    private String tell;
    private String address;
    private String type;
    private String price;
    private String hanzao;
    private String windows;
    private String ruzhu;
    private String lidian;
    private ListView listview;
    private  String time1,time2;
    private static  int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.billdata_main);
        Intent intent= getIntent();
        user_name=intent.getStringExtra("user_name");
        hotelreservaActionHeadTitle=(TextView) findViewById(R.id.hotelreserva_action_head_title);
        hotelreservaActionHeadTitle.setText("订单");
        ButterKnife.inject(this);
        listview = (ListView) findViewById(R.id.billdata_lv);
        getData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill bill= (Bill) listview.getItemAtPosition(position);
                name=bill.getName();
                tell=bill.getTell();
                address=bill.getAddress();
                type=bill.getType();
                price=bill.getPrice();
                hanzao=bill.getHanzao();
                windows=bill.getWindows();
                ruzhu=bill.getRuzhu();
                lidian=bill.getLidian();
                time1=ruzhu;
                timecompare();
                if(days<0)
                {
                    Toast.makeText(BillDataActivity.this,"订单完成或者进行中，不可修改！",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent= new Intent(BillDataActivity.this,BillReviseActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("tell",tell);
                    intent.putExtra("address",address);
                    intent.putExtra("price",price);
                    intent.putExtra("hanzao",hanzao);
                    intent.putExtra("windows",windows);
                    intent.putExtra("ruzhu",ruzhu);
                    intent.putExtra("lidian",lidian);
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
            }
        });
    }
    private void timecompare(){
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        time2=sdf.format(calendar.getTime());
        SimpleDateFormat num1 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat num2 = new SimpleDateFormat("yyyy/MM/dd");
        try
        {
            Date date = num1.parse(time1);
            Date date2 = num2.parse(time2);
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
            //Toast.makeText(BillDataActivity.this,"两个日期的差距：" + differentDaysByMillisecond(date,date2),Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));

        return days;
    }

    public void GetData(List<Bill> billdata) {
        listview.setAdapter(new DetailAdapter(this, billdata));
    }

    public void getData() {
        BmobQuery<User> queryuser= new BmobQuery<User>();
        queryuser.addWhereNotEqualTo("username",user_name);
        queryuser.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                Toast.makeText(BillDataActivity.this,"chenggong",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(BillDataActivity.this,"chenggong",Toast.LENGTH_SHORT).show();

            }
        });
//        String[] userid={"sss"};
//        queryuser.addWhereContainedIn("objectId", Arrays.asList(userid));
        User user= BmobUser.getCurrentUser(this,User.class);
        final List<Bill> billdata = new ArrayList<>();
        BmobQuery<Bill> query = new BmobQuery<Bill>();
        query.addWhereEqualTo("owner",user);
        query.order("-updatedAt");
        query.findObjects(this,new FindListener<Bill>() {
            @Override
            public void onSuccess(List<Bill> roomList) {
                for (Bill r : roomList) {
                    billdata.add(r);
                }
                GetData(billdata);
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("55555");
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        Intent intent= new Intent(this,OverallActivity.class);
        startActivity(intent);
    }

    class DetailAdapter extends BaseAdapter {
        private List<Bill> data;
        private Context context;

        private DetailAdapter(Context context, List<Bill> data) {
            this.context = context;
            this.data = data;

        }

        private class ViewHolder {
            public TextView name;
            public TextView hanzao;
            public TextView windows;
            public TextView price;
            public TextView type;
            public TextView address;
            public TextView tell;
            public TextView ruzhu;
            public TextView lidian;
            private TextView shijian;
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
                convertView = getLayoutInflater().inflate(R.layout.billdata_lv_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.billdata_name);
                holder.type = (TextView) convertView.findViewById(R.id.billdata_type);
                holder.tell = (TextView) convertView.findViewById(R.id.billdata_tell);
                holder.address = (TextView) convertView.findViewById(R.id.billdata_address);
                holder.hanzao = (TextView) convertView.findViewById(R.id.billdata_hanzao);
                holder.windows = (TextView) convertView.findViewById(R.id.billdata_windows);
                holder.ruzhu = (TextView) convertView.findViewById(R.id.billdata_ruzhu);
                holder.lidian = (TextView) convertView.findViewById(R.id.billdata_lidian);
                holder.price = (TextView) convertView.findViewById(R.id.billdata_price);
                holder.shijian=(TextView)convertView.findViewById(R.id.billdata_shijian) ;

                convertView.setTag(holder);        // 给View添加一个格外的数据
            } else {
                holder = (ViewHolder) convertView.getTag(); // 把数据取出来
            }
            holder.name.setText(((Bill) getItem(position)).getName());
            holder.hanzao.setText(((Bill) getItem(position)).getHanzao());
            holder.windows.setText(((Bill) getItem(position)).getWindows());
            holder.price.setText("¥" + ((Bill) getItem(position)).getPrice());
            holder.type.setText(((Bill) getItem(position)).getType());
            holder.address.setText(((Bill) getItem(position)).getAddress());
            holder.tell.setText(((Bill) getItem(position)).getTell());
            holder.ruzhu.setText(((Bill) getItem(position)).getRuzhu());
            holder.lidian.setText(((Bill) getItem(position)).getLidian());
            holder.shijian.setText(((Bill) getItem(position)).getCreatedAt());

            return convertView;
        }
    }
}
