package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zun.zhifa.R;
import com.zun.zhifa.adapter.UserMessageAdapter;

public class UserMessageActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_user_message);

        RecyclerView mRecView = (RecyclerView)findViewById(R.id.user_message_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter adapter = new UserMessageAdapter(this);
        mRecView.setAdapter(adapter);
    }
}
