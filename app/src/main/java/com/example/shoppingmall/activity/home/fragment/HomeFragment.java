package com.example.shoppingmall.activity.home.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.app.MainActivity;
import com.example.shoppingmall.activity.base.BaseFragment;
import com.example.shoppingmall.activity.home.adapter.HomeFragmentAdapter;
import com.example.shoppingmall.activity.home.bean.HomeResultBean;
import com.example.shoppingmall.activity.utils.Constants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * 主页面的Fragment
 */
public class HomeFragment extends BaseFragment {


    private TextView tvSearchHome;
    private TextView tvMessageHome;
    private RecyclerView rvHome;
    private ImageButton ibTop;

    private HomeResultBean.ResultBean resultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e(TAG, "initView: 主页面的Fragment的UI被初始化了");
        View view = View.inflate(context, R.layout.fragment_home, null);
        tvSearchHome = view.findViewById(R.id.tv_search_home);
        tvMessageHome = view.findViewById(R.id.tv_message_home);
        rvHome = view.findViewById(R.id.rv_home);
        ibTop = view.findViewById(R.id.ib_top);

        //设置监听时间
        initListener();

        return view;
    }

    private void initListener() {
        //返回顶部的监听
        ibTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到顶部
                rvHome.scrollToPosition(0);
            }
        });

        //搜索的监听
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tvMessageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 主页面的Fragment的数据被初始化了");

        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: 首页请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: 首页请求成功" + string);
                processData(string);
            }
        });
    }

    private void processData(String json) {
        HomeResultBean homeResultBean = JSON.parseObject(json, HomeResultBean.class);
        resultBean = homeResultBean.getResult();

        if (resultBean != null) {
            //有数据
            //设置适配器
            ((MainActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new HomeFragmentAdapter(context, resultBean);
                    rvHome.setAdapter(adapter);

                    GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if (position <= 3) {
                                //隐藏
                                ibTop.setVisibility(View.GONE);
                            } else {
                                //显示
                                ibTop.setVisibility(View.VISIBLE);
                            }
                            //只能返回1
                            return 1;
                        }
                    });
                    rvHome.setLayoutManager(layoutManager);
                }
            });

        } else {
            //没有数据

        }
    }


}
