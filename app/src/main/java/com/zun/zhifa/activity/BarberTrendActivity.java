package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zun.zhifa.R;
import com.zun.zhifa.adapter.BarberTrendItemAdapter;
import com.zun.zhifa.adapter.ImageCardAdapter;
import com.zun.zhifa.httputil.BarberUtil;

public class BarberTrendActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_barber_trend);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.barber_trend_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new BarberTrendItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }


}
