package com.zun.zhifa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SelfieActivity extends AppCompatActivity {

    public static final String SELFIE_PATH_KEY = "SELFIE_PATH_KEY";
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        super.setContentView(R.layout.activity_selfie);
        ImageView selfieImgView = (ImageView)findViewById(R.id.selfie_image_view);

        String path = getIntent().getExtras().getString(SELFIE_PATH_KEY);
        Bitmap bmp = getPhotoBitmap(path);
        if(bmp != null){
            selfieImgView.setImageBitmap(bmp);
        }
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
