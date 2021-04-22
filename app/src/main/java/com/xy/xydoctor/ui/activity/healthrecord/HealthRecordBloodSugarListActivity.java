package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SugarListAdapter;
import com.xy.xydoctor.bean.SugarListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 每个时间点血糖测量详情
 * 作者: LYD
 * 创建日期: 2019/6/8 10:03
 */
public class HealthRecordBloodSugarListActivity extends BaseHideLineActivity {
    @BindView(R.id.tv_lv_title)
    TextView tvLvTitle;
    @BindView(R.id.lv_sugar_list)
    ListView lvSugarList;


    /**
     * 获取数据
     *
     * @param userid
     * @param time
     * @param type
     */
    private void getSugarList(String userid, String time, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("time", time);
        map.put("type", type);
        RxHttp.postForm(XyUrl.GET_SUGAR_LIST)
                .addAll(map)
                .asResponseList(SugarListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<SugarListBean>>() {
                    @Override
                    public void accept(List<SugarListBean> data) throws Exception {
                        if (data != null && data.size() > 0) {
                            lvSugarList.setAdapter(new SugarListAdapter(getPageContext(), R.layout.item_sugar_list, data));
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_blood_sugar_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String userid = getIntent().getStringExtra("userid");
        String time = getIntent().getStringExtra("time");
        String type = getIntent().getStringExtra("type");
        setTitle(time);
        String sendType = "";
        switch (type) {
            case "凌晨":
                sendType = "8";
                break;
            case "早餐空腹":
                sendType = "1";
                break;
            case "早餐后":
                sendType = "2";
                break;
            case "午餐前":
                sendType = "3";
                break;
            case "午餐后":
                sendType = "4";
                break;
            case "晚餐前":
                sendType = "5";
                break;
            case "晚餐后":
                sendType = "6";
                break;
            case "睡前":
                sendType = "7";
                break;
        }
        tvLvTitle.setText(type);//时间点 凌晨8 后边1到7
        getSugarList(userid, time, sendType);
    }
}
