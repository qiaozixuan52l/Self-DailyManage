package com.selfmanage.doily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.selfmanage.doily.MainActivity;
import com.selfmanage.doily.R;
import com.selfmanage.doily.adapter.ClockAdapter;
import com.selfmanage.doily.entity.Clock;

import java.util.ArrayList;
import java.util.List;

public class clockList extends AppCompatActivity {
    private List<Clock> dataSource = new ArrayList<>();
    private ClockAdapter clockAdapter;
    private ListView listView;
    private ImageView back;
    private ImageView add;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clocklist);
        getSupportActionBar().hide();

        findView();
        setListener();
        Intent intent = getIntent();
        String clocks = intent.getStringExtra("clocks");
        dataSource = gson.fromJson(clocks, new TypeToken<List<Clock>>() {
        }.getType());
        Log.e("list", dataSource.get(2).getDate() + dataSource.toArray().length);
        clockAdapter = new ClockAdapter(this, dataSource, R.layout.clock_item);
        listView.setAdapter(clockAdapter);



    }

    private void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(clockList.this, MainActivity.class);
                intent.putExtra("num","1");
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(clockList.this, concentration.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        listView = findViewById(R.id.lv_clock);
        back=findViewById(R.id.clock_back_club);
        add=findViewById(R.id.add_clock);
    }
}
