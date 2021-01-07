package com.zhowin.youmamall.mine.fragment;

import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.utils.UserLevelHelper;
import com.zhowin.youmamall.databinding.IncludeMineFragmentLayoutBinding;
import com.zhowin.youmamall.login.activity.LoginActivity;
import com.zhowin.youmamall.mine.activity.DepositActivity;
import com.zhowin.youmamall.mine.activity.OpenAgentActivity;
import com.zhowin.youmamall.mine.activity.SetPasswordActivity;
import com.zhowin.youmamall.mine.adapter.ColumnListAdapter;
import com.zhowin.youmamall.mine.model.ColumnList;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.activity.ContactServiceActivity;
import com.zhowin.youmamall.mine.activity.FeedbackActivity;
import com.zhowin.youmamall.mine.activity.MallOrderListActivity;
import com.zhowin.youmamall.mine.activity.MyCouponActivity;
import com.zhowin.youmamall.mine.activity.MyTeamActivity;
import com.zhowin.youmamall.mine.activity.ProductListActivity;
import com.zhowin.youmamall.mine.activity.ReleaseGoodActivity;
import com.zhowin.youmamall.mine.activity.SettingActivity;
import com.zhowin.youmamall.mine.activity.ShareMaterialActivity;
import com.zhowin.youmamall.mine.activity.WithdrawActivity;
import com.zhowin.youmamall.mine.model.DepositMessage;
import com.zhowin.youmamall.mine.model.GoodInfo;
import com.zhowin.youmamall.mine.model.MineItemConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class MineFragment extends BaseBindFragment<IncludeMineFragmentLayoutBinding> implements BaseQuickAdapter.OnItemClickListener {

    private ColumnListAdapter columnListAdapter;
    private boolean isOpenMerchant;//是否开通店铺
    private int passwordType, itemType;//密码的状态 , 审核状态

    @Override
    public int getLayoutId() {
        return R.layout.include_mine_fragment_layout;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvShopMallAllOrder, R.id.llDFKLayout, R.id.llDFHLayout, R.id.llDSHLayout, R.id.llYWCLayout,
                R.id.tvReleaseGood, R.id.llSPLBLayout, R.id.llYSSPLayout, R.id.llXSLSLayout, R.id.llZXDPLayout, R.id.tvOpenStore
        );

    }

    @Override
    public void initData() {
        columnListAdapter = new ColumnListAdapter(new ArrayList<>());
        mBinding.moreRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mBinding.moreRecyclerView.setAdapter(columnListAdapter);
        columnListAdapter.setOnItemClickListener(this::onItemClick);
    }

    private List<ColumnList> getMineItemList() {
        List<ColumnList> columnList = new ArrayList<>();
        switch (itemType) {
            case 1: //全部放开
                columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通会员"));
                columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_xyzp, "账号升级"));
                columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjtx, "佣金提现"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
                columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
                columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
                break;
            case 2: //放开账号升级
                columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通会员"));
                columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjtx, "佣金提现"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
                columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
                columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
                break;
            case 3://放开佣金提现
                columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通会员"));
                columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_xyzp, "账号升级"));
                columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
                columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
                columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
                break;
            case 4://隐藏  账号升级 和  佣金提现
                columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通会员"));
                columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
                columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
                columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
                columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
                columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
                break;
        }
        return columnList;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfoMessage();
        getMineItemConfig();
        isOpenMerchant = SPUtils.getBoolean(ConstantValue.IS_OPEN_MERCHANT);
        mBinding.tvOpenStore.setText(isOpenMerchant ? "发布商品" : "开通店铺");
    }

    private void getUserInfoMessage() {
        HttpRequest.getUserInfoMessage(this, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                    passwordType = userInfo.getIs_pay_pwd();
                    GlideUtils.loadUserPhotoForLogin(mContext, userInfo.getAvatar(), mBinding.civUserHead);
                    mBinding.tvUserNickName.setText(userInfo.getNickname());
                    mBinding.ivUserLevel.setVisibility(0 != userInfo.getLevel() ? View.VISIBLE : View.GONE);
                    mBinding.ivUserLevel.setImageResource(UserLevelHelper.getUserLevel(userInfo.getLevel()));
                    mBinding.tvProxy.setText("(" + userInfo.getLevel_name() + ")");
                    mBinding.tvYQMCode.setText("邀请码：" + userInfo.getInvitation_code());
                    mBinding.tvTJRText.setText("推荐人：" + userInfo.getF_nickname());
                    mBinding.tvKTYJValue.setText(userInfo.getMoney());
                    mBinding.tvLJTXValue.setText(userInfo.getWithdrawal());
                    mBinding.tvJRSYValue.setText(userInfo.getToday_income());
                    mBinding.tvLJSYValue.setText(userInfo.getIncome());
                }

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getMineItemConfig() {
        HttpRequest.getMineItemConfig(this, new HttpCallBack<MineItemConfig>() {
            @Override
            public void onSuccess(MineItemConfig mineItemConfig) {
                if (mineItemConfig != null) {
                    if (TextUtils.equals("1", mineItemConfig.getOpen_upgrade()) && TextUtils.equals("1", mineItemConfig.getOpen_withdraw())) {
                        itemType = 1;
                    } else if (!TextUtils.equals("1", mineItemConfig.getOpen_upgrade()) && TextUtils.equals("1", mineItemConfig.getOpen_withdraw())) {
                        itemType = 3;
                    } else if (TextUtils.equals("1", mineItemConfig.getOpen_upgrade()) && !TextUtils.equals("1", mineItemConfig.getOpen_withdraw())) {
                        itemType = 2;
                    } else if (!TextUtils.equals("1", mineItemConfig.getOpen_upgrade()) && !TextUtils.equals("1", mineItemConfig.getOpen_withdraw())) {
                        itemType = 4;
                    }
                    columnListAdapter.setNewData(getMineItemList());
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvShopMallAllOrder://全部订单
                MallOrderListActivity.start(mContext, 0);
                break;
            case R.id.llDFKLayout:
                MallOrderListActivity.start(mContext, 1);
                break;
            case R.id.llDFHLayout:
                MallOrderListActivity.start(mContext, 2);
                break;
            case R.id.llDSHLayout:
                MallOrderListActivity.start(mContext, 3);
                break;
            case R.id.llYWCLayout:
                MallOrderListActivity.start(mContext, 4);
                break;
            case R.id.tvOpenStore: //开通店铺
            case R.id.tvReleaseGood: //发布商品
                jumpOpenMerchant(0);
                break;
            case R.id.llSPLBLayout://商品列表
                jumpOpenMerchant(1);
                break;
            case R.id.llYSSPLayout://已售商品
                jumpOpenMerchant(2);
                break;
            case R.id.llXSLSLayout://销售流水
                jumpOpenMerchant(3);
                break;
            case R.id.llZXDPLayout://注销店铺
                jumpOpenMerchant(4);
                break;
        }
    }
    private void jumpOpenMerchant(int type) {
        if (isOpenMerchant) {
            switch (type) {
                case 0:
                    ReleaseGoodActivity.start(mContext, false, new GoodInfo());
                    break;
                case 1:
                    ProductListActivity.start(mContext, 1);
                    break;
                case 2:
                    ProductListActivity.start(mContext, 2);
                    break;
                case 3:
                    ProductListActivity.start(mContext, 3);
                    break;
                case 4:
                    showOutLoginDialog(2, "确定要注销店铺吗?");
                    break;
            }
        } else {
            startActivity(DepositActivity.class);
        }
    }

    @Override
    public void initListener() {
        mBinding.tvTitleView.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class);
            }
        });
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (itemType) {
            case 1: //全部放开
                switch (position) {
                    case 0:
                        OpenAgentActivity.start(mContext, 2);
                        break;
                    case 1:
                        startActivity(ShareMaterialActivity.class);
                        break;
                    case 2:
                        OpenAgentActivity.start(mContext, 1);
                        break;
                    case 3:
                        startActivity(MyTeamActivity.class);
                        break;
                    case 4:
                        startActivity(WithdrawActivity.class);
                        break;
                    case 5:
                        MyCouponActivity.start(mContext, 1);
                        break;
                    case 6:
                        SetPasswordActivity.start(mContext, passwordType);
                        break;
                    case 7:
                        startActivity(FeedbackActivity.class);
                        break;
                    case 8:
                        startActivity(ContactServiceActivity.class);
                        break;
                    case 9:
                        showOutLoginDialog(1, "确定要退出登录吗?");
                        break;
                }

                break;
            case 2: //放开账号升级
                switch (position) {
                    case 0:
                        OpenAgentActivity.start(mContext, 2);
                        break;
                    case 1:
                        startActivity(ShareMaterialActivity.class);
                        break;
                    case 2:
                        startActivity(MyTeamActivity.class);
                        break;
                    case 3:
                        startActivity(WithdrawActivity.class);
                        break;
                    case 4:
                        MyCouponActivity.start(mContext, 1);
                        break;
                    case 5:
                        SetPasswordActivity.start(mContext, passwordType);
                        break;
                    case 6:
                        startActivity(FeedbackActivity.class);
                        break;
                    case 7:
                        startActivity(ContactServiceActivity.class);
                        break;
                    case 8:
                        showOutLoginDialog(1, "确定要退出登录吗?");
                        break;
                }
                break;
            case 3://放开佣金提现
                switch (position) {
                    case 0:
                        OpenAgentActivity.start(mContext, 2);
                        break;
                    case 1:
                        startActivity(ShareMaterialActivity.class);
                        break;
                    case 2:
                        OpenAgentActivity.start(mContext, 1);
                        break;
                    case 3:
                        startActivity(MyTeamActivity.class);
                        break;
                    case 4:
                        MyCouponActivity.start(mContext, 1);
                        break;
                    case 5:
                        SetPasswordActivity.start(mContext, passwordType);
                        break;
                    case 6:
                        startActivity(FeedbackActivity.class);
                        break;
                    case 7:
                        startActivity(ContactServiceActivity.class);
                        break;
                    case 8:
                        showOutLoginDialog(1, "确定要退出登录吗?");
                        break;
                }
                break;
            case 4://隐藏  账号升级 和  佣金提现
                switch (position) {
                    case 0:
                        OpenAgentActivity.start(mContext, 2);
                        break;
                    case 1:
                        startActivity(ShareMaterialActivity.class);
                        break;
                    case 2:
                        startActivity(MyTeamActivity.class);
                        break;
                    case 3:
                        MyCouponActivity.start(mContext, 1);
                        break;
                    case 4:
                        SetPasswordActivity.start(mContext, passwordType);
                        break;
                    case 5:
                        startActivity(FeedbackActivity.class);
                        break;
                    case 6:
                        startActivity(ContactServiceActivity.class);
                        break;
                    case 7:
                        showOutLoginDialog(1, "确定要退出登录吗?");
                        break;
                }
                break;
        }

    }

    private void showOutLoginDialog(int type, String title) {
        new CenterHitMessageDialog(mContext, title, new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                if (1 == type) {
                    outLoginAppOrRemoveMerchant(true);
                } else {
                    outLoginAppOrRemoveMerchant(false);
                }
            }
        }).show();
    }

    private void outLoginAppOrRemoveMerchant(boolean outLoginApp) {
        HttpRequest.outLoginApp(this, outLoginApp, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                UserInfo.setUserInfo(new UserInfo());
                if (outLoginApp) {
                    SPUtils.set(ConstantValue.REMEMBER_PASSWORD, false);
                    startActivity(LoginActivity.class);
                } else {
                    ToastUtils.showCustomToast(mContext, "注销成功");
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
