package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zun.zhifa.R;
import com.zun.zhifa.adapter.HaircutImageAdapter;

public class BarberHaircutsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_barber_haircut_gallery);

        RecyclerView mRecyclerView = (RecyclerView)findViewById(
                R.id.barber_haircut_gallery_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new HaircutImageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
