package com.lingayatpanchasanagam.sbjm.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lingayatpanchasanagam.sbjm.R;
import com.lingayatpanchasanagam.sbjm.model.SlideImages;


/**
 * Created by Basavaraj Navi on 08/01/18.
 * Project SBJM
 * Copyright (c) 2018 KaHa Technologies Pvt Ltd. All rights reserved.
 */


public class ViewPagerAdapter extends PagerAdapter
{

    private Context mContext;
    private int[] mResources;
    private SlideImages slideImages;

    public ViewPagerAdapter(Context mContext, SlideImages slideImages) {
        this.mContext = mContext;
        this.slideImages = slideImages;
    }

    @Override
    public int getCount() {
        if (slideImages.getSliderImages()!=null) {
            return slideImages.getSliderImages().size();
        }else return 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        //imageView.setImageResource(mResources[position]);
        if (slideImages.getSliderImages()!=null) {
            Glide.with(mContext)
                    .load(slideImages.getSliderImages().get(position).getLink())
                    .placeholder(R.drawable.splash_image)
                    .error(R.drawable.splash_image)
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.splash_image);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
