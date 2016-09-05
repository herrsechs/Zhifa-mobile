package com.zun.zhifa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zun.zhifa.R;

public class SelfieActivity extends AppCompatActivity {

    public static final String SELFIE_PATH_KEY = "SELFIE_PATH_KEY";
    public static Bitmap selfieBmp;
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        super.setContentView(R.layout.activity_selfie);
        ImageView selfieImgView = (ImageView)findViewById(R.id.selfie_image_view);

        if(selfieBmp != null){
            selfieImgView.setImageBitmap(selfieBmp);
        }

        Button confirmBtn = (Button)findViewById(R.id.selfie_confirm_btn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfieActivity.this, ChangingFaceActivity.class);
                ChangingFaceActivity.selfieBmp = selfieBmp;
                startActivity(intent);
            }
        });
    }

    public Bitmap getPhotoBitmap(String path){
        Bitmap bmp = BitmapFactory.decodeFile(path);
        if(bmp == null){
            return null;
        }
        int height = (int) ( bmp.getHeight() * (512.0 / bmp.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bmp, 512, height, true);
        return scaled;
    }
}
