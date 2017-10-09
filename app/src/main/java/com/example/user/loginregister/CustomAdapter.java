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

/**
 * Created by User on 2017.10.08.
 */

 class CustomAdapter extends ArrayAdapter<String> {

     CustomAdapter(Context context, String[] Items) {
        super(context,R.layout.custom_row, Items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String singleItem = getItem(position);
        TextView tvItem = (TextView) customView.findViewById(R.id.tvItem);
        ImageView ivPicture = (ImageView) customView.findViewById(R.id.ivPicture);

        tvItem.setText(singleItem);
        ivPicture.setImageResource(R.drawable.masina);

        return customView;
    }


}
