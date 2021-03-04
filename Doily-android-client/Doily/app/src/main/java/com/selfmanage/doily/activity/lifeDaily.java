package com.selfmanage.doily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.selfmanage.doily.MainActivity;
import com.selfmanage.doily.R;

public class lifeDaily extends AppCompatActivity {
    private ImageView back_club = null;
    private LinearLayout note_book;
    private LinearLayout daily_book;
    private LinearLayout record_movie;
    private LinearLayout important_day;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifedaliy);
        getSupportActionBar().hide();
        findView();
        setListener();
    }

    private void setListener() {
        back_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lifeDaily.this, MainActivity.class);
                startActivity(intent);
            }
        });
        note_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.lifeDaily.this, noteBook.class);
                startActivity(intent);
            }
        });
        daily_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.lifeDaily.this, dailyBook.class);
                startActivity(intent);
            }
        });
        record_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.lifeDaily.this, recordMovie.class);
                startActivity(intent);
            }
        });
        note_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.lifeDaily.this, noteBook.class);
                startActivity(intent);
            }
        });
        important_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.selfmanage.doily.activity.lifeDaily.this, importantDay.class);
                startActivity(intent);
            }
        });
    }

    private void findView() {
        back_club = findViewById(R.id.back_club);
        note_book = findViewById(R.id.notebook);
        daily_book = findViewById(R.id.diary);
        record_movie = findViewById(R.id.review_movie);
        important_day = findViewById(R.id.important_day);
    }
}
