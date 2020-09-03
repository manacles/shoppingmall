package com.example.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.HomeResultBean;
import com.example.shoppingmall.utils.Constants;

import java.util.List;

public class HotGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<HomeResultBean.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context context, List<HomeResultBean.ResultBean.HotInfoBean> hot_info) {
        this.context = context;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_hot_grid_view, null);
            holder.ivHot = convertView.findViewById(R.id.iv_hot);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeResultBean.ResultBean.HotInfoBean bean = datas.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText("￥"+bean.getCover_price());
        Glide.with(context).load(Constants.IMG_URL + bean.getFigure()).into(holder.ivHot);

        return convertView;
    }

    static class ViewHolder {
        ImageView ivHot;
        TextView tvName;
        TextView tvPrice;
    }
}
