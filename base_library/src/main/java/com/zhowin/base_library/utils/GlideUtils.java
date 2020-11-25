package com.zhowin.base_library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zhowin.base_library.R;
import com.zhowin.base_library.widget.BlurTransformation;


/**
 * author      : Z_B
 * date       : 2018/11/21
 * function  : Glide图片加载的工具类
 */
public class GlideUtils {


    /**
     * 加载图片
     */
    public static void loadObjectImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_def_image)
                .error(R.mipmap.ic_def_image)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(url)
                .apply(options)
                .into(imageView);
    }


    /**
     * 加载用户图像
     */
    public static void loadUserPhotoForLogin(Context context, Object photoUrl, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_default_hp)
                .error(R.mipmap.ic_default_hp)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(photoUrl)
                .apply(options)
                .into(imageView);
    }


    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }

    public static void loadUrlWithDefault(Context context, String url, int res, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .error(res)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }


    /**
     * 判断Activity是否Destroy
     *
     * @return true  or false
     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}
