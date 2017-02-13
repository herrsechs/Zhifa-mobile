package com.zun.zhifa.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zun.zhifa.R;
import com.zun.zhifa.activity.LoginActivity;
import com.zun.zhifa.activity.UserMessageActivity;
import com.zun.zhifa.httputil.UserUtil;

public class ProfileFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        final Activity act = getActivity();
        preInit(act);

        RelativeLayout collection = (RelativeLayout)act.findViewById(R.id.profile_user_collection);
        RelativeLayout document = (RelativeLayout)act.findViewById(R.id.profile_user_document);
        RelativeLayout account = (RelativeLayout)act.findViewById(R.id.profile_user_account);
        RelativeLayout msgRL = (RelativeLayout)act.findViewById(R.id.profile_user_message);

        if (account != null) {
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (msgRL != null) {
            msgRL.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(act, UserMessageActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void preInit(Context c) {
        UserUtil.getMessageFromServer(c);
    }
}
