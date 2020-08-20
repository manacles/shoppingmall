package com.example.shoppingmall.activity.community.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.activity.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * 发现的Fragment
 */
public class CommunityFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "initView: 发现的Fragment的UI被初始化了");
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 发现的Fragment的数据被初始化了");
        textView.setText("我是发现的内容");
    }
}
