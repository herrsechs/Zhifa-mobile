package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zun.zhifa.R;

public class RegisterActivity extends AppCompatActivity{
    public static final String TAG = ".RegisterActivity";

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);

        EditText usnText = (EditText)findViewById(R.id.register_username_input);
        EditText pwdText = (EditText)findViewById(R.id.register_password_input);
        EditText cfmPwdText = (EditText)findViewById(R.id.register_confirm_pwd);

        Spinner roleSpin = (Spinner)findViewById(R.id.register_role_spinner);
        Spinner genderSpin = (Spinner)findViewById(R.id.register_gender_spinner);
        Spinner haircutSpin = (Spinner)findViewById(R.id.register_haircut_spinner);

        Button clearBtn = (Button)findViewById(R.id.register_clear_btn);
        Button submitBtn = (Button)findViewById(R.id.register_submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
