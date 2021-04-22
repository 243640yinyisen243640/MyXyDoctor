package com.xy.xydoctor.ui.activity.smart.smartanalyse;

import android.os.Bundle;
import android.widget.ListView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.AnalyseMonthListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 智能分析月份列表
 * 作者: LYD
 * 创建日期: 2019/4/17 9:38
 */

public class AnalyseMonthListActivity extends BaseActivity {
    private static final int GET_LV_DATA = 10010;
    @BindView(R.id.lv_month)
    ListView lvMonth;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analyse_month_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case "0":
                setTitle("血压分析报告");
                break;
            case "1":
                setTitle("血糖分析报告");
                break;
        }
        //setTitle("分析报告");
        getLvData();
    }

    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("uid", getIntent().getStringExtra("userid"));
        RxHttp.postForm(XyUrl.GET_PORT_PERSONAL_MONTHLIST)
                .addAll(map)
                .asResponseList(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        List<String> listData = strings;
                        if (listData != null && listData.size() > 0) {
                            String type = getIntent().getStringExtra("type");
                            String userid = getIntent().getStringExtra("userid");
                            AnalyseMonthListAdapter adapter = new AnalyseMonthListAdapter(getPageContext(), R.layout.item_analyse_month, listData, type, userid);
                            lvMonth.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


}
