package com.example.shoppingmall.activity.app;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = MyApplication.this;
    }

    public static Context getContext() {
        return mContext;
    }
}
