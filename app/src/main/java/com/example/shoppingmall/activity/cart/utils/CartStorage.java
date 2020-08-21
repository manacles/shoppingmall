package com.example.shoppingmall.activity.cart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.shoppingmall.activity.app.MyApplication;
import com.example.shoppingmall.activity.home.bean.GoodsBean;
import com.example.shoppingmall.activity.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式
 * 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。
 * 这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * <p>
 * 这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。
 * 这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。
 * <p>
 * 注意：
 * <p>
 * 1、单例类只能有一个实例。
 * 2、单例类必须自己创建自己的唯一实例。
 * 3、单例类必须给所有其他对象提供这一实例。
 */
public class CartStorage {

    public static final String JSON_CART = "JSON_CART";
    private static CartStorage instance;
    private final Context mContext;
    //SparseArray的性能优于HashMap
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context context) {
        this.mContext = context;
        //把之前存储的数据读取
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        //把list数据转换成SparseArray
        for (int i = 0; i < goodsBeanList.size(); i++) {
            GoodsBean goodsBean = goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        }
    }

    /**
     * 获取本地所有的数据
     *
     * @return
     */
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1.从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        Log.e("调试中", "getAllData: " + json);
        //2.使用fastjson转换成List集合
        if (!TextUtils.isEmpty(json)) {
            goodsBeanList = JSONObject.parseArray(json, GoodsBean.class);
        }
        return goodsBeanList;
    }

    /**
     * 得到购物车实例
     *
     * @return
     */
    public static CartStorage getInstance() {
        if (instance == null) {
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;
    }

    public void addData(GoodsBean goodsBean) {
        //1.添加到内存中SparseArray
        //如果当前数据已经存在，就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData != null) {
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber() + 1);
        } else {
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        //同步到内存
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), tempData);

        //2.同步到本地
        saveLocal();
    }

    public void deleteData(GoodsBean goodsBean) {
        //1.内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        //2.同步到本地
        saveLocal();
    }

    public void updateData(GoodsBean goodsBean) {
        //1.内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);

        //2.同步到本地
        saveLocal();
    }

    /**
     * 保存数据到本地
     */
    private void saveLocal() {
        //1.SparseArray转换成List集合
        List<GoodsBean> goodsBeanList = sparseToList();

        //2.把List转换成String类型
        String json = JSON.toJSONString(goodsBeanList);

        //3.把String数据保存
        CacheUtils.putString(mContext, JSON_CART, json);
    }

    private List<GoodsBean> sparseToList() {

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //把list数据转换成SparseArray
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);       //这里被坑死了！！！valueAt(index)和get(key)完全不是一回事，视频说明误导了
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }
}
