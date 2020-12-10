package com.zhowin.youmamall.mine.fragment;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class MineFragment extends BaseBindFragment<IncludeMineFragmentLayoutBinding> implements BaseQuickAdapter.OnItemClickListener {

    private ColumnListAdapter columnListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_mine_fragment_layout;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvShopMallAllOrder, R.id.llDFKLayout, R.id.llDFHLayout, R.id.llDSHLayout, R.id.llYWCLayout,
                R.id.tvReleaseGood, R.id.llSPLBLayout, R.id.llYSSPLayout, R.id.llXSLSLayout, R.id.llZXDPLayout
        );

    }

    @Override
    public void initData() {
        List<ColumnList> columnList = new ArrayList<>();
        columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通代理"));
        columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_xyzp, "幸运转盘"));
        columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yjtx, "佣金提现"));
        columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yhj, "我的优惠券"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yqm, "邀请码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
        columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
        columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
        columnListAdapter = new ColumnListAdapter(columnList);
        mBinding.moreRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mBinding.moreRecyclerView.setAdapter(columnListAdapter);
        columnListAdapter.setOnItemClickListener(this::onItemClick);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfoMessage();
    }

    private void getUserInfoMessage() {
        HttpRequest.getUserInfoMessage(this, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                    GlideUtils.loadUserPhotoForLogin(mContext, userInfo.getAvatar(), mBinding.civUserHead);
                    mBinding.tvUserNickName.setText(userInfo.getNickname());
                    mBinding.ivUserLevel.setVisibility(0 != userInfo.getLevel() ? View.VISIBLE : View.GONE);
                    mBinding.ivUserLevel.setImageResource(UserLevelHelper.getUserLevel(userInfo.getLevel()));
                    mBinding.tvYQMCode.setText("邀请码：" + userInfo.getInvitation_code());
                    mBinding.tvTJRText.setText("推荐人：");
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
            case R.id.tvShopMallAllOrder:
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
            case R.id.tvReleaseGood:
                startActivity(ReleaseGoodActivity.class);
                break;
            case R.id.llSPLBLayout:
                ProductListActivity.start(mContext, 1);
                break;
            case R.id.llYSSPLayout:
                ProductListActivity.start(mContext, 2);
                break;
            case R.id.llXSLSLayout:
                ProductListActivity.start(mContext, 3);
                break;
            case R.id.llZXDPLayout:
                break;
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
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
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
                MyCouponActivity.start(mContext, 2);
                break;
            case 7:
                startActivity(ShareMaterialActivity.class);
                break;
            case 8:
                break;
            case 9:
                startActivity(FeedbackActivity.class);
                break;
            case 10:
                startActivity(ContactServiceActivity.class);
                break;
            case 11:
                showOutLoginDialog();
                break;
        }
    }

    private void showOutLoginDialog() {
        new CenterHitMessageDialog(mContext, "确定要退出吗?", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {

            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                outLoginApp();
            }
        }).show();
    }

    private void outLoginApp() {
        HttpRequest.outLoginApp(this, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                UserInfo.setUserInfo(new UserInfo());
                SPUtils.set(ConstantValue.REMEMBER_PASSWORD, false);
                ActivityManager.getAppInstance().AppExit(mContext);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
