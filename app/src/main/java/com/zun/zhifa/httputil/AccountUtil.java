package com.zun.zhifa.httputil;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.model.Account;

import java.io.IOException;
import java.io.InputStream;


public class AccountUtil {
    public static final String AUTH_RESULT_KEY = "AUTH_RESULT_KEY";
    public static void login(Account account, Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", account.username);
            jsonObj.put("password", account.password);
            jsonObj.put("role", account.role);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String jsonStr = jsonObj.toString();
        try{
            HttpUtil.postJSON(HttpConstants.LOGIN, jsonStr, context, AUTH_RESULT_KEY);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
