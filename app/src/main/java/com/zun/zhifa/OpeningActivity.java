package com.zun.zhifa;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {

    private static final int FAILURE = 0;
    private static final int SUCCESS = 1;
    private static final int OFFLINE = 2;
    private static final int MIN_SHOWTIME = 800;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        super.setContentView(R.layout.activity_opening);

        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void...params){
                long start = System.currentTimeMillis();

                int result;
                result = loadingCache();

                long loading = System.currentTimeMillis() - start;
                if(loading < MIN_SHOWTIME){
                    try{
                        Thread.sleep(MIN_SHOWTIME - loading);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(Integer result){
                Intent intent = new Intent();
                intent.setClass(OpeningActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute();

    }

    protected Integer loadingCache(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        if(cm != null){
//            NetworkInfo[] infos = cm.getAllNetworkInfo();
//            if(infos != null){
//                for(NetworkInfo ni : infos){
//                    if(ni.isConnected())
//                        return SUCCESS;
//                }
//            }
//        }
        return OFFLINE;
    }
}
