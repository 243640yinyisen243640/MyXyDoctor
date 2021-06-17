package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodOxygenListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodPressureListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodSugarMainActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordCheckListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordFoodAndDrinkListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordHeightAndWeightListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordLiverListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordPharmacyListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordSaccharifyListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordSportListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordTemperatureListActivity;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordWeightListActivity;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class HealthRecordGvAdapter extends CommonAdapter {
    private String userId;
    private Context context;

    public HealthRecordGvAdapter(Context context, int layoutId, List datas, String userId) {
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
                imgPic.setImageResource(R.drawable.health_record_blood_sugar);
                tvText.setText("血糖记录");
                break;
            case 1:
                imgPic.setImageResource(R.drawable.health_record_blood_pressure);
                tvText.setText("血压记录");
                break;
            case 2:
                imgPic.setImageResource(R.drawable.health_record_sport);
                tvText.setText("运动记录");
                break;
            case 3:
                imgPic.setImageResource(R.drawable.health_record_food_and_drink);
                tvText.setText("饮食记录");
                break;
            case 4:
                imgPic.setImageResource(R.drawable.health_record_pharmacy);
                tvText.setText("用药记录");
                break;
            case 5:
                imgPic.setImageResource(R.drawable.health_record_saccharify);
                tvText.setText("糖化记录");
                break;
            case 6:
                imgPic.setImageResource(R.drawable.health_record_check);
                tvText.setText("检查记录");
                break;
            case 7:
                imgPic.setImageResource(R.drawable.health_record_height);
                tvText.setText("BMI记录");
                break;
            case 8:
                imgPic.setImageResource(R.drawable.health_record_blood_oxygen);
                tvText.setText("血氧记录");
                break;
            case 9:
                imgPic.setImageResource(R.drawable.health_record_liver);
                tvText.setText("肝病记录");
                break;
            case 10:
                imgPic.setImageResource(R.drawable.health_record_reduce_weight);
                tvText.setText("体重记录");
                break;
            case 11:
                imgPic.setImageResource(R.drawable.temperature_for_record);
                tvText.setText(context.getString(R.string.record_temperature));
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
                    case 2:
                        intent = new Intent(Utils.getApp(), HealthRecordSportListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(Utils.getApp(), HealthRecordFoodAndDrinkListActivity.class);
                        break;
                    case 4:
                        intent = new Intent(Utils.getApp(), HealthRecordPharmacyListActivity.class);
                        break;
                    case 5:
                        intent = new Intent(Utils.getApp(), HealthRecordSaccharifyListActivity.class);
                        break;
                    case 6:
                        intent = new Intent(Utils.getApp(), HealthRecordCheckListActivity.class);
                        break;
                    case 7:
                        intent = new Intent(Utils.getApp(), HealthRecordHeightAndWeightListActivity.class);
                        break;
                    case 8:
                        intent = new Intent(Utils.getApp(), HealthRecordBloodOxygenListActivity.class);
                        break;
                    case 9:
                        intent = new Intent(Utils.getApp(), HealthRecordLiverListActivity.class);
                        break;
                    case 10:
                        intent = new Intent(Utils.getApp(), HealthRecordWeightListActivity.class);
                        break;
                    case 11:
                        intent = new Intent(Utils.getApp(), HealthRecordTemperatureListActivity.class);
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
