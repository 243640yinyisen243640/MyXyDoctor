package com.xy.xydoctor.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.utils.DrawableUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class GvSugarAddAdapter extends CommonAdapter<String> {
    private List<String> list;
    //默认选中第一个
    private int defaultSelection = 0;

    public GvSugarAddAdapter(Context context, int layoutId, List<String> datas, int defaultSelection) {
        super(context, layoutId, datas);
        this.defaultSelection = defaultSelection;
        this.list = datas;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_time, item);
        TextView tvTime = viewHolder.getView(R.id.tv_time);
        if (position == defaultSelection) {
            //选中时
            GradientDrawable gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.white), ColorUtils.getColor(R.color.main_red));
            tvTime.setBackground(gradientDrawable);
            tvTime.setTextColor(ColorUtils.getColor(R.color.main_red));
        } else {
            //未选中时
            GradientDrawable gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.sugar_add_tv_normal_bg), ColorUtils.getColor(R.color.sugar_add_tv_normal_bg));
            tvTime.setBackground(gradientDrawable);
            tvTime.setTextColor(ColorUtils.getColor(R.color.gray_text));
        }
    }

    /**
     * 修改选中时的状态
     *
     * @param position
     */
    public void setSelect(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }
    }
}
