package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodPressureListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodSugarMainActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
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
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishToActivity(PatientInfoActivity.class, false);
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(Utils.getApp(), HealthRecordBloodSugarMainActivity.class);
                        break;
                    case 1:
                        intent = new Intent(Utils.getApp(), HealthRecordBloodPressureListActivity.class);
                        break;

                    default:
                        break;
                }
                intent.putExtra("userid", userId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
