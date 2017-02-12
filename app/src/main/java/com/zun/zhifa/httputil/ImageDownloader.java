package com.zun.zhifa.httputil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;

public class ImageDownloader {
    private Hashtable<String, Integer> taskCollection;
    private LruCache<String, Bitmap> lruCache;
    private ExecutorService threadPool;
    private static final String DIR_CACHE = "zhifa/cache";
    private File cacheFileDir;
    private static final long DIR_CACHE_LIMIT = 10 * 1024 * 1024;
    private static final int IMAGE_DOWNLOAD_FAIL_TIMES = 2;

    public ImageDownloader(Context context) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        lruCache = new LruCache<String, Bitmap>(maxMemory / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
        taskCollection = new Hashtable<>();
        threadPool = Executors.newFixedThreadPool(10);
        cacheFileDir = FileUtil.createFileDir(context, DIR_CACHE);
    }

    private void addToCache(String key, Bitmap bmp) {
        if (getFromCache(key) == null && bmp != null) {
            lruCache.put(key, bmp);
        }
    }

    public Bitmap getFromCache(String key) {
        return lruCache.get(key);
    }

    public void loadImage(final String url, final String jsonStr, final int width,
                          final int height, AsyncImageLoaderListener listener) {
        final ImageHandler handler = new ImageHandler(listener);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = downloadImage(url, jsonStr, width, height);
                Message msg = handler.obtainMessage();
                msg.obj = bmp;
                handler.sendMessage(msg);
                addToCache(jsonStr, bmp);

                long cacheFileSize = FileUtil.getFileSize(cacheFileDir);
                if (cacheFileSize > DIR_CACHE_LIMIT) {
                    FileUtil.delFile(cacheFileDir, false);
                    taskCollection.clear();
                }

                Date date = new Date();
                java.text.DateFormat format = new SimpleDateFormat("hhmmss");
                String timestamp = format.format(date);
                String filename = timestamp + ".jpg";
                FileUtil.saveBitmap(cacheFileDir, filename, bmp);
            }
        };
        taskCollection.put(jsonStr, 0);
        threadPool.execute(runnable);
    }

    private Bitmap downloadImage(String url, String jsonStr, int width, int height) {
        Bitmap bmp = null;
        try {
            Response response = HttpUtil.syncPostJson(url, jsonStr);
            byte[] bytes = response.body().bytes();

            BitmapFactory.Options bmpOpts = new BitmapFactory.Options();
            bmpOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpOpts);

//            if (height > 0 && width > 0) {
//                int heightRatio = (int) Math.ceil(bmpOpts.outHeight / height);
//                int widthRatio = (int) Math.ceil(bmpOpts.outWidth / width);
//                if (heightRatio > 1 && widthRatio > 1) {
//                    bmpOpts.inSampleSize = heightRatio > widthRatio ? heightRatio :
//                            widthRatio;
//                }
//            }

            bmpOpts.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, bmpOpts);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (taskCollection.get(jsonStr) != null) {
//            int times = taskCollection.get(jsonStr);
//            if (bmp == null && times < IMAGE_DOWNLOAD_FAIL_TIMES) {
//                times++;
//                taskCollection.put(jsonStr, times);
//                bmp = downloadImage(url, jsonStr, width, height);
//            }
//        }
        return bmp;
    }

    public synchronized void cancelTasks() {
        if (threadPool != null) {
            threadPool.shutdown();
            threadPool = null;
        }
    }

    public interface AsyncImageLoaderListener {
        void onImageLoader(Bitmap bitmap);
    }

    static class ImageHandler extends Handler {
        private AsyncImageLoaderListener listener;
        public ImageHandler(AsyncImageLoaderListener listener) {
            this.listener = listener;
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listener.onImageLoader((Bitmap)msg.obj);
        }
    }
}
