package com.example.shoppingmall.cart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingmall.R;
import com.example.shoppingmall.cart.utils.CartStorage;
import com.example.shoppingmall.cart.view.AddSubtractView;
import com.example.shoppingmall.home.bean.GoodsBean;
import com.example.shoppingmall.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<GoodsBean> datas;
    private final CheckBox checkboxAll;
    private final TextView tvShopcartTotal;
    private final CheckBox cbAll;   //完成状态下的删除CheckBox

    public CartAdapter(Context context, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.context = context;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;

        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //1.根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2.设置其取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3.本地更新
                //CartStorage.getInstance().updateData(goodsBean);
                //4.刷新状态
                notifyItemChanged(position);
                //5.校验是否全选
                checkAll();
                //6.重新计算总价格
                showTotalPrice();
            }
        });

        //checkBox的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3.计算总价格
                showTotalPrice();
            }
        });
        //checkBox的点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
            }
        });
    }

    /**
     * 设置全选和非全选
     */
    public void checkAll_none(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    //选中的
                    number++;
                }
            }
            if (number == datas.size()) {
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText(String.format("%.2f", getTotalPrice()));
    }

    private double getTotalPrice() {
        double totalPrice = 0.00;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    totalPrice += Double.parseDouble(goodsBean.getCover_price()) * goodsBean.getNumber();
                }

            }
        }
        return totalPrice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //1.根据位置得到对应的bean对象
        final GoodsBean goodsBean = datas.get(position);

        //2.设置数据
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Log.e("调试中", "onBindViewHolder: goodsBean.isSelected()" + goodsBean.isSelected());
        Glide.with(context).load(Constants.IMG_URL + goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥" + goodsBean.getCover_price());
        holder.addSubtractView.setValue(goodsBean.getNumber());
        holder.addSubtractView.setMinValue(1);
        holder.addSubtractView.setMaxValue(10);

        //设置商品数量的变化
        holder.addSubtractView.setOnNumberChangeListener(new AddSubtractView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.当前List集合中更新
                goodsBean.setNumber(value);
                //2.本地更新
                CartStorage.getInstance().updateData(goodsBean);
                //3.刷新适配器
                notifyItemChanged(position);
                //4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void deleteData() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                //删除选中的
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()) {
                    //List集合中移除
                    datas.remove(goodsBean);
                    //更新到本地
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;        //一定要有
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubtractView addSubtractView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            addSubtractView = itemView.findViewById(R.id.addSubtractView);

            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 点击Item的监听者
     */
    public interface OnItemClickListener {
        /**
         * 当点击某条的时候被回调
         *
         * @param position
         */
        public void OnItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置Item的监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
