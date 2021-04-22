package com.xy.xydoctor.ui.activity.followupvisit;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowUpVisitAddRvAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.FollowUpVisitAddRvBean;
import com.xy.xydoctor.bean.FollowUpVisitCreateBean;
import com.xy.xydoctor.bean.FollowUpVisitDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.OnItemClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.view.popup.FollowUpVisitSavePopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  创建随访 1血糖 2血压
 * 作者: LYD
 * 创建日期: 2019/8/8 9:57
 */
public class FollowUpVisitAddActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FollowUpVisitAddActivity";
    private static final int BACK_LEFT = 0;
    private static final int BACK_RIGHT = 1;
    private static final int SAVE_LEFT = 2;
    private static final int SAVE_RIGHT = 3;
    @BindView(R.id.et_count)
    ColorEditText etCount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;

    @BindView(R.id.rg_four_way)
    RadioGroup rgFourWay;
    @BindView(R.id.rb_one)
    RadioButton rbOne;
    @BindView(R.id.rb_two)
    RadioButton rbTwo;
    @BindView(R.id.rb_three)
    RadioButton rbThree;
    @BindView(R.id.rb_four)
    RadioButton rbFour;

    @BindView(R.id.rg_four_time)
    RadioGroup rgFourTime;
    @BindView(R.id.rb_time_one)
    RadioButton rbTimeOne;
    @BindView(R.id.rb_time_two)
    RadioButton rbTimeTwo;
    @BindView(R.id.rb_time_three)
    RadioButton rbTimeThree;
    @BindView(R.id.rb_time_four)
    RadioButton rbTimeFour;

    @BindView(R.id.et_content)
    EditText etContent;

    @BindView(R.id.img_all_check)
    ImageView imgAllCheck;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;

    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @BindView(R.id.rv_follow_up_visit_add)
    RecyclerView rvFollowUpVisitAdd;
    //选择
    private FollowUpVisitAddRvAdapter secondAdapter;
    private List<String> selectDatasSecond = new ArrayList<>();
    private List<FollowUpVisitAddRvBean> list;
    //选择
    private FollowUpVisitSavePopup popupBack;
    private FollowUpVisitSavePopup popupSave;
    //"way": 1,//随访方式 1门诊  2家庭   3电话  4远程
    //"remind": 2,//提醒时间  1五天  2三天  3两天  4一天
    private String way = "1";
    private String remind = "3";


    /**
     * 设置四种拜访方式监听
     */
    private void initRgCheckListener() {
        rgFourWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one:
                        etContent.setText("根据随访要求定时到医院");
                        way = "1";
                        break;
                    case R.id.rb_two:
                        etContent.setText("保持手机畅通，医生会与您确定家访时间");
                        way = "2";
                        break;
                    case R.id.rb_three:
                        etContent.setText("保持手机畅通，医生近期会与您电话沟通");
                        way = "3";
                        break;
                    case R.id.rb_four:
                        etContent.setText("在随访时间内完成随访内容并发送给医生");
                        way = "4";
                        break;
                }
            }
        });
        rgFourTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_time_one:
                        remind = "5";
                        break;
                    case R.id.rb_time_two:
                        remind = "3";
                        break;
                    case R.id.rb_time_three:
                        remind = "2";
                        break;
                    case R.id.rb_time_four:
                        remind = "1";
                        break;
                }
            }
        });
    }


    /**
     * 初始化弹窗
     */
    private void initPopup() {
        popupBack = new FollowUpVisitSavePopup(getPageContext());
        popupSave = new FollowUpVisitSavePopup(getPageContext());
        TextView tvContentTopBack = popupBack.findViewById(R.id.tv_content_top);
        TextView tvContentBottomBack = popupBack.findViewById(R.id.tv_content_bottom);
        TextView tvLeftBack = popupBack.findViewById(R.id.tv_left);
        TextView tvRightBack = popupBack.findViewById(R.id.tv_right);

        TextView tvContentTopSave = popupSave.findViewById(R.id.tv_content_top);
        TextView tvContentBottomSave = popupSave.findViewById(R.id.tv_content_bottom);
        TextView tvLeftSave = popupSave.findViewById(R.id.tv_left);
        TextView tvRightSave = popupSave.findViewById(R.id.tv_right);
        tvContentTopBack.setText("您还未为患者勾选完成");
        tvContentBottomBack.setText("是否取消发送");
        tvLeftBack.setText("存为草稿");
        tvRightBack.setText("取消");

        tvContentTopSave.setText("您已为患者勾选完成");
        tvContentBottomSave.setText("是否发送患者");
        tvLeftSave.setText("存为草稿");
        tvRightSave.setText("发送");

        tvLeftBack.setOnClickListener(this);
        tvRightBack.setOnClickListener(this);
        tvLeftSave.setOnClickListener(this);
        tvRightSave.setOnClickListener(this);
        tvLeftBack.setTag(BACK_LEFT);
        tvRightBack.setTag(BACK_RIGHT);
        tvLeftSave.setTag(SAVE_LEFT);
        tvRightSave.setTag(SAVE_RIGHT);
    }

    /**
     * 设置Rv适应Sc
     */
    private void initRv() {
        //SC嵌套RV 解决现实不全
        rvFollowUpVisitAdd.setNestedScrollingEnabled(false);

        String type = getIntent().getStringExtra("type");
        //在加载数据之前配置
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFollowUpVisitAdd.setLayoutManager(linearLayoutManager);

        //创建一个适配器
        int[] imgRes = new int[0];
        String[] listTitle = new String[0];
        String[] listDesc = new String[0];
        int length = 0;
        if ("1".equals(type)) {
            length = 15;
            imgRes = new int[]{R.drawable.follow_up_add_one, R.drawable.follow_up_add_two,
                    R.drawable.follow_up_add_three, R.drawable.follow_up_add_four, R.drawable.follow_up_add_five,
                    R.drawable.follow_up_add_five, R.drawable.follow_up_add_six, R.drawable.follow_up_add_seven,
                    R.drawable.follow_up_add_nine, R.drawable.follow_up_add_ten, R.drawable.follow_up_add_eleven,
                    R.drawable.follow_up_add_twelve, R.drawable.follow_up_add_thirteen,
                    R.drawable.follow_up_add_fourteen,
                    R.drawable.follow_up_add_fifteen};
            listTitle = getResources().getStringArray(R.array.follow_up_visit_add_title_blood_sugar);
            listDesc = getResources().getStringArray(R.array.follow_up_visit_add_desc_blood_sugar);
        } else if ("2".equals(type)) {
            length = 5;
            imgRes = new int[]{R.drawable.follow_up_add_one, R.drawable.follow_up_add_two,
                    R.drawable.follow_up_add_three, R.drawable.follow_up_add_four, R.drawable.follow_up_add_five};
            listTitle = getResources().getStringArray(R.array.follow_up_visit_add_title_blood_pressure);
            listDesc = getResources().getStringArray(R.array.follow_up_visit_add_desc_blood_pressure);
        } else {
            length = 11;
            imgRes = new int[]{R.drawable.liver_one, R.drawable.liver_two, R.drawable.liver_three,
                    R.drawable.liver_four, R.drawable.liver_five, R.drawable.liver_six,
                    R.drawable.liver_seven, R.drawable.liver_eight, R.drawable.liver_nine,
                    R.drawable.liver_ten, R.drawable.liver_eleven};
            listTitle = getResources().getStringArray(R.array.follow_up_visit_add_title_liver);
            listDesc = getResources().getStringArray(R.array.follow_up_visit_add_title_liver_desc);
        }

        list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            FollowUpVisitAddRvBean bean = new FollowUpVisitAddRvBean(listTitle[i], listDesc[i], imgRes[i], false);
            list.add(bean);
        }
        secondAdapter = new FollowUpVisitAddRvAdapter(list);
        rvFollowUpVisitAdd.setAdapter(secondAdapter);
        int finalLength = length;
        secondAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!secondAdapter.isSelected.get(position)) {
                    //修改map的值保存状态
                    secondAdapter.isSelected.put(position, true);
                    secondAdapter.notifyItemChanged(position);
                    selectDatasSecond.add(position + 1 + "");
                } else {
                    //修改map的值保存状态
                    secondAdapter.isSelected.put(position, false);
                    secondAdapter.notifyItemChanged(position);
                    selectDatasSecond.remove(position + 1 + "");
                }
                if (finalLength == selectDatasSecond.size()) {
                    imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check_ed);
                    imgAllCheck.setTag("1");
                } else {
                    imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check);
                    imgAllCheck.setTag("0");
                }
            }
        });
    }

    /**
     * 是否第一次创建随访
     */
    private void isFirst() {
        int id = getIntent().getIntExtra("id", 0);
        if (0 == id) {
            initFirstTime();
        } else {
            getFollowUpDetail(id);
        }
    }

    /**
     * 获取详情
     *
     * @param id
     */
    private void getFollowUpDetail(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FOLLOW_DETAIL_NEW)
                .addAll(map)
                .asResponse(FollowUpVisitDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitDetailBean>() {
                    @Override
                    public void accept(FollowUpVisitDetailBean data) throws Exception {
                        String times = data.getTimes();
                        String visittime = data.getVisittime();
                        etCount.setText(times);
                        tvTime.setText(visittime);
                        //随访方式
                        String way = data.getWay();
                        //提醒时间
                        String remind = data.getRemind();
                        switch (way) {
                            case "1":
                                rbOne.setChecked(true);
                                break;
                            case "2":
                                rbTwo.setChecked(true);
                                break;
                            case "3":
                                rbThree.setChecked(true);
                                break;
                            case "4":
                                rbFour.setChecked(true);
                                break;
                        }
                        switch (remind) {
                            case "5":
                                rbTimeOne.setChecked(true);
                                break;
                            case "3":
                                rbTimeTwo.setChecked(true);
                                break;
                            case "2":
                                rbTimeThree.setChecked(true);
                                break;
                            case "1":
                                rbTimeFour.setChecked(true);
                                break;
                        }
                        String recontent = data.getRecontent();
                        etContent.setText(recontent);

                        List<String> questionstr = data.getQuestionstr();
                        selectDatasSecond.addAll(questionstr);
                        int length = 0;
                        String type = getIntent().getStringExtra("type");
                        if ("1".equals(type)) {
                            length = 15;
                        } else if ("2".equals(type)) {
                            length = 5;
                        } else {
                            length = 11;
                        }
                        if (length == selectDatasSecond.size()) {
                            imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check_ed);
                            imgAllCheck.setTag("1");
                        } else {
                            imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check);
                            imgAllCheck.setTag("0");
                        }
                        for (int i = 0; i < questionstr.size(); i++) {
                            String checkString = questionstr.get(i);
                            int checkInt = TurnsUtils.getInt(checkString, 0);
                            //修改map的值保存状态
                            secondAdapter.isSelected.put(checkInt - 1, true);
                            secondAdapter.notifyDataSetChanged();
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 初始化时间
     */
    private void initFirstTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = TimeUtils.getNowString(sdf);
        tvTime.setText(nowString);
    }

    @OnClick({R.id.ll_select_time, R.id.tv_submit, R.id.ll_check_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_time:
                setSelectTime();
                break;
            case R.id.ll_check_all:
                setAllCheck();
                break;
            case R.id.tv_submit:
                toDoCheckSubmit();
                break;
        }
    }

    /**
     * 设置选中时间
     */
    private void setSelectTime() {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvTime.setText(content);
            }
        });
    }

    /**
     * 设置全部选中
     */
    private void setAllCheck() {
        String type = getIntent().getStringExtra("type");
        int length;
        if ("1".equals(type)) {
            length = 15;
        } else if ("2".equals(type)) {
            length = 5;
        } else {
            length = 11;
        }
        String tag = (String) imgAllCheck.getTag();
        if ("0".equals(tag)) {
            imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check_ed);
            imgAllCheck.setTag("1");
            for (int i = 0; i < length; i++) {
                //修改map的值保存状态
                secondAdapter.isSelected.put(i, true);
                secondAdapter.notifyDataSetChanged();
                selectDatasSecond.add(i + 1 + "");
            }
        } else {
            imgAllCheck.setBackgroundResource(R.drawable.follow_up_visit_add_check);
            imgAllCheck.setTag("0");
            for (int i = 0; i < length; i++) {
                //修改map的值保存状态
                secondAdapter.isSelected.put(i, false);
                secondAdapter.notifyDataSetChanged();
                selectDatasSecond.remove(i + 1 + "");
            }
        }
    }

    /**
     * 提交校验
     */
    private void toDoCheckSubmit() {
        String count = etCount.getText().toString().trim();
        if (TextUtils.isEmpty(count)) {
            ToastUtils.showShort("请输入随访次数");
            return;
        }
        String time = tvTime.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort("请选择随访时间");
            return;
        }
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("请编辑需要对患者提醒的消息内容");
            return;
        }
        if (selectDatasSecond.size() < 1) {
            ToastUtils.showShort("请至少选择一个随访内容");
            return;
        }
        popupSave.showPopupWindow();
    }

    /**
     * 提交
     */
    private void toDoSubmit(String status) {
        String count = etCount.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        String reContent = etContent.getText().toString().trim();
        FollowUpVisitCreateBean data = new FollowUpVisitCreateBean();
        String token = SPStaticUtils.getString("token");
        data.setAccess_token(token);
        if ("1".equals(status)) {
            data.setStatus(1);
        } else {
            data.setStatus(2);
        }
        String type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            data.setType(1);
        } else if ("2".equals(type)) {
            data.setType(2);
        } else {
            data.setType(3);
        }
        data.setTimes(count);
        data.setVisittime(time);
        data.setUserid(getIntent().getStringExtra("userid"));
        data.setSubject(selectDatasSecond);
        int id = getIntent().getIntExtra("id", 0);
        data.setVid(id);
        //设置三个新增
        data.setWay(way);
        data.setRemind(remind);
        data.setRecontent(reContent);
        LogUtils.e(getIntent().getBooleanExtra("from_family", false));
        if (getIntent().getBooleanExtra("from_family", false)) {
            data.setIs_family(1);
        }
        String jsonResult = GsonUtils.toJson(data);
        Log.i("yys", "jsonResult===" + jsonResult);
        LogUtils.e(jsonResult);
        //调接口
        RxHttp.postJson(XyUrl.FOLLOW_UP_VISIT_CREATE)
                .addAll(jsonResult)
                .asResponse(String.class)
                //感知生命周期，并在主线程回调
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("获取成功");
                        finish();
                        EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT, getIntent().getStringExtra("type")));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键按下
            popupBack.showPopupWindow();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case BACK_LEFT:
            case SAVE_LEFT:
                //保存草稿
                toDoSubmit("1");
                break;
            case BACK_RIGHT:
                finish();
                break;
            case SAVE_RIGHT:
                //发送
                toDoSubmit("2");
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_upvisit_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("创建随访");
        initPopup();
        initRgCheckListener();
        initRv();
        isFirst();
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupBack.showPopupWindow();
            }
        });
    }
}
