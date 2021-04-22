package com.xy.xydoctor.ui.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FastReplyAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.FastReplyListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:   快捷回复
 * 作者: LYD
 * 创建日期: 2019/6/12 16:11
 */
@BindEventBus
public class FastReplyActivity extends BaseEventBusActivity implements AdapterClickImp {
    @BindView(R.id.lv_fast_reply)
    ListView lvFastReply;
    private List<FastReplyListBean> list;


    private void getFastReplyList() {
        HashMap<String, String> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_FAST_REPLY_LIST)
                .addAll(map)
                .asResponseList(FastReplyListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FastReplyListBean>>() {
                    @Override
                    public void accept(List<FastReplyListBean> listBean) throws Exception {
                        list = listBean;
                        if (list != null && list.size() > 0) {
                            lvFastReply.setAdapter(new FastReplyAdapter(getPageContext(), R.layout.item_fast_reply, list, FastReplyActivity.this));
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onAdapterClick(View view, int position) {
        String content = list.get(position).getContent();
        Intent intent = new Intent();
        intent.putExtra("content", content);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.ADD_REPLY:
                getFastReplyList();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fast_reply;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("快捷回复");
        getTvMore().setText("添加");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getPageContext(), ReplyAddActivity.class));
            }
        });
        getFastReplyList();
    }
}
