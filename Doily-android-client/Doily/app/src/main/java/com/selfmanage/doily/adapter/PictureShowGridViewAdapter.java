package com.selfmanage.doily.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.selfmanage.doily.R;


import java.util.List;


public class PictureShowGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> dataSource;
    private int itemId;

    public PictureShowGridViewAdapter(Context context, List<String> dataSource, int itemId) {
        this.context = context;
        this.dataSource = dataSource;
        this.itemId = itemId;
    }

    @Override
    public int getCount() {
        int count = (dataSource == null ? 1 : dataSource.size() + 1);
        if (count > 9) {
            return dataSource.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        if (dataSource != null) {
            return dataSource.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemId, parent, false);
        }

        ImageView iv = convertView.findViewById(R.id.grid_view_item);
        if (position < dataSource.size()) {
            Glide.with(context).load(dataSource.get(position)).into(iv);
        } else {
            iv.setImageResource(R.drawable.addpng);
        }

        return convertView;
    }
}
