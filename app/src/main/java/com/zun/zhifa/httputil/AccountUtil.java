package com.zun.zhifa.httputil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.model.Account;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class AccountUtil {
    public static final String AUTH_RESULT_KEY = "AUTH_RESULT_KEY";
    public static void login(Account account, final Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", account.username);
            jsonObj.put("password", account.password);
            jsonObj.put("role", account.role);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String jsonStr = jsonObj.toString();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent intent = new Intent();
                intent.setAction("AUTH");
                intent.putExtra(AUTH_RESULT_KEY, response.body().string());
                context.sendBroadcast(intent);
            }
        };
        try{
            HttpUtil.postJSON(HttpConstants.LOGIN, jsonStr, callback);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
