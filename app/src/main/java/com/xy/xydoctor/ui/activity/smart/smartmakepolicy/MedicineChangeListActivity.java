package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.widget.ListView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MedicineChangeListAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.MedicineChangeListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.CommonAdapterClickImp;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 药物更换列表
 * 作者: LYD
 * 创建日期: 2019/4/1 14:19
 */
@BindEventBus
public class MedicineChangeListActivity extends BaseEventBusActivity implements CommonAdapterClickImp {
    private static final String TAG = "MedicineChangeActivity";
    @BindView(R.id.lv)
    ListView lv;

    private List<MedicineChangeListBean> data;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_medicine_change_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("更换药物");
        getLvData();
    }

    @Override
    protected void receiveStickyEvent(EventMessage event) {
        super.receiveStickyEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.SEND_GROUP_ID:
                String groupId = event.getMsg();
                SPStaticUtils.put("groupId", groupId);
                break;
        }
    }

    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        map.put("listid", getIntent().getStringExtra("listId"));
        RxHttp.postForm(XyUrl.GET_DRUGS_OTHER)
                .addAll(map)
                .asResponseList(MedicineChangeListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<MedicineChangeListBean>>() {
                    @Override
                    public void accept(List<MedicineChangeListBean> medicineChangeListBeans) throws Exception {
                        data = medicineChangeListBeans;
                        if (data != null && data.size() > 0) {
                            lv.setAdapter(new MedicineChangeListAdapter(getPageContext(), R.layout.item_medicine_select_list, data, MedicineChangeListActivity.this));
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void adapterViewClick(int position) {
        DialogUtils.getInstance().showDialog(getPageContext(), "更换", "确定要更换药物?", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                change(position);
            }
        });
    }

    /**
     * 换药
     *
     * @param position
     */
    private void change(int position) {
        HashMap map = new HashMap<>();
        String pid = SPStaticUtils.getString("pid");
        String groupId = SPStaticUtils.getString("groupId");
        map.put("paid", pid);
        map.put("id", getIntent().getStringExtra("id"));
        map.put("chid", data.get(position).getId());
        map.put("groupid", groupId);

        RxHttp.postForm(XyUrl.DRUGS_CHANGE).addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort(s);
                        //发送
                        EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.CHANGE_MEDICINE));
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
