package com.xy.xydoctor.adapter;
/*
 * 类名:     HomeSignFamilyAdapter
 * 描述:     家签家庭列表
 * 作者:     ZWK
 * 创建日期: 2020/1/14 15:27
 */

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.HomeSignFamilyBean;

import java.util.List;

public class HomeSignFamilyNewAdapter extends BaseQuickAdapter<HomeSignFamilyBean, BaseViewHolder> {

    private boolean isExtra;
    private OnItemSelectListener onItemSelectListener;
    private List<Boolean> booleanList;
    private int x = 0;
    private List<HomeSignFamilyBean> datas;
    private RecyclerView recyclerView;

    public HomeSignFamilyNewAdapter(@Nullable List<HomeSignFamilyBean> data) {
        super(R.layout.item_home_sign, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, HomeSignFamilyBean homeSignBean) {
        int position = holder.getAdapterPosition();

        holder.setText(R.id.tv_name, homeSignBean.getNickname());
        holder.setText(R.id.tv_phone_number, homeSignBean.getTel());
        holder.setText(R.id.tv_id_number, homeSignBean.getIdcard());
        holder.setText(R.id.tv_identity, getRelationString(homeSignBean.getRelation()));
        if (isExtra) {
            holder.setVisible(R.id.cb_add, true);
            CheckBox cb = holder.getView(R.id.cb_add);
            if (booleanList.size() < datas.size()) {
                for (int i = 0; i < datas.size() - booleanList.size(); i++) {
                    booleanList.add(false);
                }
            }
            cb.setChecked(booleanList.get(position));
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (int i = 0; i < booleanList.size(); i++) {
                            booleanList.set(i, false);
                        }
                        booleanList.set(position, true);
                        if (recyclerView.isComputingLayout()) {
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(x);
                                    x = position;
                                }
                            });
                        } else {
                            notifyItemChanged(x);
                            x = position;
                        }

                    }
                }
            });
        } else {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelectListener.OnItemSelect(v, homeSignBean);
                }
            });
        }
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }


    public int getId() {
        return datas.get(x).getId();
    }

    private String getRelationString(int rel) {
        String relation = "";
        switch (rel) {
            case 1:
                relation = "户主";
                break;
            case 2:
                relation = "配偶";
                break;
            case 3:
                relation = "子";
                break;
            case 4:
                relation = "儿媳";
                break;
            case 5:
                relation = "女";
                break;
            case 6:
                relation = "女婿";
                break;
            case 7:
                relation = "外（孙）子女";
                break;
            case 8:
                relation = "其他";
                break;
        }
        return relation;
    }

    public interface OnItemSelectListener {
        void OnItemSelect(View v, HomeSignFamilyBean bean);
    }

}
