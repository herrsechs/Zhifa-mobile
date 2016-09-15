package com.zun.zhifa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zun.zhifa.httputil.AccountUtil;

public class AuthReceiver extends BroadcastReceiver {
    private static final String TAG = ".receiver.AuthReceiver";
    @Override
    public void onReceive(final Context context, Intent intent){
        String result = intent.getExtras().getString(AccountUtil.AUTH_RESULT_KEY);
        Log.d(TAG, result);
    }

}
