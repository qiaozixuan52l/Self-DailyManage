
package com.selfmanage.doily.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.selfmanage.doily.R;


public class myAccount_reedit_Brief extends AppCompatActivity {

    private ImageView backImg;
    private EditText editText;
    private Button saveBtn;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.myaccount_rebrief);
        findView();
        setOnClickListener();
    }
    private class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reedit_brief_back:
                    finish();
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;

                case R.id.reedit_brief:

                break;
                case R.id.reedit_brief_save:
                    if(editText.getText().toString()!=null&&!editText.getText().toString().equals("")){
                        //保存到数据库

                    }
                    else{
                        Toast.makeText(myAccount_reedit_Brief.this,"起个名字吧",Toast.LENGTH_LONG).show();
                    }

                break;


            }
        }
    }
    private void  setOnClickListener(){

    }
    private void findView(){
        backImg=findViewById(R.id.reedit_brief_back);
        editText=findViewById(R.id.reedit_brief);
        saveBtn=findViewById(R.id.reedit_brief_save);

    }
}
