package com.zun.zhifa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.BarberUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class BarberTrendItemAdapter extends RecyclerView.Adapter<BarberTrendItemAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<Integer> mHaircutIds;
    private ArrayList<String> mBarberNames;
    private ArrayList<Integer> mFavorCounts;
    public BarberTrendItemAdapter(Context c) {
        mContext = c;
        String itemInfoStr = BarberUtil.getTrendItems(c);
        mHaircutIds = new ArrayList<>();
        mBarberNames = new ArrayList<>();
        mFavorCounts = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(itemInfoStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String name = jsonObj.getString("barber_name");
                Integer hid = jsonObj.getInt("hair_img_id");
                Integer fCount = jsonObj.getInt("favor_count");
                mBarberNames.add(name);
                mHaircutIds.add(hid);
                mFavorCounts.add(fCount);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBindViewHolder(ViewHolder holder, int pos) {
        holder.numberTxt.setText(String.valueOf(pos));
        holder.barberNameTxt.setText(mBarberNames.get(pos));

    }

    public BarberTrendItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barber_trend, parent, false);
        return new ViewHolder(v);
    }

    public int getItemCount() {
        return mBarberNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberTxt;
        ImageView haircutView;
        TextView barberNameTxt;
        ViewHolder(View v) {
            super(v);
            numberTxt = (TextView)v.findViewById(R.id.item_barber_trend_number_text);
            haircutView = (ImageView)v.findViewById(R.id.item_barber_trend_haircut_image);
            barberNameTxt = (TextView)v.findViewById(R.id.item_barber_trend_barber_name);
        }
    }
}
