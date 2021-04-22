package com.xy.xydoctor.adapter;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/7/23 9:42
 */
public class FollowUpVisitRvNewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HashMap<Integer, Boolean> selectMap;//选中保存
    private List<String> list;
    private List<String> checkList;
    private String status;
    private com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener;

    public FollowUpVisitRvNewAdapter(@Nullable List<String> datas, List<String> checkList, String status) {
        super(R.layout.item_include_follow_up_visit_symptom, datas);
        this.list = datas;
        this.checkList = checkList;
        this.status = status;
        init(checkList);
    }


    /**
     * 初始化全部为false
     *
     * @param checkList
     */
    private void init(List<String> checkList) {
        selectMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            selectMap.put(i, false);
        }
        if (checkList != null && checkList.size() > 0) {
            if (checkList.contains("1")) {
                selectMap.put(0, true);
            }
            if (checkList.contains("2")) {
                selectMap.put(1, true);
            }
            if (checkList.contains("3")) {
                selectMap.put(2, true);
            }
            if (checkList.contains("4")) {
                selectMap.put(3, true);
            }
            if (checkList.contains("5")) {
                selectMap.put(4, true);
            }
            if (checkList.contains("6")) {
                selectMap.put(5, true);
            }
            if (checkList.contains("7")) {
                selectMap.put(6, true);
            }
            if (checkList.contains("8")) {
                selectMap.put(7, true);
            }
            if (checkList.contains("9")) {
                selectMap.put(8, true);
            }
        }
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getAdapterPosition();
        holder.setText(R.id.tv_rv_symptom, s);
        CheckBox cbCheck = holder.getView(R.id.cb_symptom);
        cbCheck.setChecked(selectMap.get(position));
        if ("5".equals(status)) {
            cbCheck.setClickable(false);
        } else {
            cbCheck.setClickable(true);
        }
        if (mOnItemClickListener != null) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    public void setOnItemClickListener(com.xy.xydoctor.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
