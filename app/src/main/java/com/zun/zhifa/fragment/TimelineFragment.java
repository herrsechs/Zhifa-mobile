package com.zun.zhifa.fragment;

import android.Manifest;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

//import com.roughike.bottombar.BottomBar;
import com.zun.zhifa.R;
import com.zun.zhifa.adapter.ImageCardAdapter;
import com.zun.zhifa.constants.SettingConstants;

public class TimelineFragment extends Fragment {
//    private BottomBar bottomBar;
//    private Toolbar toolbar;
    private FloatingActionButton fab;
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
        RecyclerView.Adapter mAdapter = new ImageCardAdapter(act);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
//            //            private boolean scrolling = false;
//            private int SCROLL_THRESHOLD = 5;
//            private int sum = 0;
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
//                Log.d("SCROLL_Y", "" + dy);
//                if(dy > 0) {
//                    sum += dy;
//                }
//                if(sum > SCROLL_THRESHOLD){
//                    hide();
//                    sum = 0;
//                }else{
//                    show();
//                }
//            }
//
//            public void hide(){
////                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
////                bottomBar.animate().translationY(bottomBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
//                int fabBottomMargin = lp.bottomMargin;
//                fab.animate().translationY(fab.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
//            }
//
//            public void show(){
////                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
////                bottomBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//            }
//        });
    }
}
