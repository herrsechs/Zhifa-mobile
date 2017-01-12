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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zun.zhifa.R;
import com.zun.zhifa.fragment.CommentFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_RES_ID = "ImageID";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_image_detail);

        ImageView imgView = (ImageView)findViewById(R.id.image_detail_image_view);
        ImageButton favorBtn = (ImageButton)findViewById(R.id.image_detail_favor_btn);
//        ImageButton delBtn = (ImageButton)findViewById(R.id.image_detail_delete_btn);

        int imgId = getIntent().getExtras().getInt(IMAGE_RES_ID);
        if(imgView != null) {
            imgView.setImageResource(imgId);
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.comment_item,
                new String[]{"user_name", "comment"},
                new int[]{R.id.comment_user_name, R.id.comment_content});

        if(listView != null) {
            listView.setAdapter(adapter);
        }
    }


    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", "gamino");
        map.put("comment", "再遥远一点，雪花朦胧的双眼");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("user_name", "naruto123456");
        map.put("comment", "It wasn't love, it wasn't love, it was a perfect illusion");
        list.add(map);

        return list;
    }


}
