package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.MassHistoryBean;
import com.xy.xydoctor.bean.PatientEducationHistoryListBean;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgAffiliatedPersonActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientEducationHistoryAdapter extends CommonAdapter<PatientEducationHistoryListBean.DataBean> {
    public PatientEducationHistoryAdapter(Context context, int layoutId, List<PatientEducationHistoryListBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PatientEducationHistoryListBean.DataBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getAddtime());
        String count = String.format(Utils.getApp().getString(R.string.msg_count), item.getCount() + "");
        TextView tvPersonName = viewHolder.getView(R.id.tv_person_name);
        TextView tvMorePersonName = viewHolder.getView(R.id.tv_more_person_name);
        tvPersonName.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = tvPersonName.getLineCount();
                if (lineCount > 1) {
                    tvPersonName.setMaxLines(1);
                    tvMorePersonName.setVisibility(View.VISIBLE);
                } else {
                    tvMorePersonName.setVisibility(View.GONE);
                }
            }
        });
        List<PatientEducationHistoryListBean.DataBean.UsersBean> user = item.getUsers();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < user.size(); i++) {
            String nickname = user.get(i).getNickname();
            stringBuffer.append(nickname);
            stringBuffer.append("、");
        }
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);//截取最后,
        tvPersonName.setText(substring);
        StringBuilder stringBuilder = new StringBuilder();

        List<String> titles = item.getTitles();
        for (int i = 0; i < titles.size(); i++) {
            stringBuilder.append(titles.get(i));
            stringBuilder.append("\n");
        }
        viewHolder.setText(R.id.tv_remind, stringBuilder.toString());
        viewHolder.setText(R.id.tv_send, count);

        List<MassHistoryBean.UserBean> sendUser = new ArrayList<>();
        for (int i = 0; i < user.size(); i++) {
            MassHistoryBean.UserBean userBean = new MassHistoryBean.UserBean();
            userBean.setAge(user.get(i).getAge());
            userBean.setSex(user.get(i).getSex());
            userBean.setNickname(user.get(i).getNickname());
            userBean.setUsername(user.get(i).getUsername());
            userBean.setPicture(user.get(i).getPicture());
            userBean.setUserid(user.get(i).getUserid());
            userBean.setUserId(user.get(i).getUserId());
            sendUser.add(userBean);
        }
        tvMorePersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MassMsgAffiliatedPersonActivity.class);
                intent.putExtra("listUser", (Serializable) sendUser);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
