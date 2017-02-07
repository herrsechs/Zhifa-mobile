package com.zun.zhifa.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.BarberUtil;
import com.zun.zhifa.model.HaircutImage;

public class BarberUploadActivity extends AppCompatActivity {
    private Button galleryBtn;
    private Button confirmBtn;
    private ImageView imageView;
    private Spinner haircutSpinner;
    private Spinner genderSpinner;

    private String haircutImagePath;
    private static final int IMAGE_CODE = 1;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_barber_upload_activity);

        galleryBtn = (Button)findViewById(R.id.barber_upload_gallery_btn);
        confirmBtn = (Button)findViewById(R.id.barber_upload_confirm_btn);
        imageView = (ImageView)findViewById(R.id.barber_upload_image_view);
        haircutSpinner = (Spinner)findViewById(R.id.barber_upload_haircut_spinner);
        genderSpinner = (Spinner)findViewById(R.id.barber_upload_gender_spinner);

        bindClickListener(galleryBtn, galleryBtnLtnr);
        bindClickListener(confirmBtn, confirmBtnLtnr);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    View.OnClickListener galleryBtnLtnr = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE_CODE);
        }
    };

    View.OnClickListener confirmBtnLtnr = new View.OnClickListener() {
        public void onClick(View v) {
            if (haircutImagePath != null) {
                String gender = genderSpinner.getSelectedItem().toString();
                String hair = haircutSpinner.getSelectedItem().toString();
                HaircutImage image = new HaircutImage();
                gender = (gender.equals("男")) ? "male" : "female";
                hair = (hair.equals("长")) ? "long" : "short";
                image.hair = hair;
                image.gender = gender;
                image.path = haircutImagePath;
                BarberUtil.uploadHaircut(BarberUploadActivity.this, image);
            } else {
                Toast.makeText(BarberUploadActivity.this,
                        "请先从相册中选择图片！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK
                && data != null) {
            Uri selectedImage = data.getData();
            String[] filepathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filepathColumns,
                    null, null, null);
            if (c != null) {
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filepathColumns[0]);
                String imagePath = c.getString(columnIndex);
                haircutImagePath = imagePath;

                Bitmap bmp = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bmp);
                c.close();
            }
        }
    }



    protected void bindClickListener(View v, View.OnClickListener func) {
        if (v != null) {
            v.setOnClickListener(func);
        }
    }

}
