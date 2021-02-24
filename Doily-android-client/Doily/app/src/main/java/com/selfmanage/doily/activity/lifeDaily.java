package com.selfmanage.doily.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.selfmanage.doily.R;

public class lifeDaily extends AppCompatActivity{
    private ImageView back_club=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration);
        getSupportActionBar().hide();
        findView();
        setListener();
    }
    private void setListener() {
        back_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.selfmanage.doily.activity.lifeDaily.this.finish();
            }
        });
    }

    private void findView() {
        back_club=findViewById(R.id.back_club);

    }
}
