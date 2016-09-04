package com.zun.zhifa;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int CODE_GALLERY_REQUEST = 0xa0;
    public static final int CODE_CAMERA_REQUEST = 0xa1;
    public static final int CODE_RESULT_REQUEST = 0xa2;
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CODE_CAMERA_REQUEST);
            }
        });

        BottomBar bottomBar = (BottomBar)findViewById(R.id.main_bottom_bar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_compass){

                }else if(tabId == R.id.tab_profile){

                }else if(tabId == R.id.tab_collection){

                }
            }
        });

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String path = "";
        if(requestCode == CODE_GALLERY_REQUEST){
            if(data == null){
                Log.d("Debug", "Gallery image is empty!");
            }
            try {
                File image = new File(getRealFilePath(this, data.getData()));
                path = getRealFilePath(this, data.getData());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }else if(requestCode == CODE_CAMERA_REQUEST){
            if(data == null){
                Log.d("Debug", "Camera image is empty!");
            }else {
                Bitmap bmp = (Bitmap)data.getExtras().get("data");
                SelfieActivity.selfieBmp = bmp;
            }
        }

        Intent intent = new Intent(MainActivity.this, SelfieActivity.class);
        intent.putExtra(SelfieActivity.SELFIE_PATH_KEY, path);
        startActivity(intent);
    }



    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
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

        @Override
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
            holder.itemView.setTag(position);
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

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                        int id = mThumbIds[(int)v.getTag()];
                        intent.putExtra(ImageDetailActivity.IMAGE_RES_ID, id);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
