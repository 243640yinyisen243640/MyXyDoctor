package com.xy.xydoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.SystemMessageListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.todo.SystemMsgDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

public class SystemMessageListAdapter extends CommonAdapter<SystemMessageListBean> {

    public SystemMessageListAdapter(Context context, int layoutId, List<SystemMessageListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SystemMessageListBean item, int position) {
        viewHolder.setText(R.id.tv_title, item.getTitle());
        viewHolder.setText(R.id.tv_time, item.getAddtime());
        viewHolder.setText(R.id.tv_content, item.getContent());
        ColorTextView tvRedPoint = viewHolder.getView(R.id.tv_red_point);
        //1已读      2未读
        int isread = item.getIsread();
        if (1 == isread) {
            tvRedPoint.setVisibility(View.GONE);
        } else {
            tvRedPoint.setVisibility(View.VISIBLE);
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SystemMsgDetailActivity.class);
                intent.putExtra("id", item.getPid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                //本地更改消息状态为已读
                item.setIsread(1);
                notifyDataSetChanged();
                //接口更改消息状态为已读
                changeIsRead(item.getPid(), v);
            }
        });
    }

    private void changeIsRead(int pid, View v) {
        HashMap map = new HashMap<>();
        map.put("pid", pid);
        RxHttp.postForm(XyUrl.GET_PORT_MESSAGE_EDIT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(v))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String updateBean) throws Exception {
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.APPLY_TO_HOSPITAL));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
