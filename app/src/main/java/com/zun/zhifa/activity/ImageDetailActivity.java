package com.zun.zhifa.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zun.zhifa.R;
import com.zun.zhifa.fragment.CommentFragment;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_RES_ID = "ImageID";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_image_detail);
        ImageView imgView = (ImageView)findViewById(R.id.image_detail_image_view);
        ImageButton favorBtn = (ImageButton)findViewById(R.id.image_detail_favor_btn);
        ImageButton delBtn = (ImageButton)findViewById(R.id.image_detail_delete_btn);

        int imgId = getIntent().getExtras().getInt(IMAGE_RES_ID);
        imgView.setImageResource(imgId);

        setCommentArea();
    }

    private void setCommentArea(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        CommentFragment frag = new CommentFragment();
        transaction.add(R.id.image_detail_comment_area, frag);
        transaction.commit();
    }



}
