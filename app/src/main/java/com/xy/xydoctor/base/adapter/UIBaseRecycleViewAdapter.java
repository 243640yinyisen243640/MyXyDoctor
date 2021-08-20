package com.xy.xydoctor.base.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

public abstract class UIBaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<T> mList;
    private IAdapterViewClickListener mListener;

    public UIBaseRecycleViewAdapter(Context mContext, List<T> mList, IAdapterViewClickListener mListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
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

    protected IAdapterViewClickListener getListener() {
        return mListener;
    }
}
