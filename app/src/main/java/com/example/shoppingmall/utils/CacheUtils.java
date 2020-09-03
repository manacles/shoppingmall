package com.example.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 缓存工具
 */
public class CacheUtils {
    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("buchiyu", Context.MODE_PRIVATE);
        Log.e("调试中", "getAllData: " + sharedPreferences.getString(key, ""));
        return sharedPreferences.getString(key, "");
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("buchiyu", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
}
