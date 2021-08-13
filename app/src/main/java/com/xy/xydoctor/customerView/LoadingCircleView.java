package com.xy.xydoctor.customerView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 类描述：
 * 类传参：
 * 创建时间：2018/3/5
 */

public class LoadingCircleView extends AppCompatImageView {
    private AnimationDrawable mAnimationDrawable;
    public LoadingCircleView(Context context) {
        super(context);
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startLoadingAnim(){
        if (this.getBackground() instanceof AnimationDrawable) {
            mAnimationDrawable = (AnimationDrawable) this.getBackground();
            this.post(new Runnable() {

                @Override
                public void run() {
                    mAnimationDrawable.start();
                }
            });
        }
    }
    public void stopLoaddingAnim() {
        if (this==null){
            return;
        }
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}
