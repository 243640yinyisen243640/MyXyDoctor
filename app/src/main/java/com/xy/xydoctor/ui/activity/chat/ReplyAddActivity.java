package com.xy.xydoctor.ui.activity.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 添加回复
 * 作者: LYD
 * 创建日期: 2019/6/12 16:53
 */
public class ReplyAddActivity extends BaseActivity {

    @BindView(R.id.et_reply)
    ColorEditText etReply;

    /**
     * 保存建议
     *
     * @param reply
     */
    private void toSaveReply(String reply) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", reply);
        RxHttp.postForm(XyUrl.ADD_FAST_REPLY)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("添加成功");//跳转消息历史记录
                        finish();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.ADD_REPLY));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("编辑建议");
        getTvMore().setText("保存");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = etReply.getText().toString().trim();
                if (TextUtils.isEmpty(reply)) {
                    ToastUtils.showShort("请输入建议");
                    return;
                }
                toSaveReply(reply);
            }
        });
    }
}
