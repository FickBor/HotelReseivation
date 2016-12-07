package com.example.hotelreservation.hotelreservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Plix on 2016/10/22.
 */

public class FirstActivity extends Activity implements  Runnable{
    private  final Handler handler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        handler.postDelayed(this,3000);
    }

    @Override
    public void run() {
        Intent intent = new Intent(FirstActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
