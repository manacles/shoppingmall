package com.example.shoppingmall.activity.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.home.bean.HomeResultBean;
import com.example.shoppingmall.activity.utils.Constants;

import java.util.List;

public class RecommendGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<HomeResultBean.ResultBean.RecommendInfoBean> datas;

    public RecommendGridViewAdapter(Context context, List<HomeResultBean.ResultBean.RecommendInfoBean> recommend_info) {
        this.context = context;
        this.datas = recommend_info;
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
            convertView = View.inflate(context, R.layout.item_recommend_grid_view, null);
            holder.ivRecommendItem = convertView.findViewById(R.id.iv_recommend_item);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeResultBean.ResultBean.RecommendInfoBean bean = datas.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText("￥" + bean.getCover_price());
        Glide.with(context).load(Constants.IMG_URL + bean.getFigure()).into(holder.ivRecommendItem);

        return convertView;
    }

    static class ViewHolder {
        private ImageView ivRecommendItem;
        private TextView tvName;
        private TextView tvPrice;
    }
}
