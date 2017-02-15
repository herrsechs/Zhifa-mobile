package com.zun.zhifa.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zun.zhifa.R;
import com.zun.zhifa.activity.ImageDetailActivity;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.httputil.ImageDownloader;
import com.zun.zhifa.httputil.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {
    private Context mContext;
    private int cardViewWidth;
    private int cardViewHeight;
    public int deviceWidth;
    private ArrayList<Integer> mThumbIds;
    private ArrayList<Integer> mHaircutIds;
    private ImageDownloader loader;

    public ImageCardAdapter(Context c){
        mContext = c;
        mThumbIds = new ArrayList<>();
        mHaircutIds = new ArrayList<>();
        loader = new ImageDownloader(mContext);

        String recHaircutStr = UserUtil.getRecHaircuts(mContext);
        try {
            JSONArray jsonArr = new JSONArray(recHaircutStr);
            for (int i = 0; i < jsonArr.length(); i++) {
                mHaircutIds.add(jsonArr.getInt(i));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ImageCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_image_card_view, parent, false);

        // Get device info through shared preference
        SharedPreferences settings = mContext.getSharedPreferences(SettingConstants.DEVICE_INFO, 0);
        deviceWidth = settings.getInt(SettingConstants.DEVICE_WIDTH, 800);

        cardViewWidth = deviceWidth / 2;
        cardViewHeight = (int) (cardViewWidth * 1.3);
        v.setLayoutParams(new RecyclerView.LayoutParams(cardViewWidth, cardViewHeight));
        return new ViewHolder(v);
    }


    public void onBindViewHolder(final ViewHolder holder, int position){
//        holder.imageView.setImageResource(mThumbIds.get(position));
        String id = mHaircutIds.get(position).toString();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("pid", id);
            String jsonStr = jsonObj.toString();
            Bitmap bmp = loader.getFromCache(jsonStr);
            if (bmp == null) {
                loader.loadImage(HttpConstants.GET_HAIRCUT, jsonStr,
                        0, 0, new ImageDownloader.AsyncImageLoaderListener(){
                            public void onImageLoader(Bitmap bitmap) {
                                holder.imageView.setImageBitmap(bitmap);
                            }
                        });
            } else {
                holder.imageView.setImageBitmap(bmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.itemView.setTag(position);
        holder.favorBtn.setTag(position);
        holder.deleteBtn.setTag(position);
    }

    public long getItemId(int position){
        return mHaircutIds.get(position);
    }
    public int getItemCount(){
        return mHaircutIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageButton favorBtn;
        private ImageButton deleteBtn;
        private int pos;

        public ViewHolder(View v){
            super(v);
            this.imageView = (ImageView)v.findViewById(R.id.main_card_image_view);
            this.favorBtn = (ImageButton)v.findViewById(R.id.main_favor_btn);
            this.deleteBtn = (ImageButton)v.findViewById(R.id.main_delete_btn);

            int imgWidth, imgHeight;
            imgWidth = cardViewWidth;
            imgHeight = cardViewWidth;
            this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(imgWidth, imgHeight));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageDetailActivity.class);
                    int id = mHaircutIds.get((int)v.getTag());
                    intent.putExtra(ImageDetailActivity.IMAGE_RES_ID, id);
                    mContext.startActivity(intent);
                }
            });

            favorBtn.setOnClickListener(new View.OnClickListener() {
                private boolean clicked = false;
                @Override
                public void onClick(View v) {
                    if(!clicked) {
                        favorBtn.setColorFilter(android.R.color.holo_red_dark);
                        clicked = true;
                    }else{
                        favorBtn.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int)v.getTag();
                    mThumbIds.remove(id);
                    notifyItemRemoved(id);
                    notifyItemRangeChanged(0, mThumbIds.size()-2);
                }
            });
        }
    }
}
