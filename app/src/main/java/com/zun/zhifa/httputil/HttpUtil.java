package com.zun.zhifa.httputil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    protected static OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = ".httputil.HttpUtil";

    public static void init(){
        client = new OkHttpClient();
    }

    public static void postJSON(String url, String json,
                                final Context context, final String intentKey) throws IOException{
        if(client == null){
            init();
        }

        Log.d(TAG, json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent intent = new Intent();
                intent.setAction("AUTH");
                intent.putExtra(intentKey, "Something");
                context.sendBroadcast(intent);
            }
        });

    }

}
