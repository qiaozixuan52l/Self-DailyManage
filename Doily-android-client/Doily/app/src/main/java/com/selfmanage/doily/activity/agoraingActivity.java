package com.selfmanage.doily.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.selfmanage.doily.R;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class agoraingActivity extends AppCompatActivity {
    private ImageView exit;
    private TextView agoraingTag;
    private TextView agoraingRecord;
    private ImageView reagora;
    private ImageView agoStatusBtn;
    private Button confirmBtn;
    private boolean agoraing=false;
    private boolean agoraed=false;
    private boolean playing=false;
    private boolean isPause=false;
    private boolean isPlaying=false;
    private MediaRecorder recorder=null;
    private MediaPlayer mMediaPlayer;
    //录音文件名字
    private String filePath=null;
    private final Handler handler = new Handler();
    private final Handler playHandler=new Handler();
   private  Runnable runnable;
   private Runnable playRunnable;
    private int Count;
    private int playingTime;
   // private static boolean isStop=false;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agoraing_activity);
        getSupportActionBar().hide();
        findView();
        setOnClickListener();
    }

    private class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.agoraing_exit:
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case R.id.agoraing_reagora:
                    //重录
                    if(mMediaPlayer.isPlaying()){
                        mMediaPlayer.stop();
                        playing=false;
                    }
                    agoStatusBtn.setImageResource(R.drawable.agoraclick);
                    reagora.setVisibility(View.GONE);
                    confirmBtn.setVisibility(View.GONE);
                    agoraingTag.setText("开始录制吧");
                    agoraingRecord.setText("0");
                    //todo 删除文件
                    filePath=null;
                    agoraed=false;
                    agoraing=false;
                    break;
                case R.id.agoraing_statusbtn:
                    if(agoraing==false&&agoraed==false) {
                        agoStatusBtn.setImageResource(R.drawable.agoraing);
                       //
                         agoraing = true;
                         isPlaying=true;
                        reagora.setVisibility(View.GONE);
                        confirmBtn.setVisibility(View.GONE);
                        agoraingTag.setText("录制中");
                        StartRecord();
                        //开始录制
                    }
                    else if(agoraing==true&&agoraed==false){
                        agoStatusBtn.setImageResource(R.drawable.startagora);
                        agoraed=true;
                        agoraing=false;
                        reagora.setVisibility(View.VISIBLE);
                        confirmBtn.setVisibility(View.VISIBLE);
                        agoraingTag.setText("录音时长");
                        StopRecord();
                        //停止录制
                    }
                    else if(agoraing==false&&agoraed==true&&playing==false){
                        agoStatusBtn.setImageResource(R.drawable.pauseagora);
                        playing=true;
                        //开始播放
                        startPlayAudio();
                    }
                    else if(agoraing==false&&agoraed==true&&playing==true){
                        agoStatusBtn.setImageResource(R.drawable.startagora);
                        pauseOrpPlay();
                        //暂停播放

                    }

                    break;
                case R.id.agoraing_confirmbtn:
                    //完成录制
                    agoraed=false;
                    agoraing=false;
                    //todo:跳转到editAudio
                  //  Intent intent =new Intent(this,)
                    Intent myIntent=getIntent();
                    if(myIntent.getStringExtra("source").equals("editAudio")) {
                        Intent intent = new Intent(com.selfmanage.doily.activity.agoraingActivity.this, editAudio.class);
                        intent.putExtra("filePath",filePath);
                        startActivity(intent);
                        finish();
                    }
                    else if(myIntent.getStringExtra("sourse").equals("myAccount")){
                        Intent intent = new Intent(com.selfmanage.doily.activity.agoraingActivity.this, myAccount.class);
                        intent.putExtra("filePath",filePath);
                        startActivity(intent);
                        finish();
                    }
                  break;


            }

        }
    }
    private void findView(){
        exit=findViewById(R.id.agoraing_exit);
        agoraingTag=findViewById(R.id.agoraing_statustag);
        agoraingRecord=findViewById(R.id.agoraing_record);
        reagora=findViewById(R.id.agoraing_reagora);
        agoStatusBtn=findViewById(R.id.agoraing_statusbtn);
        confirmBtn=findViewById(R.id.agoraing_confirmbtn);

    }
    private void setOnClickListener() {
        Listener listener=new Listener();
       exit.setOnClickListener(listener);
       reagora.setOnClickListener(listener);
       agoStatusBtn.setOnClickListener(listener);
       confirmBtn.setOnClickListener(listener);

    }

    private void StartRecord() {
        if (recorder == null) {
            recorder = new MediaRecorder();//初始化录音对象
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录音的输入源(麦克)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置音频格式(amr)
            createRecorderFile();//创建保存录音的文件夹
            String name= getCurrentTime();
            recorder.setOutputFile("sdcard/recorder" + "/" +name + ".AAC"); //设置录音保存的文件
            filePath="sdcard/recorder" + "/" +name + ".AAC";
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//设置音频编码
            try {
                recorder.prepare();//准备录音
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            if (recorder != null) {
                recorder.start(); //开始录音
                Count = 0;
                runnable=new Runnable() {
                    @Override
                    public void run() {
                            if (Count == 120) {//限时2min
                                StopRecord();
                                handler.removeCallbacks(runnable);
                                agoStatusBtn.setImageResource(R.drawable.startagora);
                                agoraed = true;
                                agoraing = false;
                                reagora.setVisibility(View.VISIBLE);
                                confirmBtn.setVisibility(View.VISIBLE);
                                agoraingTag.setText("录音时长");

                            }
                            Count++;
                            agoraingRecord.setText(Integer.toString(Count));
                            //刷新time视图显示计时
                            //String str=showTimeCount((long)Count)+"/30:00";
                            handler.postDelayed(this, 1000);//每一秒刷新一次 }

                        if (recorder == null) {
                            handler.removeCallbacks(runnable);
                        }

                    }
                };
                runnable.run();

            }

    }

            private void StopRecord () {
                if (recorder != null) {
                    try {
                        //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
                        //报错为：RuntimeException:stop failed
                        recorder.setOnErrorListener(null);
                        recorder.setOnInfoListener(null);
                        recorder.setPreviewDisplay(null);
                        recorder.stop();
                    } catch (IllegalStateException e) {
                        // TODO: handle exception
                        Log.i("Exception", Log.getStackTraceString(e));
                    }catch (RuntimeException e) {
                        // TODO: handle exception
                        Log.i("Exception", Log.getStackTraceString(e));
                    }catch (Exception e) {
                        // TODO: handle exception
                        Log.i("Exception", Log.getStackTraceString(e));
                    }
                    recorder.release();
                    recorder = null;
                 //isStop=true;
                }
            }


            private void startPlayAudio() {
                mMediaPlayer = new MediaPlayer();
                try{
                    mMediaPlayer.setDataSource(filePath);
                    mMediaPlayer.prepare();
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        @Override
                                public void onPrepared(MediaPlayer mp){
                                mMediaPlayer.start();
                            final int playTime=Integer.valueOf(agoraingRecord.getText().toString());
                            playingTime=playTime;
                            playRunnable=new Runnable() {
                                public void run() {

                                        if (Integer.valueOf(agoraingRecord.getText().toString()) == 0&&isPlaying==true&&playing==true) {
                                            agoraingRecord.setText(Integer.toString(playTime));
                                            playing = false;
                                            playHandler.removeCallbacks(playRunnable);
                                            agoStatusBtn.setImageResource(R.drawable.startagora);
                                        }
                                        else if (isPlaying==true&&playing==true) {
                                            playingTime--;
                                            agoraingRecord.setText(Integer.toString(playingTime));
                                        }

                                        playHandler.postDelayed(this, 1000);//每一秒刷新一次 }

                                }
                            };
                            playRunnable.run();
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
                    });} catch(IOException e){
                    e.printStackTrace();
                }


            }

            private void pauseOrpPlay(){
                if(mMediaPlayer.isPlaying()){//变为bof状态
                   //每一秒刷新一次 }
                    isPlaying=false;
                    mMediaPlayer.pause();
                    agoStatusBtn.setImageResource(R.drawable.startagora);

                }else{//bianw pause状态
                    isPlaying=true;
                    mMediaPlayer.start();
                    agoStatusBtn.setImageResource(R.drawable.pauseagora);


                }
            }


            //创建保存录音的目录
            private void createRecorderFile() {
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileDir = absolutePath + "/recorder";
                File file = new File(fileDir);
                if (!file.exists()) {
                    file.mkdir();
                }

            }

            private String getCurrentTime(){
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                String str = format.format(date);
                return str;
    }


    }



