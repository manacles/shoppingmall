package com.example.shoppingmall.activity.cart.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.base.BaseFragment;
import com.example.shoppingmall.activity.cart.adapter.CartAdapter;
import com.example.shoppingmall.activity.cart.utils.CartStorage;
import com.example.shoppingmall.activity.home.bean.GoodsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

/**
 * 购物车的Fragment
 */
public class CartFragment extends BaseFragment {


    @BindView(R.id.tv_cart_edit)
    TextView tvCartEdit;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;

    private CartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        Log.e(TAG, "initView: 购物车的Fragment的UI被初始化了");
        View view = View.inflate(context, R.layout.fragment_cart, null);
        ButterKnife.bind(this, view);

        initListener();
        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tvCartEdit.setTag(ACTION_EDIT);
        tvCartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int) v.getTag();
                if (action == ACTION_EDIT) {
                    //切换为完成状态
                    showDelete();
                } else {
                    //切换为编辑状态
                    hideDelete();
                }
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "initData: 购物车的Fragment的数据被初始化了");
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    /**
     * 显示数据
     */
    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();
        Log.e("调试中", "showData: " + goodsBeanList);
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            //有数据
            llEmptyShopcart.setVisibility(View.GONE);
            hideDelete();

            //设置适配器
            adapter = new CartAdapter(context, goodsBeanList, tvShopcartTotal, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);

            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        } else {
            //无数据
            showEmptyCart();
        }

    }

    private void hideDelete() {
        //1.设置状态和文本-编辑
        tvCartEdit.setTag(ACTION_EDIT);
        tvCartEdit.setText("编辑");
        tvCartEdit.setVisibility(View.VISIBLE);
        //2.变成勾选
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        //3.删除视图隐藏
        llDelete.setVisibility(View.GONE);
        //4.结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
        //1.设置状态和文本-完成
        tvCartEdit.setTag(ACTION_COMPLETE);
        tvCartEdit.setText("完成");
        tvCartEdit.setVisibility(View.VISIBLE);
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        //3.删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        //4.结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    private void showEmptyCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        tvCartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_check_out, R.id.btn_delete, R.id.btn_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check_out:
                Toast.makeText(context, "hshal", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete:
                //删除选中的
                adapter.deleteData();
                //校验状态
                adapter.checkAll();
                //数据大小为0
                if (adapter.getItemCount() == 0) {
                    showEmptyCart();
                }
                break;
            case R.id.btn_collection:
                break;
        }
    }
}
