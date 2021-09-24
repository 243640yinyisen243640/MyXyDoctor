package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.community_management.CommunitySugarOrPressureListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FollowManagementGvAdapter extends CommonAdapter {
    private String userId;
    private Context context;

    public FollowManagementGvAdapter(Context context, int layoutId, List datas, String userId) {
        super(context, layoutId, datas);
        this.userId = userId;
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        ImageView imgPic = viewHolder.getView(R.id.img_pic);
        TextView tvText = viewHolder.getView(R.id.tv_text);
        //设置
        switch (position) {
            case 0:
                imgPic.setImageResource(R.drawable.user_info_blood_sugar_follow_up);
                tvText.setText(context.getString(R.string.user_info_blood_sugar_follow_up));
                break;
            case 1:
                imgPic.setImageResource(R.drawable.user_info_blood_pressure_follow_up);
                tvText.setText(context.getString(R.string.user_info_blood_pressure));
                break;
            default:
                break;
        }
        //跳转
        viewHolder.getConvertView().setOnClickListener(v -> {
            Intent intent = null;
            /**
             * 1血糖2血压
             */
            switch (position) {
                case 0:
                    intent = new Intent(context, CommunitySugarOrPressureListActivity.class).putExtra("userid", userId).putExtra("type", "1");
                    break;
                case 1:
                    intent = new Intent(context, CommunitySugarOrPressureListActivity.class).putExtra("userid", userId).putExtra("type", "2");
                    break;
                default:
                    break;
            }

           context.startActivity(intent);
        });
    }
}
