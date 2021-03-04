package com.selfmanage.doily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.selfmanage.doily.R;

public class noteBook extends AppCompatActivity {
    private ImageView back_club=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook);
        getSupportActionBar().hide();
        findView();
        setListener();
    }
    private void findView() {
        back_club=findViewById(R.id.back_club);
    }

    private void setListener() {
        back_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.noteBook.this, lifeDaily.class);
                startActivity(intent);
            }
        });
    }
}
