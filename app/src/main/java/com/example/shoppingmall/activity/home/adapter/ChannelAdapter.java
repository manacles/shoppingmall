package com.example.shoppingmall.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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

public class ChannelAdapter extends BaseAdapter {
    private final Context context;
    private final List<HomeResultBean.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context context, List<HomeResultBean.ResultBean.ChannelInfoBean> channel_info) {
        this.context = context;
        this.datas = channel_info;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_channel, null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_channel);
            viewHolder.textView = convertView.findViewById(R.id.tv_channel);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HomeResultBean.ResultBean.ChannelInfoBean bean = datas.get(position);
        Glide.with(context).load(Constants.IMG_URL + bean.getImage()).into(viewHolder.imageView);
        viewHolder.textView.setText(bean.getChannel_name());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
