package com.zun.zhifa.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SearchableActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    protected void doSearch(String q){

    }
}
