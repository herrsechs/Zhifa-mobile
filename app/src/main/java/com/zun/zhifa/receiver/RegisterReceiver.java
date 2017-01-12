package com.zun.zhifa.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zun.zhifa.activity.MainActivity;
import com.zun.zhifa.httputil.AccountUtil;

public class RegisterReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        String result = intent.getExtras().getString(AccountUtil.REGISTER_RESULT_KEY);
        if ("success".equals(result)){
            Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(context, MainActivity.class);
            context.startActivity(it);
        } else {
            Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}
