package com.zun.zhifa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.zun.zhifa.activity.ProfileActivity;
import com.zun.zhifa.httputil.AccountUtil;

public class AuthReceiver extends BroadcastReceiver {
    private static final String TAG = ".receiver.AuthReceiver";
    @Override
    public void onReceive(final Context context, Intent intent){
        String result = intent.getExtras().getString(AccountUtil.AUTH_RESULT_KEY);
        Log.d(TAG, result);
        if("success".equals(result)){
            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(context, ProfileActivity.class);
            context.startActivity(it);
        }else{
            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

}
