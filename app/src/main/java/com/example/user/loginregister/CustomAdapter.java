package com.example.user.loginregister;

import android.content.Context;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2017.10.08.
 */

 class CustomAdapter extends ArrayAdapter<ItemClass> {
    private Context mContext;
    private int mResource;

     CustomAdapter(Context context, int resource, ArrayList<ItemClass> objects) {
        super(context, resource, objects);
         mContext = context;
         mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        String itemname = getItem(position).getItem();
        String description = getItem(position).getDescription();
        double price = getItem(position).getPrice();

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);


        tvItem.setText(itemname);
        tvDescription.setText(description);
        tvPrice.setText(price + "");


        return convertView;
    }


}
