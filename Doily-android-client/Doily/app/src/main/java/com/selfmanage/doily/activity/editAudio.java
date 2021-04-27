package com.selfmanage.doily.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.selfmanage.doily.R;
import com.selfmanage.doily.adapter.PictureShowGridViewAdapter;
import com.zyao89.view.zloading.ZLoadingDialog;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class editAudio extends TakePhotoActivity {
    private ImageView back = null;
    private GridView addpng = null;
    private Button publish = null;
    private ImageView editShift = null;
    private LinearLayout selfSelect = null;
    private LinearLayout edit_agoraclick=null;
    private ImageView selfSelecttag = null;
    private  ImageView agoraimgtag=null;
    private TextView agoratexttag=null;
    private boolean selftag = false;
    private AppCompatTextView playerView=null;

    private static final int MAX_SELECT_PIC_NUM = 3;
    private List<String> dataSource = new ArrayList<>();

    private ZLoadingDialog zLoadingDialog=new ZLoadingDialog(this);
    private OkHttpClient client = new OkHttpClient();
    private MediaPlayer mMediaPlayer;
    private PictureShowGridViewAdapter adapter;
//    private boolean initTag=false;
    private static final String AUDIOPOST_PUBLISH_PATH = "/post/publish";
    private static final String PUBLISH_POST_SUCCESS = "success";
    private String filePath=null;//录音文件
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiopost_edit_activity);
        findView();
        initTagView();
        setOnClickListener();
        initGridView();

    }


    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.audio_edit_back:
                    com.selfmanage.doily.activity.editAudio.this.finish();
                    break;
                case R.id.audio_edit_shift:
                    Intent intent=new Intent(com.selfmanage.doily.activity.editAudio.this,editText.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.audio_edit_agoraimgtag:
                    checkPermission();

                        Intent toAgoraing = new Intent(com.selfmanage.doily.activity.editAudio.this, agoraingActivity.class);
                        toAgoraing.putExtra("source", "editAudio");
                        startActivity(toAgoraing);
                        finish();

                    break;
                case R.id.audio_edit_playerView:
                    Intent getAudioIntent =getIntent();
                    if(getAudioIntent.getStringExtra("filePath")!=null&&!getAudioIntent.getStringExtra("filePath").equals(null)){
                        //播放
                        playAudio();
                    }
                    break;

                case R.id.audio_edit_publish:

//                    if (TextUtils.isEmpty(txtContent) || dataSource.size() == 0 || dataSource == null) {
//                        Toast.makeText(com.selfmanage.doily.activity.editText.this,"请完善您的心情",Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        zLoadingDialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)
//                                .setLoadingColor(Color.WHITE)
//                                .setHintText("发布中...")
//                                .setHintTextSize(16)
//                                .setHintTextColor(Color.WHITE)
//                                .setDurationTime(0.5)
//                                .setDialogBackgroundColor(Color.BLACK)
//                                .setCanceledOnTouchOutside(false)
//                                .show();
//                        publish(txtContent, dataSource.toString(),selftag);
//
//                    }
                    break;

                case R.id.audio_edit_tomyself:
                    if(selftag==false) {
                        selftag = true;
                        selfSelecttag.setImageResource(R.drawable.selfselected);
                    }
                    else {
                        selftag = false;
                        selfSelecttag.setImageResource(R.drawable.selfselect);
                    }
                break;

            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void publishSuccess(String result) {
        if (result.equals(PUBLISH_POST_SUCCESS)) {
            zLoadingDialog.dismiss();
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private void initGridView() {
        adapter = new PictureShowGridViewAdapter(this, dataSource, R.layout.picture_show_grid_item);
        addpng.setAdapter(adapter);
        addpng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    TakePhoto takePhoto = getTakePhoto();
                    if (dataSource.size() == 0) {
                        takePhoto.onPickMultiple(MAX_SELECT_PIC_NUM);
                    } else {
                        takePhoto.onPickMultiple(MAX_SELECT_PIC_NUM - dataSource.size());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 权限申请
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, 200);
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == 200) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 200);
                    return;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200) {
            checkPermission();
        }
    }
    private void findView(){
        back = findViewById(R.id.audio_edit_back);
        addpng = findViewById(R.id.audio_edit_addpng);
        publish = findViewById(R.id.audio_edit_publish);
        editShift = findViewById(R.id.audio_edit_shift);
        selfSelect = findViewById(R.id.audio_edit_tomyself);
        selfSelecttag=findViewById(R.id.audio_edit_tomyselftag);
        edit_agoraclick=findViewById(R.id.audio_edit_agoraclick);
        agoraimgtag=findViewById(R.id.audio_edit_agoraimgtag);
        agoratexttag=findViewById(R.id.audio_edit_agoratext);
        playerView=findViewById(R.id.audio_edit_playerView);

    }
    private void playAudio() {
        if (mMediaPlayer!=null&&mMediaPlayer.isPlaying()) {
            Toast.makeText(editAudio.this, "正在播放 ", Toast.LENGTH_SHORT).show();
            return;
        }
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
    }

    private void initTagView(){
        if(getIntent().getStringExtra("filePath")!=null&&!getIntent().getStringExtra("filePath").equals(null)) {
            filePath = getIntent().getStringExtra("filePath");
            agoraimgtag.setVisibility(View.GONE);
            agoratexttag.setVisibility(View.GONE);
            mMediaPlayer=new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(filePath);
                mMediaPlayer.prepare();
                int duration = mMediaPlayer.getDuration();
                if (duration == -1){
                    playerView.setText("");
                }else {
                    int sec = duration/1000;
                    int m = sec/60;
                    int s = sec%60;
                    ViewGroup.LayoutParams lp;
                    lp= playerView.getLayoutParams();

                    if(sec*20<50)
                        lp.width=150;
                    else
                        lp.width=sec*20;
                    playerView.setText(m+":"+s);
                    //playerView.setWidth(sec*200);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerView.setVisibility(View.VISIBLE);
        }

    }


    private void setOnClickListener() {
       Listener listener=new Listener();
        back.setOnClickListener(listener);
        editShift.setOnClickListener(listener);
        publish.setOnClickListener(listener);
        selfSelect.setOnClickListener(listener);
        edit_agoraclick.setOnClickListener(listener);
        agoraimgtag.setOnClickListener(listener);
        playerView.setOnClickListener(listener);



    }
}
