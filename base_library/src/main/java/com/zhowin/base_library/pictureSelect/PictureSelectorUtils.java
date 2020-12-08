package com.zhowin.base_library.pictureSelect;

import android.app.Activity;
import android.text.TextUtils;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhowin.base_library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/12/12
 * function  : 图片选择的工具类
 */
public class PictureSelectorUtils {


    /**
     * 单张预览
     */
    public static void previewImage(Activity context, String path) {
        List<LocalMedia> localMediaList = new ArrayList<>();
        LocalMedia localMedia = new LocalMedia();
        localMedia.setPath(path);
        localMediaList.add(localMedia);
        PictureSelector.create(context)
                .themeStyle(R.style.picture_default_style)
                .imageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(0, localMediaList);
    }

    /**
     * 多图预览
     */
    public static void previewPicture(Activity activity, int position, List<String> imageList) {
        if (imageList == null) return;
        List<LocalMedia> localMediaList = new ArrayList<>();
        if (!localMediaList.isEmpty()) localMediaList.clear();
        for (int i = 0; i < imageList.size(); i++) {
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(imageList.get(i));
            localMedia.setAndroidQToPath(imageList.get(i));
            localMedia.setOriginalPath(imageList.get(i));
            localMediaList.add(localMedia);
        }
        PictureSelector.create(activity)
                .themeStyle(R.style.picture_default_style)
                .imageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(position, localMediaList);

    }


    /**
     * 选择图片--->单选
     *
     * @param activity    activity
     * @param requestCode 请求值
     */
    public static void selectImageOfOne(Activity activity, int requestCode, boolean isCamera) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .maxSelectNum(1)// 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(true)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code
    }

    public static void selectOneImage(Activity activity, int requestCode, boolean isCamera, boolean isCut) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .maxSelectNum(1)// 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(isCut)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code
    }


    /**
     * 选择多张图片
     *
     * @param activity  activity
     * @param maxNumber 选择的最大数量
     */
    public static void selectImageOfMore(Activity activity, int maxNumber, boolean isCamera, List<LocalMedia> selectList) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .minSelectNum(1)
                .selectionData(selectList)
                .maxSelectNum(maxNumber)// 最大图片选择数量
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(isCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    /**
     * 使用相机单独拍摄照片
     */
    public static void takingPictures(Activity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isEnableCrop(true)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code

    }


    /**
     * 选择图片--->单选
     *
     * @param activity    activity
     * @param requestCode 请求值
     */
    public static void selectVideoOfOne(Activity activity, int requestCode) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .imageEngine(GlideEngine.createGlideEngine())
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code

    }

    /**
     * 拍照
     *
     * @param activity activity
     */
    public static void takePhoto(Activity activity, List<LocalMedia> selectList) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .imageEngine(GlideEngine.createGlideEngine())
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.REQUEST_CAMERA);//结果回调onActivityResult code

    }

    /**
     * 拍照
     *
     * @param activity activity
     */
    public static void takePhoto(Activity activity, int requestCode) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .imageEngine(GlideEngine.createGlideEngine())
                .isPreviewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isOpenClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code

    }

    public static String getPhotoPath(LocalMedia localMedia) {
        if (!TextUtils.isEmpty(localMedia.getAndroidQToPath())) {
            return localMedia.getAndroidQToPath();
        } else {
            if (localMedia.isCut()) {
                return localMedia.getCutPath();
            }
            if (!TextUtils.isEmpty(localMedia.getCompressPath())) {
                return localMedia.getCompressPath();
            }
            return localMedia.getPath();
        }
    }
}
