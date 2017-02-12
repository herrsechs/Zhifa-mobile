package com.zun.zhifa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.BarberUtil;

public class BarberMainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        preInitUI();
        setContentView(R.layout.activity_barber_main);

        RelativeLayout uploadRL = (RelativeLayout)findViewById(R.id.barber_main_upload_haircut);
        RelativeLayout pushRL = (RelativeLayout)findViewById(R.id.barber_main_push);
        RelativeLayout trendRL = (RelativeLayout)findViewById(R.id.barber_main_trend);
        RelativeLayout haircutsRL = (RelativeLayout)findViewById(R.id.barber_main_gallery);

        if (uploadRL != null) {
            uploadRL.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(BarberMainActivity.this, BarberUploadActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (pushRL != null) {
            pushRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(BarberMainActivity.this, BarberPushActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (trendRL != null) {
            trendRL.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BarberMainActivity.this, BarberTrendActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (haircutsRL != null) {
            haircutsRL.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(BarberMainActivity.this,
                            BarberHaircutsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    private void preInitUI() {
        // Get info from server in advance
        BarberUtil.getTrendItemsFromServer(this);
        BarberUtil.getHaircutIdsFromServer(this);
    }
}
