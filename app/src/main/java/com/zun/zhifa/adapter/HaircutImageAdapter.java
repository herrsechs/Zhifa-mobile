package com.zun.zhifa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zun.zhifa.R;
import com.zun.zhifa.constants.HttpConstants;
import com.zun.zhifa.httputil.BarberUtil;
import com.zun.zhifa.httputil.ImageDownloader;
import com.zun.zhifa.model.HaircutImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HaircutImageAdapter extends RecyclerView.Adapter<HaircutImageAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Integer> haircutIds;
    private ImageDownloader loader;
    public HaircutImageAdapter(Context c){
        mContext = c;
        haircutIds = new ArrayList<>();
        loader = new ImageDownloader(mContext);
        String haircutIdsStr = BarberUtil.getHaircutIds(c);
        try {
            JSONArray jsonArr = new JSONArray(haircutIdsStr);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                haircutIds.add(jsonObj.getInt("hair_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onBindViewHolder(final ViewHolder holder, int pos){
        String id = haircutIds.get(pos).toString();
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("pid", id);
            String jsonStr = jsonObj.toString();
            Bitmap bmp = loader.getFromCache(jsonStr);
            if (bmp == null) {

                loader.loadImage(HttpConstants.GET_HAIRCUT, jsonStr,
                        holder.haircutView.getWidth(), holder.haircutView.getHeight(),
                        new ImageDownloader.AsyncImageLoaderListener() {
                            @Override
                            public void onImageLoader(Bitmap bitmap) {
                                holder.haircutView.setImageBitmap(bitmap);
                            }
                        });
            } else {
                holder.haircutView.setImageBitmap(bmp);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getItemCount(){
        return haircutIds.size();
    }
    public HaircutImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_haircut_image, parent, false);
        return new ViewHolder(v);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView haircutView;
        public ViewHolder(View v){
            super(v);
            this.haircutView = (ImageView)v.findViewById(R.id.item_haircut_image_view);
        }
    }
}
