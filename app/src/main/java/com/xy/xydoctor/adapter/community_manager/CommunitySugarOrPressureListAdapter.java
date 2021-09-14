package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.widget.view.MyListView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.adapter.UIBaseRecycleViewAdapter;
import com.xy.xydoctor.bean.community_manamer.SugarOrPressureInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;

import java.util.List;

/**
 * 描述: 血糖随访血压随访
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class CommunitySugarOrPressureListAdapter extends UIBaseRecycleViewAdapter<SugarOrPressureInfo> {
    public CommunitySugarOrPressureListAdapter(Context mContext, List<SugarOrPressureInfo> mList, IAdapterViewClickListener mListener) {
        super(mContext, mList, mListener);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //实例化得到Item布局文件的View对象
        View v = View.inflate(getContext(), R.layout.item_community_sugar_or_pressure, null);
        //返回MyViewHolder的对象
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        SugarOrPressureInfo info = getList().get(position);

        viewHolder.yearTextView.setText(info.getYear() + "年");

        viewHolder.clickLinearLayout.setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().adapterClickListener(position, v);
            }
        });
        CommunitySugarPressureChildListAdapter childListAdapter = new CommunitySugarPressureChildListAdapter(getContext(), info.getList(),position,getListener());
        viewHolder.myListView.setAdapter(childListAdapter);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView yearTextView;
        private MyListView myListView;
        private LinearLayout clickLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.fu_sugar_pressure_year);
            myListView = itemView.findViewById(R.id.lv_fu_sugar_pressure_child);
            clickLinearLayout = itemView.findViewById(R.id.ll_sugar_or_pressure_click);

        }
    }


}

