package com.example.shoppingmall.activity.home.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * 主页面的Fragment
 */
public class HomeFragment extends BaseFragment {


    @Override
    public View initView() {
        Log.e(TAG, "initView: 主页面的Fragment的UI被初始化了");
        View view = View.inflate(context, R.layout.fragment_home, null);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 主页面的Fragment的数据被初始化了");
    }
}
