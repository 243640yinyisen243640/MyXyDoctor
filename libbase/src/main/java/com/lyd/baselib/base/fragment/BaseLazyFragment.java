package com.lyd.baselib.base.fragment;

/**
 * 描述:  懒加载Fragment
 * 作者: LYD
 * 创建日期: 2019/7/11 9:39
 */
@Deprecated
public abstract class BaseLazyFragment extends BaseEventBusFragment {

    private boolean isLoaded = false;

    public abstract void loadData();

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded && !isHidden()) {
            loadData();
            isLoaded = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
    }
}
