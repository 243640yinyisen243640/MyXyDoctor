package com.xy.xydoctor.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Author: LYD
 * Date: 2021/9/8 9:42
 * Description:
 */
public class LoadImgUtils {

    /**
     * 加载矩形图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .error(defaultImageResourceId)
                .centerCrop()
                .into(imageView);
    }
}
