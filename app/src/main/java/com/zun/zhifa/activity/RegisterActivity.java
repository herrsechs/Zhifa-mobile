package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.AccountUtil;
import com.zun.zhifa.httputil.HttpUtil;
import com.zun.zhifa.model.Account;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity{
    public static final String TAG = ".RegisterActivity";
    EditText usnText;
    EditText pwdText;
    EditText cfmPwdText;

    Spinner roleSpin;
    Spinner genderSpin;
    Spinner haircutSpin;

    Button clearBtn;
    Button submitBtn;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_register);

        if(init()){
            setListener();
        }

    }

    private Boolean init(){
        Boolean result = false;
        try {
            usnText = (EditText) findViewById(R.id.register_username_input);
            pwdText = (EditText) findViewById(R.id.register_password_input);
            cfmPwdText = (EditText) findViewById(R.id.register_confirm_pwd);

            roleSpin = (Spinner) findViewById(R.id.register_role_spinner);
            genderSpin = (Spinner) findViewById(R.id.register_gender_spinner);
            haircutSpin = (Spinner) findViewById(R.id.register_haircut_spinner);

            clearBtn = (Button) findViewById(R.id.register_clear_btn);
            submitBtn = (Button) findViewById(R.id.register_submit_btn);
            result = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Fail to initialize");
        }
        return result;
    }


    private void setListener(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account();
                account.username = usnText.getText().toString();
                account.password = pwdText.getText().toString();
                account.role = roleSpin.getSelectedItem().toString();
                account.gender = roleSpin.getSelectedItem().toString();
                AccountUtil.register(account, RegisterActivity.this);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usnText.getText().clear();
                pwdText.getText().clear();
                cfmPwdText.getText().clear();

                roleSpin.setSelection(0);
                genderSpin.setSelection(0);
                haircutSpin.setSelection(0);
            }
        });
    }


}
