package com.zun.zhifa.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zun.zhifa.R;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.fragment.ChangingFaceFragment;
import com.zun.zhifa.httputil.ImageUtil;
import com.zun.zhifa.model.Image;
import com.faceplusplus.api.FaceDetecter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SelfieActivity extends AppCompatActivity {

    public static final String SELFIE_PATH_KEY = "SELFIE_PATH_KEY";
    public static Bitmap selfieBmp;
    HandlerThread detectThread = null;
    Handler detectHandler = null;
    FaceDetecter detecter = null;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        super.setContentView(R.layout.activity_selfie);
        ImageView selfieImgView = (ImageView)findViewById(R.id.selfie_image_view);

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }else{
//            Toast.makeText(SelfieActivity.this, "请先取得文件读写权限", Toast.LENGTH_SHORT).show();
//            finish();
        }

        if(selfieBmp != null && selfieImgView != null){
            selfieImgView.setImageBitmap(selfieBmp);
        }

        Button confirmBtn = (Button)findViewById(R.id.selfie_confirm_btn);
        if (confirmBtn != null) {
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SelfieActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.FRAGMENT_TAG, R.id.tab_collection);
                    ChangingFaceFragment.selfieBmp = selfieBmp;
                    String path = saveBitmap(selfieBmp);
                    char pathSeparator = '/';
                    int sepPos = path.lastIndexOf(pathSeparator);
                    int endPos = path.length();
                    String filename = path.substring(sepPos + 1, endPos);
                    if(path != null) {
                        Image img = new Image();
                        img.cid = "1";
                        img.path = path;
                        img.filename = filename;
                        ImageUtil.uploadSelfie(img, SelfieActivity.this);
                    }
                    startActivity(intent);
                }
            });
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

    // Save bitmap to storage and return file path
    private String saveBitmap(Bitmap bmp){
        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("hhmmss");
        String timestamp = format.format(date);

        String path = SettingConstants.appPath + timestamp + ".jpg";
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
