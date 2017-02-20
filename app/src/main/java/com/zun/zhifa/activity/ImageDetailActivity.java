package com.zun.zhifa.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zun.zhifa.R;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.fragment.CommentFragment;
import com.zun.zhifa.httputil.HttpUtil;
import com.zun.zhifa.httputil.ImageDownloader;
import com.zun.zhifa.httputil.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_RES_ID = "ImageID";
    private ImageDownloader loader;
    private SimpleAdapter adapter;
    private ListView commentView;
    private TextView barberNameTxtView;
    private TextView likesCountView;
    private Button followBtn;
    private ImageButton commentBtn;

    private Boolean isFollowed;
    private Boolean isFavored;
    private int barber_id;
    private int hair_id;
    private int customer_id;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_image_detail);

        int imgId = getIntent().getExtras().getInt(IMAGE_RES_ID);
        hair_id = imgId;
        loader = new ImageDownloader(this);
        final ImageView imgView = (ImageView)findViewById(R.id.image_detail_image_view);
        ImageButton favorBtn = (ImageButton)findViewById(R.id.image_detail_favor_btn);
        followBtn = (Button)findViewById(R.id.image_detail_follow);
        barberNameTxtView  = (TextView)findViewById(R.id.image_detail_barber_name);
        likesCountView = (TextView)findViewById(R.id.image_detail_likes_count);
        commentBtn = (ImageButton)findViewById(R.id.image_detail_comment_btn);

        preInit();

        if(imgView != null) {
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("pid", imgId);
                String jsonStr = jsonObj.toString();
                Bitmap bmp = loader.getFromCache(jsonStr);
                if (bmp == null) {
                    loader.loadImage(HttpConstants.GET_HAIRCUT, jsonStr,
                            0, 0, new ImageDownloader.AsyncImageLoaderListener(){
                                public void onImageLoader(Bitmap bitmap) {
                                    imgView.setImageBitmap(bitmap);
                                }
                            });
                } else {
                    imgView.setImageBitmap(bmp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        commentView = (ListView)findViewById(R.id.listView);

        if (favorBtn != null) {
            favorBtn.setOnClickListener(favorBtnLstnr);
        }
        if (followBtn != null) {
            followBtn.setOnClickListener(followBtnLstnr);
        }
        if (commentBtn != null) {
            commentBtn.setOnClickListener(commentBtnLstnr);
        }
        initCommentList();
    }

    View.OnClickListener commentBtnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ImageDetailActivity.this,
                    UserCommentActivity.class);
            intent.putExtra(IMAGE_RES_ID, hair_id);
            startActivity(intent);
        }
    };

    View.OnClickListener favorBtnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            UserUtil.favorImage(ImageDetailActivity.this, hair_id);
        }
    };

    View.OnClickListener followBtnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFollowed) {
                return;
            }
            Callback callback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Toast.makeText(ImageDetailActivity.this, "关注成功！",
                            Toast.LENGTH_LONG).show();
                }
            };
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("cid", customer_id);
                jsonObj.put("bid", barber_id);
                String jsonStr = jsonObj.toString();
                HttpUtil.postJSON(HttpConstants.FOLLOW, jsonStr, callback);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void preInit(){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String infoStr = response.body().string();
                try {
                    JSONObject jsonObj = new JSONObject(infoStr);
                    barberNameTxtView.setText(jsonObj.getString("barber_name"));
                    isFavored = jsonObj.getBoolean("is_favored");
                    isFollowed = jsonObj.getBoolean("is_followed");
                    String likesCount = jsonObj.getString("favor_count") + " likes";
                    likesCountView.setText(likesCount);
                    barber_id = jsonObj.getInt("bid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SharedPreferences sp = getSharedPreferences(SettingConstants.SP_ACCOUNT_KEY,
                Activity.MODE_PRIVATE);
        int cid = sp.getInt(SettingConstants.SP_CUSTOMER_KEY, 0);
        customer_id = cid;
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("cid", cid);
            jsonObj.put("hid", hair_id);
            String jsonStr = jsonObj.toString();
            HttpUtil.postJSON(HttpConstants.GET_HAIRCUT_INFO, jsonStr, callback);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initCommentList() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String cmtStr = response.body().string();
                List<Map<String, String>> list = new ArrayList<>();
                try {
                    JSONArray arr = new JSONArray(cmtStr);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonObj = arr.getJSONObject(i);
                        Map<String, String> map = new HashMap<>();
                        map.put("user_name", jsonObj.getString("username"));
                        map.put("comment", jsonObj.getString("text"));
                        list.add(map);
                    }
                    adapter = new SimpleAdapter(ImageDetailActivity.this,
                            list, R.layout.comment_item,
                            new String[]{"user_name", "comment"},
                            new int[]{R.id.comment_user_name, R.id.comment_content});
                    commentView.setAdapter(adapter);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("pid", hair_id);
            String str = jsonObj.toString();
            HttpUtil.postJSON(HttpConstants.GET_IMG_COMMENT, str, callback);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
