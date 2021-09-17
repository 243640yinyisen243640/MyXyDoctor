package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.DataAbnormalChildInfo;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class DataAbnormalChildVisitListAdapter extends CommonAdapter<DataAbnormalChildInfo> {

    /**
     * 1血压，2血糖
     */
    private String type;
    private IAdapterViewClickListener clickListener;
    private int parentPosi;

    private Context context;

    public DataAbnormalChildVisitListAdapter(Context context, int layoutId, List<DataAbnormalChildInfo> datas, String type, int parentPosi, IAdapterViewClickListener clickListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.clickListener = clickListener;
        this.parentPosi = parentPosi;
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder viewHolder, DataAbnormalChildInfo item, int position) {

        viewHolder.setText(R.id.tv_data_abnormal_child_name, item.getNickname());
        viewHolder.setText(R.id.tv_data_abnormal_child_phone, item.getTel());
        viewHolder.setText(R.id.tv_data_abnormal_child_location, item.getBuild_name() + item.getUnit_name() + item.getHouse_num());
        LinearLayout clickLinearLayout = viewHolder.getView(R.id.ll_data_abnormal_child_click);
        TextView checkTextView = viewHolder.getView(R.id.tv_data_abnormal_child_check);
        TextView telImageView = viewHolder.getView(R.id.tv_data_abnormal_child_phone_img);
        TextView lightTextView = viewHolder.getView(R.id.tv_data_abnormal_child_high_or_low);

        if (item.isSelected()) {
            checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_check, 0, 0, 0);
        } else {
            checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_uncheck, 0, 0, 0);
        }


        if (item.getList() != null && item.getList().size() >= 1) {
            clickLinearLayout.setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.tv_data_abnormal_child_time, item.getList().get(0).getDatetime());

            if ("1".equals(type)) {
                viewHolder.setText(R.id.tv_data_abnormal_child_amonut, item.getList().get(0).getDiastole() + "/" + item.getList().get(0).getSystolic());
                //1偏高2低3正常
                if ("1".equals(item.getList().get(0).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
                } else if ("2".equals(item.getList().get(0).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
                } else {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
                }
                lightTextView.setText("mmHg");
            } else {
                viewHolder.setText(R.id.tv_data_abnormal_child_amonut, item.getList().get(0).getGlucosevalue() + "    mmol/L");
                if ("1".equals(item.getList().get(0).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_hign, 0, 0, 0);
                } else if ("2".equals(item.getList().get(0).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_low, 0, 0, 0);
                } else {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_normal, 0, 0, 0);
                }
                lightTextView.setText(item.getList().get(0).getCategoryname());
            }

            Log.i("yys", "getIshight==" + item.getList().get(0).getIshight());

        } else {
            clickLinearLayout.setVisibility(View.GONE);
        }

        if (item.isCheck()) {
            checkTextView.setVisibility(View.VISIBLE);
        } else {
            checkTextView.setVisibility(View.GONE);
        }

        MyClickListener adapterItemClickListener = new MyClickListener(position);
        checkTextView.setOnClickListener(adapterItemClickListener);
        clickLinearLayout.setOnClickListener(adapterItemClickListener);
        telImageView.setOnClickListener(adapterItemClickListener);
    }

    /**
     * 点击事件
     */
    private class MyClickListener implements View.OnClickListener {

        private int childPosi;

        public MyClickListener(int childPosi) {
            this.childPosi = childPosi;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {

                clickListener.adapterClickListener(parentPosi, childPosi, v);
            }
        }
    }
}
