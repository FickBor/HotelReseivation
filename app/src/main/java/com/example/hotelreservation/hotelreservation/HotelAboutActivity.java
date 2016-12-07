package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Plix on 2016/11/24.
 */

public class HotelAboutActivity extends Activity {
    @InjectView(R.id.tv_summary)
    TextView tvSummary;
    @InjectView(R.id.tv_author)
    TextView tvAuthor;
    @InjectView(R.id.tv_qq)
    TextView tvQq;
    @InjectView(R.id.tv_email)
    TextView tvEmail;
    @InjectView(R.id.tv_affect)
    TextView tvAffect;
    @InjectView(R.id.btn_back)
    Button btnBack;
    @InjectView(R.id.hotelreserva_action_head_title)
    TextView hotelreservaActionHeadTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        hotelreservaActionHeadTitle.setText("关于");
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        startActivity( new Intent(this,OverallActivity.class));
    }
}
