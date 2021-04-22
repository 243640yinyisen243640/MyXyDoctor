package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MedicineSelectDetailListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.MedicineSelectDetailListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 药物选择详情
 * 作者: LYD
 * 创建日期: 2019/3/30 16:20
 */
@BindEventBus
public class MedicineSelectDetailListActivity extends BaseEventBusActivity {
    private static final String TAG = "LYD";
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_msg)
    TextView tvTitle;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_medicine_select_detail_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("药物选择");
        getLvData();
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.CHANGE_MEDICINE:
                getLvData();
                break;
        }
    }

    private void getLvData() {
        HashMap map = new HashMap<>();
        String pid = SPStaticUtils.getString("pid");
        map.put("id", getIntent().getStringExtra("id"));
        map.put("paid", pid);
        RxHttp.postForm(XyUrl.GET_DRUGS_GROUP_DETAIL)
                .addAll(map)
                .asResponse(MedicineSelectDetailListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MedicineSelectDetailListBean>() {
                    @Override
                    public void accept(MedicineSelectDetailListBean data) throws Exception {
                        //MedicineSelectDetailListBean data = (MedicineSelectDetailListBean) msg.obj;
                        String groupTitle = data.getDrugtitle();
                        tvTitle.setText(groupTitle);
                        List<MedicineSelectDetailListBean.DrugsBean> drugList = data.getDrugs();
                        if (drugList != null && drugList.size() > 0) {
                            MedicineSelectDetailListAdapter adapter = new MedicineSelectDetailListAdapter(getPageContext(), R.layout.item_medicine_select_list, drugList);
                            lv.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
