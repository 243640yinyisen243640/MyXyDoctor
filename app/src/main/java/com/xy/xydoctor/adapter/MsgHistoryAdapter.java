package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.MassHistoryBean;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgAffiliatedPersonActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.List;

public class MsgHistoryAdapter extends CommonAdapter<MassHistoryBean> {
    public MsgHistoryAdapter(Context context, int layoutId, List<MassHistoryBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MassHistoryBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getTime());
        String count = String.format(Utils.getApp().getString(R.string.msg_count), item.getNum() + "");
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
        //患者数量居然为空,这数据是怎么出来的???
        List<MassHistoryBean.UserBean> user = item.getUser();
        if (user != null && user.size() > 0) {
            StringBuilder stringBuffer = new StringBuilder();
            for (int i = 0; i < user.size(); i++) {
                String nickname = user.get(i).getNickname();
                stringBuffer.append(nickname);
                stringBuffer.append("、");
            }
            //截取最后,
            String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            tvPersonName.setText(substring);
        } else {
            tvPersonName.setText("");
        }
        viewHolder.setText(R.id.tv_remind, item.getContent());
        viewHolder.setText(R.id.tv_send, count);
        tvMorePersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MassMsgAffiliatedPersonActivity.class);
                intent.putExtra("listUser", (Serializable) user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
