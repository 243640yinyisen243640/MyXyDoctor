package com.xy.xydoctor.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyd.baselib.base.fragment.BaseFragment;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SevenBottomAdapter;
import com.xy.xydoctor.adapter.ThirtyBottomAdapter;
import com.xy.xydoctor.adapter.ThirtyBottomSearchAdapter;
import com.xy.xydoctor.bean.SevenAndThirtyBloodSugarListBean;
import com.xy.xydoctor.bean.SugarSearchBean;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.xy.xydoctor.imp.AdapterClickSearchImp;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodSugarListActivity;
import com.xy.xydoctor.utils.PickerUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 7天和30天血糖记录
 * 作者: LYD
 * 创建日期: 2019/6/5 20:27
 */
public class SevenAndThirtyBloodSugarListFragment extends BaseFragment implements AdapterClickImp, AdapterClickSearchImp {
    private static final String TAG = "SevenBloodSugarListFragment";
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.tv_normal)
    TextView tvNormal;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.tv_highest)
    TextView tvHighest;
    @BindView(R.id.tv_average)
    TextView tvAverage;
    @BindView(R.id.tv_lowest)
    TextView tvLowest;
    @BindView(R.id.rv_sugar_list)
    RecyclerView rvSugarList;
    @BindView(R.id.ll_shadow)
    LinearLayout llShadow;

    private List<SevenAndThirtyBloodSugarListBean.WeekBean.InfoBean> weekInfoList;
    private List<SevenAndThirtyBloodSugarListBean.MonthBean.InfoBeanX> monthInfoList;
    private List<SugarSearchBean.SearchBean.InfoBean> searchList;


    @Override

    protected int getLayoutId() {
        return R.layout.fragment_seven_and_thirty_blood_sugar_list;
    }

    @Override
    protected void init(View rootView) {
        String type = getArguments().getString("type");
        setSevenAndThirty(type);
    }


    /**
     * 设置数据
     *
     * @param type
     */
    private void setSevenAndThirty(String type) {
        setTop(type);
        setSecondLv(type);
    }

    /**
     * 记录
     *
     * @param type
     */
    private void setSecondLv(String type) {
        SevenAndThirtyBloodSugarListBean bean = (SevenAndThirtyBloodSugarListBean) getArguments().getSerializable("bean");
        if ("0".equals(type)) {
            weekInfoList = bean.getWeek().getInfo();
            rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvSugarList.setAdapter(new SevenBottomAdapter(weekInfoList, getPageContext()));
        } else {
            monthInfoList = bean.getMonth().getInfo();
            rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
            rvSugarList.setAdapter(new ThirtyBottomAdapter(monthInfoList, getPageContext()));
        }
    }


    /**
     * 设置顶部
     *
     * @param type
     */
    private void setTop(String type) {
        SevenAndThirtyBloodSugarListBean bean = (SevenAndThirtyBloodSugarListBean) getArguments().getSerializable("bean");
        if ("0".equals(type)) {
            SevenAndThirtyBloodSugarListBean.WeekBean week = bean.getWeek();
            String starttime = week.getStarttime();
            String endtime = week.getEndtime();
            int high = week.getXtpg();
            int normal = week.getXtzc();
            int low = week.getXtpd();
            double highest = week.getZgxt();
            double average = week.getPjxt();
            double lowest = week.getZdxt();
            tvTimeStart.setText(starttime);
            tvTimeEnd.setText(endtime);
            tvHigh.setText(high + "次");
            tvNormal.setText(normal + "次");
            tvLow.setText(low + "次");
            tvHighest.setText(highest + "");
            tvAverage.setText(average + "");
            tvLowest.setText(lowest + "");
        } else {
            SevenAndThirtyBloodSugarListBean.MonthBean month = bean.getMonth();
            String starttime = month.getStarttime();
            String endtime = month.getEndtime();
            int high = month.getXtpg();
            int normal = month.getXtzc();
            int low = month.getXtpd();
            double highest = month.getZgxt();
            double average = month.getPjxt();
            double lowest = month.getZdxt();
            tvTimeStart.setText(starttime);
            tvTimeEnd.setText(endtime);
            tvHigh.setText(high + "次");
            tvNormal.setText(normal + "次");
            tvLow.setText(low + "次");
            tvHighest.setText(highest + "");
            tvAverage.setText(average + "");
            tvLowest.setText(lowest + "");
        }
    }


    @OnClick({R.id.tv_time_start, R.id.tv_time_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_start://选择开始时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTimeStart.setText(content);
                        String end = tvTimeEnd.getText().toString().trim();
                        getSugarSearch(content, end);
                    }
                });
                break;
            case R.id.tv_time_end://选择结束时间
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTimeEnd.setText(content);
                        String start = tvTimeStart.getText().toString().trim();
                        getSugarSearch(start, content);
                    }
                });
                break;
        }
    }

    /**
     * 搜索
     *
     * @param startTime
     * @param endTime
     */
    public void getSugarSearch(String startTime, String endTime) {
        String userid = getArguments().getString("userid");
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("starttime", startTime);
        map.put("endtime", endTime);
        RxHttp.postForm(XyUrl.GET_BLOOD_GLUCOSE_SEARCH)
                .addAll(map)
                .asResponse(SugarSearchBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SugarSearchBean>() {
                    @Override
                    public void accept(SugarSearchBean sugarSearchBean) throws Exception {
                        setSearch(sugarSearchBean);
                        searchList = sugarSearchBean.getSearch().getInfo();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 设置搜索
     *
     * @param data
     */
    private void setSearch(SugarSearchBean data) {
        setSearchTop(data.getSearch());
        setSearchSecondLv(data.getSearch().getInfo());
    }

    private void setSearchSecondLv(List<SugarSearchBean.SearchBean.InfoBean> infoList) {
        rvSugarList.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvSugarList.setAdapter(new ThirtyBottomSearchAdapter(infoList, getPageContext()));
    }


    private void setSearchTop(SugarSearchBean.SearchBean search) {
        String starttime = search.getStarttime();
        String endtime = search.getEndtime();
        int high = search.getXtpg();
        int normal = search.getXtzc();
        int low = search.getXtpd();
        double highest = search.getZgxt();
        double average = search.getPjxt();
        double lowest = search.getZdxt();
        tvTimeStart.setText(starttime);
        tvTimeEnd.setText(endtime);
        tvHigh.setText(high + "次");
        tvNormal.setText(normal + "次");
        tvLow.setText(low + "次");
        tvHighest.setText(highest + "");
        tvAverage.setText(average + "");
        tvLowest.setText(lowest + "");
    }

    @Override
    public void onAdapterClick(View view, int position) {
        Intent intent = new Intent(getPageContext(), HealthRecordBloodSugarListActivity.class);
        switch (view.getId()) {
            case R.id.fl_before_dawn://凌晨
                intent.putExtra("type", "凌晨");
                break;
            case R.id.fl_before_breakfast://早餐前
                intent.putExtra("type", "早餐空腹");
                break;
            case R.id.fl_after_the_breakfast://早餐后
                intent.putExtra("type", "早餐后");
                break;
            case R.id.fl_before_lunch://午餐前
                intent.putExtra("type", "午餐前");
                break;
            case R.id.fl_after_launch://午餐后
                intent.putExtra("type", "午餐后");
                break;
            case R.id.fl_before_dinner://晚餐前
                intent.putExtra("type", "晚餐前");
                break;
            case R.id.fl_after_dinner://晚餐后
                intent.putExtra("type", "晚餐后");
                break;
            case R.id.fl_after_sleep://睡前
                intent.putExtra("type", "睡前");
                break;
        }
        intent.putExtra("userid", getArguments().getString("userid"));
        String type = getArguments().getString("type");
        if ("0".equals(type)) {
            intent.putExtra("time", weekInfoList.get(position).getTime());
        } else {
            intent.putExtra("time", monthInfoList.get(position).getTime());
        }
        startActivity(intent);
    }

    @Override
    public void onAdapterClickSearch(View view, int position) {
        Intent intent = new Intent(getPageContext(), HealthRecordBloodSugarListActivity.class);
        switch (view.getId()) {
            case R.id.fl_before_dawn://凌晨
                intent.putExtra("type", "凌晨");
                break;
            case R.id.fl_before_breakfast://早餐前
                intent.putExtra("type", "早餐空腹");
                break;
            case R.id.fl_after_the_breakfast://早餐后
                intent.putExtra("type", "早餐后");
                break;
            case R.id.fl_before_lunch://午餐前
                intent.putExtra("type", "午餐前");
                break;
            case R.id.fl_after_launch://午餐后
                intent.putExtra("type", "午餐后");
                break;
            case R.id.fl_before_dinner://晚餐前
                intent.putExtra("type", "晚餐前");
                break;
            case R.id.fl_after_dinner://晚餐后
                intent.putExtra("type", "晚餐后");
                break;
            case R.id.fl_after_sleep://睡前
                intent.putExtra("type", "睡前");
                break;
        }
        intent.putExtra("userid", getArguments().getString("userid"));
        intent.putExtra("time", searchList.get(position).getTime());
        startActivity(intent);
    }
}
