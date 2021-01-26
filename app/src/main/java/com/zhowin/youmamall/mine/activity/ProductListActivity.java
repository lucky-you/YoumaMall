package com.zhowin.youmamall.mine.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityProductListBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallRightList;
import com.zhowin.youmamall.mine.adapter.GoodSoldListAdapter;
import com.zhowin.youmamall.mine.adapter.ProductListAdapter;
import com.zhowin.youmamall.mine.adapter.SalesTurnoverAdapter;
import com.zhowin.youmamall.mine.callback.OnProductItemClickListener;
import com.zhowin.youmamall.mine.model.GoodInfo;
import com.zhowin.youmamall.mine.model.SalesTurnoverList;
import com.zhowin.youmamall.mine.model.SoldGoodList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品列表 已售商品 销售流水 共用
 */
public class ProductListActivity extends BaseBindActivity<ActivityProductListBinding> implements OnProductItemClickListener {

    private int jumpPosition;
    private ProductListAdapter productListAdapter;
    private GoodSoldListAdapter goodSoldListAdapter;
    private SalesTurnoverAdapter salesTurnoverAdapter;

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
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        switch (jumpPosition) {
            case 1:
                getGoodList(true);
                productListAdapter = new ProductListAdapter(new ArrayList<>());
                mBinding.recyclerView.setAdapter(productListAdapter);
                productListAdapter.setOnProductItemClickListener(this);
                productListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        getGoodList(false);
                    }
                }, mBinding.recyclerView);
                break;
            case 2:
                getSoldGoodList(true);
                goodSoldListAdapter = new GoodSoldListAdapter(new ArrayList<>());
                mBinding.recyclerView.setAdapter(goodSoldListAdapter);
                goodSoldListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        getSoldGoodList(false);
                    }
                }, mBinding.recyclerView);
                break;
            case 3:
                getSalesTurnoverList(true);
                salesTurnoverAdapter = new SalesTurnoverAdapter(new ArrayList<>());
                mBinding.recyclerView.setAdapter(salesTurnoverAdapter);
                salesTurnoverAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        getSalesTurnoverList(false);
                    }
                }, mBinding.recyclerView);
                break;
        }
    }

    /**
     * 自己的商品列表
     */
    private void getGoodList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        showLoadDialog();
        HttpRequest.getGoodList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<MallRightList>>() {
            @Override
            public void onSuccess(BaseResponse<MallRightList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (baseResponse.getData() != null && !baseResponse.getData().isEmpty()) {
                        if (isRefresh) {
                            productListAdapter.setNewData(baseResponse.getData());
                        } else {
                            productListAdapter.addData(baseResponse.getData());
                        }
                        if (baseResponse.getData().size() <= pageNumber) {
                            productListAdapter.loadMoreEnd(true);
                        } else {
                            productListAdapter.loadMoreComplete();
                        }
                    } else {
                        EmptyViewUtils.bindEmptyView(mContext, productListAdapter);
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    /**
     * 已售商品列表
     */
    private void getSoldGoodList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        showLoadDialog();
        HttpRequest.getSoldGoodList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<SoldGoodList>>() {
            @Override
            public void onSuccess(BaseResponse<SoldGoodList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (baseResponse.getData() != null && !baseResponse.getData().isEmpty()) {
                        if (isRefresh) {
                            goodSoldListAdapter.setNewData(baseResponse.getData());
                        } else {
                            goodSoldListAdapter.addData(baseResponse.getData());
                        }
                        if (baseResponse.getData().size() <= pageNumber) {
                            goodSoldListAdapter.loadMoreEnd(true);
                        } else {
                            goodSoldListAdapter.loadMoreComplete();
                        }
                    } else {
                        EmptyViewUtils.bindEmptyView(mContext, goodSoldListAdapter);
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    /**
     * 出售流水
     */
    private void getSalesTurnoverList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        showLoadDialog();
        HttpRequest.getSalesTurnoverList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<SalesTurnoverList>>() {
            @Override
            public void onSuccess(BaseResponse<SalesTurnoverList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (baseResponse.getData() != null && !baseResponse.getData().isEmpty()) {
                        if (isRefresh) {
                            salesTurnoverAdapter.setNewData(baseResponse.getData());
                        } else {
                            salesTurnoverAdapter.addData(baseResponse.getData());
                        }
                        if (baseResponse.getData().size() < pageNumber) {
                            salesTurnoverAdapter.loadMoreEnd(true);
                        } else {
                            salesTurnoverAdapter.loadMoreComplete();
                        }
                    }
                    if (salesTurnoverAdapter.getData() == null || salesTurnoverAdapter.getData().isEmpty()) {
                        EmptyViewUtils.bindEmptyView(mContext, salesTurnoverAdapter);
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }


    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (jumpPosition) {
                    case 1:
                        getGoodList(true);
                        break;
                    case 2:
                        getSoldGoodList(true);
                        break;
                    case 3:
                        getSalesTurnoverList(true);
                        break;
                }
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemRootLayoutClick(MallRightList mallRightList) {

    }

    @Override
    public void onChangeContentClick(MallRightList mallRightList) {
        GoodInfo goodInfo = new GoodInfo();
        goodInfo.setGoodId(mallRightList.getId());
        goodInfo.setCategoryName(mallRightList.getCategory_name());
        goodInfo.setCategoryId(mallRightList.getShop_category_id());
        goodInfo.setGoodName(mallRightList.getName());
        goodInfo.setGoodPrice(mallRightList.getPrice());
        goodInfo.setGoodStatusName(1 == mallRightList.getType() ? "热销" : "推荐");
        goodInfo.setGoodStatusId(mallRightList.getType());
        goodInfo.setGoodContact(mallRightList.getContact());
        goodInfo.setGoodContent(mallRightList.getContent());
        goodInfo.setGoodImage(mallRightList.getImage());
        ReleaseGoodActivity.start(mContext, true, goodInfo);
    }

    @Override
    public void onItemOffShelf(int itemId, int goodStatus, int position) {
        String hitTitle = 1 == goodStatus ? "确定要下架吗?" : "确定要上架吗?";
        new CenterHitMessageDialog(mContext, hitTitle, new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {

            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                goodOffShelf(itemId, goodStatus, position);
            }
        }).show();

    }

    @Override
    public void onEnterOrClearCardSecret(boolean isEnter, MallRightList mallRightList) {
        if (isEnter) {
            CardPasswordActivity.start(mContext, mallRightList.getId());
        } else {
            showClearCardSecretDialog(mallRightList.getId());
        }
    }

    private void showClearCardSecretDialog(int goodId) {
        String hitTitle = "确定要清空卡密吗？";
        new CenterHitMessageDialog(mContext, hitTitle, new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {

            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                onClearCardSecret(goodId);
            }
        }).show();
    }


    private void onClearCardSecret(int goodId) {
        showLoadDialog();
        HttpRequest.onClearCardSecret(this, goodId, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }


    /**
     * 下架
     */
    private void goodOffShelf(int itemId, int goodStatus, int position) {
        showLoadDialog();
        HttpRequest.goodOffOrPutOnShelf(this, 0 == goodStatus, itemId, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                productListAdapter.getItem(position).setStatus(1 == goodStatus ? 0 : 1);
                productListAdapter.notifyItemChanged(position);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
