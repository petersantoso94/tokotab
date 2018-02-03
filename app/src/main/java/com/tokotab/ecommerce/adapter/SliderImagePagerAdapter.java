package com.tokotab.ecommerce.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.tokotab.ecommerce.model.SliderImage;

import com.tokotab.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

public class SliderImagePagerAdapter extends PagerAdapter {

    private List<SliderImage> sliderImages = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public SliderImagePagerAdapter(Context context, List<SliderImage> sliderImages) {
        this.context = context;
        this.sliderImages = sliderImages;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public int getCount() {
        return sliderImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderImageBeranda = inflater.inflate(R.layout.slider_image_beranda, container, false);

        ImageView imageSlider = (ImageView) sliderImageBeranda.findViewById(R.id.image_slider1);
        sliderImages.get(position).setImage(imageSlider);

        imageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        container.addView(sliderImageBeranda);
        return sliderImageBeranda;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (FrameLayout) object);
    }
}
