package com.xy.xydoctor.adapter.community_manager;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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

        if (item.isSelected()) {
            checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_check, 0, 0, 0);
        } else {
            checkTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.community_special_operate_uncheck, 0, 0, 0);
        }


        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        if (item.getList() != null && item.getList().size() >= 1) {
            clickLinearLayout.setVisibility(View.VISIBLE);
            stringBuilder.append(item.getList().get(position).getDatetime());

            int start = stringBuilder.length();

            if ("1".equals(type)) {
                stringBuilder.append("   " + item.getList().get(position).getDiastole()).append("/").append(item.getList().get(position).getSystolic()).append("");
                stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.data_gray_light)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                //1偏高2低3正常
                TextView lightTextView = viewHolder.getView(R.id.tv_data_abnormal_child_high_or_low);

                if ("1".equals(item.getList().get(position).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_hign, 0);
                } else if ("2".equals(item.getList().get(position).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_low, 0);
                } else {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sugar_or_pressure_normal, 0);
                }
                lightTextView.setText("mmHg");
            } else {
                stringBuilder.append("   " + item.getList().get(position).getGlucosevalue()).append("mmol/L");
                stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.data_gray_light)), 0, start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                stringBuilder.setSpan(new StyleSpan(Typeface.NORMAL), 0, start, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                //1偏高2低3正常
                TextView lightTextView = viewHolder.getView(R.id.tv_data_abnormal_child_high_or_low);

                if ("1".equals(item.getList().get(position).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_hign, 0, 0, 0);
                } else if ("2".equals(item.getList().get(position).getIshight())) {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_low, 0, 0, 0);
                } else {
                    lightTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugar_or_pressure_normal, 0, 0, 0);
                }
                lightTextView.setText(item.getList().get(position).getCategoryname());
            }

            viewHolder.setText(R.id.tv_data_abnormal_child_time_and_amonut, stringBuilder.toString());
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
