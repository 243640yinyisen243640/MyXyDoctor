package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MedicineSelectListAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MedicineSelectListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 药物选择列表
 * 作者: LYD
 * 创建日期: 2019/3/30 11:23
 */
public class MedicineSelectListActivity extends BaseActivity {
    @BindView(R.id.lv_medicine_select)
    ListView lvMedicineSelect;
    @BindView(R.id.tv_msg)
    TextView tvMsg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_medicine_select_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("药物选择");
        getLvData();
    }


    private void getLvData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        RxHttp.postForm(XyUrl.GET_DRUGS_GROUP)
                .addAll(map)
                .asResponse(MedicineSelectListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MedicineSelectListBean>() {
                    @Override
                    public void accept(MedicineSelectListBean data) throws Exception {
                        //MedicineSelectListBean data = (MedicineSelectListBean) msg.obj;
                        String groupTitle = data.getGrouptitle();
                        tvMsg.setText(groupTitle);
                        List<MedicineSelectListBean.GroupsBean> groupList = data.getGroups();
                        if (groupList != null && groupList.size() > 0) {
                            MedicineSelectListAdapter adapter = new MedicineSelectListAdapter(getPageContext(), R.layout.item_medicine_select_list, groupList);
                            lvMedicineSelect.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

}
