package com.selfmanage.doily.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.selfmanage.doily.Constant.Constant;
import com.selfmanage.doily.R;
import com.selfmanage.doily.adapter.postPublishAdapter;
import com.selfmanage.doily.entity.Post;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class centerFragment extends Fragment {
    private ListView listView;
    private List<Map<String, Object>> dataSource = new ArrayList<>();
    private postPublishAdapter adapter = null;
//        private publishAdapter publishadapter;
    private PopupWindow popupWindow = null;
    private View popupView = null;
    private SmartRefreshLayout refreshLayout;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private int currentPageNum = 1;
    private int pageSize = 9;
    //服务器端查询发布路径
    private static final String PUBLISH_SHOW_PATH = "/post/listByUserId";
    private String result = null;
    private static final String FLAG = "AllPublish";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View centerView=inflater.inflate(R.layout.center_fragment, container,false);
        listView=centerView.findViewById(R.id.center_publish_listview);
        refreshLayout = centerView.findViewById(R.id.publish_list_refresh);
      // getAllPostInfo();
//        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if(adapter!=null){
//                    ++currentPageNum;
//                    getAllPostInfo();
//                    refreshLayout.finishLoadMore();
//                }
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                if(adapter!=null){
//                    dataSource.clear();
//                    getAllPostInfo();
//                    refreshLayout.finishRefresh();
//
//                }
//            }
//        });
        return centerView;
    }
    private void getAllPostInfo(){
        Request request=new Request.Builder().url(Constant.BASE_IP + PUBLISH_SHOW_PATH).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initData(String flag){
        if(FLAG.equals(flag)){
      List<Post> postList=gson.fromJson(result,new TypeToken<List<Post>>() {}.getType());
       for(Post post :postList){

    //将对应属性数据加入putmap 根据tag进行具体填充
    Map<String, Object> map = new HashMap<>();
   // map.put("thumbs_up_count", post.getThumbsUpCount());

 //   String[] imgUrls = post.getPostImg().split(",");
   // NineGridModel nineGridModel = new NineGridModel();
//    for (int i = 0; i < imgUrls.length; i++) {
//        nineGridModel.urlList.add(Constant.BASE_IP + imgUrls[i]);
//    }
//    nineGridModel.isShowAll = false;
//    map.put("nine_grid", nineGridModel);
//
//    long createTime = model.getPostCreateTime().getTime();
//    long currentTime = System.currentTimeMillis();
//    long sub = currentTime - createTime;
//    if (sub < 1000 * 60) {
//        map.put("create_time", "刚刚");
//    } else if (sub < 1000 * 60 * 60) {
//        map.put("create_time", sub / (1000 * 60) + "分钟前");
//    } else if (sub < 1000 * 60 * 60 * 24) {
//        map.put("create_time", sub / (1000 * 60 * 60) + "小时前");
//    } else {
//        map.put("create_time", sub / (1000 * 60 * 60 * 24) + "天前");
//    }

    dataSource.add(map);
    if(adapter==null){
        setAdapter();
    }
    else{
        adapter.notifyDataSetChanged();
    }

}

        }

    }
    private void setAdapter() {
        adapter = new postPublishAdapter(getContext(), dataSource);
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
