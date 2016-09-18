package com.zun.zhifa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zun.zhifa.R;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.httputil.ImageUtil;
import com.zun.zhifa.model.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
                String path = saveBitmap(selfieBmp);
                if(path != null) {
                    Image img = new Image();
                    img.cid = "1";
                    img.path = path;
                    ImageUtil.uploadSelfie(img, SelfieActivity.this);
                }
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

    // Save bitmap to storage and return file path
    private String saveBitmap(Bitmap bmp){
        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("hhmmss");
        String timestamp = format.format(date);

        String path = SettingConstants.appPath + timestamp + "jpg";
        File file = new File(path);
        Boolean saveFileSuccess = false;
        try{
            saveFileSuccess = file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(!saveFileSuccess){
            return null;
        }

        FileOutputStream out = null;
        try{
            out = new FileOutputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try{
            out.flush();
            out.close();
        }catch (IOException|NullPointerException e){
            e.printStackTrace();
        }
        return path;
    }
}
