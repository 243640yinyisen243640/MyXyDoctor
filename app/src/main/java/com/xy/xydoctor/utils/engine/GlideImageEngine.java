package com.xy.xydoctor.utils.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.xy.xydoctor.R;

public class GlideImageEngine implements ImageEngine {

    @Override
    public void loadImage(Context context, String url, ImageView imageView, View progressView, View customImageView) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .fitCenter()
                .error(R.drawable.default_image)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        //隐藏进度View,必须设置setCustomProgressViewLayoutID
                        progressView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }
}