package com.zun.zhifa.httputil;

import android.content.Context;
import android.net.Uri;

import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.model.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        try {
            HttpUtil.postImg(HttpConstants.UPLOAD_SELFIE, image.path, jsonObj, context, IMAGE_TRANS_RESULT_KEY);
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
    }
}
