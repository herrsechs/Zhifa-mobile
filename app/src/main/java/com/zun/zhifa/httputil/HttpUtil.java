package com.zun.zhifa.httputil;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public static Response syncPostJson(String url, String json) throws IOException {
        if (client == null) {
            init();
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        return client.newCall(request).execute();
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

        File uploadImg = new File(imgPath);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"" +
                        form.getString("IMG_TYPE") +"\"; filename=\"" +
                        form.getString("IMG_NAME")+"\""),
                RequestBody.create(IMG_TYPE, uploadImg)
        );

        if(form.has("ID_TYPE")){
            builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"" + form.getString("ID_TYPE") + "\""),
                RequestBody.create(null, form.getString("ID"))
            );
        }

        if(form.has("ROLE")){
            builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"gender\""),
                RequestBody.create(null, form.getString("GENDER"))
            );
            builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"type\""),
                RequestBody.create(null, form.getString("TYPE"))
            );
        }

        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(callback);
    }

    public static String getTimeStampString(){
        String dateStr = "";
        Date date = new Date();
        DateFormat dft = new SimpleDateFormat("MM-dd-HH-mm");
        try {
            dateStr = dft.format(date) + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
            dateStr = "error.jpg";
        }
        return dateStr;
    }

    public static void renameFile(File f) {
        String fullPath = f.getAbsolutePath();
        char pathSeparator = '/';
        int sepPos = fullPath.lastIndexOf(pathSeparator);
        int endPos = fullPath.length();
        String filename = fullPath.substring(sepPos + 1, endPos);
        filename = "zhifa" + filename;
        String path = fullPath.substring(0, sepPos + 1);

        try {
            File newF = new File(path + filename);
            f.renameTo(newF);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
