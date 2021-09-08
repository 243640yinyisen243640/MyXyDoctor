package com.xy.xydoctor.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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

    /**
     * 加载圆角图片
     *
     * @param context
     * @param defaultImageResourceId 占位图片
     * @param imagePath              图片路径
     * @param imageView              ImageView对象
     */
    public static void loadRoundImage(Context context, int defaultImageResourceId, String imagePath, ImageView imageView) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(XyScreenUtils.dip2px(context, 5));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = new RequestOptions().transform(new CenterCrop(), roundedCorners);
        Glide.with(context)
                .asBitmap()
                .load(imagePath)
                .placeholder(defaultImageResourceId)
                .error(defaultImageResourceId)
                .apply(options)
                .into(imageView);

    }

}
