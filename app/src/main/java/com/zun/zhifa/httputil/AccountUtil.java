package com.zun.zhifa.httputil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
    private static final String TAG = ".httputil.AccountUtil";
    public static final String AUTH_LOGIN_RESULT_KEY = "LOGIN_RESULT_KEY";
    public static final String REGISTER_RESULT_KEY = "REGISTER_RESULT_KEY";
    public static void login(Account account, final Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", account.username);
            jsonObj.put("password", account.password);
            jsonObj.put("role", account.role);
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "JSON error");
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
                intent.putExtra(AUTH_LOGIN_RESULT_KEY, response.body().string());
                context.sendBroadcast(intent);
            }
        };
        try{
            HttpUtil.postJSON(HttpConstants.LOGIN, jsonStr, callback);
        }catch (IOException e){
            e.printStackTrace();
            Log.d(TAG, "Error happened while sending json to server");
        }

    }
    public static void register(Account account, final Context context){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("username", account.username);
            jsonObj.put("password", account.password);
            jsonObj.put("role", account.role);
            jsonObj.put("gender", account.gender);
        } catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "Json error");
        }

        String jsonStr = jsonObj.toString();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "HTTP error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent intent = new Intent();
                intent.setAction("REGISTER");
                intent.putExtra(REGISTER_RESULT_KEY, response.body().string());
                context.sendBroadcast(intent);
            }
        };

        try {
            HttpUtil.postJSON(HttpConstants.REGISTER, jsonStr, callback);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "HTTP error");
        }
    }

}
