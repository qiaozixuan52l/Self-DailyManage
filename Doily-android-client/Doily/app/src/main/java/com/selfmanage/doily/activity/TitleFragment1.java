package com.selfmanage.doily.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selfmanage.doily.R;

public class TitleFragment1 extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fanfragment,container,false); //  此处的布局文件是普通的线性布局（此博客忽略）
        return view;
    }
}
