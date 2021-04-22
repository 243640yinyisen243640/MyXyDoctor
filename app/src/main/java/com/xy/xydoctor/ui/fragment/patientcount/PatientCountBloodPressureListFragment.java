package com.xy.xydoctor.ui.fragment.patientcount;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientCountListAdapter;
import com.xy.xydoctor.adapter.PatientCountNoValueAdapter;
import com.xy.xydoctor.base.fragment.LazyLoadBaseFragment;
import com.xy.xydoctor.bean.PatientCountListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgMainActivity;
import com.xy.xydoctor.view.popup.PatientCountSelectTimePopup;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 血糖血压列表Fragment
 * 作者: LYD
 * 创建日期: 2019/9/26 15:46
 */
public class PatientCountBloodPressureListFragment extends LazyLoadBaseFragment implements View.OnClickListener {
    private static final String TAG = "PatientCountBloodSugarAndBloodPressureListFragment";
    @BindView(R.id.tv_remind)
    ColorTextView tvRemind;
    @BindView(R.id.ll_change_three)
    RelativeLayout llChangeThree;
    @BindView(R.id.tv_change_three)
    TextView tvChangeThree;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private PatientCountSelectTimePopup popup;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_count_blood_sugar_and_blood_pressure_list;
    }


    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        //初始化Popup
        initPopup();
        //获取数据
        int listPosition = getArguments().getInt("listPosition");
        getList(listPosition, "1");
    }

    /**
     * 初始化Popup
     */
    private void initPopup() {
        popup = new PatientCountSelectTimePopup(getActivity());
        popup.findViewById(R.id.ll_today).setOnClickListener(this);
        popup.findViewById(R.id.ll_seven).setOnClickListener(this);
        popup.findViewById(R.id.ll_thirty).setOnClickListener(this);
    }


    /**
     * 获取列表
     *
     * @param listPosition
     */
    private void getList(int listPosition, String time) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "1");
        map.put("style", listPosition + 1 + "");
        map.put("time", time);
        RxHttp.postForm(XyUrl.GET_APPLY_TO_HOSPITAL_LIST)
                .addAll(map)
                .asResponseList(PatientCountListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<PatientCountListBean>>() {
                    @Override
                    public void accept(List<PatientCountListBean> data) throws Exception {
                        llEmpty.setVisibility(View.GONE);
                        lvList.setVisibility(View.VISIBLE);
                        if (data != null && data.size() > 0) {
                            setListAdapter(data, listPosition);
                        }
                        popup.dismiss();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.cancel();
                        llEmpty.setVisibility(View.VISIBLE);
                        lvList.setVisibility(View.GONE);
                        popup.dismiss();
                    }
                });
    }

    /**
     * 设置Adapter
     *
     * @param data
     * @param listPosition
     */
    private void setListAdapter(List<PatientCountListBean> data, int listPosition) {
        if (3 == listPosition) {
            //未测
            lvList.setAdapter(new PatientCountNoValueAdapter(getPageContext(), R.layout.item_patient_count_no_value, data));
        } else {
            lvList.setAdapter(new PatientCountListAdapter(getPageContext(), R.layout.item_patient_count, data, listPosition + ""));
        }
    }

    @OnClick({R.id.tv_remind, R.id.ll_change_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_remind:
                toRemind();
                break;
            case R.id.ll_change_three:
                popup.showPopupWindow(llChangeThree);
                break;
        }
    }


    /**
     * 去提醒
     */
    private void toRemind() {
        int listPosition = getArguments().getInt("listPosition");
        String changeThreeText = tvChangeThree.getText().toString().trim();
        String time = "";
        switch (changeThreeText) {
            case "今日":
                time = "1";
                break;
            case "7天":
                time = "7";
                break;
            case "30天":
                time = "30";
                break;
        }
        Intent intent = new Intent(getActivity(), MassMsgMainActivity.class);
        intent.putExtra("type", "1");
        //对应接口的type
        intent.putExtra("mainPosition", 1);
        //对应接口的style
        intent.putExtra("listPosition", listPosition);
        //查询血压传
        intent.putExtra("time", time);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int listPosition = getArguments().getInt("listPosition");
        switch (v.getId()) {
            case R.id.ll_today:
                getList(listPosition, "1");
                tvChangeThree.setText("今日");
                break;
            case R.id.ll_seven:
                getList(listPosition, "7");
                tvChangeThree.setText("7天");
                break;
            case R.id.ll_thirty:
                getList(listPosition, "30");
                tvChangeThree.setText("30天");
                break;
        }
    }
}
