package com.zun.zhifa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zun.zhifa.R;

public class HaircutImageAdapter {
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView haircutView;
        public ViewHolder(View v){
            super(v);
            this.haircutView = (ImageView)v.findViewById(R.id.item_haircut_image_view);
        }
    }
}
