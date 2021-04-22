package com.lyd.baselib.util.glide;

import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lyd.baselib.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * 描述: Glide加载图片工具类
 * 作者: LYD
 * 创建日期: 2019/4/15 17:19
 */
public class GlideUtils {
    private static final int defaultErrorImg = R.drawable.default_img_round;

    /**
     * 私有构造方法
     */
    private GlideUtils() {

    }

    /**
     * 加载普通图片
     *
     * @param imgUrl   图片路径
     * @param errorImg 默认图片(0为默认,其它为自设置)
     * @param img      显示图片的目标
     */
    public static void load(String imgUrl, int errorImg, ImageView img) {
        Glide.with(Utils.getApp())
                //.asBitmap()
                .load(imgUrl)
                .placeholder(errorImg <= 0 ? defaultErrorImg : errorImg)
                .error(errorImg <= 0 ? defaultErrorImg : errorImg)
                .into(img);
    }

    /**
     * 加载圆形图片
     *
     * @param imgUrl   图片路径
     * @param errorImg 默认图片(0为默认,其它为自设置)
     * @param img      显示图片的目标
     */
    public static void loadCircle(String imgUrl, int errorImg, ImageView img) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(Utils.getApp())
                .load(imgUrl)
                .placeholder(errorImg <= 0 ? defaultErrorImg : errorImg)
                .error(errorImg <= 0 ? defaultErrorImg : errorImg)
                .apply(requestOptions)
                .into(img);
    }

    /**
     * 加载圆角图片
     * 注意:img的xml中不可配置android:scaleType="centerCrop"
     *
     * @param imgUrl   图片路径
     * @param errorImg 默认图片(0为默认,其它为自设置)
     * @param img      显示图片的目标
     */
    public static void loadRound(String imgUrl, int errorImg, ImageView img) {
        //设置图片圆角角度
        RoundedCornersTransformation transformation = new RoundedCornersTransformation(45, 0, RoundedCornersTransformation.CornerType.ALL);
        RequestOptions option = bitmapTransform(transformation);
        Glide.with(Utils.getApp())
                .load(imgUrl)
                .placeholder(errorImg <= 0 ? defaultErrorImg : errorImg)
                .error(errorImg <= 0 ? defaultErrorImg : errorImg)
                .apply(option)
                .into(img);
    }


    /**
     * 高斯模糊
     *
     * @param imgUrl   图片路径
     * @param errorImg 默认图片(0为默认,其它为自设置)
     * @param img      显示图片的目标
     */
    public static void loadBlur(String imgUrl, int errorImg, ImageView img) {
        Glide.with(Utils.getApp())
                .load(imgUrl)
                .placeholder(errorImg <= 0 ? defaultErrorImg : errorImg)
                .error(errorImg <= 0 ? defaultErrorImg : errorImg)
                .apply(bitmapTransform(new BlurTransformation(25)))
                .into(img);
    }


}
