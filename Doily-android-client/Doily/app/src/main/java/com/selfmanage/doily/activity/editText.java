package com.selfmanage.doily.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.selfmanage.doily.Constant.Constant;
import com.selfmanage.doily.R;
import com.selfmanage.doily.adapter.PictureShowGridViewAdapter;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class editText extends TakePhotoActivity {
    private ImageView back = null;
    private EditText editText = null;
    private GridView addpng = null;
    private Button publish = null;
    private ImageView editShift = null;
    private LinearLayout selfSelect = null;
    private ImageView selfSelecttag = null;
    private boolean selftag = false;

    private static final int MAX_SELECT_PIC_NUM = 3;
    private List<String> dataSource = new ArrayList<>();

    private ZLoadingDialog zLoadingDialog=new ZLoadingDialog(this);
    private OkHttpClient client = new OkHttpClient();
    private PictureShowGridViewAdapter adapter;
    private static final String TEXTPOST_PUBLISH_PATH = "/post/publish";
    private static final String PUBLISH_POST_SUCCESS = "success";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textpost_edit_activity);
        EventBus.getDefault().register(this);
        findView();
        setOnClickListener();
        initGridView();


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
    private void findView() {
        back = findViewById(R.id.text_edit_back);
        editText = findViewById(R.id.text_edit_edittext);
        addpng = findViewById(R.id.text_edit_addpng);
        publish = findViewById(R.id.text_edit_publish);
        editShift = findViewById(R.id.text_edit_shift);
        selfSelect = findViewById(R.id.text_edit_tomyself);
        selfSelecttag=findViewById(R.id.text_edit_tomyselftag);

    }

    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_edit_back:
                    com.selfmanage.doily.activity.editText.this.finish();
                    break;
                case R.id.text_edit_shift:
                    Toast.makeText(com.selfmanage.doily.activity.editText.this,"当前编辑未保存",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(com.selfmanage.doily.activity.editText.this,editAudio.class);
                    startActivity(intent);
                    finish();
                    break;


                case R.id.text_edit_publish:{
                    String txtContent=editText.getText().toString();
                    if (TextUtils.isEmpty(txtContent) || dataSource.size() == 0 || dataSource == null) {
                        Toast.makeText(com.selfmanage.doily.activity.editText.this,"请完善您的心情",Toast.LENGTH_LONG).show();
                    }
                    else{
                        zLoadingDialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)
                                .setLoadingColor(Color.WHITE)
                                .setHintText("发布中...")
                                .setHintTextSize(16)
                                .setHintTextColor(Color.WHITE)
                                .setDurationTime(0.5)
                                .setDialogBackgroundColor(Color.BLACK)
                                .setCanceledOnTouchOutside(false)
                                .show();
                        publish(txtContent, dataSource.toString(),selftag);

                    }
                    break;
                }
                case R.id.text_edit_tomyself:{
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
    }
    private void setOnClickListener() {
         Listener listener=new Listener();
         back.setOnClickListener(listener);
         editShift.setOnClickListener(listener);
         publish.setOnClickListener(listener);
         selfSelect.setOnClickListener(listener);


    }
    //
    private void publish(String txtContent,String picPath,boolean selftag){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("txtContent", txtContent);
        builder.addFormDataPart("selftag", String.valueOf(selftag));
        // todo: 从SharedPreference中得到真实user_id, 此处添加测试数据 user_id = 1
        builder.addFormDataPart("userId", String.valueOf(1));
        //添加图片
        String[] paths = picPath.substring(1, picPath.length() - 1).replace(" ", "").split(",");
        for (String path : paths) {
            File file = new File(path);
            if (file != null) {
                builder.addFormDataPart("pic", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
            }
        }

        MultipartBody body = builder.build();
        Request request = new Request.Builder().url(Constant.BASE_IP + TEXTPOST_PUBLISH_PATH).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().post(response.body().string());
            }
        });
    }

    @Override
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void publishSuccess(String result) {
        if (result.equals(PUBLISH_POST_SUCCESS)) {
            zLoadingDialog.dismiss();
            Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        for (TImage tImage : result.getImages()) {
            dataSource.add(tImage.getOriginalPath());
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(this, "picture fetch error: " + msg, Toast.LENGTH_SHORT).show();
    }
}
