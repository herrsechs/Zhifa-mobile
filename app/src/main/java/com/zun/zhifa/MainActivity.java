package com.zun.zhifa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private int deviceWidth;
    private int deviceHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;
        deviceHeight = displayMetrics.heightPixels;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
        Intent intent = new Intent(this, ImageDetailActivity.class);
        String trans_favor_btn = (String)getResources().getString(R.string.favor_btn_trans);
        String trans_del_btn = (String)getResources().getString(R.string.del_btn_trans);
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                new Pair<View, String>(view.findViewById(R.id.main_favor_btn), trans_favor_btn),
                new Pair<View, String>(view.findViewById(R.id.main_delete_btn), trans_del_btn));
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        private Context mContext;
        public ImageAdapter(Context c){
            mContext = c;
        }
        public int getItemCount(){
            return mThumbIds.length;
        }
        public int cardViewWidth;
        public int cardViewHeight;

        public long getItemId(int position){
            return mThumbIds[position];
        }

        public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_image_card_view, parent, false);
            cardViewWidth = deviceWidth / 2;
            cardViewHeight = (int)(cardViewWidth * 1.3);
            v.setLayoutParams(new RecyclerView.LayoutParams(cardViewWidth, cardViewHeight));
            return new ViewHolder(v);
        }

        public void onBindViewHolder(ViewHolder holder, int position){
            holder.imageView.setImageResource(mThumbIds[position]);
        }

        private Integer[] mThumbIds = {
                R.drawable.res1,
                R.drawable.res2,
                R.drawable.res3,
                R.drawable.res4,
                R.drawable.res5
        };

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private ImageButton favorBtn;
            private ImageButton deleteBtn;

            public ViewHolder(View v){
                super(v);
                this.imageView = (ImageView)v.findViewById(R.id.main_card_image_view);
                this.favorBtn = (ImageButton)v.findViewById(R.id.main_favor_btn);
                this.deleteBtn = (ImageButton)v.findViewById(R.id.main_delete_btn);

                int imgWidth, imgHeight;
                imgWidth = imgHeight = cardViewWidth;
                this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(imgWidth, imgHeight));
//                int btnWidth, btnHeight;
//                btnWidth = btnHeight = (int)(cardViewHeight * 0.2);
//                this.favorBtn.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
//                this.deleteBtn.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
            }
        }
    }
}
