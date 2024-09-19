package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.syber.ssspltd.R;
import com.syber.ssspltd.fragment.HomeFragment;

public class SliderAdapter  extends PagerAdapter {
    Context mContext;
    HomeFragment fragment;

    public SliderAdapter(Context context, HomeFragment fragment)
    {
        this.mContext = context ;
        this.fragment = fragment ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    private int[] sliderImageId = new int[]{
            R.drawable.filter_button_bg, R.drawable.button_one, R.drawable.button_two,R.drawable.button_three, R.drawable.button_four,
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return sliderImageId.length;
    }
}
