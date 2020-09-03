package com.example.shoppingmall.user.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * 个人中心的Fragment
 */
public class UserFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "initView: 个人中心的Fragment的UI被初始化了");
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 个人中心的Fragment的数据被初始化了");
        textView.setText("我是个人中心的内容");
    }
}
