package com.zhowin.youmamall.mine.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityProductListBinding;
import com.zhowin.youmamall.mine.adapter.ProductListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 商品列表 已售商品 销售流水 共用
 */
public class ProductListActivity extends BaseBindActivity<ActivityProductListBinding> {

    private int jumpPosition;

    private ProductListAdapter productListAdapter;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    public void initView() {
        jumpPosition = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        switch (jumpPosition) {
            case 1:
                mBinding.tvTitleView.setTitle("商品列表");
                break;
            case 2:
                mBinding.tvTitleView.setTitle("已售商品");
                break;
            case 3:
                mBinding.tvTitleView.setTitle("销售流水");
                break;
        }
    }

    @Override
    public void initData() {
        List<String> stringList = Arrays.asList("", "", "", "", "", "");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        switch (jumpPosition) {
            case 1:
                productListAdapter = new ProductListAdapter(stringList);
                mBinding.recyclerView.setAdapter(productListAdapter);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
    }
}
