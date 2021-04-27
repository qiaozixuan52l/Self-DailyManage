package com.selfmanage.doily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.selfmanage.doily.R;

public class userAccount extends AppCompatActivity {
    private ImageView imgBack;
    private ImageView headImg;
    private TextView nickName;
    private TextView brief;
    private ImageView microphone;
    private ImageView attentionImg;
    private TextView attentionText;
    private Button moreImg;
    private boolean attentiontag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.useraccount_activity);
        findView();



    }
    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.useraccount_back:
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;


                case R.id.useraccount_playaudio:


                    break;

                case R.id.useraccount_attentionimg:

                    break;
                case   R.id.myaccount_renickNamemore:
                    Intent nintent=new Intent(com.selfmanage.doily.activity.userAccount.this,myAccount_reedit_nickName.class);
                    startActivity(nintent);
                    break;
                case R.id.myaccount_rebriefmore:
                    Intent bintent=new Intent(com.selfmanage.doily.activity.userAccount.this,myAccount_reedit_Brief.class);
                    startActivity(bintent);
                    break;
                case R.id.myaccount_reaudiobriefmore:
                    break;

            }

        }
    }

    private void  setOnClickListener(){

    }
    private void findView() {
        imgBack=findViewById(R.id.useraccount_back);
        headImg=findViewById(R.id.useraccount_headimg);
        nickName=findViewById(R.id.useraccount_name);
        brief=findViewById(R.id.useraccount_brief);
        microphone=findViewById(R.id.useraccount_playaudio);
        attentionImg=findViewById(R.id.useraccount_attentionimg);
        attentionText=findViewById(R.id.useraccount_attentiontext);
        moreImg=findViewById(R.id.useraccount_moreabout);


    }
    }

