package com.xy.xydoctor.ui.activity.director;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientGroupLevelAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.bean.PatientGroupLevelOneBean;
import com.xy.xydoctor.bean.PatientGroupLevelZeroBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  患者分组
 * 作者: LYD
 * 创建日期: 2020/5/18 14:18
 */
public class PatientGroupListActivity extends BaseActivity {
    @BindView(R.id.rv_patient_group)
    RecyclerView rvPatientGroup;
    @BindView(R.id.srl_patient_group)
    SmartRefreshLayout srlPatientGroup;

    ArrayList<MultiItemEntity> res;
    List<PatientGroupLevelZeroBean> lv0 = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_group_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        getPatientList();
    }


    private void getPatientList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", getIntent().getIntExtra("userid", 0));
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> myTreatPlanBeans) throws Exception {
                        List<GroupListBean> data = myTreatPlanBeans;
                        if (data != null) {
                            addData(data);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void addData(List<GroupListBean> data) {
        res = new ArrayList<>();
        lv0.clear();
        for (int i = 0; i < data.size(); i++) {
            GroupListBean groupListBean = data.get(i);
            lv0.add(new PatientGroupLevelZeroBean(groupListBean.getGname(), groupListBean.getGid()));
            List<GroupListBean.GroupersBean> groupersList = groupListBean.getGroupers();
            for (int j = 0; j < groupersList.size(); j++) {
                GroupListBean.GroupersBean groupersBean = groupersList.get(j);
                lv0.get(i).addSubItem(new PatientGroupLevelOneBean(groupersBean.getUserid(), groupersBean.getPicture(), groupersBean.getNickname(), groupersBean.getSex(), groupersBean.getAge(), groupersBean.getUsername(), groupersBean.getDiabeteslei() + ""));
            }
        }
        for (int j = 0; j < lv0.size(); j++) {
            res.add(lv0.get(j));
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getPageContext());
        rvPatientGroup.setAdapter(new PatientGroupLevelAdapter(res, getPageContext()));
        rvPatientGroup.setLayoutManager(manager);
    }
}
