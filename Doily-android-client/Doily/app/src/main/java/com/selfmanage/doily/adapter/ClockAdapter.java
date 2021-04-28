package com.selfmanage.doily.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.selfmanage.doily.R;

import com.selfmanage.doily.entity.Clock;

import java.util.List;


public class ClockAdapter extends BaseAdapter {
    private Context context;
    private List<Clock> dataSource;
    private int itemLayoutId;

    public ClockAdapter(Context context, List<Clock> dataSource, int itemLayoutId) {
        this.context = context;
        this.dataSource = dataSource;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        if (null != dataSource) {
            return dataSource.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != dataSource) {
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
        if (null == convertView) {
            //加载item对应的布局文件
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
        }
        TextView name = convertView.findViewById(R.id.clock_name);
        TextView time = convertView.findViewById(R.id.clock_time);
        TextView date = convertView.findViewById(R.id.clock_date);
        TextView type = convertView.findViewById(R.id.clock_type);

        name.setText(dataSource.get(position).getTitle());
        time.setText(dataSource.get(position).getTime()+" 秒");
        date.setText(dataSource.get(position).getDate().toString());
        int type1 = dataSource.get(position).getType();
        if (type1 == 0) {
            type.setText("正向计时");
        } else {
            type.setText("倒计时");
        }
        return convertView;
    }
}
