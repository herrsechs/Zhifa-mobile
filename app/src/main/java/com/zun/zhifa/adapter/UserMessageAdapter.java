package com.zun.zhifa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zun.zhifa.R;
import com.zun.zhifa.httputil.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mMessages;
    private ArrayList<String> mBarberName;
    public UserMessageAdapter(Context context){
        mContext = context;
        mMessages = new ArrayList<>();
        mBarberName = new ArrayList<>();
        String itemInfoStr = UserUtil.getMessages(mContext);
        try {
            JSONArray jsonArr = new JSONArray(itemInfoStr);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                String name = jsonObj.getString("barber_name");
                String text = jsonObj.getString("text");
                mBarberName.add(name);
                mMessages.add(text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getItemCount(){
        return mMessages.size();
    }

    public void onBindViewHolder(ViewHolder holder, int pos) {
        holder.barberName.setText(mBarberName.get(pos));
        holder.msgText.setText(mMessages.get(pos));
    }

    public UserMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_user_message, parent, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView barberName;
        TextView msgText;
        ImageView headImage;
        ViewHolder(View v) {
            super(v);
            barberName = (TextView)v.findViewById(R.id.item_user_msg_barber_name);
            msgText = (TextView)v.findViewById(R.id.item_user_msg_text);
            headImage = (ImageView)v.findViewById(R.id.item_user_msg_head_image);
        }
    }
}
