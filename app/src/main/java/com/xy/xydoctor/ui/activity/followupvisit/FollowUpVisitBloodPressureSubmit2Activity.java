package com.xy.xydoctor.ui.activity.followupvisit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowUpVisitLvAdapterFour;
import com.xy.xydoctor.adapter.FollowUpVisitRvNew2Adapter;
import com.xy.xydoctor.bean.FollowUpVisitAddBean;
import com.xy.xydoctor.bean.FollowUpVisitDetailBean;
import com.xy.xydoctor.bean.FollowUpVisitLvBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.OnItemClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OkHttpCallBack;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.healthrecord.BaseHideLineActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:血压提交
 * 作者: LYD
 * 创建日期: 2019/7/19 16:19
 */
public class FollowUpVisitBloodPressureSubmit2Activity extends BaseHideLineActivity {
    private static final int GET_FOLLOW_UP_VISIT_DETAIL = 10010;
    private static final String TAG = "FollowUpVisitBloodSugarSubmitActivity";
    //标题开始
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.tv_page_title)
    TextView tvPageTitle;
    @BindView(R.id.tv_more)
    TextView tvMore;
    //标题结束
    //总结开始
    @BindView(R.id.et_summary_main_question)
    ColorEditText etSummaryMainQuestion;//主要问题
    @BindView(R.id.et_summary_improve_measure)
    ColorEditText etSummaryImproveMeasure;//改进措施
    @BindView(R.id.et_summary_main_purpose)
    ColorEditText etSummaryMainPurpose;//到达目的
    @BindView(R.id.ll_summary)
    LinearLayout llSummary;
    //总结结束
    //症状开始
    @BindView(R.id.rv_symptom)
    RecyclerView rvSymptom;
    @BindView(R.id.ll_symptom)
    LinearLayout llSymptom;
    //症状结束
    //体征开始
    @BindView(R.id.et_physical_sign_high)
    ColorEditText etPhysicalSignHigh;
    @BindView(R.id.et_physical_sign_low)
    ColorEditText etPhysicalSignLow;
    @BindView(R.id.et_height)
    ColorEditText etHeight;
    @BindView(R.id.et_weight)
    ColorEditText etWeight;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.et_bpm)
    EditText etBpm;
    @BindView(R.id.et_physical_other)
    ColorEditText etPhysicalOther;
    @BindView(R.id.ll_physical_sign)
    LinearLayout llPhysicalSign;
    //体征结束
    //生活方式开始
    @BindView(R.id.et_smoke)
    ColorEditText etSmoke;
    @BindView(R.id.et_drink)
    ColorEditText etDrink;
    @BindView(R.id.et_sport_count)
    ColorEditText etSportCount;
    @BindView(R.id.et_sport_time)
    ColorEditText etSportTime;
    @BindView(R.id.rb_take_salt_light)
    RadioButton rbTakeSaltLight;
    @BindView(R.id.rb_take_salt_center)
    RadioButton rbTakeSaltCenter;
    @BindView(R.id.rb_take_salt_weight)
    RadioButton rbTakeSaltWeight;
    @BindView(R.id.rg_life_style_take_salt)
    RadioGroup rgLifeStyleTakeSalt;
    @BindView(R.id.rb_psychological_adjust_well)
    RadioButton rbPsychologicalAdjustWell;
    @BindView(R.id.rb_psychological_adjust_common)
    RadioButton rbPsychologicalAdjustCommon;
    @BindView(R.id.rb_psychological_adjust_bad)
    RadioButton rbPsychologicalAdjustBad;
    @BindView(R.id.rg_life_style_psychological_adjust)
    RadioGroup rgLifeStylePsychologicalAdjust;
    @BindView(R.id.rb_follow_doctor_well)
    RadioButton rbFollowDoctorWell;
    @BindView(R.id.rb_follow_doctor_common)
    RadioButton rbFollowDoctorCommon;
    @BindView(R.id.rb_follow_doctor_bad)
    RadioButton rbFollowDoctorBad;
    @BindView(R.id.rg_life_style_follow_doctor)
    RadioGroup rgLifeStyleFollowDoctor;
    @BindView(R.id.ll_life_style)
    LinearLayout llLifeStyle;
    //生活方式结束
    //辅助检查开始
    @BindView(R.id.rb_drug_use_yield_rule)
    RadioButton rbDrugUseYieldRule;
    @BindView(R.id.rb_drug_use_yield_gap)
    RadioButton rbDrugUseYieldGap;
    @BindView(R.id.rb_drug_use_yield_not_take_medicine)
    RadioButton rbDrugUseYieldNotTakeMedicine;
    @BindView(R.id.rg_drug_use_yield_to)
    RadioGroup rgDrugUseYieldTo;
    @BindView(R.id.rg_adverse_drug_reaction_have)
    RadioButton rgAdverseDrugReactionHave;
    @BindView(R.id.rg_adverse_drug_reaction_not)
    RadioButton rgAdverseDrugReactionNot;
    @BindView(R.id.rg_drug_use_adverse_drug_reaction)
    RadioGroup rgDrugUseAdverseDrugReaction;//
    @BindView(R.id.rb_classify_satisfaction)
    RadioButton rbClassifySatisfaction;
    @BindView(R.id.rb_classify_satisfaction_not)
    RadioButton rbClassifySatisfactionNot;
    @BindView(R.id.rg_drug_use_classify)
    RadioGroup rgDrugUseClassify;//111
    @BindView(R.id.rg_drug_use_classify_second)
    RadioGroup rgDrugUseClassifySecond;//111
    @BindView(R.id.rb_classify_adverse_reaction)
    RadioButton rbClassifyAdverseReaction;
    @BindView(R.id.rb_classify_complication)
    RadioButton rbClassifyComplication;
    @BindView(R.id.ll_blood_pressure_examine)
    LinearLayout llExamine;
    //辅助检查开始
    //用药情况开始
    @BindView(R.id.lv_drug_use)
    MyListView lvDrugUse;
    @BindView(R.id.ll_blood_pressure_drug_use)
    LinearLayout llDrugUse;
    //用药情况结束
    private FollowUpVisitRvNew2Adapter adapter;
    private List<String> selectDatas = new ArrayList<>();
    private FollowUpVisitLvAdapterFour lvAdapter;


    private FollowUpVisitDetailBean allInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_blood_pressure_submit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        initTitle();
        setRadioGroup();
        setHeightAndWeightListener();
        KeyboardUtils.fixAndroidBug5497(this);
        getFollowUpVisitDetail();
    }

    /**
     * 设置
     */
    private void setRadioGroup() {
        rbClassifySatisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassifySecond.clearCheck();
            }
        });
        rbClassifySatisfactionNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassifySecond.clearCheck();
            }
        });
        rbClassifyAdverseReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassify.clearCheck();
            }
        });
        rbClassifyComplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassify.clearCheck();
            }
        });
    }

    /**
     * 设置身高体重监听求BMI值
     */
    private void setHeightAndWeightListener() {
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String height = s.toString();
                String weight = etWeight.getText().toString().trim();
                if (!TextUtils.isEmpty(weight)) {
                    double doubleHeight = TurnsUtils.getDouble(height, 0);
                    double doubleWeight = TurnsUtils.getDouble(weight, 0);
                    double doubleHeightM = doubleHeight / 100;
                    double bmi = doubleWeight / (doubleHeightM * doubleHeightM);
                    DecimalFormat df = new DecimalFormat("0.00");
                    tvBmi.setText(df.format(bmi));
                }
            }
        });
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String weight = s.toString();
                String height = etHeight.getText().toString().trim();
                if (!TextUtils.isEmpty(height)) {
                    double doubleHeight = TurnsUtils.getDouble(height, 0);
                    double doubleWeight = TurnsUtils.getDouble(weight, 0);
                    double doubleHeightM = doubleHeight / 100;
                    double bmi = doubleWeight / (doubleHeightM * doubleHeightM);
                    DecimalFormat df = new DecimalFormat("0.00");
                    tvBmi.setText(df.format(bmi));
                }


            }
        });
    }

    /**
     * 查看随访管理
     */
    private void getFollowUpVisitDetail() {
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FOLLOW_DETAIL_NEW)
                .addAll(map)
                .asResponse(FollowUpVisitDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<FollowUpVisitDetailBean>() {
                    @Override
                    public void accept(FollowUpVisitDetailBean data) throws Exception {
                        //FollowUpVisitDetailBean data = (FollowUpVisitDetailBean) msg.obj;
                        allInfo = data;
                        setIsClickable();
                        setVisibleOrGone(data);
                        setFiveData(data);
                        int status = data.getStatus();
                        setTitleRight(status);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 设置标题栏
     */
    private void initTitle() {
        tvPageTitle.setText("随访报告");
    }


    /**
     * 设置五个数据
     *
     * @param data
     */
    private void setFiveData(FollowUpVisitDetailBean data) {
        //症状
        List<String> symptom = data.getSymptom();//症状
        if (symptom != null) {
            setRvSymptom(symptom);
        } else {
            List<String> symptomNew = new ArrayList<>();
            setRvSymptom(symptomNew);
        }
        //体征
        setPhysicalSign(data);
        //生活方式
        setLifeStyle(data);
        //辅助检查
        //用药情况
        setDrugUse(data);
    }


    /**
     * 用药情况
     *
     * @param data
     */
    private void setDrugUse(FollowUpVisitDetailBean data) {
        int compliance = data.getCompliance();
        int drugreactions = data.getDrugreactions();
        int followstyle = data.getFollowstyle();
        //用药依从性
        if (1 == compliance) {
            rbDrugUseYieldRule.setChecked(true);
            rbDrugUseYieldGap.setChecked(false);
            rbDrugUseYieldNotTakeMedicine.setChecked(false);
        } else if (2 == compliance) {
            rbDrugUseYieldRule.setChecked(false);
            rbDrugUseYieldGap.setChecked(true);
            rbDrugUseYieldNotTakeMedicine.setChecked(false);
        } else if (3 == compliance) {
            rbDrugUseYieldRule.setChecked(false);
            rbDrugUseYieldGap.setChecked(false);
            rbDrugUseYieldNotTakeMedicine.setChecked(true);
        }
        //药物不良反应
        if (1 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(false);
            rgAdverseDrugReactionNot.setChecked(true);
        } else if (2 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(true);
            rgAdverseDrugReactionNot.setChecked(false);
        }
        //此次随访分类
        if (1 == followstyle) {
            rbClassifySatisfaction.setChecked(true);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(false);
        } else if (2 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(true);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(false);
        } else if (3 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(true);
            rbClassifyComplication.setChecked(false);
        } else if (4 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(true);
        }
        //设置四个药物
        List<List<String>> medicdetail = data.getMedicdetail();
        List<FollowUpVisitLvBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            FollowUpVisitLvBean bean = new FollowUpVisitLvBean();
            bean.setName("");
            bean.setCount("");
            bean.setDosage("");
            list.add(bean);
        }
        lvAdapter = new FollowUpVisitLvAdapterFour(getPageContext(), list, medicdetail);
        lvDrugUse.setAdapter(lvAdapter);
    }

    private void setIsClickable() {
        if (5 == allInfo.getStatus()) {
            /**
             * 体征
             */
            //血压最高 和最低
            etPhysicalSignHigh.setEnabled(false);
            etPhysicalSignLow.setEnabled(false);
            //身高体重
            etHeight.setEnabled(false);
            etWeight.setEnabled(false);
            //其他
            etPhysicalOther.setEnabled(false);

            /**
             * 生活方式
             */
            //吸烟量
            etSmoke.setEnabled(false);
            etDrink.setEnabled(false);
            etSportCount.setEnabled(false);
            etSportTime.setEnabled(false);
            disableRadioGroup(rgLifeStylePsychologicalAdjust);
            disableRadioGroup(rgLifeStyleFollowDoctor);
            disableRadioGroup(rgLifeStyleTakeSalt);

            /**
             * 用药情况
             */
            disableRadioGroup(rgDrugUseYieldTo);
            disableRadioGroup(rgDrugUseAdverseDrugReaction);
            disableRadioGroup(rgDrugUseClassify);
            disableRadioGroup(rgDrugUseClassifySecond);

            etSummaryMainQuestion.setEnabled(false);
            etSummaryMainPurpose.setEnabled(false);
        }
    }

    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }


    /**
     * 设置生活方式
     *
     * @param data
     */
    private void setLifeStyle(FollowUpVisitDetailBean data) {
        String smok = data.getSmok();
        String drink = data.getDrink();
        String sportnum = data.getSportnum();
        String sporttime = data.getSporttime();
        int saltrelated = data.getSaltrelated();
        int psychological = data.getPsychological();
        int behavior = data.getBehavior();
        etSmoke.setText(smok);
        etDrink.setText(drink);
        etSportCount.setText(sportnum);
        etSportTime.setText(sporttime);
        if (1 == saltrelated) {
            rbTakeSaltLight.setChecked(true);
            rbTakeSaltCenter.setChecked(false);
            rbTakeSaltWeight.setChecked(false);
        } else if (2 == saltrelated) {
            rbTakeSaltLight.setChecked(false);
            rbTakeSaltCenter.setChecked(true);
            rbTakeSaltWeight.setChecked(false);
        } else if (3 == saltrelated) {
            rbTakeSaltLight.setChecked(false);
            rbTakeSaltCenter.setChecked(false);
            rbTakeSaltWeight.setChecked(true);
        }
        if (1 == psychological) {
            rbPsychologicalAdjustWell.setChecked(true);
            rbPsychologicalAdjustCommon.setChecked(false);
            rbPsychologicalAdjustBad.setChecked(false);
        } else if (2 == psychological) {
            rbPsychologicalAdjustWell.setChecked(false);
            rbPsychologicalAdjustCommon.setChecked(true);
            rbPsychologicalAdjustBad.setChecked(false);
        } else if (3 == psychological) {
            rbPsychologicalAdjustWell.setChecked(false);
            rbPsychologicalAdjustCommon.setChecked(false);
            rbPsychologicalAdjustBad.setChecked(true);
        }
        if (1 == behavior) {
            rbFollowDoctorWell.setChecked(true);
            rbFollowDoctorCommon.setChecked(false);
            rbFollowDoctorBad.setChecked(false);
        } else if (2 == behavior) {
            rbFollowDoctorWell.setChecked(false);
            rbFollowDoctorCommon.setChecked(true);
            rbFollowDoctorBad.setChecked(false);
        } else if (3 == behavior) {
            rbFollowDoctorWell.setChecked(false);
            rbFollowDoctorCommon.setChecked(false);
            rbFollowDoctorBad.setChecked(true);
        }
    }

    /**
     * 设置体征
     *
     * @param data
     */
    private void setPhysicalSign(FollowUpVisitDetailBean data) {
        String systolic = data.getSystolic();//收缩压
        String diastolic = data.getDiastolic();//舒张压
        double height = data.getHeight();
        double weight = data.getWeight();
        String heartrate = data.getHeartrate();
        String elseX = data.getOther();//其它
        etPhysicalSignHigh.setText(systolic);
        etPhysicalSignLow.setText(diastolic);
        if (0.0 == height) {
            etHeight.setText("");//身高
        } else {
            etHeight.setText(height + "");//身高
        }
        etWeight.setText(weight + "");//体重
        if (0.0 == weight) {
            etWeight.setText("");
        } else {
            etWeight.setText(weight + "");//身高
        }
        if (!(0.0 == height) && !(0.0 == weight)) {
            double doubleHeightM = height / 100;
            double bmi = weight / (doubleHeightM * doubleHeightM);
            DecimalFormat df = new DecimalFormat("0.00");
            tvBmi.setText(df.format(bmi) + "");//BMI
        } else {
            tvBmi.setText("");
        }
        etPhysicalOther.setText(elseX);
        etBpm.setText(heartrate);
        //etPhysicalOther.setSelection(elseX.length());
    }

    /**
     * 设置症状
     *
     * @param symptom
     */
    private void setRvSymptom(List<String> symptom) {
        String[] stringArray = getResources().getStringArray(R.array.follow_up_visit_blood_pressure_symptom);
        List<String> list = Arrays.asList(stringArray);
        adapter = new FollowUpVisitRvNew2Adapter(getPageContext(), R.layout.item_include_follow_up_visit_symptom, list, symptom, allInfo.getStatus()+"");
        int symptomItemHeight = 30;
        int symptomSpanCount = 3;
        ViewGroup.LayoutParams params = rvSymptom.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ConvertUtils.dp2px(list.size() / symptomSpanCount * symptomItemHeight);
        rvSymptom.setLayoutParams(params);


        rvSymptom.setLayoutManager(new GridLayoutManager(getPageContext(), symptomSpanCount));
        rvSymptom.setAdapter(adapter);
        //添加已选中
        selectDatas.addAll(symptom);
        //添加已选中
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!adapter.selectMap.get(position)) {
                    adapter.selectMap.put(position, true);//修改map的值保存状态
                    adapter.notifyItemChanged(position);
                    selectDatas.add(position + 1 + "");
                } else {
                    adapter.selectMap.put(position, false);//修改map的值保存状态
                    adapter.notifyItemChanged(position);
                    selectDatas.remove(position + 1 + "");
                }
            }
        });
    }

    /**
     * 设置显示隐藏
     *
     * @param data
     */
    private void setVisibleOrGone(FollowUpVisitDetailBean data) {
        List<String> list = data.getQuestionstr();
        int status = data.getStatus();
        if (4 == status) {
            llSummary.setVisibility(View.VISIBLE);
        } else if (5 == status) {
            llSummary.setVisibility(View.VISIBLE);
            String paquestion = data.getPaquestion();
            String measures = data.getMeasures();
            String target = data.getTarget();
            etSummaryMainQuestion.setText(paquestion + measures);
            //            etSummaryImproveMeasure.setText(measures);
            etSummaryMainPurpose.setText(target);
            etSummaryMainQuestion.setEnabled(false);
            etSummaryImproveMeasure.setEnabled(false);
            etSummaryMainPurpose.setEnabled(false);
        } else {
            llSummary.setVisibility(View.VISIBLE);
        }
        if (list != null && list.size() > 0) {
            if (list.contains("1")) {
                llSymptom.setVisibility(View.VISIBLE);
            }
            if (list.contains("2")) {
                llPhysicalSign.setVisibility(View.VISIBLE);
            }
            if (list.contains("3")) {
                llLifeStyle.setVisibility(View.VISIBLE);
            }
            if (list.contains("4")) {
                llExamine.setVisibility(View.VISIBLE);
            }
            if (list.contains("5")) {
                llDrugUse.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 设置右上角
     *
     * @param status
     */
    private void setTitleRight(int status) {
        if (5 == status) {
            tvMore.setVisibility(View.GONE);
        } else {
            tvMore.setText("保存");
            tvMore.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.bt_back, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_more:
                Log.i("yys", "tv_more===");
                toDoSubmit(allInfo.getStatus() + "");
                break;
        }
    }

    /**
     * 提交数据
     *
     * @param status
     */
    private void toDoSubmit(String status) {
        //设置ID
        String id = getIntent().getStringExtra("id");
        String userGuid = SPStaticUtils.getString("userGuid");
        String token = SPStaticUtils.getString("token");
        String version = ConstantParam.SERVER_VERSION;
        //最后需要转换的Bean
        FollowUpVisitAddBean postData = new FollowUpVisitAddBean();
        postData.setId(id);
        postData.setGuid(userGuid);
        postData.setAccess_token(token);
        postData.setVersion(version);
        postData.setId(id);
        //设置数据Bean
        FollowUpVisitAddBean.DataBean dataBean = new FollowUpVisitAddBean.DataBean();
        //设置状态
        //        dataBean.setStatus(status);
        dataBean.setStatus("4");
        //症状
        StringBuilder sbSymptom = new StringBuilder();
        for (int i = 0; i < selectDatas.size(); i++) {
            sbSymptom.append(selectDatas.get(i));
            sbSymptom.append(",");
        }
        if (sbSymptom.length() > 0) {
            String sbSymptomSub = sbSymptom.substring(0, sbSymptom.length() - 1);
            dataBean.setSymptom(sbSymptomSub);
        }
        //体征
        String systolic = etPhysicalSignHigh.getText().toString().trim();
        dataBean.setSystolic(systolic);//收缩压
        String diastolic = etPhysicalSignLow.getText().toString().trim();
        dataBean.setDiastolic(diastolic);//收缩压
        String height = etHeight.getText().toString().trim();
        dataBean.setHeight(height);//身高
        String weight = etWeight.getText().toString().trim();
        dataBean.setWeight(weight);//体重
        String bpm = etBpm.getText().toString().trim();
        dataBean.setHeartrate(bpm);//心率
        String other = etPhysicalOther.getText().toString().trim();
        dataBean.setOther(other);//其它
        //生活方式
        String smoke = etSmoke.getText().toString().trim();
        dataBean.setSmok(smoke);
        String drink = etDrink.getText().toString().trim();
        dataBean.setDrink(drink);
        String sportCount = etSportCount.getText().toString().trim();
        dataBean.setSportnum(sportCount);
        String sportTime = etSportTime.getText().toString().trim();
        dataBean.setSporttime(sportTime);
        if (rbTakeSaltLight.isChecked()) {
            dataBean.setSaltrelated(1);//涉盐情况（1轻2中3重）
        } else if (rbTakeSaltCenter.isChecked()) {
            dataBean.setSaltrelated(2);//涉盐情况（1轻2中3重）
        } else if (rbTakeSaltWeight.isChecked()) {
            dataBean.setSaltrelated(3);//涉盐情况（1轻2中3重）
        }
        if (rbPsychologicalAdjustWell.isChecked()) {
            dataBean.setPsychological(1);//心理状态1:良好 2：一般 3：差
        } else if (rbPsychologicalAdjustCommon.isChecked()) {
            dataBean.setPsychological(2);//心理状态1:良好 2：一般 3：差
        } else if (rbPsychologicalAdjustBad.isChecked()) {
            dataBean.setPsychological(3);//心理状态1:良好 2：一般 3：差
        }
        if (rbFollowDoctorWell.isChecked()) {
            dataBean.setBehavior(1);//（遵医行为）1良好2一般3差
        } else if (rbFollowDoctorCommon.isChecked()) {
            dataBean.setBehavior(2);//（遵医行为）1良好2一般3差
        } else if (rbFollowDoctorBad.isChecked()) {
            dataBean.setBehavior(3);//（遵医行为）1良好2一般3差
        }
        //辅助检查
        //用药情况
        if (rbDrugUseYieldRule.isChecked()) {
            dataBean.setCompliance(1);//用药依从性 1规律2不间断3不服药
        } else if (rbDrugUseYieldGap.isChecked()) {
            dataBean.setCompliance(2);//用药依从性 1规律2不间断3不服药
        } else if (rbDrugUseYieldNotTakeMedicine.isChecked()) {
            dataBean.setCompliance(3);//用药依从性 1规律2不间断3不服药
        }
        if (rgAdverseDrugReactionHave.isChecked()) {
            dataBean.setDrugreactions(2);
        } else if (rgAdverseDrugReactionNot.isChecked()) {
            dataBean.setDrugreactions(1);
        }
        if (rbClassifySatisfaction.isChecked()) {
            dataBean.setFollowstyle(1);
        } else if (rbClassifySatisfactionNot.isChecked()) {
            dataBean.setFollowstyle(2);
        } else if (rbClassifyAdverseReaction.isChecked()) {
            dataBean.setFollowstyle(3);
        } else if (rbClassifyComplication.isChecked()) {
            dataBean.setFollowstyle(4);
        }
        //四种药物
        HashMap<Integer, String> saveMapName = lvAdapter.saveMapName;
        HashMap<Integer, String> saveMapCount = lvAdapter.saveMapCount;
        HashMap<Integer, String> saveMapDosage = lvAdapter.saveMapDosage;
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> thirdList = new ArrayList<>();
        List<String> fourList = new ArrayList<>();
        for (Map.Entry<Integer, String> entrySelect : saveMapName.entrySet()) {
            String value = entrySelect.getValue();
            int key = entrySelect.getKey();
            switch (key) {
                case 0:
                    firstList.add(value);
                    break;
                case 1:
                    secondList.add(value);
                    break;
                case 2:
                    thirdList.add(value);
                    break;
                case 3:
                    fourList.add(value);
                    break;

            }
        }
        for (Map.Entry<Integer, String> entrySelect : saveMapCount.entrySet()) {
            String value = entrySelect.getValue();
            int key = entrySelect.getKey();
            switch (key) {
                case 0:
                    firstList.add(value);
                    break;
                case 1:
                    secondList.add(value);
                    break;
                case 2:
                    thirdList.add(value);
                    break;
                case 3:
                    fourList.add(value);
                    break;
            }
        }
        for (Map.Entry<Integer, String> entrySelect : saveMapDosage.entrySet()) {
            String value = entrySelect.getValue();
            int key = entrySelect.getKey();
            switch (key) {
                case 0:
                    firstList.add(value);
                    break;
                case 1:
                    secondList.add(value);
                    break;
                case 2:
                    thirdList.add(value);
                    break;
                case 3:
                    fourList.add(value);
                    break;
            }
        }
        List<String> postMedicList = new ArrayList<>();
        postMedicList.addAll(firstList);
        postMedicList.addAll(secondList);
        postMedicList.addAll(thirdList);
        postMedicList.addAll(fourList);
        LogUtils.e(firstList);
        LogUtils.e(secondList);
        LogUtils.e(thirdList);
        LogUtils.e(fourList);
        LogUtils.e(postMedicList);
        postData.setMedicdetail(postMedicList);
        //最后数据提交
        postData.setData(dataBean);
        String jsonResult = JSON.toJSONString(postData);
        XyUrl.okPostJson(XyUrl.FOLLOW_ADD, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                toDoSubmit();
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
            }
        });
    }

    /**
     * 提交总结
     */
    private void toDoSubmit() {
        String question = etSummaryMainQuestion.getText().toString().trim();
        if (TextUtils.isEmpty(question)) {
            ToastUtils.showShort("请输入患者目前存在的主要问题");
            return;
        }
        String mainPurpose = etSummaryMainPurpose.getText().toString().trim();
        if (TextUtils.isEmpty(mainPurpose)) {
            ToastUtils.showShort("请输入预期到达目标");
            return;
        }

        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("vid", id);
        map.put("paquest", question);
        map.put("measures", "");
        map.put("target", mainPurpose);
        RxHttp.postForm(XyUrl.FOLLOW_UP_VISIT_SUMMARY_ADD)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        finish();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUMMARY_ADD));
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}
