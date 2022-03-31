package com.xy.xydoctor.ui.fragment.patientcount;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.lyd.baselib.util.TurnsUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.wei.android.lib.colorview.view.ColorRelativeLayout;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientCountMainSugarBloodAdapter;
import com.xy.xydoctor.bean.PatientCountListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 患者情况统计之血糖
 * style: 1高2低3正常 4未测量  5自定义（默认1高）
 * time: 1今天 2昨日 3三天 7七天 30 30天（默认1）查询血压时传
 * 作者: LYD
 * 创建日期: 2020/11/10 15:27
 */
public class PatientCountBloodSugarMainFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    @BindView(R.id.tv_bottom_select)
    TextView tvBottomSelect;
    @BindView(R.id.rl_show_bottom_select)
    ColorRelativeLayout rvShowBottomSelect;

    @BindView(R.id.et_min)
    ColorEditText etMin;
    @BindView(R.id.et_max)
    ColorEditText etMax;
    @BindView(R.id.ll_input)
    LinearLayout llInput;

    @BindView(R.id.tv_filt)
    ColorTextView tvFilt;

    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;

    private String beginTime = "";
    private String endTime = "";
    private String beginSugar = "";
    private String endSugar = "";
    private String style = "1";
    private CallBackValue callBackValue;
    private PatientCountMainSugarBloodAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_count_blood_sugar_main;
    }

    @Override
    protected void init(View rootView) {
        setTime();
        callBackValue.sendValue(style, beginTime, endTime, beginSugar, endSugar);
        getList(style, beginTime, endTime, beginSugar, endSugar);
    }

    private void setTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvStartTime.setText(nowString);
        tvEndTime.setText(nowString);
        beginTime = nowString;
        endTime = nowString;
    }

    /**
     * 获取血糖列表
     *
     * @param style
     * @param beginTime
     * @param endTime
     * @param beginSugar
     * @param endSugar
     */
    private void getList(String style, String beginTime, String endTime, String beginSugar, String endSugar) {
        Log.i("yys","style==="+style);
        HashMap<String, Object> map = new HashMap<>();
        //默认血糖
        //map.put("type", "2");
        map.put("style", style);
        map.put("starttime", beginTime);
        map.put("endtime", endTime);
        map.put("startSugar", beginSugar);
        map.put("endSugar", endSugar);
        RxHttp.postForm(XyUrl.GET_APPLY_TO_HOSPITAL_LIST)
                .addAll(map)
                .asResponseList(PatientCountListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<PatientCountListBean>>() {
                    @Override
                    public void accept(List<PatientCountListBean> list) throws Exception {
                        llEmpty.setVisibility(View.GONE);
                        rvList.setVisibility(View.VISIBLE);
                        rvList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                        if ("4".equals(style)) {
                            adapter = new PatientCountMainSugarBloodAdapter(style, R.layout.item_patient_count_main_sugar_blood_no_value, list);
                        } else {
                            adapter = new PatientCountMainSugarBloodAdapter(style, R.layout.item_patient_count_main_sugar_blood, list);
                        }
                        rvList.setAdapter(adapter);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        ToastUtils.cancel();
                        llEmpty.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.rl_show_bottom_select, R.id.tv_filt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        toJudgeBeginTime(content);
                    }
                });
                clearFilterCondition();
                break;
            case R.id.tv_end_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        toJudgeEndTime(content);
                    }
                });
                clearFilterCondition();
                break;
            //展示底部选择
            case R.id.rl_show_bottom_select:
                showBottomSelect();
                clearFilterCondition();
                break;
            //开始筛选
            case R.id.tv_filt:
                beginSugar = etMin.getText().toString().trim();
                endSugar = etMax.getText().toString().trim();
                double doubleBegin = TurnsUtils.getDouble(beginSugar, 0);
                double doubleEnd = TurnsUtils.getDouble(endSugar, 0);
                if (doubleBegin > doubleEnd) {
                    ToastUtils.showShort("结束值应大于起始值");
                    return;
                }
                //保存开始结束血糖
                callBackValue.sendValue(style, beginTime, endTime, beginSugar, endSugar);
                getList(style, beginTime, endTime, beginSugar, endSugar);
                KeyboardUtils.hideSoftInput(getActivity());
                break;
        }
    }


    /**
     * 清除筛选条件
     */
    private void clearFilterCondition() {
        beginSugar = "";
        endSugar = "";
    }


    /**
     * 判断开始时间
     *
     * @param content
     */
    private void toJudgeBeginTime(String content) {
        endTime = tvEndTime.getText().toString().trim();
        if (endTime.compareTo(content) < 0) {
            ToastUtils.showShort("开始时间" + "大于" + "结束时间");
            return;
        }
        tvStartTime.setText(content);
        beginTime = tvStartTime.getText().toString().trim();
        //保存开始结束时间
        callBackValue.sendValue(style, beginTime, endTime, beginSugar, endSugar);
        getList(style, beginTime, endTime, beginSugar, endSugar);
    }

    /**
     * 判断结束时间
     *
     * @param content
     */
    private void toJudgeEndTime(String content) {
        beginTime = tvStartTime.getText().toString().trim();
        if (content.compareTo(beginTime) < 0) {
            ToastUtils.showShort("结束时间" + "小于" + "开始时间");
            return;
        }
        tvEndTime.setText(content);
        endTime = tvEndTime.getText().toString().trim();
        //保存开始结束时间
        callBackValue.sendValue(style, beginTime, endTime, beginSugar, endSugar);
        getList(style, beginTime, endTime, beginSugar, endSugar);
    }


    private void showBottomSelect() {
        String[] sexStr = getResources().getStringArray(R.array.patient_count_bottom_select);
        List<String> listStr = Arrays.asList(sexStr);
        PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
            @Override
            public void execEvent(String content, int position) {
                tvBottomSelect.setText(content);
                style = position + 1 + "";
                //保存style
                callBackValue.sendValue(style, beginTime, endTime, beginSugar, endSugar);
                if ("5".equals(style)) {
                    llInput.setVisibility(View.VISIBLE);
                    tvFilt.setVisibility(View.VISIBLE);
                } else {
                    llInput.setVisibility(View.GONE);
                    tvFilt.setVisibility(View.GONE);
                    getList(style, beginTime, endTime, beginSugar, endSugar);
                }
            }
        }, listStr);
    }

    @Override
    public void onAttach(@NonNull Activity context) {
        super.onAttach(context);
        //当前fragment从activity重写了回调接口得到接口的实例化对象
        callBackValue = (CallBackValue) getActivity();
    }

    //定义一个回调接口
    public interface CallBackValue {
        void sendValue(String style, String beginTime, String endTime, String beginSugar, String endSugar);
    }
}
