package com.example.shoppingmall.cart.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shoppingmall.R;

/**
 * 自定义删除增加按钮
 */
public class AddSubtractView extends LinearLayout {

    private ImageView ivSubtract;
    private ImageView ivAdd;
    private TextView tvValue;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;

    public AddSubtractView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //把布局文件实例化，并且加载到AddSubtractView类中
        View.inflate(context, R.layout.add_subtract_view, AddSubtractView.this);
        ivSubtract = findViewById(R.id.iv_subtract);
        ivAdd = findViewById(R.id.iv_add);
        tvValue = findViewById(R.id.tv_value);

        int tempValue = getValue();
        setValue(tempValue);

        //设置点击事件
        ivSubtract.setOnClickListener(new MyOnClickListener());
        ivAdd.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_subtract:
                    subNumber();
                    break;
                case R.id.iv_add:
                    addNumber();
                    break;
            }
        }
    }

    private void subNumber() {
        if (value > minValue) {
            value--;
        }
        setValue(value);

        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChange(value);
        }
    }

    private void addNumber() {
        if (value < maxValue) {
            value++;
        }
        setValue(value);

        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChange(value);
        }
    }

    public int getValue() {
        String valueStr = tvValue.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        tvValue.setText(new StringBuilder().append("").append(value).toString());
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 当数量发生变化的时候回调
     */
    public interface OnNumberChangeListener {
        /**
         * 当数量发生变化的时候回调
         *
         * @param value
         */
        public void onNumberChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    /**
     * 设置数量变化的监听
     *
     * @param onNumberChangeListener
     */
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
