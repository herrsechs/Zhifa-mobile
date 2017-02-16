package com.zun.zhifa.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.roughike.bottombar.BottomBar;
import com.zun.zhifa.R;
import com.zun.zhifa.adapter.ImageCardAdapter;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.httputil.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TimelineFragment extends Fragment {
//    private BottomBar bottomBar;
//    private Toolbar toolbar;
    private FloatingActionButton fab;
    private EditText searchInput;
    ImageCardAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("onCreateView", "HERE");
        View timelineFrag = inflater.inflate(R.layout.activity_timeline, container, false);
        return timelineFrag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

//        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
//        bottomBar = (BottomBar)getActivity().findViewById(R.id.main_bottom_bar);
        final Activity act = getActivity();
        Log.d("onActivityCreated", "HERE");
        fab = (FloatingActionButton)act.findViewById(R.id.fab);
        searchInput = (EditText)act.findViewById(R.id.fragment_search_input_box);

        if (searchInput != null) {
            searchInput.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        handleTextInput(act);
                    }
                    return false;
                }
            });
        }

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(act.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        act.startActivityForResult(intent, SettingConstants.CODE_CAMERA_REQUEST);
                    }else{
                        Toast.makeText(act, "无拍照权限，请先设置", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        RecyclerView mRecyclerView = (RecyclerView)act.findViewById(R.id.timeline_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(act, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageCardAdapter(act);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void handleTextInput(final Activity a){
        String gender = "";
        String hair_len = "";
        String text = searchInput.getText().toString();
        if (text.contains("男")) {
            gender = "male";
        } else if (text.contains("女")) {
            gender = "female";
        }
        if (text.contains("长")) {
            hair_len = "long";
        } else if (text.contains("短")) {
            hair_len = "short";
        }
        if (!"".equals(gender) || !"".equals(hair_len)) {
            try {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("gender", gender);
                jsonObj.put("hair_len", hair_len);
                String jsonStr = jsonObj.toString();
                Callback callback = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String hids = response.body().string();
                        a.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.cleanData();
                                mAdapter.parseHaircutString(hids);
                            }
                        });

                    }
                };
                HttpUtil.postJSON(HttpConstants.GET_SEARCH_RESULT, jsonStr,
                        callback);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }


}
