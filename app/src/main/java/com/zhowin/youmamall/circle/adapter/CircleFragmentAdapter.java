package com.zhowin.youmamall.circle.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.circle.callback.OnGridImageClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public class CircleFragmentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CircleFragmentAdapter(@Nullable List<String> data) {
        super(R.layout.include_circle_fragment_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

        RecyclerView imageRecyclerView = helper.getView(R.id.imageRecyclerView);
        List<String> imageList = Arrays.asList("", "", "", "", "");
        ImageAdapter imageAdapter = new ImageAdapter();
        imageAdapter.updateData(imageList);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageRecyclerView.setAdapter(imageAdapter);
    }
}
