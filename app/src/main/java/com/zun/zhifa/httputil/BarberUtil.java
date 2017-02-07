package com.zun.zhifa.httputil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.model.HaircutImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BarberUtil {
    private static final String TAG = ".httputil.BarberUtil";
    private static volatile Boolean fileFreshed = false;
    private static final String FILENAME = "trendItems.txt";

    public static String getTrendItems(final Context context) {
        String result = "";
        if (fileFreshed) {

            try {
                File f = new File(context.getFilesDir().getPath() + FILENAME);
                if (f.exists()) {
                    FileInputStream fin = new FileInputStream(f);
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while((ch = fin.read()) != -1) {
                        builder.append((char)ch);
                    }
                    result += builder.toString();
                    fin.close();
                    fileFreshed = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error when reading file");
            }
        } else {
            getTrendItemsFromServer(context);
        }
        return result;
    }
    public static void getTrendItemsFromServer(final Context context){
    // Get trend items info from server and save it into a file
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    String jsonStr = response.body().string();
                    File f = new File(context.getFilesDir().getPath() + FILENAME);
                    Boolean fileExists = f.exists() || f.createNewFile();

                    if (fileExists) {
                        FileOutputStream fout = new FileOutputStream(f);
                        byte[] bts = jsonStr.getBytes();
                        fout.write(bts);
                        fout.close();
                        fileFreshed = true;
                    }
                } catch ( IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Error when doing file operation");
                }
            }
        };
        try {
            HttpUtil.postJSON(HttpConstants.GET_TREND_ITEMS, "", callback);
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error when postJson");
        }
    }
    public static void uploadHaircut(final Context context, HaircutImage image) {
        SharedPreferences sp = context.getSharedPreferences(
                SettingConstants.SP_ACCOUNT_KEY, Activity.MODE_PRIVATE);
        int bid = sp.getInt(SettingConstants.SP_BARBER_KEY, 0);

        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("hhmmss");
        String timestamp = format.format(date);
        String filename = timestamp + ".jpg";

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("IMG_TYPE", "hair_img");
            jsonObj.put("ID_TYPE", "barber_id");
            jsonObj.put("ID", bid);
            jsonObj.put("IMG_NAME", filename);
            jsonObj.put("ROLE", "barber");
            jsonObj.put("GENDER", image.gender);
            jsonObj.put("TYPE", image.hair);
        } catch (JSONException e){
            e.printStackTrace();
            Log.d(TAG, "Error when doing json job");
        }

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "上传失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg = response.body().string();
                Toast.makeText(context, "上传成功！", Toast.LENGTH_SHORT).show();
            }
        };

        try {
            HttpUtil.postImg(HttpConstants.UPLOAD_HAIRCUT, image.path, jsonObj, callback);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Error when post http");
        }
    }
}
