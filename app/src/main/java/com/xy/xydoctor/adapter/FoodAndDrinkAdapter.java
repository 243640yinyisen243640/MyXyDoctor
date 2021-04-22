package com.xy.xydoctor.adapter;

import android.content.Context;
import android.widget.ListView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FoodAndDrinkBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FoodAndDrinkAdapter extends CommonAdapter<FoodAndDrinkBean> {
    public FoodAndDrinkAdapter(Context context, int layoutId, List<FoodAndDrinkBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FoodAndDrinkBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getDatetime());
        switch (item.getCategory()) {
            case "1":
                viewHolder.setText(R.id.tv_type, "早餐");
                break;
            case "2":
                viewHolder.setText(R.id.tv_type, "午餐");
                break;
            case "3":
                viewHolder.setText(R.id.tv_type, "晚餐");
                break;
            case "4":
                viewHolder.setText(R.id.tv_type, "加餐");
                break;
        }
        ListView listView = viewHolder.getView(R.id.listView_recorderFood);
        List<FoodAndDrinkBean.FoodsBean> list = item.getFoods();
        CommonAdapter adapter = new CommonAdapter<FoodAndDrinkBean.FoodsBean>(Utils.getApp(), R.layout.item_food_recoder_listview, list) {
            @Override
            protected void convert(ViewHolder viewHolder, FoodAndDrinkBean.FoodsBean item, int position) {
                viewHolder.setText(R.id.tv_name, item.getFoodname());
                viewHolder.setText(R.id.tv_count, item.getDosage() + "g");
                viewHolder.setText(R.id.tv_listItem_foodKcal, "100");
            }
        };
        listView.setAdapter(adapter);
    }
}
