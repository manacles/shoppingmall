package com.example.shoppingmall.activity.cart.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.activity.base.BaseFragment;

import static android.content.ContentValues.TAG;

/**
 * 购物车的Fragment
 */
public class CartFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "initView: 购物车的Fragment的UI被初始化了");
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 购物车的Fragment的数据被初始化了");
        textView.setText("我是购物车的内容");
    }
}
