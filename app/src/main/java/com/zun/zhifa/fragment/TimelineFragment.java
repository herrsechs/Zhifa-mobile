package com.zun.zhifa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.roughike.bottombar.BottomBar;
import com.zun.zhifa.R;
import com.zun.zhifa.adapter.ImageCardAdapter;
import com.zun.zhifa.constants.SettingConstants;

public class TimelineFragment extends Fragment {
    private BottomBar bottomBar;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View timelineFrag = inflater.inflate(R.layout.app_bar_main, container, false);
        return timelineFrag;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        bottomBar = (BottomBar)getActivity().findViewById(R.id.main_bottom_bar);
        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, SettingConstants.CODE_CAMERA_REQUEST);
                }
            });
        }

        RecyclerView mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.main_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new ImageCardAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
            //            private boolean scrolling = false;
            private int SCROLL_THRESHOLD = 5;
            private int sum = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                Log.d("SCROLL_Y", "" + dy);
                if(dy > 0) {
                    sum += dy;
                }
                if(sum > SCROLL_THRESHOLD){
                    hide();
                    sum = 0;
                }else{
                    show();
                }
            }

            public void hide(){
                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                bottomBar.animate().translationY(bottomBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)fab.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                fab.animate().translationY(fab.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            public void show(){
                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                bottomBar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });
    }
}
