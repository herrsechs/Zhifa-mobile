package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.BarberUtil;

public class BarberPushActivity extends AppCompatActivity {
    EditText editText;
    Button confirmBtn;
    Button cancelBtn;
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_barber_push);

        editText = (EditText)findViewById(R.id.barber_push_edit_box);
        confirmBtn = (Button)findViewById(R.id.barber_push_ok_button);
        cancelBtn = (Button)findViewById(R.id.barber_push_cancel_button);

        if (confirmBtn != null) {
            confirmBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    String str = editText.getText().toString();
                    BarberUtil.uploadMesssage(BarberPushActivity.this, str);
                }
            });
        }
        if (cancelBtn != null) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editText.getText().clear();
                }
            });
        }
    }
}
