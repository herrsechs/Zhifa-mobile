package com.zun.zhifa.httputil;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImageUtil {
    public static String IMAGE_TRANS_RESULT_KEY = "IMAGE_TRANS";
    public static void uploadSelfie(Image image, Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("IMG_TYPE", "selfie_img");
            jsonObj.put("ID_TYPE", "cid");
            jsonObj.put("ID", image.cid);
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

            }
        };

        try {
            HttpUtil.postImg(HttpConstants.UPLOAD_SELFIE, image.path, jsonObj, callback);
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
    }

    public static void downloadChangedFace(final Context context){
        JSONObject jsonObj = new JSONObject();
        try{
            jsonObj.put("hair_id", "15");
            jsonObj.put("selfie_id", "7");
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
