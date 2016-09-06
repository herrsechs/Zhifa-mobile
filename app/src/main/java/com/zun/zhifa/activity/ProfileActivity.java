package com.zun.zhifa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.zun.zhifa.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_profile);

        RelativeLayout collection = (RelativeLayout)findViewById(R.id.profile_user_collection);
        RelativeLayout document = (RelativeLayout)findViewById(R.id.profile_user_document);
        RelativeLayout account = (RelativeLayout)findViewById(R.id.profile_user_account);

        if (account != null) {
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
