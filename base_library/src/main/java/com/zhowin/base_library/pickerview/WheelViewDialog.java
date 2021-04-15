package com.zhowin.base_library.pickerview;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.zhowin.base_library.R;
import com.zhowin.base_library.base.BaseDialogFragment;

import java.io.Serializable;
import java.util.List;

/**
 * 底部滚动选择
 */
public class WheelViewDialog extends BaseDialogFragment {


    private WheelView wheelViewData;
    private TextView tvDialogTitle;
    private List<?> wheelDataList;
    private OnWheelDataSelectListener wheelDataSelectListener;
    private Object wheelDataBean;
    private int selectPosition;
    private String dialogTitle;

    public static WheelViewDialog newInstance(List<?> dataList, String title) {
        WheelViewDialog dialog = new WheelViewDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) dataList);
        bundle.putString("title", title);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_wheel_view_dialog_layout;
    }

    @Override
    public void initView() {
        get(R.id.tvCancel).setOnClickListener(this::onClick);
        get(R.id.tvDetermine).setOnClickListener(this::onClick);
        tvDialogTitle = get(R.id.tvDialogTitle);
        wheelViewData = get(R.id.wheelViewData);
    }

    @Override
    public void initData() {
        wheelDataList = (List<?>) getArguments().getSerializable("data");
        dialogTitle = getArguments().getString("title");
        if (TextUtils.isEmpty(dialogTitle)) {
            tvDialogTitle.setVisibility(View.GONE);
        } else {
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText(dialogTitle);
        }
        wheelViewData.setCyclic(false);
        if (wheelDataList == null || wheelDataList.isEmpty()) return;
        wheelViewData.setAdapter(new ArrayWheelAdapter(wheelDataList));
        wheelViewData.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selectPosition = index;
                wheelDataBean = wheelDataList.get(index);
            }
        });
    }

    public void setWheelDataSelectListener(OnWheelDataSelectListener wheelDataSelectListener) {
        this.wheelDataSelectListener = wheelDataSelectListener;
    }

    @Override
    public void onViewClick(View view) {
        int id = view.getId();
        if (id == R.id.tvCancel) {
        } else if (id == R.id.tvDetermine) {
            if (wheelDataSelectListener != null) {
                if (wheelDataBean != null) {
                    wheelDataSelectListener.onWheelDataSelect(selectPosition, wheelDataBean);
                }
            }
        }
        dismiss();
    }


}
