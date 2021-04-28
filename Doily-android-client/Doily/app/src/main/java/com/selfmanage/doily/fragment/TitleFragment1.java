package com.selfmanage.doily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.selfmanage.doily.Constant.Constant;
import com.selfmanage.doily.R;
import com.selfmanage.doily.activity.clockList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class TitleFragment1 extends Fragment {
    private MyCountDownTimer myCountDownTimer;
    private Button finish;
    private Button start;
    private int num;
    private int current = 0;
    private TextView textView;
    private EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fanfragment, container, false);
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
        finish = view.findViewById(R.id.finish);
        start = view.findViewById(R.id.begin);
        textView = view.findViewById(R.id.tv_fan_clock);
        editText = view.findViewById(R.id.time_fan);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current=0;
                String time = String.valueOf(editText.getText());
                String[] words = time.split(" ");
                num = Integer.valueOf(String.valueOf(words[0]))*60;
                myCountDownTimer = new MyCountDownTimer(60000 * num, 1000);
                textView.setText(String.valueOf(num));
                myCountDownTimer.start();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.cancel();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Date date = new Date(System.currentTimeMillis());
                            Log.e("time", String.valueOf(current));
                            URL url = new URL(Constant.BASE_IP + "/concentrate/saveClock" + "?user=lky" + "&time=" + current + "&type=1" + "&date=" + date+"&title="+editText.getText());
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

        return view;
    }


    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            if (num == 0) {
                textView.setText(String.valueOf(0));
                current++;
                cancel();
            } else {
                //防止计时过程中重复点击
                Log.e("ni", current + "");
                textView.setText(String.valueOf(num--));
                current++;
            }
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {

        }

    }
}
