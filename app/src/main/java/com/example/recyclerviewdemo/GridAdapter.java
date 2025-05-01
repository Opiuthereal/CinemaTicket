package com.example.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    Context context;
    int[] imageSiege;

    LayoutInflater inflater;
    public GridAdapter(Context context, int[] imageSiege) {
        this.context = context;
        this.imageSiege = imageSiege;
    }


    @Override
    public int getCount() {
        return imageSiege.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.grid_items, null);
        }

        ImageView imageView = view.findViewById(R.id.siege);

        if (imageSiege[position] == 0) {
            imageView.setVisibility(View.INVISIBLE); // invisible (prend place)
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(imageSiege[position]);
        }



        return view;
    }

}
