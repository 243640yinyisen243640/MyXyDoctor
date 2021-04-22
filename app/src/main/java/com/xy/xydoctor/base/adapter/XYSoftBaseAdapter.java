package com.xy.xydoctor.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 一个简单的Adapter，继承自BaseAdapter<br/>
 * 默认实现了出getView方法意外的其他的方法
 *
 * @param <T>
 * @author yuan
 */
public abstract class XYSoftBaseAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList;

    public XYSoftBaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    /**
     * 返回构造HHBaseAdapter的时候传入的Context对象
     *
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * 返回adapter绑定的数据源
     *
     * @return
     */
    public List<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据View的ID，在parentView中查找ID为viewID的View
     *
     * @param parentView
     * @param viewID
     * @return
     */
    public <T> T getViewByID(View parentView, int viewID) {
        return (T) parentView.findViewById(viewID);
    }
}
