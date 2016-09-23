package com.zun.zhifa.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.zun.zhifa.constants.SettingConstants;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {
    private Context mContext;
    private int cardViewWidth;
    private int cardViewHeight;
    public int deviceWidth;
    private Integer[] mThumbIds = {
            R.drawable.res1,
            R.drawable.res2,
            R.drawable.res3,
            R.drawable.res4,
            R.drawable.res5,
            R.drawable.res1,
            R.drawable.res2
    };

    public ImageCardAdapter(Context c){
        mContext = c;
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


    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imageView.setImageResource(mThumbIds[position]);
        holder.itemView.setTag(position);
    }

    public long getItemId(int position){
        return mThumbIds[position];
    }
    public int getItemCount(){
        return mThumbIds.length;
    }

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
                    Intent intent = new Intent(mContext, ImageDetailActivity.class);
                    int id = mThumbIds[(int)v.getTag()];
                    intent.putExtra(ImageDetailActivity.IMAGE_RES_ID, id);
                    mContext.startActivity(intent);
                }
            });

            favorBtn.setOnClickListener(new View.OnClickListener() {
                private boolean clicked = false;
                @Override
                public void onClick(View v) {
                    if(!clicked) {

                    }else{
                        favorBtn.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            });
        }
    }
}
