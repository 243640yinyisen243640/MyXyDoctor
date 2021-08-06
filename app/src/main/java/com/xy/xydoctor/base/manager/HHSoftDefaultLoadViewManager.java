package com.xy.xydoctor.base.manager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.imp.HHSoftLoadStatus;
import com.xy.xydoctor.base.imp.HHSoftLoadViewInterface;
import com.xy.xydoctor.base.model.HHSoftLoadViewConfig;
import com.xy.xydoctor.base.model.HHSoftLoadViewInfo;
import com.xy.xydoctor.base.model.HHSoftLoadViewStateRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：页面加载动画管理器
 * 创建人：xiao
 * 创建时间：2018/2/27
 */

public class HHSoftDefaultLoadViewManager implements HHSoftLoadViewInterface {
    /*布局及控件*/
    private View mLoadingView;
    private ImageView mLoadingImageView;
    private TextView mLoadingTextView;
    /*状态信息*/
    // mLoaddingView点击事件的散列表
    private Map<HHSoftLoadStatus, HHSoftLoadViewStateRecord> mStateListenerMap = new HashMap<>();
    //控制各个状态下显示的图片和文字
    private Map<HHSoftLoadStatus, HHSoftLoadViewInfo> mLoadViewInfoMap = new HashMap<>();
    /*显示动画的drawable*/
    private AnimationDrawable mAnimationDrawable;
    private View mParentView;
    private Context mContext;

    private HHSoftLoadViewManager.IPageLoad mPageLoadInterface;

    //构造方法
    public HHSoftDefaultLoadViewManager(Context context, View parentView, HHSoftLoadViewManager.IPageLoad pageLoad) {
        super();
        this.mParentView = parentView;
        this.mContext = context;
        this.mPageLoadInterface = pageLoad;
        //实例化加载的页面
        mLoadingView = View.inflate(context, R.layout.hhsoft_include_load_view_default, null);
        mLoadingImageView = mLoadingView.findViewById(R.id.huahansoft_iv_loading_first);
        mLoadingTextView = mLoadingView.findViewById(R.id.huahansoft_tv_loading_first);

    }

    @Override
    public void init(HHSoftLoadViewConfig config) {
        mLoadingView.setBackgroundColor(Color.parseColor(config.loadViewBgColor));
    }

    @Override
    public void changeLoadState(HHSoftLoadStatus state) {
        //还原用户原来设置的点击事件
        bindListener(state);
        switch (state) {
            case LOADING:
                changeTipViewInfo(state, null);
                // 如果mLoaddingImageView显示的背景图是一个AnimationDrawable的实例，则开启动画效果
                startLoadingAnim();
                if (mPageLoadInterface != null) {
                    mPageLoadInterface.onPageLoad();
                }
                break;
            case FAILED:
            case NODATA:
                changeTipViewInfo(state, null);
                break;
            case SUCCESS:
                //判断当前的加载的视图在页面中的位置，如果当前的加载视图确实是在页面中存在就把当前的页面从页面中移除掉
                FrameLayout baseContainerLayout = (FrameLayout) mParentView;
                int indexOfChild = baseContainerLayout.indexOfChild(mLoadingView);
                if (indexOfChild != -1) {
                    baseContainerLayout.removeViewAt(indexOfChild);
                }
                stopLoaddingAnim();
                break;
            default:
                break;
        }
    }

    @Override
    public void changeLoadStateWithHint(HHSoftLoadStatus state, String stateHint) {
        //还原用户原来设置的点击事件
        bindListener(state);
        switch (state) {
            case LOADING:
                changeTipViewInfo(state, stateHint);
                // 如果mLoaddingImageView显示的背景图是一个AnimationDrawable的实例，则开启动画效果
                startLoadingAnim();
                if (mPageLoadInterface != null) {
                    mPageLoadInterface.onPageLoad();
                }
                break;
            case FAILED:
            case NODATA:
                changeTipViewInfo(state, stateHint);
                break;
            case SUCCESS:
                //判断当前的加载的视图在页面中的位置，如果当前的加载视图确实是在页面中存在就把当前的页面从页面中移除掉
                FrameLayout baseContainerLayout = (FrameLayout) mParentView;
                int indexOfChild = baseContainerLayout.indexOfChild(mLoadingView);
                if (indexOfChild != -1) {
                    baseContainerLayout.removeViewAt(indexOfChild);
                }
                stopLoaddingAnim();
                break;
            default:
                break;
        }
    }


    /*绑定监听*/
    private void bindListener(final HHSoftLoadStatus state) {
        switch (state) {
            //当当前的状态是长在加载或者加载成功的时候，取消所有控件的点击事件
            case LOADING:
            case SUCCESS:
                mLoadingView.setOnClickListener(null);
                mLoadingImageView.setOnClickListener(null);
                break;
            case FAILED:
                /*当获取数据失败的时候，判断用户时候给加载的页面设置了相应的点击事件，如果设置了点击事件，就使用
                用户自己的点击事件，如果没有设置点击事件，则添加默认的点击事件，默认的点击事件是点击以后重新显示
                正在加载的页面并且重新加载数据*/
                if (!bindChildListener(state)) {
                    LoadClickListener listener = new LoadClickListener();
                    mLoadingView.setOnClickListener(listener);
                    mLoadingImageView.setOnClickListener(listener);
                }
                break;
            case NODATA:
                /*当获取到数据，但是不需要显示的时候判断用户时候设置了相应的点击事件，如果用户设置了点击事件，则使用
                用户设置的点击事件，如果没有设置点击事件，则使用默认的事件，默认的事件就是不给控件添加任何事件*/
                if (!bindChildListener(state)) {
                    mLoadingImageView.setOnClickListener(null);
                    mLoadingView.setOnClickListener(null);
                }
                break;

            default:
                break;
        }
    }

    /*绑定监听*/
    private boolean bindChildListener(HHSoftLoadStatus state) {
        //判断时候给当前的状态添加了点击事件，如果添加了点击事件，就重新给控件绑定特定的点击事件
        if (mStateListenerMap != null && mStateListenerMap.get(state) != null) {
            //获取特定状态绑定的状态
            HHSoftLoadViewStateRecord record = mStateListenerMap.get(state);
            //判断是不是只是给ImageView设置了点击事件，并且根据设置的状态给控件添加点击事件
            if (record.isJustImageView()) {
                mLoadingImageView.setOnClickListener(record.getOnClickListener());
                mLoadingView.setOnClickListener(null);
            } else {
                mLoadingImageView.setOnClickListener(null);
                mLoadingView.setOnClickListener(record.getOnClickListener());
            }
            return true;
        }
        return false;
    }

    @Override
    public void setOnClickListener(HHSoftLoadStatus status, View.OnClickListener listener) {
        loadingViewClickListener(status, listener, false);
    }

    /*加载失败控件监听*/
    public void loadingViewClickListener(HHSoftLoadStatus state, View.OnClickListener listener, boolean justImageView) {
        if (state == HHSoftLoadStatus.LOADING || state == HHSoftLoadStatus.SUCCESS) {
            return;
        }
        HHSoftLoadViewStateRecord record = mStateListenerMap.get(state);
        if (record == null) {
            record = new HHSoftLoadViewStateRecord(listener, justImageView);
            mStateListenerMap.put(state, record);
        } else {
            record.setJustImageView(justImageView);
            record.setOnClickListener(listener);
        }
    }

    /*设置提示的视图显示的内容*/
    private void changeTipViewInfo(HHSoftLoadStatus state, String hint) {
        //首先需要停止当前动画效果
        stopLoaddingAnim();
        HHSoftLoadViewInfo hhLoadViewInfo = mLoadViewInfoMap.get(state);
        //定义变量，保存显示的图片资源和显示的文本信息
        int drawableID = 0;
        String msg = "";
        //用户没有为单独的页面设置显示的图片和现实的文本
        if (hhLoadViewInfo == null) {
//            HHSoftLoadViewInfo loadViewInfo = HuaHanSoftConstantParam.loadViewMap.get(state);
            HHSoftLoadViewInfo loadViewInfo = null;
            //用户没有为整个App设置显示的图片和文本
            if (loadViewInfo == null) {
                //设置默认的图片和文本
                switch (state) {
                    case FAILED:
                        drawableID = R.drawable.hhsoft_loading_state_error;
                        msg = TextUtils.isEmpty(hint) ? getString(R.string.huahansoft_load_state_failed) : hint;
                        break;
                    case NODATA:
                        drawableID = R.drawable.hhsoft_loading_state_no_data;
                        msg = TextUtils.isEmpty(hint) ? getString(R.string.huahansoft_load_state_no_data) : hint;
                        break;
                    case LOADING:
                        drawableID = R.drawable.hhsoft_loading_anim;
                        msg = TextUtils.isEmpty(hint) ? getString(R.string.huahansoft_load_state_loading) : hint;
                        break;
                    default:
                        break;
                }
            } else {
                //用户为整个App设置了显示的文本和图片
                drawableID = loadViewInfo.getDrawableID();
                msg = loadViewInfo.getMsgInfo();
            }
        } else {
            //用户为单独的页面设置了显示的文本和显示的图片
            drawableID = hhLoadViewInfo.getDrawableID();
            msg = hhLoadViewInfo.getMsgInfo();
        }
        //设置控件显示的图片和文本
        mLoadingImageView.setBackgroundResource(drawableID);
        mLoadingTextView.setText(msg);
        //判断加载的视图是不是已经加载到了页面当中，如果没有加载到页面当中，就把加载的视图添加到页面中
        FrameLayout baseContainerLayout = (FrameLayout) mParentView;
        int indexOfChild = baseContainerLayout.indexOfChild(mLoadingView);
        if (indexOfChild == -1) {
            baseContainerLayout.addView(mLoadingView, -1, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }

    /*开始当前的加载动画*/
    private void startLoadingAnim() {
        if (mLoadingImageView.getBackground() instanceof AnimationDrawable) {
            mAnimationDrawable = (AnimationDrawable) mLoadingImageView.getBackground();
            mLoadingImageView.post(new Runnable() {

                @Override
                public void run() {
                    mAnimationDrawable.start();
                }
            });
        }
    }

    /*暂停当前的加载动画*/
    private void stopLoaddingAnim() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }

    /*根据ID获取字符串*/
    private String getString(int resID) {
        return mContext.getString(resID);
    }

    /*加载失败的时候点击执行的默认的监听器*/
    private class LoadClickListener implements View.OnClickListener {

        public LoadClickListener() {
            super();
        }

        @Override
        public void onClick(View v) {
            changeLoadState(HHSoftLoadStatus.LOADING);
        }
    }
}
