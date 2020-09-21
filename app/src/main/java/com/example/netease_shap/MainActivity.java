package com.example.netease_shap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.insertfaces.IBasePresenter;
import com.example.netease_shap.ui.fragment.ClassifyFragment;
import com.example.netease_shap.ui.fragment.HomeFragment;
import com.example.netease_shap.ui.fragment.MeFragment;
import com.example.netease_shap.ui.fragment.ShoppingCartFragment;
import com.example.netease_shap.ui.fragment.SpecialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.Netease_line)
    FrameLayout NeteaseLine;
    @BindView(R.id.Netease_tb)
    TabLayout NeteaseTb;
    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private SpecialFragment specialFragment;
    private MeFragment meFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
         shoppingCartFragment = new ShoppingCartFragment();
        classifyFragment = new ClassifyFragment();
        specialFragment = new SpecialFragment();
        meFragment = new MeFragment();
        transaction.add(R.id.Netease_line,homeFragment)
                .show(homeFragment)
                .commit();
        NeteaseTb.addTab(NeteaseTb.newTab().setText("首页").setIcon(R.drawable.home_sel));
        NeteaseTb.addTab(NeteaseTb.newTab().setText("专题").setIcon(R.drawable.special_sel));
        NeteaseTb.addTab(NeteaseTb.newTab().setText("分类").setIcon(R.drawable.find_sel));
        NeteaseTb.addTab(NeteaseTb.newTab().setText("购物车").setIcon(R.drawable.shop_sel));
        NeteaseTb.addTab(NeteaseTb.newTab().setText("我的").setIcon(R.drawable.mine_sel));
        NeteaseTb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                addFragemt(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void addFragemt(TabLayout.Tab tab) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                if (!homeFragment.isAdded()) {
                    fragmentTransaction.add(R.id.Netease_line, homeFragment);
                }
                fragmentTransaction.show(homeFragment).hide(classifyFragment).hide(specialFragment).hide(meFragment).hide(shoppingCartFragment);
                break;
            case 1:
                if (!specialFragment.isAdded()) {
                    fragmentTransaction.add(R.id.Netease_line, specialFragment);
                }
                fragmentTransaction.show(specialFragment).hide(homeFragment).hide(shoppingCartFragment).hide(meFragment).hide(classifyFragment);
                break;
            case 2:
                if (!classifyFragment.isAdded()) {
                    fragmentTransaction.add(R.id.Netease_line, classifyFragment);
                }
                fragmentTransaction.show(classifyFragment).hide(homeFragment).hide(specialFragment).hide(meFragment).hide(shoppingCartFragment);
                break;

            case 3:

                if (!shoppingCartFragment.isAdded()) {
                    fragmentTransaction.add(R.id.Netease_line, shoppingCartFragment);
                    tab.select();
                }
                fragmentTransaction.show(shoppingCartFragment).hide(classifyFragment).hide(homeFragment).hide(meFragment).hide(specialFragment);
                break;
            case 4:


                if (!meFragment.isAdded()) {
                    fragmentTransaction.add(R.id.Netease_line, meFragment);
                }
                fragmentTransaction.show(meFragment).hide(classifyFragment).hide(specialFragment).hide(homeFragment).hide(specialFragment);

                break;
        }
        fragmentTransaction.commit();
    }


    @Override
    protected int getlayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1000){
            NeteaseTb.getTabAt(3).select();

        }

    }
}