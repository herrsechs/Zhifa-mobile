package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.zun.zhifa.R;

public class FollowListActivity extends AppCompatActivity {
    private static final String TAG = ".activity.FollowList";
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_follow_list);

        mListView = (ListView)findViewById(R.id.follow_list_view);


    }
}
