package com.example.hotelreservation.hotelreservation;



import static com.example.hotelreservation.hotelreservation.R.id.tv_photo;
import static com.example.hotelreservation.hotelreservation.R.id.tv_describe;
import static com.example.hotelreservation.hotelreservation.R.id.tv_time;
import static com.example.hotelreservation.hotelreservation.R.id.tv_title;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import  com.example.hotelreservation.adapter.BaseAdapterHelper;
import  com.example.hotelreservation.adapter.QuickAdapter;
import  com.example.hotelreservation.base.EditPopupWindow;
import com.example.hotelreservation.bean.Lost;
import com.example.hotelreservation.tools.Constants;
import  com.example.hotelreservation.uitl.IPopupItemClick;



import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
/**
 * Created by Administrator on 2016/11/8.
 */

public class CommentActivity extends BaseActivity implements OnClickListener,
        IPopupItemClick, OnItemLongClickListener  {


    private  RelativeLayout layout_action;
    private LinearLayout layout_all;
    private TextView tv_lost;
    private ListView listview;
    private Button btn_add;
    private Button btn_back;

        protected QuickAdapter<Lost> LostAdapter;
        private Button layout_found;
        private Button layout_lost;
        PopupWindow morePop;

        RelativeLayout progress;
        LinearLayout layout_no;
        TextView tv_no;

        @Override
        public void setContentView() {
            // TODO Auto-generated method stub
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.comment_main);
        }

        @Override
        public void initViews() {
            // TODO Auto-generated method stub
            progress = (RelativeLayout) findViewById(R.id.progress);
            layout_no = (LinearLayout) findViewById(R.id.layout_no);
            tv_no = (TextView) findViewById(R.id.tv_no);

            layout_action = (RelativeLayout) findViewById(R.id.layout_action);
            layout_all = (LinearLayout) findViewById(R.id.layout_all);
            tv_lost = (TextView) findViewById(R.id.tv_lost);
            tv_lost.setTag("Lost");
            listview = (ListView) findViewById(R.id.list_lost);
            btn_add = (Button) findViewById(R.id.btn_add);
            btn_back=(Button) findViewById(R.id.btn_back);
            // 初始化长按弹窗
            initEditPop();
        }

        @Override
        public void initListeners() {
            // TODO Auto-generated method stub
            listview.setOnItemLongClickListener(this);
            btn_add.setOnClickListener(this);
            layout_all.setOnClickListener(this);
            btn_back.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == layout_all) {
                showListPop();
            } else if (v == btn_add) {
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("from", tv_lost.getTag().toString());
                startActivityForResult(intent, Constants.REQUESTCODE_ADD);
            } else if (v == layout_found) {
                changeTextView(v);
                morePop.dismiss();
            } else if (v == layout_lost) {
                changeTextView(v);
                morePop.dismiss();
                queryLosts();
            }else if(v==btn_back){
                Intent intent= new Intent(this,OverallActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void initData() {
            // TODO Auto-generated method stub
            if (LostAdapter == null) {
                LostAdapter = new QuickAdapter<Lost>(this, R.layout.item_list) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, Lost lost) {
                        helper.setText(tv_title, lost.getTitle())
                                .setText(tv_describe, lost.getDescribe())
                                .setText(tv_time, lost.getCreatedAt())
                                .setText(tv_photo, lost.getPhone());
                    }
                };
            }


            listview.setAdapter(LostAdapter);
            queryLosts();
        }

    private void changeTextView(View v) {
        if (v == layout_found) {
            tv_lost.setTag("Found");
            tv_lost.setText("Found");
        } else {
            tv_lost.setTag("Lost");
            tv_lost.setText("Lost");
        }
    }

    private void showListPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_lost, null);
        // 注入
        layout_found = (Button) view.findViewById(R.id.layout_found);
        layout_lost = (Button) view.findViewById(R.id.layout_lost);
        layout_found.setOnClickListener(this);
        layout_lost.setOnClickListener(this);
        morePop = new PopupWindow(view, mScreenWidth, 600);

        morePop.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    morePop.dismiss();
                    return true;
                }
                return false;
            }
        });

        morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        morePop.setTouchable(true);
        morePop.setFocusable(true);
        morePop.setOutsideTouchable(true);
        morePop.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从顶部弹下
        morePop.setAnimationStyle(R.style.MenuPop);
        morePop.showAsDropDown(layout_action, 0, -dip2px(this, 2.0F));
    }

    private void initEditPop() {
        mPopupWindow = new EditPopupWindow(this, 200, 48);
        mPopupWindow.setOnPopupItemClickListner(this);
    }

    EditPopupWindow mPopupWindow;
    int position;

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
        // TODO Auto-generated method stub
        position = arg2;
        int[] location = new int[2];
        arg1.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(arg1, Gravity.RIGHT | Gravity.TOP,
                location[0], getStateBar() + location[1]);
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.REQUESTCODE_ADD:// 添加成功之后的回调
                String tag = tv_lost.getTag().toString();
                if (tag.equals("Lost")) {
                    queryLosts();
                }
                break;
        }
    }

    private void queryLosts() {
        showView();
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.order("-createdAt");// 按照时间降序
        query.findObjects(this, new FindListener<Lost>() {

            @Override
            public void onSuccess(List<Lost> losts) {
                // TODO Auto-generated method stub
                LostAdapter.clear();
                if (losts == null || losts.size() == 0) {
                    showErrorView(0);
                    LostAdapter.notifyDataSetChanged();
                    return;
                }
                progress.setVisibility(View.GONE);
                LostAdapter.addAll(losts);
                listview.setAdapter(LostAdapter);
            }

            @Override
            public void onError(int code, String arg0) {
                // TODO Auto-generated method stub
                showErrorView(0);
            }
        });
    }


    /**
     * 请求出错或者无数据时候显示的界面 showErrorView

     */
    private void showErrorView(int tag) {
        progress.setVisibility(View.GONE);
        listview.setVisibility(View.GONE);
        layout_no.setVisibility(View.VISIBLE);
        if (tag == 0) {
            tv_no.setText(getResources().getText(R.string.list_no_data_lost));
        } else {
            tv_no.setText(getResources().getText(R.string.list_no_data_found));
        }
    }

    private void showView() {
        listview.setVisibility(View.VISIBLE);
        layout_no.setVisibility(View.GONE);
    }

    @Override
    public void onEdit(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        Intent intent = new Intent(this, AddActivity.class);
        String title = "";
        String describe = "";
        String phone = "";
        if (tag.equals("Lost")) {
            title = LostAdapter.getItem(position).getTitle();
            describe = LostAdapter.getItem(position).getDescribe();
            phone = LostAdapter.getItem(position).getPhone();
        }
        intent.putExtra("describe", describe);
        intent.putExtra("phone", phone);
        intent.putExtra("title", title);
        intent.putExtra("from", tag);
        startActivityForResult(intent, Constants.REQUESTCODE_ADD);
    }

    @Override
    public void onDelete(View v) {
        // TODO Auto-generated method stub
        String tag = tv_lost.getTag().toString();
        if (tag.equals("Lost")) {
            deleteLost();
        }
    }

    private void deleteLost() {
        Lost lost = new Lost();
        lost.setObjectId(LostAdapter.getItem(position).getObjectId());
        lost.delete(this, new DeleteListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                LostAdapter.remove(position);
            }

            @Override
            public void onFailure(int code, String arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


}
