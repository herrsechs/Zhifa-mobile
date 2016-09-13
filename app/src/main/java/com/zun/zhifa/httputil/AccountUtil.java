package com.zun.zhifa.httputil;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.model.Account;

import java.io.IOException;
import java.io.InputStream;


public class AccountUtil {
    public static void login(Account account){
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
            HttpUtil.postJSON(HttpConstants.LOGIN, jsonStr);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
