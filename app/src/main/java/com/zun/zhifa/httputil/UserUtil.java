package com.zun.zhifa.httputil;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserUtil {
    private static final String TAG = ".httputil.UserUtil";
    private static final String MESSAGE_FILENAME = "messages.txt";
    private static volatile Boolean msgFileFreshed = false;

    public static String getMessages(final Context context){
        String result = "";
        if (msgFileFreshed) {
            try {
                File f = new File(context.getFilesDir().getPath() + MESSAGE_FILENAME);
                if (f.exists()) {
                    FileInputStream fin = new FileInputStream(f);
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while((ch = fin.read()) != -1) {
                        builder.append((char)ch);
                    }
                    result += builder.toString();
                    fin.close();
                    msgFileFreshed = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error when reading file");
            }
        } else {
            getMessageFromServer(context);
        }

        return result;
    }
    public static void getMessageFromServer(final Context context){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    String jsonStr = response.body().string();
                    File f = new File(context.getFilesDir().getPath() + MESSAGE_FILENAME);
                    Boolean fileExists = f.exists() || f.createNewFile();

                    if (fileExists) {
                        FileOutputStream fout = new FileOutputStream(f);
                        byte[] bts = jsonStr.getBytes();
                        fout.write(bts);
                        fout.close();
                        msgFileFreshed = true;
                    }
                } catch ( IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Error when doing file operation");
                }
            }
        };

        SharedPreferences sp = context.getSharedPreferences(
                SettingConstants.SP_ACCOUNT_KEY, Activity.MODE_PRIVATE);
        int cid = sp.getInt(SettingConstants.SP_CUSTOMER_KEY, 0);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("cid", cid);
            String jsonStr = jsonObj.toString();
            HttpUtil.postJSON(HttpConstants.GET_CUSTOMER_MESSAGE, jsonStr, callback);
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
