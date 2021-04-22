package com.xy.xydoctor.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyd.baselib.base.adapter.XyBaseAdapter;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.SugarAbnormalBean;
import com.xy.xydoctor.imp.AdapterClickImp;

import java.util.List;

public class SugarAbnormalAdapter extends XyBaseAdapter<SugarAbnormalBean.SevenglucoseBean> {
    private AdapterClickImp listener;

    public SugarAbnormalAdapter(Context mContext, List<SugarAbnormalBean.SevenglucoseBean> mList, AdapterClickImp listener) {
        super(mContext, mList);
        this.listener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_seven_and_thirty_bottom, parent, false);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);

            viewHolder.flBeforeDawn = convertView.findViewById(R.id.fl_before_dawn);
            viewHolder.tvBeforeDawn = convertView.findViewById(R.id.tv_before_dawn);
            viewHolder.imgBeforeDawnMore = convertView.findViewById(R.id.img_before_dawn_more);
            viewHolder.imgBeforeDawnAdd = convertView.findViewById(R.id.img_before_dawn_add);

            viewHolder.flBeforeBreakfast = convertView.findViewById(R.id.fl_before_breakfast);
            viewHolder.tvBeforeBreakfast = convertView.findViewById(R.id.tv_before_breakfast);
            viewHolder.imgBeforeBreakfastMore = convertView.findViewById(R.id.img_before_breakfast_more);
            viewHolder.imgBeforeBreakfastAdd = convertView.findViewById(R.id.img_before_breakfast_add);

            viewHolder.flAfterTheBreakfast = convertView.findViewById(R.id.fl_after_the_breakfast);
            viewHolder.tvAfterTheBreakfast = convertView.findViewById(R.id.tv_after_the_breakfast);
            viewHolder.imgAfterTheBreakfastMore = convertView.findViewById(R.id.img_after_the_breakfast_more);
            viewHolder.imgAfterTheBreakfastAdd = convertView.findViewById(R.id.img_after_the_breakfast_add);

            viewHolder.flBeforeLunch = convertView.findViewById(R.id.fl_before_lunch);
            viewHolder.tvBeforeLunch = convertView.findViewById(R.id.tv_before_lunch);
            viewHolder.imgBeforeLunchMore = convertView.findViewById(R.id.img_before_lunch_more);
            viewHolder.imgBeforeLunchAdd = convertView.findViewById(R.id.img_before_lunch_add);

            viewHolder.flAfterLaunch = convertView.findViewById(R.id.fl_after_launch);
            viewHolder.tvAfterLaunch = convertView.findViewById(R.id.tv_after_launch);
            viewHolder.imgAfterLaunchMore = convertView.findViewById(R.id.img_after_launch_more);
            viewHolder.imgAfterLaunchAdd = convertView.findViewById(R.id.img_after_launch_add);

            viewHolder.flBeforeDinner = convertView.findViewById(R.id.fl_before_dinner);
            viewHolder.tvBeforeDinner = convertView.findViewById(R.id.tv_before_dinner);
            viewHolder.imgBeforeDinnerMore = convertView.findViewById(R.id.img_before_dinner_more);
            viewHolder.imgBeforeDinnerAdd = convertView.findViewById(R.id.img_before_dinner_add);

            viewHolder.flAfterDinner = convertView.findViewById(R.id.fl_after_dinner);
            viewHolder.tvAfterDinner = convertView.findViewById(R.id.tv_after_dinner);
            viewHolder.imgAfterDinnerMore = convertView.findViewById(R.id.img_after_dinner_more);
            viewHolder.imgAfterDinnerAdd = convertView.findViewById(R.id.img_after_dinner_add);

            viewHolder.flAfterSleep = convertView.findViewById(R.id.fl_after_sleep);
            viewHolder.tvAfterSleep = convertView.findViewById(R.id.tv_after_sleep);
            viewHolder.imgAfterSleepMore = convertView.findViewById(R.id.img_after_sleep_more);
            viewHolder.imgAfterSleepAdd = convertView.findViewById(R.id.img_after_sleep_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        SugarAbnormalBean.SevenglucoseBean item = getList().get(position);

        String time = item.getTime();
        String subTime = time.substring(5);
        viewHolder.tvTime.setText(subTime);

        //凌晨
        String lch = item.getLch();
        int lchmore = item.getLchmore();//0否 1是
        if (TextUtils.isEmpty(lch)) {
            //没有值
            viewHolder.tvBeforeDawn.setVisibility(View.GONE);
            viewHolder.imgBeforeDawnAdd.setVisibility(View.VISIBLE);
        } else {
            //有值
            viewHolder.tvBeforeDawn.setVisibility(View.VISIBLE);
            viewHolder.tvBeforeDawn.setText(lch);
            viewHolder.imgBeforeDawnAdd.setVisibility(View.GONE);
        }
        if (0 == lchmore) {
            viewHolder.imgBeforeDawnMore.setVisibility(View.GONE);
            viewHolder.flBeforeDawn.setOnClickListener(null);
        } else {
            viewHolder.imgBeforeDawnMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder = viewHolder;
            viewHolder.flBeforeDawn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder.flBeforeDawn, position);
                }
            });
        }
        //早餐前
        String zcq = item.getZcq();
        int zcqmore = item.getZcqmore();
        if (TextUtils.isEmpty(zcq)) {
            //没有值
            viewHolder.tvBeforeBreakfast.setVisibility(View.GONE);
            viewHolder.imgBeforeBreakfastAdd.setVisibility(View.VISIBLE);
        } else {
            //有值
            viewHolder.tvBeforeBreakfast.setVisibility(View.VISIBLE);
            viewHolder.tvBeforeBreakfast.setText(zcq);
            viewHolder.imgBeforeBreakfastAdd.setVisibility(View.GONE);
        }
        if (0 == zcqmore) {
            viewHolder.imgBeforeBreakfastMore.setVisibility(View.GONE);
            viewHolder.flBeforeBreakfast.setOnClickListener(null);
        } else {
            viewHolder.imgBeforeBreakfastMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder1 = viewHolder;
            viewHolder.flBeforeBreakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder1.flBeforeBreakfast, position);
                }
            });
        }
        //早餐后
        String zch = item.getZch();
        int zchmore = item.getZchmore();
        if (0 == zchmore) {
            viewHolder.imgAfterTheBreakfastMore.setVisibility(View.GONE);
            viewHolder.flAfterTheBreakfast.setOnClickListener(null);
        } else {
            viewHolder.imgAfterTheBreakfastMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder2 = viewHolder;
            viewHolder.flAfterTheBreakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder2.flAfterTheBreakfast, position);
                }
            });
        }
        if (TextUtils.isEmpty(zch)) {
            viewHolder.tvAfterTheBreakfast.setVisibility(View.GONE);
            viewHolder.imgAfterTheBreakfastAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvAfterTheBreakfast.setVisibility(View.VISIBLE);
            viewHolder.imgAfterTheBreakfastAdd.setVisibility(View.GONE);
            viewHolder.tvAfterTheBreakfast.setText(zch);
        }
        //午餐前
        String wcq = item.getWcq();
        int wcqmore = item.getWcqmore();
        if (0 == wcqmore) {
            viewHolder.imgBeforeLunchMore.setVisibility(View.GONE);
            viewHolder.flBeforeLunch.setOnClickListener(null);
        } else {
            viewHolder.imgBeforeLunchMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder3 = viewHolder;
            viewHolder.flBeforeLunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder3.flBeforeLunch, position);
                }
            });
        }
        if (TextUtils.isEmpty(wcq)) {
            viewHolder.tvBeforeLunch.setVisibility(View.GONE);
            viewHolder.imgBeforeLunchAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvBeforeLunch.setVisibility(View.VISIBLE);
            viewHolder.imgBeforeLunchAdd.setVisibility(View.GONE);
            viewHolder.tvBeforeLunch.setText(wcq);
        }
        //午餐后
        String wch = item.getWch();
        int wchmore = item.getWchmore();
        if (0 == wchmore) {
            viewHolder.imgAfterLaunchMore.setVisibility(View.GONE);
            viewHolder.flAfterLaunch.setOnClickListener(null);
        } else {
            viewHolder.imgAfterLaunchMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder4 = viewHolder;
            viewHolder.flAfterLaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder4.flAfterLaunch, position);
                }
            });
        }
        if (TextUtils.isEmpty(wch)) {
            viewHolder.tvAfterLaunch.setVisibility(View.GONE);
            viewHolder.imgAfterLaunchAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvAfterLaunch.setVisibility(View.VISIBLE);
            viewHolder.imgAfterLaunchAdd.setVisibility(View.GONE);
            viewHolder.tvAfterLaunch.setText(wch);
        }
        //晚餐前
        String wfq = item.getWfq();
        int wfqmore = item.getWfqmore();
        if (0 == wfqmore) {
            viewHolder.imgBeforeDinnerMore.setVisibility(View.GONE);
            viewHolder.flBeforeDinner.setOnClickListener(null);
        } else {
            viewHolder.imgBeforeDinnerMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder5 = viewHolder;
            viewHolder.flBeforeDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder5.flBeforeDinner, position);
                }
            });
        }
        if (TextUtils.isEmpty(wfq)) {
            viewHolder.tvBeforeDinner.setVisibility(View.GONE);
            viewHolder.imgBeforeDinnerAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvBeforeDinner.setVisibility(View.VISIBLE);
            viewHolder.imgBeforeDinnerAdd.setVisibility(View.GONE);
            viewHolder.tvBeforeDinner.setText(wfq);
        }
        //晚餐后
        String wfh = item.getWfh();
        int wfhmore = item.getWfhmore();
        if (0 == wfhmore) {
            viewHolder.imgAfterDinnerMore.setVisibility(View.GONE);
            viewHolder.flAfterDinner.setOnClickListener(null);
        } else {
            viewHolder.imgAfterDinnerMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder6 = viewHolder;
            viewHolder.flAfterDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder6.flAfterDinner, position);
                }
            });
        }
        if (TextUtils.isEmpty(wfh)) {
            viewHolder.tvAfterDinner.setVisibility(View.GONE);
            viewHolder.imgAfterDinnerAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvAfterDinner.setVisibility(View.VISIBLE);
            viewHolder.imgAfterDinnerAdd.setVisibility(View.GONE);
            viewHolder.tvAfterDinner.setText(wfh + "");
        }
        //睡前
        String shq = item.getShq();
        int shqmore = item.getShqmore();
        if (0 == shqmore) {
            viewHolder.imgAfterSleepMore.setVisibility(View.GONE);
            viewHolder.flAfterSleep.setOnClickListener(null);
        } else {
            viewHolder.imgAfterSleepMore.setVisibility(View.VISIBLE);
            ViewHolder finalViewHolder7 = viewHolder;
            viewHolder.flAfterSleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAdapterClick(finalViewHolder7.flAfterSleep, position);
                }
            });
        }
        if (TextUtils.isEmpty(shq)) {
            viewHolder.tvAfterSleep.setVisibility(View.GONE);
            viewHolder.imgAfterSleepAdd.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvAfterSleep.setVisibility(View.VISIBLE);
            viewHolder.imgAfterSleepAdd.setVisibility(View.GONE);
            viewHolder.tvAfterSleep.setText(shq);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tvTime;
        FrameLayout flBeforeDawn;
        TextView tvBeforeDawn;
        ImageView imgBeforeDawnMore;
        ImageView imgBeforeDawnAdd;

        FrameLayout flBeforeBreakfast;
        TextView tvBeforeBreakfast;
        ImageView imgBeforeBreakfastMore;
        ImageView imgBeforeBreakfastAdd;

        FrameLayout flAfterTheBreakfast;
        TextView tvAfterTheBreakfast;
        ImageView imgAfterTheBreakfastMore;
        ImageView imgAfterTheBreakfastAdd;

        FrameLayout flBeforeLunch;
        TextView tvBeforeLunch;
        ImageView imgBeforeLunchMore;
        ImageView imgBeforeLunchAdd;

        FrameLayout flAfterLaunch;
        TextView tvAfterLaunch;
        ImageView imgAfterLaunchMore;
        ImageView imgAfterLaunchAdd;

        FrameLayout flBeforeDinner;
        TextView tvBeforeDinner;
        ImageView imgBeforeDinnerMore;
        ImageView imgBeforeDinnerAdd;

        FrameLayout flAfterDinner;
        TextView tvAfterDinner;
        ImageView imgAfterDinnerMore;
        ImageView imgAfterDinnerAdd;

        FrameLayout flAfterSleep;
        TextView tvAfterSleep;
        ImageView imgAfterSleepMore;
        ImageView imgAfterSleepAdd;
    }
}
