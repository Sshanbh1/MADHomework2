package com.example.hw02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdapterCustom extends BaseAdapter {

    Context mainContext;
    ArrayList<Integer> toppingImages;

    public AdapterCustom(Context mainContext, ArrayList<Integer> toppingImages){
        this.mainContext = mainContext;
        this.toppingImages = toppingImages;
    }

    @Override
    public int getCount() {
        return toppingImages.size();
    }

    @Override
    public Object getItem(int position) {
        return toppingImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView topping = new ImageView(mainContext);

        // Set Image to the Image View
        topping.setImageResource(toppingImages.get(position));
        topping.setScaleType(ImageView.ScaleType.CENTER_CROP);
        topping.setLayoutParams(new GridView.LayoutParams(150,150));
        return topping;
    }
}
