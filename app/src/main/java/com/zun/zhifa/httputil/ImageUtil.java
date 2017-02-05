package com.zun.zhifa.httputil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImageUtil {
    public static String IMAGE_TRANS_RESULT_KEY = "IMAGE_TRANS";
    private static final String TAG = ".httputil.ImageUtil";
    public static void uploadSelfie(Image image, final Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("IMG_TYPE", "selfie_img");
            jsonObj.put("ID_TYPE", "cid");
            jsonObj.put("ID", image.cid);
            jsonObj.put("IMG_NAME", image.filename);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                try {
                    JSONObject jsonObj = new JSONObject(msg);
                    int hid = jsonObj.getInt(SettingConstants.SP_HAIRCUT_KEY);
                    int sid = jsonObj.getInt(SettingConstants.SP_SELFIE_KEY);
                    SharedPreferences sp = context.getSharedPreferences(
                            SettingConstants.SP_CHANGE_FACE_KEY, Context.MODE_PRIVATE
                    );
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putInt(SettingConstants.SP_HAIRCUT_KEY, hid);
                    edit.putInt(SettingConstants.SP_SELFIE_KEY, sid);
                    edit.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "JSON object error");
                }
                Log.d(TAG, response.body().string());
            }
        };

        try {
            HttpUtil.postImg(HttpConstants.UPLOAD_SELFIE, image.path, jsonObj, callback);
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
    }

    public static void downloadChangedFace(final Context context, int hid, int sid, final ImageView view){
        JSONObject jsonObj = new JSONObject();
        try{
            jsonObj.put("hair_id", hid);
            jsonObj.put("selfie_id", sid);
            jsonObj.put("file_name", "cf3.jpg");
        }catch (JSONException e){
            e.printStackTrace();
        }

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                final Activity activity = (Activity)context;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bitmap != null) {
                            view.setImageBitmap(bitmap);
                        }
                    }
                });
                Log.d(".downloadChangedFace", "success");
            }
        };
        try{
            HttpUtil.postJSON(HttpConstants.CHANGE_FACE, jsonObj.toString(), callback);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
