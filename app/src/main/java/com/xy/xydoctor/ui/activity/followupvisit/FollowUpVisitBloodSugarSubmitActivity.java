package com.xy.xydoctor.ui.activity.followupvisit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.AddPicAdapter;
import com.xy.xydoctor.adapter.FollowUpVisitBloodSugarAddAdapter;
import com.xy.xydoctor.adapter.FollowUpVisitLvAdapterThree2;
import com.xy.xydoctor.adapter.FollowUpVisitRvNewAdapter;
import com.xy.xydoctor.bean.FollowUpVisitBloodSugarAddBean;
import com.xy.xydoctor.bean.FollowUpVisitLvBean;
import com.xy.xydoctor.bean.NewFollowUpVisitDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
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
 * 描述:血糖提交
 * 作者: LYD
 * 创建日期: 2019/7/26 10:09
 */
public class FollowUpVisitBloodSugarSubmitActivity extends BaseHideLineActivity {
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
    //    @BindView(R.id.rb_physical_sign_not)
    //    RadioButton rbPhysicalSignNot;
    //    @BindView(R.id.rb_physical_sign_have)
    //    RadioButton rbPhysicalSignHave;

    @BindView(R.id.rb_physical_sign_not)
    RadioButton rbPhysicalSignNot;
    @BindView(R.id.rb_physical_sign_have)
    RadioButton rbPhysicalSignHave;
    @BindView(R.id.rb_physical_sign_not_second)
    RadioButton rbPhysicalSignNotSecond;

    @BindView(R.id.rg_physical_sign)
    RadioGroup rgPhysicalSign;
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
    @BindView(R.id.et_main_food)
    ColorEditText etMainFood;
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
    @BindView(R.id.et_empty_sugar)
    ColorEditText etEmptySugar;
    @BindView(R.id.et_blood_red)
    ColorEditText etBloodRed;
    @BindView(R.id.ll_select_time)
    LinearLayout llCheckTime;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    @BindView(R.id.ll_examine)
    LinearLayout llExamine;
    //辅助检查结束
    //用药情况开始
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
    RadioGroup rgDrugUseAdverseDrugReaction;
    @BindView(R.id.rb_hypoglycemic_reaction_not)
    RadioButton rbHypoglycemicReactionNot;
    @BindView(R.id.rb_hypoglycemic_reaction_once)
    RadioButton rbHypoglycemicReactionOnce;
    @BindView(R.id.rb_hypoglycemic_reaction_often)
    RadioButton rbHypoglycemicReactionOften;
    @BindView(R.id.rg_drug_use_hypoglycemic_reaction)
    RadioGroup rgDrugUseHypoglycemicReaction;
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
    @BindView(R.id.lv_drug_use)
    MyListView lvDrugUse;
    @BindView(R.id.et_insulin)
    ColorEditText etInsulin;
    @BindView(R.id.et_insulin_dosage)
    ColorEditText etInsulinDosage;
    @BindView(R.id.ll_drug_use)
    LinearLayout llDrugUse;
    //血糖
    @BindView(R.id.lv_seven_day_blood_sugar)
    ListView lvSevenDayBloodSugar;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    //尿常规
    @BindView(R.id.ll_piss_common)
    LinearLayout llPissCommon;
    @BindView(R.id.et_piss_common_one_left)
    ColorEditText etPissCommonOneLeft;
    @BindView(R.id.et_piss_common_one_right)
    ColorEditText etPissCommonOneRight;
    @BindView(R.id.et_piss_common_two_left)
    ColorEditText etPissCommonTwoLeft;
    @BindView(R.id.et_piss_common_two_right)
    ColorEditText etPissCommonTwoRight;
    @BindView(R.id.et_piss_common_three_left)
    ColorEditText etPissCommonThreeLeft;
    @BindView(R.id.et_piss_common_three_right)
    ColorEditText etPissCommonThreeRight;
    @BindView(R.id.et_piss_common_four_left)
    ColorEditText etPissCommonFourLeft;
    @BindView(R.id.et_piss_common_four_right)
    ColorEditText etPissCommonFourRight;
    @BindView(R.id.et_piss_common_five_left)
    ColorEditText etPissCommonFiveLeft;
    @BindView(R.id.et_piss_common_five_right)
    ColorEditText etPissCommonFiveRight;
    @BindView(R.id.et_piss_common_six_left)
    ColorEditText etPissCommonSixLeft;
    //血脂
    @BindView(R.id.ll_blood_fat)
    LinearLayout llBloodFat;
    @BindView(R.id.et_blood_fat_one)
    ColorEditText etBloodFatOne;
    @BindView(R.id.et_blood_fat_two)
    ColorEditText etBloodFatTwo;
    @BindView(R.id.et_blood_fat_three)
    ColorEditText etBloodFatThree;
    @BindView(R.id.et_blood_fat_four)
    ColorEditText etBloodFatFour;
    //尿微量白蛋白
    @BindView(R.id.ll_piss_tiny_albumin)
    LinearLayout llPissTinyAlbumin;
    @BindView(R.id.et_piss_tiny_albumin)
    ColorEditText etPissTinyAlbumin;
    //血清肌酐
    @BindView(R.id.ll_serum)
    LinearLayout llSerum;
    @BindView(R.id.et_serum)
    ColorEditText etSerum;
    //肝功能
    @BindView(R.id.ll_liver)
    LinearLayout llLiver;
    @BindView(R.id.tv_follow_up_visit_liver)
    TextView tvFollowUpVisitLiver;
    @BindView(R.id.rv_liver)
    RecyclerView rvLiver;
    @BindView(R.id.tv_liver_desc)
    ColorEditText tvLiverDesc;
    //促甲状腺激素(TSH)
    @BindView(R.id.ll_tsh)
    LinearLayout llTsh;
    @BindView(R.id.et_tsh)
    ColorEditText etTsh;
    //心电图
    @BindView(R.id.ll_electrocardiogram)
    LinearLayout llHeart;
    @BindView(R.id.tv_follow_up_visit_electrocardiogram)
    TextView tvFollowUpVisitElectrocardiogram;
    @BindView(R.id.rv_heart)
    RecyclerView rvHeart;
    @BindView(R.id.tv_heart_desc)
    ColorEditText tvHeartDesc;
    //眼底照相
    @BindView(R.id.ll_fundus_photography)
    LinearLayout llEyes;
    @BindView(R.id.tv_follow_up_visit_fundus_photography)
    TextView tvFollowUpVisitFundusPhotography;
    @BindView(R.id.rv_eyes)
    RecyclerView rvEyes;
    @BindView(R.id.tv_eyes_desc)
    ColorEditText tvEyesDesc;
    //神经病变相关检查
    @BindView(R.id.ll_neuropathy_related_examination)
    LinearLayout llNeuropathy;
    @BindView(R.id.tv_follow_up_visit_neuropathy_related_examination)
    TextView tvFollowUpVisitNeuropathyRelatedExamination;
    @BindView(R.id.rv_neuropathy)
    RecyclerView rvNeuropathy;
    @BindView(R.id.tv_neuropathy_desc)
    ColorEditText tvNeuropathyDesc;

    //用药情况结束
    private FollowUpVisitRvNewAdapter adapter;
    private List<String> selectDatas = new ArrayList<>();

    private FollowUpVisitLvAdapterThree2 lvAdapter;

    //血糖
    private List<FollowUpVisitBloodSugarAddBean> sevenSugarList;
    private FollowUpVisitBloodSugarAddAdapter sugarAddAdapter;
    //肝功能
    private AddPicAdapter liverAddPicAdapter;
    private List<String> liverPics;
    //心电图
    private AddPicAdapter heartAddPicAdapter;
    private List<String> heartPics;
    //眼底检查
    private AddPicAdapter eyesAddPicAdapter;
    private List<String> eyesPics;
    //神经病变
    private AddPicAdapter neuropathyAddPicAdapter;
    private List<String> neuropathyPics;


    private int status;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_blood_sugar_submit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        hideTitleBar();
        initTitle();
        getFollowUpVisitDetail();
    }


    /**
     * 查看随访管理
     */
    private void getFollowUpVisitDetail() {
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FOLLOW_DETAIL_NEW).addAll(map).asResponse(NewFollowUpVisitDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<NewFollowUpVisitDetailBean>() {
                    @Override
                    public void accept(NewFollowUpVisitDetailBean data) throws Exception {
                        LogUtils.e("GET_FOLLOW_UP_VISIT_DETAIL");
                        //NewFollowUpVisitDetailBean data = (NewFollowUpVisitDetailBean) msg.obj;
                        setVisibleOrGone(data);
                        setFifteenData(data);
                        status = data.getStatus();
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
    private void setFifteenData(NewFollowUpVisitDetailBean data) {
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
        setExamine(data);
        //用药情况
        setDrugUse(data);
        //血糖
        setBloodSugar(data);
        //尿常规
        setPissCommon(data);
        //设置血脂
        if (data.getBloodfat() != null) {
            if (data.getBloodfat().size() > 0) {
                etBloodFatOne.setText(data.getBloodfat().get(0));
            }
            if (data.getBloodfat().size() > 1) {
                etBloodFatTwo.setText(data.getBloodfat().get(1));
            }
            if (data.getBloodfat().size() > 2) {
                etBloodFatThree.setText(data.getBloodfat().get(2));
            }
            if (data.getBloodfat().size() > 3) {
                etBloodFatFour.setText(data.getBloodfat().get(3));
            }
        }
        //设置尿微量蛋白
        if (!TextUtils.isEmpty(data.getUrinemicro())) {
            etPissTinyAlbumin.setText(data.getUrinemicro());
        }
        //设置血清肌酐
        if (!TextUtils.isEmpty(data.getCreatinine())) {
            etSerum.setText(data.getCreatinine());
        }
        //设置肝功能
        setLiver(data);
        //设置促甲状腺激素(TSH)
        if (!TextUtils.isEmpty(data.getStimulating())) {
            etTsh.setText(data.getStimulating());
        }
        //设置心电图
        setHeart(data);
        //设置眼底检查
        setEyes(data);
        //设置神经病变相关
        setNeuropathy(data);
    }


    /**
     * 用药情况
     *
     * @param data
     */
    private void setDrugUse(NewFollowUpVisitDetailBean data) {
        int compliance = data.getCompliance();
        int drugreactions = data.getDrugreactions();
        int reaction = data.getReaction();
        int followstyle = data.getFollowstyle();
        String insulin = data.getInsulin();
        String insulinnum = data.getInsulinnum();
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
        //低血糖反应
        if (1 == reaction) {
            rbHypoglycemicReactionNot.setChecked(true);
            rbHypoglycemicReactionOnce.setChecked(false);
            rbHypoglycemicReactionOften.setChecked(false);
        } else if (2 == reaction) {
            rbHypoglycemicReactionNot.setChecked(false);
            rbHypoglycemicReactionOnce.setChecked(true);
            rbHypoglycemicReactionOften.setChecked(false);
        } else if (3 == reaction) {
            rbHypoglycemicReactionNot.setChecked(false);
            rbHypoglycemicReactionOnce.setChecked(false);
            rbHypoglycemicReactionOften.setChecked(true);
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
        //胰岛素
        etInsulin.setText(insulin);
        etInsulinDosage.setText(insulinnum);
        //设置三个药物
        List<List<String>> medicdetail = data.getMedicdetail();
        List<FollowUpVisitLvBean> list = new ArrayList<>();
        //LogUtils.e(medicdetail.size());
        if (medicdetail != null && medicdetail.size() > 0) {
            for (int i = 0; i < medicdetail.size(); i++) {
                FollowUpVisitLvBean bean = new FollowUpVisitLvBean();
                bean.setName("");
                bean.setCount("");
                bean.setDosage("");
                list.add(bean);
            }
            lvAdapter = new FollowUpVisitLvAdapterThree2(getPageContext(), list, medicdetail);
            lvDrugUse.setAdapter(lvAdapter);
        }
    }


    /**
     * 辅助检查
     *
     * @param data
     */
    private void setExamine(NewFollowUpVisitDetailBean data) {
        String fastingbloodsugar = data.getFastingbloodsugar();
        String hemoglobin = data.getHemoglobin();
        String examinetime = data.getExaminetime();
        etEmptySugar.setText(fastingbloodsugar);
        etBloodRed.setText(hemoglobin);
        tvCheckTime.setText(examinetime);
    }


    /**
     * 设置生活方式
     *
     * @param data
     */
    private void setLifeStyle(NewFollowUpVisitDetailBean data) {
        String smok = data.getSmok();
        String drink = data.getDrink();
        String sportnum = data.getSportnum();
        String sporttime = data.getSporttime();
        String mainfood = data.getMainfood();
        int psychological = data.getPsychological();
        int behavior = data.getBehavior();
        etSmoke.setText(smok);
        etDrink.setText(drink);
        etSportCount.setText(sportnum);
        etSportTime.setText(sporttime);
        etMainFood.setText(mainfood);
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
    private void setPhysicalSign(NewFollowUpVisitDetailBean data) {
        String systolic = data.getSystolic();//收缩压
        String diastolic = data.getDiastolic();//舒张压
        double height = data.getHeight();
        double weight = data.getWeight();
        int pulsation = data.getPulsation();//足背动脉搏动（1未触及2触及）
        String elseX = data.getOther();//其它
        etPhysicalSignHigh.setText(systolic);//收缩压
        etPhysicalSignLow.setText(diastolic);//舒张压
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
        //足背动脉搏动（1未触及2触及）
        //足背动脉搏动（1未触及2触及）
        if (1 == pulsation) {
            rbPhysicalSignNot.setChecked(true);
            rbPhysicalSignHave.setChecked(false);
            rbPhysicalSignNotSecond.setChecked(false);
        } else if (2 == pulsation) {
            rbPhysicalSignNot.setChecked(false);
            rbPhysicalSignHave.setChecked(true);
            rbPhysicalSignNotSecond.setChecked(false);
        } else if (3 == pulsation) {
            rbPhysicalSignNot.setChecked(false);
            rbPhysicalSignHave.setChecked(false);
            rbPhysicalSignNotSecond.setChecked(true);
        }
        etPhysicalOther.setText(elseX);
    }

    /**
     * 设置症状
     *
     * @param symptom
     */
    private void setRvSymptom(List<String> symptom) {
        String[] stringArray = getResources().getStringArray(R.array.follow_up_visit_blood_sugar_symptom);
        List<String> list = Arrays.asList(stringArray);
        adapter = new FollowUpVisitRvNewAdapter(list, symptom, status + "");
        rvSymptom.setLayoutManager(new GridLayoutManager(getPageContext(), 3));
        rvSymptom.setAdapter(adapter);
        //添加已选中
        selectDatas.addAll(symptom);
    }


    /**
     * 设置显示隐藏
     *
     * @param data
     */
    private void setVisibleOrGone(NewFollowUpVisitDetailBean data) {
        List<String> list = data.getQuestionstr();
        int status = data.getStatus();
        if (4 == status) {
            llSummary.setVisibility(View.VISIBLE);
        } else if (5 == status) {
            llSummary.setVisibility(View.VISIBLE);
            String paquestion = data.getPaquestion();
            String measures = data.getMeasures();
            String target = data.getTarget();
            etSummaryMainQuestion.setText(paquestion);
            etSummaryImproveMeasure.setText(measures);
            etSummaryMainPurpose.setText(target);
            etSummaryMainQuestion.setEnabled(false);
            etSummaryImproveMeasure.setEnabled(false);
            etSummaryMainPurpose.setEnabled(false);
        } else {
            llSummary.setVisibility(View.GONE);
        }
        if (list != null && list.size() > 0) {
            //症状
            if (list.contains("1")) {
                llSymptom.setVisibility(View.VISIBLE);
            }
            //体征
            if (list.contains("2")) {
                llPhysicalSign.setVisibility(View.VISIBLE);
            }
            //生活方式
            if (list.contains("3")) {
                llLifeStyle.setVisibility(View.VISIBLE);
            }
            //辅助检查
            if (list.contains("4")) {
                llExamine.setVisibility(View.VISIBLE);
            }
            //用药情况
            if (list.contains("5")) {
                llDrugUse.setVisibility(View.VISIBLE);
            }
            //血糖
            if (list.contains("6")) {
                llBloodSugar.setVisibility(View.VISIBLE);
            }
            //尿常规
            if (list.contains("7")) {
                llPissCommon.setVisibility(View.VISIBLE);
            }
            //血脂
            if (list.contains("8")) {
                llBloodFat.setVisibility(View.VISIBLE);
            }
            //尿微量白蛋白
            if (list.contains("9")) {
                llPissTinyAlbumin.setVisibility(View.VISIBLE);
            }
            //血清肌酐
            if (list.contains("10")) {
                llSerum.setVisibility(View.VISIBLE);
            }
            //肝功能
            if (list.contains("11")) {
                llLiver.setVisibility(View.VISIBLE);
            }
            //促甲状腺激素(TSH)
            if (list.contains("12")) {
                llTsh.setVisibility(View.VISIBLE);
            }
            //心电图
            if (list.contains("13")) {
                llHeart.setVisibility(View.VISIBLE);
            }
            //眼底照相
            if (list.contains("14")) {
                llEyes.setVisibility(View.VISIBLE);
            }
            //神经病变相关检查
            if (list.contains("15")) {
                llNeuropathy.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置血糖
     *
     * @param data
     */
    private void setBloodSugar(NewFollowUpVisitDetailBean data) {
        List<List<String>> sugars = data.getSugars();
        //取七天血糖值
        sevenSugarList = new ArrayList<>();
        if (sugars != null && 7 == sugars.size()) {
            for (int i = 0; i < sugars.size(); i++) {
                List<String> list = sugars.get(i);
                FollowUpVisitBloodSugarAddBean bean = new FollowUpVisitBloodSugarAddBean();
                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    switch (j) {
                        case 0:
                            bean.setOne(s);
                            break;
                        case 1:
                            bean.setTwo(s);
                            break;
                        case 2:
                            bean.setThree(s);
                            break;
                        case 3:
                            bean.setFour(s);
                            break;
                        case 4:
                            bean.setFive(s);
                            break;
                        case 5:
                            bean.setSix(s);
                            break;
                        case 6:
                            bean.setSeven(s);
                            break;
                        case 7:
                            bean.setEight(s);
                            break;
                    }
                }
                sevenSugarList.add(bean);
            }
        }
        sugarAddAdapter = new FollowUpVisitBloodSugarAddAdapter(getPageContext(),
                R.layout.item_seven_and_thirty_bottom_for_follow_up_visit, sevenSugarList);
        lvSevenDayBloodSugar.setAdapter(sugarAddAdapter);
    }

    /**
     * 设置尿常规
     */
    private void setPissCommon(NewFollowUpVisitDetailBean data) {
        List<TextView> tvList = new ArrayList<>();
        tvList.add(etPissCommonOneLeft);
        tvList.add(etPissCommonOneRight);

        tvList.add(etPissCommonTwoLeft);
        tvList.add(etPissCommonTwoRight);

        tvList.add(etPissCommonThreeLeft);
        tvList.add(etPissCommonThreeRight);

        tvList.add(etPissCommonFourLeft);
        tvList.add(etPissCommonFourRight);

        tvList.add(etPissCommonFiveLeft);
        tvList.add(etPissCommonFiveRight);

        tvList.add(etPissCommonSixLeft);
        List<String> pissCommonList = data.getRoutine();
        if (pissCommonList != null && 11 == pissCommonList.size()) {
            for (int i = 0; i < pissCommonList.size(); i++) {
                String s = pissCommonList.get(i);
                TextView tv = tvList.get(i);
                tv.setText(s);
            }
        }
    }

    //设置肝功能
    private void setLiver(NewFollowUpVisitDetailBean data) {
        liverPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getLiverfun1())) {
            liverPics.add(data.getLiverfun1());
        }
        if (!TextUtils.isEmpty(data.getLiverfun2())) {
            liverPics.add(data.getLiverfun2());
        }
        if (!TextUtils.isEmpty(data.getLiverfun3())) {
            liverPics.add(data.getLiverfun3());
        }
        if (!TextUtils.isEmpty(data.getLivercon())) {
            tvLiverDesc.setText(data.getLivercon());
        }
        liverAddPicAdapter = new AddPicAdapter(this, liverPics);
        //liverAddPicAdapter.setList(liverPics);
        rvLiver.setAdapter(liverAddPicAdapter);
        rvLiver.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置心电图
    private void setHeart(NewFollowUpVisitDetailBean data) {
        heartPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getHeartpic1())) {
            heartPics.add(data.getHeartpic1());
        }
        if (!TextUtils.isEmpty(data.getHeartpic2())) {
            heartPics.add(data.getHeartpic2());
        }
        if (!TextUtils.isEmpty(data.getHeartpic3())) {
            heartPics.add(data.getHeartpic3());
        }
        if (!TextUtils.isEmpty(data.getHeartcontent())) {
            tvHeartDesc.setText(data.getHeartcontent());
        }

        heartAddPicAdapter = new AddPicAdapter(this, heartPics);
        //heartAddPicAdapter.setList(heartPics);
        //rvHeart.setAdapter(heartAddPicAdapter);
        rvHeart.setAdapter(heartAddPicAdapter);
        rvHeart.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置眼底检查
    private void setEyes(NewFollowUpVisitDetailBean data) {
        eyesPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getEyespic1())) {
            eyesPics.add(data.getEyespic1());
        }
        if (!TextUtils.isEmpty(data.getEyespic2())) {
            eyesPics.add(data.getEyespic2());
        }
        if (!TextUtils.isEmpty(data.getEyespic3())) {
            eyesPics.add(data.getEyespic3());
        }
        if (!TextUtils.isEmpty(data.getEyescontent())) {
            tvEyesDesc.setText(data.getEyescontent());
        }
        eyesAddPicAdapter = new AddPicAdapter(this, eyesPics);
        //eyesAddPicAdapter.setList(eyesPics);
        rvEyes.setAdapter(eyesAddPicAdapter);
        rvEyes.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置神经病变
    private void setNeuropathy(NewFollowUpVisitDetailBean data) {
        neuropathyPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getNeuropathypic1())) {
            neuropathyPics.add(data.getNeuropathypic1());
        }
        if (!TextUtils.isEmpty(data.getNeuropathypic2())) {
            neuropathyPics.add(data.getNeuropathypic2());
        }
        if (!TextUtils.isEmpty(data.getNeuropathypic3())) {
            neuropathyPics.add(data.getNeuropathypic3());
        }
        if (!TextUtils.isEmpty(data.getNeuropathycontent())) {
            tvNeuropathyDesc.setText(data.getNeuropathycontent());
        }
        neuropathyAddPicAdapter = new AddPicAdapter(this, neuropathyPics);
        //neuropathyAddPicAdapter.setList(neuropathyPics);
        rvNeuropathy.setAdapter(neuropathyAddPicAdapter);
        rvNeuropathy.setLayoutManager(new GridLayoutManager(this, 3));
    }


    /**
     * 设置右上角
     *
     * @param status
     */
    private void setTitleRight(int status) {
        if (4 == status) {
            tvMore.setText("保存");
            tvMore.setVisibility(View.VISIBLE);
        } else {
            tvMore.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.bt_back, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_more:
                toDoSubmit();
                break;
        }
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
        String improveMeasure = etSummaryImproveMeasure.getText().toString().trim();
//        if (TextUtils.isEmpty(improveMeasure)) {
//            ToastUtils.showShort("请输入主要改进措施");
//            return;
//        }
        String mainPurpose = etSummaryMainPurpose.getText().toString().trim();
        if (TextUtils.isEmpty(mainPurpose)) {
            ToastUtils.showShort("请输入预期到达目标");
            return;
        }
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("vid", id);
        map.put("paquest", question);
        map.put("measures", improveMeasure);
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
