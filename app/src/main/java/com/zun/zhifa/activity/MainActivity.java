package com.zun.zhifa.activity;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.zun.zhifa.R;
import com.zun.zhifa.adapter.ImageCardAdapter;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.fragment.ChangingFaceFragment;
import com.zun.zhifa.fragment.ProfileFragment;
import com.zun.zhifa.fragment.TimelineFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TimelineFragment timelineFg;
    private ProfileFragment profileFg;
    private ChangingFaceFragment changingFaceFg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomBar bottomBar = (BottomBar)findViewById(R.id.main_bottom_bar);
        if (bottomBar != null) {
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                if(tabId == R.id.tab_compass){
                    setTabSelection(0);
                }else if(tabId == R.id.tab_profile){
                    setTabSelection(1);
                }else if(tabId == R.id.tab_collection){
                    setTabSelection(2);
                }
                }
            });
        }
    }

    private void setTabSelection(int index){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch(index){
            case 0:
                if(timelineFg == null){
                    timelineFg = new TimelineFragment();
                    transaction.add(R.id.main_content, timelineFg);
                }else{
                    transaction.show(timelineFg);
                }
                break;
            case 1:
                if(profileFg == null){
                    profileFg = new ProfileFragment();
                    transaction.add(R.id.main_content, profileFg);
                }else{
                    transaction.show(profileFg);
                }
                break;
            case 2:
                if(changingFaceFg == null){
                    changingFaceFg = new ChangingFaceFragment();
                    transaction.add(R.id.main_content, changingFaceFg);
                }else{
                    transaction.show(changingFaceFg);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction){
        if(timelineFg != null){
            transaction.hide(timelineFg);
        }
        if(profileFg != null){
            transaction.hide(profileFg);
        }
        if(changingFaceFg != null){
            transaction.hide(changingFaceFg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.main_search);
        SearchManager searchManager = (SearchManager)MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if(searchItem != null){
            searchView = (SearchView)searchItem.getActionView();
        }
        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        String path = "";
        if(requestCode == SettingConstants.CODE_GALLERY_REQUEST){
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
        }else if(requestCode == SettingConstants.CODE_CAMERA_REQUEST){
            if(data == null){
                Log.d("Debug", "Camera image is empty!");
            }else {
                SelfieActivity.selfieBmp = (Bitmap)data.getExtras().get("data");
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


}
