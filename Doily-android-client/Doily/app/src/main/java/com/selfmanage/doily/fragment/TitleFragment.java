package com.selfmanage.doily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.selfmanage.doily.Constant.Constant;
import com.selfmanage.doily.R;
import com.selfmanage.doily.activity.clockList;
import com.selfmanage.doily.entity.Clock;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TitleFragment extends Fragment {
    private Chronometer mChronometer;
    private EditText editText = null;
    Button begin = null;
    Button finish = null;
    int current = 0;
    private List<Clock> dataSource = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhengfragment, container, false);
        // findView();

        mChronometer = view.findViewById(R.id.chronometer);
        begin = view.findViewById(R.id.begin);
        finish = view.findViewById(R.id.finish);
        editText = view.findViewById(R.id.name);
        setListener();
        initClock();
        return view;
    }

    private void setListener() {
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.start();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Date date = new Date(System.currentTimeMillis());
                            Log.e("time", String.valueOf(current));
                            URL url = new URL(Constant.BASE_IP + "/concentrate/saveClock" + "?user=lky" + "&time=" + current + "&type=0" + "&date=" + date+"&title="+editText.getText());
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            InputStreamReader in = new InputStreamReader(conn.getInputStream());
                            Log.e("time", String.valueOf(current));
                            BufferedReader reader = new BufferedReader(in);
                            String info = reader.readLine().toString();
                            Intent intent = new Intent(getActivity(), clockList.class);
                            intent.putExtra("clocks", info);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    private void initClock() {
        //正数计时设置初始值（重置）
        mChronometer.setBase(0);
        //正数计时事件监听器，时间发生变化时可进行操作
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer ch) {
                // 如果从开始计时到现在超过了60s
                if (SystemClock.elapsedRealtime() - mChronometer.getBase() > 60 * 24 * 60 * 1000) {
                    mChronometer.stop();
                }

            }
        });
        //设置格式(默认"MM:SS"格式)
        mChronometer.setFormat("%s");
        mChronometer.setText(FormatMiss(current));
        initData();

    }

    private void initData() {
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                current++;
                chronometer.setText(FormatMiss(current));
            }
        });
    }


    public static String FormatMiss(int time) {
        String hh = time / 3600 > 9 ? time / 3600 + "" : "0" + time / 3600;
        String mm = (time % 3600) / 60 > 9 ? (time % 3600) / 60 + "" : "0" + (time % 3600) / 60;
        String ss = (time % 3600) % 60 > 9 ? (time % 3600) % 60 + "" : "0" + (time % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }

}
