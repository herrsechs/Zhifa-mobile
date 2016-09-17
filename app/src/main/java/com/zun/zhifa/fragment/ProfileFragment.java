package com.zun.zhifa.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zun.zhifa.R;
import com.zun.zhifa.activity.LoginActivity;

public class ProfileFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        final Activity act = getActivity();

        RelativeLayout collection = (RelativeLayout)act.findViewById(R.id.profile_user_collection);
        RelativeLayout document = (RelativeLayout)act.findViewById(R.id.profile_user_document);
        RelativeLayout account = (RelativeLayout)act.findViewById(R.id.profile_user_account);

        if (account != null) {
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(act, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
