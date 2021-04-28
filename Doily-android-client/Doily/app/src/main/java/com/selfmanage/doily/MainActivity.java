package com.selfmanage.doily;

import android.accounts.Account;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.selfmanage.doily.activity.editAudio;
import com.selfmanage.doily.activity.editText;
import com.selfmanage.doily.activity.myAccount;
import com.selfmanage.doily.activity.myAttention;
import com.selfmanage.doily.activity.myPublish;
import com.selfmanage.doily.activity.myThumb;
import com.selfmanage.doily.activity.personalAbout;
import com.selfmanage.doily.fragment.centerFragment;
import com.selfmanage.doily.fragment.clubFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Doily主頁導航頁
 */

public class MainActivity extends AppCompatActivity {
    private class MypageTab {
        private ImageView imageView = null;
        private TextView textView = null;
        private int normalImage;
        private int selectImage;
        private Fragment fragment = null;

        private void setSelect(boolean select) {
            if (select) {
                //被选中的图片指针
                imageView.setImageResource(selectImage);

            } else {
                imageView.setImageResource(normalImage);
            }
        }


        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }


        public void setNormalImage(int normalImage) {
            this.normalImage = normalImage;
        }

        public int getNormalImage() {
            return normalImage;
        }

        public void setSelectImage(int selectImage) {
            this.selectImage = selectImage;
        }

        public int getSelectImage() {
            return selectImage;
        }
    }

    private Map<String, MypageTab> map = new HashMap<>();
    private String[] hostTabId = {"首页", "俱乐部"};
    private Fragment currentFragment = null;
    private ImageView settingMore = null;
    private PopupWindow settingWindow = null;
    private PopupWindow addselectWindow = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        loadWindow();
        initData();
        setListener();
        if (getIntent()!=null) {
            String num = getIntent().getStringExtra("num");
            if(num!=null){
                if(num.equals("1")){
                    changeTab(hostTabId[1]);
                }
            }
        } else {
            changeTab(hostTabId[1]);
        }
    }

    private void loadWindow() {
        settingMore = findViewById(R.id.main_more_btn);
        settingMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow();
                settingWindow.showAsDropDown(v, -150, -200);

            }
        });

    }

    private void getPopupWindow() {
        if (settingWindow != null) {
            settingWindow.dismiss();
        } else {
            initPopupWindow();
        }
    }

    private void initPopupWindow() {

        View popupView = getLayoutInflater().inflate(R.layout.setting_window, null);
        settingWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        //设置点击popupWindow之外的屏幕，关闭popupWindow
        settingWindow.setFocusable(true);
        settingWindow.setOutsideTouchable(true);
        //设置popupWindow弹出效果
        settingWindow.setAnimationStyle(R.style.anim_bottonbar);
        LinearLayout account = popupView.findViewById(R.id.setting_acount);
        LinearLayout mypublish = popupView.findViewById(R.id.setting_publish);
        LinearLayout mythumb = popupView.findViewById(R.id.setting_thumb);
        LinearLayout aboutDoily = popupView.findViewById(R.id.setting_about);
        LinearLayout myattention = popupView.findViewById(R.id.setting_attention);
        LinearLayout personal = popupView.findViewById(R.id.setting_personal);
        LinearLayout shiftaccount = popupView.findViewById(R.id.setting_shiftaccount);
        Listener listener = new Listener();
        account.setOnClickListener(listener);
        mypublish.setOnClickListener(listener);
    }


    private void initData() {
        map.put(hostTabId[0], new MypageTab());
        map.put(hostTabId[1], new MypageTab());
        //设置fragment
        setFragment();
        setView();
        setImage();

    }

    private void setFragment() {
        map.get(hostTabId[0]).setFragment(new centerFragment());
        map.get(hostTabId[1]).setFragment(new clubFragment());

    }

    private void setView() {
        ImageView centerView = findViewById(R.id.main_img_center);
        ImageView clubView = findViewById(R.id.main_img_club);
        map.get(hostTabId[0]).setImageView(centerView);
        map.get(hostTabId[1]).setImageView(clubView);

    }

    private void setImage() {
        map.get(hostTabId[0]).setNormalImage(R.drawable.home_normal);
        map.get(hostTabId[0]).setSelectImage(R.drawable.home_selected);
        map.get(hostTabId[1]).setNormalImage(R.drawable.club_normal);
        map.get(hostTabId[1]).setSelectImage(R.drawable.club_selected);

    }

    private class Listener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_center: {
                    changeTab(hostTabId[0]);
                    break;
                }
                case R.id.main_club: {
                    Log.e("为啥", "俱乐部");
                    changeTab(hostTabId[1]);
                    break;
                }
                case R.id.main_addpublish: {
                    Log.e("为啥", "编辑");
                    popSelectWindow();
                    break;
                }
                case R.id.add_editsound: {
                    Intent intent = new Intent(MainActivity.this, editAudio.class);
                    startActivity(intent);

                    break;
                }
                case R.id.add_edittext: {
                    Intent intent = new Intent(MainActivity.this, editText.class);
                    startActivity(intent);

                    break;
                }
                case R.id.setting_acount: {
                    Log.e("为啥", "账户");
                    Intent intent = new Intent(MainActivity.this, myAccount.class);
                    startActivity(intent);
                    break;

                }
                case R.id.setting_publish: {
                    Log.e("为啥", "发布");
                    Intent intent = new Intent(MainActivity.this, myPublish.class);
                    startActivity(intent);
                    break;
                }
                case R.id.setting_thumb: {
                    Log.e("为啥", "关注");
                    Intent intent = new Intent(MainActivity.this, myThumb.class);
                    startActivity(intent);
                    break;
                }
                //静面？？待定
                case R.id.setting_about: {
                    Log.e("为啥", "Doily");
//                    Intent intent = new Intent(MainActivity.this, .class);
//                    startActivity(intent);
                    break;
                }
                case R.id.setting_attention: {
                    Intent intent = new Intent(MainActivity.this, myAttention.class);
                    startActivity(intent);

                }
                case R.id.setting_personal: {
                    Log.e("为啥", "隐私");
                    Intent intent = new Intent(MainActivity.this, personalAbout.class);
                    startActivity(intent);
                    break;
                }
                //静面？？待定
                case R.id.setting_shiftaccount: {
                    Log.e("为啥", "切换账号");
                    Intent intent = new Intent(MainActivity.this, editAudio.class);
                    startActivity(intent);
                    break;
                }


            }

        }
    }

    private void popSelectWindow() {
        //选择文字或语音
        if (addselectWindow != null) {
            addselectWindow.dismiss();
        } else {
            initSelctPopWindow();
        }
        addselectWindow.showAtLocation(findViewById(R.id.main_addpublish), Gravity.BOTTOM, 0, 240);


    }

    private void initSelctPopWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.editselect_window, null);
        addselectWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置点击popupWindow之外的屏幕，关闭popupWindow
        addselectWindow.setFocusable(true);
        addselectWindow.setOutsideTouchable(true);
        LinearLayout editSound = popupView.findViewById(R.id.add_editsound);
        LinearLayout editText = popupView.findViewById(R.id.add_edittext);
        Listener listener = new Listener();
        editSound.setOnClickListener(listener);
        editText.setOnClickListener(listener);

    }

    private void changeTab(String s) {
        //切换fragment
        changeFragment(s);
        //切换图片以及文字
        changeImage(s);

    }

    private void changeFragment(String s) {
        Fragment fragment = map.get(s).getFragment();
        if (currentFragment == fragment)
            return;
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        //当前的fragment不为空，要显示其他的f，要将现有的指向隐藏掉
        if (currentFragment != null)
            transaction.hide(currentFragment);
        //对新指向的F进行显示,先对F进行添加
        if (!fragment.isAdded()) {
            transaction.add(R.id.frame_content, fragment);
        }
        transaction.show(fragment);
        currentFragment = fragment;
        transaction.commit();


    }

    private void changeImage(String s) {
        //先对所有的图片变成默认的未选中的状态
        for (String key : map.keySet()) {
            map.get(key).setSelect(false);

        }
        //再对选中的而图片进行设置成选中状态
        map.get(s).setSelect(true);

    }

    private void setListener() {
        LinearLayout center = findViewById(R.id.main_center);
        LinearLayout addpublish = findViewById(R.id.main_addpublish);
        LinearLayout club = findViewById(R.id.main_club);
        Listener listener = new Listener();
        center.setOnClickListener(listener);
        addpublish.setOnClickListener(listener);
        club.setOnClickListener(listener);

    }


}
