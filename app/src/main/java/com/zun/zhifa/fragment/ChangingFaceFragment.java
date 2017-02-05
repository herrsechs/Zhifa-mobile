package com.zun.zhifa.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zun.zhifa.R;
import com.zun.zhifa.constants.ImageFilterConstants;
import com.zun.zhifa.constants.SettingConstants;
import com.zun.zhifa.httputil.ImageUtil;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;

public class ChangingFaceFragment  extends Fragment{
    public static Bitmap selfieBmp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_changing_face, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        final Activity act = getActivity();

        final ImageView mergeFaceView = (ImageView)act.findViewById(R.id.change_face_merging_image_view);
        ImageView haircutView = (ImageView)act.findViewById(R.id.changing_face_haircut_image);
        Button normalBtn = (Button)act.findViewById(R.id.change_face_normal_btn);
        Button sketchBtn = (Button)act.findViewById(R.id.change_face_sketch_btn);
        Button softLightBtn = (Button)act.findViewById(R.id.change_face_soft_light_btn);
        Button nostalgiaBtn = (Button)act.findViewById(R.id.change_face_nostalgia_btn);

        if(mergeFaceView != null && selfieBmp != null){
            mergeFaceView.setImageBitmap(selfieBmp);
        }


        if(normalBtn != null){
            normalBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mergeFaceView != null && selfieBmp != null){
                        mergeFaceView.setImageBitmap(selfieBmp);
                    }
                }
            });
        }

        if(sketchBtn != null){
            sketchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFilterImg(act, mergeFaceView, ImageFilterConstants.SKETCH_FILTER);
                }
            });
        }

        if(softLightBtn != null){
            softLightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFilterImg(act, mergeFaceView, ImageFilterConstants.SOFT_LIGHT_FILTER);
                }
            });
        }

        if(nostalgiaBtn != null){
            nostalgiaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFilterImg(act, mergeFaceView, ImageFilterConstants.NOSTALGIA_FILTER);
                }
            });
        }

        if(mergeFaceView != null){
            mergeFaceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = act.getSharedPreferences(SettingConstants.SP_CHANGE_FACE_KEY,
                            Context.MODE_PRIVATE);
                    int hid = sp.getInt(SettingConstants.SP_HAIRCUT_KEY, 0);
                    int sid = sp.getInt(SettingConstants.SP_SELFIE_KEY, 0);
                    ImageUtil.downloadChangedFace(act, hid, sid, mergeFaceView);
                }
            });
        }

    }

    private void setFilterImg(Context context, ImageView view, int flag){
        if(selfieBmp == null){
            Toast.makeText(context, "请先设置自拍", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap filteredBmp = filterImg(context, selfieBmp, getFilter(flag));
        if(view != null){
            view.setImageBitmap(filteredBmp);
        }
    }

    private Bitmap filterImg(Context context, Bitmap bmp, GPUImageFilter filter){
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(bmp);
        gpuImage.setFilter(filter);
        return gpuImage.getBitmapWithFilterApplied();
    }

    private GPUImageFilter getFilter(int flag){
        GPUImageFilter filter;
        switch (flag){
            case ImageFilterConstants.SKETCH_FILTER:
                filter = new GPUImageSketchFilter();
                break;
            case ImageFilterConstants.SOFT_LIGHT_FILTER:
                filter = new GPUImageSoftLightBlendFilter();
                break;
            case ImageFilterConstants.NOSTALGIA_FILTER:
                filter = new GPUImageSepiaFilter();
                break;
            default:
                filter = new GPUImageFilter();
                break;
        }
        return filter;
    }

}
