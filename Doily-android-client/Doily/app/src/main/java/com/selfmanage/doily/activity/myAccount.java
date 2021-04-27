package com.selfmanage.doily.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.selfmanage.doily.Constant.Constant;
import com.selfmanage.doily.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.OkHttpClient;

public class myAccount extends AppCompatActivity {
    private ImageView myHeaderimg;
    private TextView nickName;
    private ImageView playAudio;
    private ImageView backImg;
    private TextView accountNum;
    private ImageView reHeaderImg;
    private TextView renickName;
    private TextView editBrief;
    private  ImageView reRecodermore;
    private ImageView reHeaderImgmore;
    private ImageView renickNamemore;
    private ImageView editBriefmore;

    private MediaPlayer mMediaPlayer;

    private OkHttpClient client = new OkHttpClient();
    private Handler handler;

    private Map<String, Object> map;//录音文件名字
    private String filePath=null;
    private static final String SEARCH_USERINFO = "/post/listByUserId";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_activity);
        getSupportActionBar().hide();
        findView();
        getUserInfo();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String messages = (String) msg.obj;
                Log.e("获取——————————userInfo", messages);
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                map = new Gson().fromJson(messages, type);
            }

        };
        setOnClickListener();



    }
    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.myaccount_back:
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;


                case R.id.myaccount_playaudio:
                        playAudio();

                    break;

                case R.id.myaccount_reheadimgmore:

                    break;
                case   R.id.myaccount_renickNamemore:
                    Intent nintent=new Intent(com.selfmanage.doily.activity.myAccount.this,myAccount_reedit_nickName.class);
                    startActivity(nintent);
                    break;
                case R.id.myaccount_rebriefmore:
                    Intent bintent=new Intent(com.selfmanage.doily.activity.myAccount.this,myAccount_reedit_Brief.class);
                    startActivity(bintent);
                    break;
                case R.id.myaccount_reaudiobriefmore:
                    Intent intent=new Intent(com.selfmanage.doily.activity.myAccount.this,agoraingActivity.class);
                    startActivity(intent);
                    break;

            }

        }
    }

    private void  setOnClickListener(){
        Listener listener=new Listener();
        backImg.setOnClickListener(listener);
        playAudio.setOnClickListener(listener);
        reHeaderImgmore.setOnClickListener(listener);
        renickNamemore.setOnClickListener(listener);
        editBriefmore.setOnClickListener(listener);
        reRecodermore.setOnClickListener(listener);



    }

    private void findView(){
        backImg=findViewById(R.id.myaccount_back);
        myHeaderimg=findViewById(R.id.myaccount_headerimg);
        nickName=findViewById(R.id.myaccount_nickName);
        playAudio=findViewById(R.id.myaccount_playaudio);
        accountNum=findViewById(R.id.myaccount_accountnum);
        reHeaderImg=findViewById(R.id.myaccount_reheadimg);
        reHeaderImgmore=findViewById(R.id.myaccount_reheadimgmore);
        renickName=findViewById(R.id.myaccount_renickName);
        renickNamemore=findViewById(R.id.myaccount_renickNamemore);
        editBrief=findViewById(R.id.myaccount_rebrief);
        editBriefmore=findViewById(R.id.myaccount_rebriefmore);
        reRecodermore=findViewById(R.id.myaccount_reaudiobriefmore);

    }

    private void getUserInfo(){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Constant.BASE_IP + "/center/userInfo" + "?id=" + 1);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("******************", info);
                    Message message = new Message();
                    message.obj = info;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



      private void playAudio() {
          if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
              Toast.makeText(myAccount.this, "正在播放 ", Toast.LENGTH_SHORT).show();
              return;
          } else {
              if(getIntent().getStringExtra("filePath")!=null)
              filePath=getIntent().getStringExtra("filePath");
              else if(filePath.equals(null)) {
                  Toast.makeText(com.selfmanage.doily.activity.myAccount.this,"您还没有语音签名喲",Toast.LENGTH_SHORT);
                  return;
              }
              mMediaPlayer = new MediaPlayer();
              //判断filePath?空
              try {
                  mMediaPlayer.setDataSource("");
                  mMediaPlayer.prepare();
                  mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                      @Override
                      public void onPrepared(MediaPlayer mp) {
                          mMediaPlayer.start();

                      }
                  });
                  mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                      @Override
                      public void onCompletion(MediaPlayer mp) {
                          if (mMediaPlayer.isPlaying()) {
                              Log.i("false", "onCompletion:正在播放");
                          } else {
                              mMediaPlayer.release();
                          }

                      }
                  });
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }






}












