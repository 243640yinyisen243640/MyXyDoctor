package com.xy.xydoctor.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitBloodSugarAddBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Arrays;
import java.util.List;

/**
 * 描述: item_seven_and_thirty_bottom_for_follow_up_visit
 * 作者: LYD
 * 创建日期: 2019/11/6 16:42
 */
public class FollowUpVisitBloodSugarAddAdapter extends CommonAdapter<FollowUpVisitBloodSugarAddBean> {

    public FollowUpVisitBloodSugarAddAdapter(Context context, int layoutId, List<FollowUpVisitBloodSugarAddBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitBloodSugarAddBean item, int position) {
        //值处理
        String one = item.getOne();
        String two = item.getTwo();
        String three = item.getThree();
        String four = item.getFour();
        String five = item.getFive();
        String six = item.getSix();
        String seven = item.getSeven();
        String eight = item.getEight();

        TextView tvOne = viewHolder.getView(R.id.tv_one);
        TextView tvTwo = viewHolder.getView(R.id.tv_two);
        TextView tvThree = viewHolder.getView(R.id.tv_three);
        TextView tvFour = viewHolder.getView(R.id.tv_four);
        TextView tvFive = viewHolder.getView(R.id.tv_five);
        TextView tvSix = viewHolder.getView(R.id.tv_six);
        TextView tvSeven = viewHolder.getView(R.id.tv_seven);
        TextView tvEight = viewHolder.getView(R.id.tv_eight);

        ImageView imgOne = viewHolder.getView(R.id.img_one);
        ImageView imgTwo = viewHolder.getView(R.id.img_two);
        ImageView imgThree = viewHolder.getView(R.id.img_three);
        ImageView imgFour = viewHolder.getView(R.id.img_four);
        ImageView imgFive = viewHolder.getView(R.id.img_five);
        ImageView imgSix = viewHolder.getView(R.id.img_six);
        ImageView imgSeven = viewHolder.getView(R.id.img_seven);
        ImageView imgEight = viewHolder.getView(R.id.img_eight);


        //凌晨
        setValueAndIsShow(one, imgOne, tvOne);
        //早餐前
        setValueAndIsShow(two, imgTwo, tvTwo);
        //早餐后
        setValueAndIsShow(three, imgThree, tvThree);
        //午餐前
        setValueAndIsShow(four, imgFour, tvFour);
        //午餐后
        setValueAndIsShow(five, imgFive, tvFive);
        //晚餐前
        setValueAndIsShow(six, imgSix, tvSix);
        //晚餐后
        setValueAndIsShow(seven, imgSeven, tvSeven);
        //睡前
        setValueAndIsShow(eight, imgEight, tvEight);
        //时间处理
        TextView tvTime = viewHolder.getView(R.id.tv_time);
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.follow_up_visit_seven_blood_sugar);
        List<String> list = Arrays.asList(stringArray);
        tvTime.setText(list.get(position));

    }


    /**
     * 设置
     *
     * @param value
     * @param img
     * @param tv
     */
    private void setValueAndIsShow(String value, ImageView img, TextView tv) {
        if (TextUtils.isEmpty(value)) {
            img.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            tv.setText(value);
        } else {
            img.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
            tv.setText(value);
        }
    }

}
