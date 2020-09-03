package com.example.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.home.bean.HomeResultBean;
import com.example.shoppingmall.utils.Constants;

import java.util.List;

/**
 * 秒杀的适配器
 */
public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.ViewHolder> {


    private final List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> list;
    private final Context context;

    public SeckillAdapter(Context context, List<HomeResultBean.ResultBean.SeckillInfoBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_seckill, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeResultBean.ResultBean.SeckillInfoBean.ListBean bean = list.get(position);
        holder.tvCoverPrice.setText("￥" + bean.getCover_price());
        holder.tvOriginPrice.setText("￥" + bean.getOrigin_price());
        Glide.with(context).load(Constants.IMG_URL + bean.getFigure()).into(holder.ivFigure);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFigure = itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, getLayoutPosition() + "--" + list.get(getLayoutPosition()).getName(), Toast.LENGTH_SHORT).show();
                    if (onSeckillRecyclerView!=null){
                        onSeckillRecyclerView.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    //监听器
    public interface OnSeckillRecyclerView {
        //当某条被点击的时候被回调
        public void OnItemClick(int position);
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    //设置item的监听
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
