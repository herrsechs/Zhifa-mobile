package com.zun.zhifa;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ChangingFaceActivity extends AppCompatActivity {
    public static Bitmap selfieBmp;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_changing_face);

        ImageView mergeFace = (ImageView)findViewById(R.id.change_face_merging_image_view);


        if(selfieBmp != null){
            mergeFace.setImageBitmap(selfieBmp);
        }
    }
}
