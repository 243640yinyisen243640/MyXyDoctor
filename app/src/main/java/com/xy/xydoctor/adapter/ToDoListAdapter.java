package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.ToDoListBean;
import com.xy.xydoctor.ui.activity.followupvisit.FollowUpVisitWaitDoListActivity;
import com.xy.xydoctor.ui.activity.todo.ApplyToHospitalListActivity;
import com.xy.xydoctor.ui.activity.todo.NewPatientListActivity;
import com.xy.xydoctor.ui.activity.todo.SystemMsgListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ToDoListAdapter extends CommonAdapter<ToDoListBean> {
    private String type;
    private int userId;

    public ToDoListAdapter(Context context, int layoutId, List<ToDoListBean> datas, String type, int userId) {
        super(context, layoutId, datas);
        this.type = type;
        this.userId = userId;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ToDoListBean item, int position) {
        //系统消息数量
        int xtxx = item.getXtxx();
        //新的患者数量
        int xdhz = item.getXdhz();
        //新的患者名称
        String xdhzname = item.getXdhzname();
        //住院申请数量
        int zyshq = item.getZyshq();
        //住院申请名称
        String zyshqname = item.getZyshqname();
        //随访待办数量
        int follow = item.getFollow();
        //最新随访者名字
        String followname = item.getFollowname();
        TextView tvRedPoint = viewHolder.getView(R.id.tv_red_point);
        TextView tvDesc = viewHolder.getView(R.id.tv_desc);
        if ("homeSign".equals(type)) {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.to_do_img_home_sign);
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.to_do_string_home_sign);
            viewHolder.setImageResource(R.id.img_to_do, imgArray.getResourceId(position, 0));
            viewHolder.setText(R.id.tv_title, stringArray[position]);
            switch (position) {
                case 0:
                    if (xdhz > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(xdhz) + "");
                        String newPatient = String.format(Utils.getApp().getString(R.string.to_do_list_new_patient), xdhzname);
                        tvDesc.setText(newPatient);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无患者请求");
                    }
                    break;
                case 1:
                    if (zyshq > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(zyshq) + "");
                        String admittedToHospital = String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital), zyshqname);
                        tvDesc.setText(admittedToHospital);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无住院申请");
                    }
                    break;
                case 2:
                    if (follow > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(follow) + "");
                        String admittedToHospital = String.format(Utils.getApp().getString(R.string.to_do_list_follow_up_visit_wait_do), followname);
                        tvDesc.setText(admittedToHospital);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无随访待办");
                    }
                    break;
            }
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(Utils.getApp(), NewPatientListActivity.class);
                            break;
                        case 1:
                            intent = new Intent(Utils.getApp(), ApplyToHospitalListActivity.class);
                            break;
                        case 2:
                            intent = new Intent(Utils.getApp(), FollowUpVisitWaitDoListActivity.class);
                            break;
                    }
                    intent.putExtra("userid", userId);
                    intent.putExtra("type", type);
//                    intent.putExtra("docid",)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            });
        } else {
            TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.to_do_img);
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.to_do_string);
            viewHolder.setImageResource(R.id.img_to_do, imgArray.getResourceId(position, 0));
            viewHolder.setText(R.id.tv_title, stringArray[position]);
            switch (position) {
                case 0:
                    if (xtxx > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(xtxx) + "");
                        tvDesc.setText("最新系统通知消息内容");
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无最新通知");
                    }
                    break;
                case 1:
                    if (xdhz > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(xdhz) + "");
                        String newPatient = String.format(Utils.getApp().getString(R.string.to_do_list_new_patient), xdhzname);
                        tvDesc.setText(newPatient);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无患者请求");
                    }
                    break;
                case 2:
                    if (zyshq > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(zyshq) + "");
                        String admittedToHospital = String.format(Utils.getApp().getString(R.string.to_do_list_admitted_to_hospital), zyshqname);
                        tvDesc.setText(admittedToHospital);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无住院申请");
                    }
                    break;
                case 3:
                    if (follow > 0) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        tvRedPoint.setText(isAbove99(follow) + "");
                        String admittedToHospital = String.format(Utils.getApp().getString(R.string.to_do_list_follow_up_visit_wait_do), followname);
                        tvDesc.setText(admittedToHospital);
                    } else {
                        tvRedPoint.setVisibility(View.GONE);
                        tvDesc.setText("暂无随访待办");
                    }
                    break;
            }
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(Utils.getApp(), SystemMsgListActivity.class);
                            break;
                        case 1:
                            intent = new Intent(Utils.getApp(), NewPatientListActivity.class);
                            break;
                        case 2:
                            intent = new Intent(Utils.getApp(), ApplyToHospitalListActivity.class);
                            break;
                        case 3:
                            intent = new Intent(Utils.getApp(), FollowUpVisitWaitDoListActivity.class);
                            break;
                    }
                    intent.putExtra("userid", userId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            });
        }
    }

    private String isAbove99(int count) {
        if (count > 999) {
            return "...";
        }
        return count + "";
    }
}
