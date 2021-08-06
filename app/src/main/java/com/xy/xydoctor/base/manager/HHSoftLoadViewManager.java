package com.xy.xydoctor.base.manager;

import android.view.View;

import com.xy.xydoctor.base.imp.HHSoftLoadStatus;
import com.xy.xydoctor.base.imp.HHSoftLoadViewInterface;
import com.xy.xydoctor.base.model.HHSoftLoadViewConfig;


public class HHSoftLoadViewManager {
    public static final HHSoftLoadViewConfig mLoadViewConfig = new HHSoftLoadViewConfig();

    /**
     * 加载页面模式
     */
    public enum LoadMode {
        DRAWABLE,
        PROGRESS
    }

    private HHSoftLoadViewInterface mAvalibleLoadViewInterface;

    /**
     * 加载布局管理器初始化
     *
     * @param loadMode   加载页面模式
     * @param parentView 必须是帧布局
     */
    public HHSoftLoadViewManager(LoadMode loadMode, View parentView, IPageLoad pageLoad) {
        if (parentView == null) {
            new Throwable("parentView is not null");
        }
        if (LoadMode.DRAWABLE == loadMode) {
            mAvalibleLoadViewInterface = new HHSoftDefaultLoadViewManager(parentView.getContext(), parentView, pageLoad);
        } else {
            mAvalibleLoadViewInterface = new HHSoftProgressLoadViewManager(parentView.getContext(), parentView, pageLoad);
        }
        mAvalibleLoadViewInterface.init(mLoadViewConfig);
    }

    public HHSoftLoadViewInterface loadViewInterface() {
        return mAvalibleLoadViewInterface;
    }

    /**
     * 在调用加载中方法之前调用该方法
     *
     * @param config
     */
    public void initLoadView(HHSoftLoadViewConfig config) {
        mAvalibleLoadViewInterface.init(config);
    }

    public void changeLoadState(HHSoftLoadStatus status) {
        mAvalibleLoadViewInterface.changeLoadState(status);
    }

    public void changeLoadStateWithHint(HHSoftLoadStatus status, String hint) {
        mAvalibleLoadViewInterface.changeLoadStateWithHint(status, hint);
    }

    public void setOnClickListener(HHSoftLoadStatus status, View.OnClickListener listener) {
        mAvalibleLoadViewInterface.setOnClickListener(status, listener);
    }

    public interface IPageLoad {
        void onPageLoad();
    }
}
