package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.AccountUtil;
import com.zun.zhifa.model.Account;

public class LoginActivity extends AppCompatActivity {
    private EditText usnTxt;
    private EditText pwdTxt;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button)findViewById(R.id.login_btn);
        Button registBtn = (Button)findViewById(R.id.register_btn);
        usnTxt = (EditText)findViewById(R.id.login_username);
        pwdTxt = (EditText)findViewById(R.id.login_password);
        Log.d("TEST", "A STRING");

        if (loginBtn != null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TEST", "STRING");
                    Toast.makeText(LoginActivity.this, "Click", Toast.LENGTH_SHORT).show();
                    Account accnt = new Account();
                    if(usnTxt != null) {
                        accnt.username = usnTxt.getText().toString();
                    }
                    if(pwdTxt != null){
                        accnt.password = pwdTxt.getText().toString();
                    }
                    accnt.role = "Customer";
                    AccountUtil.login(accnt);
                }
            });
        }


    }

}