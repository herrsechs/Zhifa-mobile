package com.zun.zhifa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_RES_ID = "IMAGE_RES_ID";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_image_detail);
        ImageView imgView = (ImageView)findViewById(R.id.image_detail_image_view);
        ImageButton favorBtn = (ImageButton)findViewById(R.id.image_detail_favor_btn);
        ImageButton delBtn = (ImageButton)findViewById(R.id.image_detail_delete_btn);
    }

    public static void launch(AppCompatActivity activity, View transitionView, String imageId){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, imageId);
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        //ntent.putExtra(IMAGE_RES_ID, );
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
}
