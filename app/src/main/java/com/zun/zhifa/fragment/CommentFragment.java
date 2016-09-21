package com.zun.zhifa.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zun.zhifa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentFragment extends Fragment {
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        return inflater.inflate(R.layout.list_view_fragment, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        Activity act = getActivity();
        listView = (ListView)act.findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(act, getData(), R.layout.comment_item,
                new String[]{"user_name", "comment"},
                new int[]{R.id.comment_user_name, R.id.comment_content});

        listView.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", "gamino");
        map.put("comment", "再遥远一点，雪花朦胧的双眼");
        list.add(map);

        map.clear();
        map.put("user_name", "naruto123456");
        map.put("comment", "It wasn't love, it wasn't love, it was a perfect illusion");
        list.add(map);

        return list;
    }
}
