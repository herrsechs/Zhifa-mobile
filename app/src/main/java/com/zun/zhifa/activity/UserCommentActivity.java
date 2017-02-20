package com.zun.zhifa.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zun.zhifa.R;

public class UserCommentActivity extends AppCompatActivity{
    private EditText commentBox;
    private Button cancelBtn;
    private Button okBtn;
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_user_comment);

        commentBox = (EditText)findViewById(R.id.user_comment_edit_box);
        cancelBtn = (Button)findViewById(R.id.user_comment_cancel_button);
        okBtn = (Button)findViewById(R.id.user_comment_ok_button);

        okBtn.setOnClickListener(okBtnLstnr);
        cancelBtn.setOnClickListener(cancelBtnLstnr);
    }

    View.OnClickListener okBtnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = commentBox.getText().toString();

            finish();
        }
    };

    View.OnClickListener cancelBtnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            commentBox.getText().clear();
        }
    };
}
