package com.example.shoppingmall.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoppingmall.R;
import com.example.shoppingmall.activity.base.BaseFragment;
import com.example.shoppingmall.activity.cart.fragment.CartFragment;
import com.example.shoppingmall.activity.community.fragment.CommunityFragment;
import com.example.shoppingmall.activity.home.fragment.HomeFragment;
import com.example.shoppingmall.activity.type.fragment.TypeFragment;
import com.example.shoppingmall.activity.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private int position = 0;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initListener();

    }

    /**
     * 添加的时候要按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new CartFragment());
        fragments.add(new UserFragment());
    }

    /**
     * 初始化radiogroup的监听
     */
    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment baseFragment = getFragment(position);
                switchFragment(tempFragment, baseFragment);

            }
        });

        rgMain.check(R.id.rb_home);
    }

    /**
     * @param position 根据位置
     * @return 获取fragment
     */
    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);
        }
        return null;
    }

    /**
     * @param fromFragment 上次显示的Fragment
     * @param nextFragment 当前正要显示的Fragment
     */
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断fragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.fl_main, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }


}
