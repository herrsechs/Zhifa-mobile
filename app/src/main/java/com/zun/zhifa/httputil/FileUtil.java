package com.zun.zhifa.httputil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FileUtil {
    public static Boolean hasSDcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static File createFileDir(Context context, String dirName) {
        String filePath;
        if (hasSDcard()) {
            filePath = Environment.getExternalStorageDirectory() + File.separator
                    + dirName;
        } else {
            filePath =context.getCacheDir() + File.separator
                    + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        return destDir;
    }

    public static void delFile(File file, boolean delThisPath) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
            }
        }
        if (delThisPath) {
            file.delete();
        }
    }

    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    public static void saveBitmap(File dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFromCache(final Context context, final String filename) {
        String result = "";
        try {
            File f = new File(context.getFilesDir().getPath() + filename);
            if (f.exists()) {
                FileInputStream fin = new FileInputStream(f);
                StringBuilder builder = new StringBuilder();
                int ch;
                while((ch = fin.read()) != -1) {
                    builder.append((char)ch);
                }
                result += builder.toString();
                fin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getFromServer(final Context context, final String filename,
                                     String url, final String jsonStr) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    String jsonStr = response.body().string();
                    File f = new File(context.getFilesDir().getPath() + filename);
                    Boolean fileExists = f.exists() || f.createNewFile();

                    if (fileExists) {
                        FileOutputStream fout = new FileOutputStream(f);
                        byte[] bts = jsonStr.getBytes();
                        fout.write(bts);
                        fout.close();
                    }
                } catch ( IOException e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            HttpUtil.postJSON(url, jsonStr, callback);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
