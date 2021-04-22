package com.xy.xydoctor.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.HealthArchiveGroupLevelOneBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述:type:0 记录用药 1:添加用药
 * 作者: LYD
 * 创建日期: 2019/6/19 17:06
 */
public class PharmacyAddActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private static final String[] dosageNameList = {"请选择剂量单位", "mg", "g", "iu", "ml"};
    @BindView(R.id.tv_pharmacy_name)
    TextView tvPharmacyName;
    @BindView(R.id.et_pharmacy_name)
    EditText etPharmacyName;
    @BindView(R.id.tv_pharmacy_count)
    TextView tvPharmacyCount;
    @BindView(R.id.et_pharmacy_count)
    EditText etPharmacyCount;
    @BindView(R.id.tv_pharmacy_unit)
    TextView tvPharmacyUnit;
    @BindView(R.id.tv_pharmacy_dosage)
    TextView tvPharmacyDosage;
    @BindView(R.id.et_pharmacy_dosage)
    EditText etPharmacyDosage;
    @BindView(R.id.spinner_pharmacy_dosage)
    Spinner spinnerPharmacyDosage;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.img_start_time)
    ImageView imgStartTime;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.img_end_time)
    ImageView imgEndTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.ll_spinner)
    LinearLayout llSpinner;
    private int cardNumber;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pharmacy_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        HealthArchiveGroupLevelOneBean detailBean = (HealthArchiveGroupLevelOneBean) getIntent().getSerializableExtra("detailBean");
        if (detailBean != null) {
            String name = detailBean.getName();
            etPharmacyName.setText(name);
            etPharmacyName.setEnabled(false);
            String center = detailBean.getCenter();
            etPharmacyCount.setText(center);
            etPharmacyCount.setEnabled(false);
            String content = detailBean.getContent();
            etPharmacyDosage.setText(content);
            etPharmacyDosage.setEnabled(false);
            String startTime = detailBean.getStartTime();
            String endTime = detailBean.getEndTime();
            tvStartTime.setText(startTime);
            tvEndTime.setText(endTime);
            getTvMore().setVisibility(View.GONE);
            rlStartTime.setEnabled(false);
            rlEndTime.setEnabled(false);
            llSpinner.setVisibility(View.GONE);
        } else {
            getTvMore().setVisibility(View.VISIBLE);
            getTvMore().setText("保存");
            getTvMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = etPharmacyName.getText().toString().trim();
                    String count = etPharmacyCount.getText().toString().trim();
                    String dosage = etPharmacyDosage.getText().toString().trim();
                    String startTime = tvStartTime.getText().toString().trim();
                    String endTime = tvEndTime.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        ToastUtils.showShort("请输入药品名称");
                        return;
                    }
                    if (TextUtils.isEmpty(count)) {
                        ToastUtils.showShort("请输入服药次数");
                        return;
                    }
                    if (TextUtils.isEmpty(dosage)) {
                        ToastUtils.showShort("请输入服药剂量");
                        return;
                    }
                    if (cardNumber == 0) {
                        ToastUtils.showShort("请选择剂量单位");
                        return;
                    }
                    //                    if (TextUtils.isEmpty(startTime)) {
                    //                        ToastUtils.showShort("请选择开始用药时间");
                    //                        return;
                    //                    }
                    //                    if (TextUtils.isEmpty(endTime)) {
                    //                        ToastUtils.showShort("请选择结束用药时间");
                    //                        return;
                    //                    }
                    saveData(name, count, dosage, startTime, endTime);
                }
            });
            rlStartTime.setEnabled(true);
            rlEndTime.setEnabled(true);
            llSpinner.setVisibility(View.VISIBLE);
        }
        if ("0".equals(type)) {
            setTitle("记录用药");
        } else {
            setTitle("添加用药");
        }
        initView();
        setTime();
    }


    private void saveData(String name, String count, String dosage, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        String userid = getIntent().getStringExtra("userid");
        map.put("uid", userid);
        map.put("times", count);
        map.put("drugname", name);
        map.put("remark", 2);
        map.put("dosage", dosage + dosageNameList[cardNumber]);
        map.put("starttime", startTime);
        map.put("endtime", endTime);
        String type = getIntent().getStringExtra("type");
        String url;
        if ("0".equals(type)) {
            url = XyUrl.ADD_PHARMACY;
        } else {
            url = XyUrl.ADD_PHARMACY_RECORD;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        ToastUtils.showShort("保存成功");
                        finish();
                        if ("1".equals(type)) {
                            //刷新
                            EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.ADD_MEDICINE));
                        } else {
                            EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.PHARMACY_RECORD_ADD));
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initView() {
        spinnerPharmacyDosage.setOnItemSelectedListener(this);
        //spinner选项
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_spinner_pharmacy, dosageNameList);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown_pharmacy);
        spinnerPharmacyDosage.setAdapter(adapter);
        //        String type = getIntent().getStringExtra("type");
        //        if ("0".equals(type)) {
        //            rlStartTime.setVisibility(View.VISIBLE);
        //            rlEndTime.setVisibility(View.VISIBLE);
        //        } else {
        //            rlStartTime.setVisibility(View.GONE);
        //            rlEndTime.setVisibility(View.GONE);
        //        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardNumber = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void setTime() {
        SimpleDateFormat allFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), allFormat);
        //tvStartTime.setText(allTimeString);
    }

    @OnClick({R.id.rl_start_time, R.id.rl_end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_start_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvStartTime.setText(content);
                    }
                });
                break;
            case R.id.rl_end_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvEndTime.setText(content);
                    }
                });
                break;
        }
    }
}
