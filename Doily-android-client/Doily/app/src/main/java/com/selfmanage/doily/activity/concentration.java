package com.selfmanage.doily.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.selfmanage.doily.R;
import com.selfmanage.doily.fragment.TitleFragment;
import com.selfmanage.doily.fragment.TitleFragment1;

public class concentration extends AppCompatActivity {
    private ImageView back_club=null;
    Fragment fragment=null;
    LinearLayout zheng=null;
    LinearLayout fan=null;
    TextView tv_zheng=null;
    TextView tv_fan=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration);
        getSupportActionBar().hide();
        findView();
        setListener();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.frame_concentrate,new TitleFragment()) // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
    private void setListener() {
        back_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.selfmanage.doily.activity.concentration.this.finish();
            }
        });
        zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new TitleFragment();
                getSupportFragmentManager()   //所得到的是所在fragment 的父容器的管理器
                        .beginTransaction()
                        //此处的id，是FrameLayout的id
                        .replace(R.id.frame_concentrate,fragment)   //anotherFragment()为新建的fragment的java文件，OnCreateView里面把.xml文件添加
                        .addToBackStack(null).commit();
                        //添加到后退栈
                tv_zheng.setTextColor(getResources().getColor(R.color.cc));
                tv_fan.setTextColor(Color.BLACK);
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frame_concentrate,new TitleFragment1())
                        .commit();
                tv_zheng.setTextColor(Color.BLACK);
                tv_fan.setTextColor(getResources().getColor(R.color.cc));

            }
        });
    }

    private void findView() {
        back_club=findViewById(R.id.back_club);
        zheng=findViewById(R.id.zheng);
        fan=findViewById(R.id.fan);
        tv_fan=findViewById(R.id.tv_fan);
        tv_zheng=findViewById(R.id.tv_zheng);
    }
}
