package com.selfmanage.doily.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.selfmanage.doily.R;

import java.util.List;
import java.util.Map;

public class center extends Fragment {
    private List<Map<String, Object>> datasourse = null;
    //    private publishAdapter publishadapter;
    private PopupWindow popupWindow = null;
    private View popupView = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View centerView=inflater.inflate(R.layout.center_fragment,
                container,false);
        return centerView;
    }
}
