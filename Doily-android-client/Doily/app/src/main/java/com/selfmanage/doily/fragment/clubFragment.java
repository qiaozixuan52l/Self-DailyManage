package com.selfmanage.doily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.selfmanage.doily.activity.accountBook;
import com.selfmanage.doily.activity.lifeDaily;
import com.selfmanage.doily.activity.concentration;
import com.selfmanage.doily.R;

import java.util.List;
import java.util.Map;

public class clubFragment extends Fragment {
    private List<Map<String, Object>> datasourse = null;
    //    private ClubAdapter clubadapter;
    private PopupWindow popupWindow = null;
    private View popupView = null;

    private LinearLayout lifeDaily=null;
    private LinearLayout accountBook=null;
    private LinearLayout concentration=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View clubView = inflater.inflate(R.layout.club_fragment,
                container, false);
        findView(clubView);
        setlistener();
        return clubView;
    }

    private void setlistener() {
        lifeDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), lifeDaily.class);
                startActivity(intent);
            }
        });
        accountBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), accountBook.class);
                startActivity(intent);
            }
        });
        concentration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), concentration.class);
                startActivity(intent);
            }
        });
    }

    private void findView(View clubView) {
        lifeDaily=clubView.findViewById(R.id.club_liferecord_tag);
        accountBook=clubView.findViewById(R.id.club_billcheck_tag);
        concentration=clubView.findViewById(R.id.club_concentration_tag);
    }


}
