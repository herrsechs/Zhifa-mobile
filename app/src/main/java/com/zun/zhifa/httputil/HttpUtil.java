package com.zun.zhifa.httputil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    protected static OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType IMG_TYPE = MediaType.parse("image/jpg");
    private static final String TAG = ".httputil.HttpUtil";

    public static void init(){
        client = new OkHttpClient();
    }

    public static void postJSON(String url, String json, Callback callback) throws IOException{
        if(client == null){
            init();
        }

        Log.d(TAG, json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(callback);
    }

    public static void postImg(String url, String imgPath, JSONObject form, Callback callback) throws IOException, JSONException {
        if(client == null){
            init();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"" + form.getString("IMG_TYPE") +"\""),
                RequestBody.create(IMG_TYPE, new File(imgPath))
        );

        if(form.has("ID_TYPE")){
            builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"" + form.getString("ID_TYPE") + "\""),
                RequestBody.create(null, form.getString("ID"))
            );
        }

        if(form.has("ROLE")){
            builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"role\""),
                RequestBody.create(null, form.getString("ROLE"))
            );
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(callback);
        new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.body().string());
            }
        };

    }

}
