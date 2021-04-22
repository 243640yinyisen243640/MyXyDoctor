package com.xy.xydoctor.ui.activity.todo;

import android.os.Bundle;
import android.widget.TextView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.ShowMessageBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  系统消息详情
 * 作者: LYD
 * 创建日期: 2020/12/9 9:48
 */
public class SystemMsgDetailActivity extends BaseActivity {
    @BindView(R.id.tv_sys_title)
    TextView tvSysTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_msg_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("信息内容");
        getDetail();
    }

    private void getDetail() {
        String id = getIntent().getStringExtra("id");
        HashMap map = new HashMap<>();
        map.put("pid", id);
        RxHttp.postForm(XyUrl.GET_UNREAD_SYSTEM_MESSAGE_DETAIL)
                .addAll(map)
                .asResponse(ShowMessageBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ShowMessageBean>() {
                    @Override
                    public void accept(ShowMessageBean showMessage) throws Exception {
                        tvSysTitle.setText(showMessage.getTitle());
                        tvTime.setText(showMessage.getSendtime());
                        tvContent.setText("\u3000\u3000" + showMessage.getContent());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }
}