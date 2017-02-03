package com.zun.zhifa.httputil;
import android.content.Context;
import android.util.Log;

import com.zun.zhifa.constants.HttpConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
