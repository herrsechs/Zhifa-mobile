package com.zun.zhifa.constants;

import android.os.Environment;

public class SettingConstants {
    public static final String DEVICE_INFO = "DEVICE_INFO";
    public static final String DEVICE_WIDTH = "DEVICE_WIDTH";
    public static final String DEVICE_HEIGHT = "DEVICE_HEIGHT";
    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;
    public static final String appPath = Environment.getExternalStorageDirectory().getPath() + "/zhifa/";
}
