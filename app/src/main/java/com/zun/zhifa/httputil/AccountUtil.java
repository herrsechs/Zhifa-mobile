package com.zun.zhifa.httputil;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.zun.zhifa.constants.HttpConstants;

import java.io.IOException;
import java.io.InputStream;


public class AccountUtil {
    public static String login(String username, String password, String role){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", username);
            jsonObj.put("password", password);
            jsonObj.put("role", role);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String jsonStr = jsonObj.toString();
        try{
            return HttpUtil.postJSON(HttpConstants.LOGIN, jsonStr);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
