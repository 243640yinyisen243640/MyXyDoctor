package com.xy.xydoctor.ui.activity.followupvisit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.AddPic2Adapter;
import com.xy.xydoctor.adapter.FollowUpVisitBloodSugarAdd2Adapter;
import com.xy.xydoctor.adapter.FollowUpVisitLvAdapterThree;
import com.xy.xydoctor.adapter.FollowUpVisitRvNew2Adapter;
import com.xy.xydoctor.base.activity.BaseHandlerActivity;
import com.xy.xydoctor.bean.FollowUpVisitBloodSugarAddBean;
import com.xy.xydoctor.bean.FollowUpVisitLvBean;
import com.xy.xydoctor.bean.NewFollowUpVisitDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.xy.xydoctor.imp.AdapterClickSearchImp;
import com.xy.xydoctor.imp.OnItemClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OkHttpInstance;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DialogUtils;
import com.xy.xydoctor.utils.GifSizeFilter;
import com.xy.xydoctor.utils.ListCastUtils;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.view.popup.SlideFromBottomPopup;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rxhttp.wrapper.param.RxHttp;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * ??????:????????????
 * ??????: LYD
 * ????????????: 2019/7/26 10:09
 */
public class FollowUpVisitBloodSugarSubmit2Activity extends BaseHandlerActivity implements AdapterClickImp, AdapterClickSearchImp {
    private static final String TAG = "FollowUpVisitBloodSugarSubmitActivity";

    private static final int COMPRESS_OVER = 8888;

    private static final int ADD_FOLLOW_OVER = 9999;

    /**
     * ????????????
     */
    private static final int REQUEST_CAMERA = 1000;
    /**
     * ?????????
     */
    private static final int REQUEST_CODE_CHOOSE_LIVER = 1998;
    /**
     * ?????????
     */
    private static final int REQUEST_CODE_CHOOSE_HEART = 2998;
    /**
     * ????????????
     */
    private static final int REQUEST_CODE_CHOOSE_EYES = 3998;
    /**
     * ????????????
     */
    private static final int REQUEST_CODE_CHOOSE_NEUROPATHY = 4998;
    /**
     * ?????????????????????
     */
    private static final int LIVER = 1111;
    private static final int HEART = 2222;
    private static final int EYES = 3333;
    private static final int NEUROPATHY = 4444;


    /**
     * ??????
     */
    private File file;
    private SlideFromBottomPopup photoPopu;
    private int mPosition;
    //????????????
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.tv_page_title)
    TextView tvPageTitle;
    @BindView(R.id.tv_more)
    TextView tvMore;
    //????????????
    //????????????
    @BindView(R.id.et_summary_main_question)
    ColorEditText etSummaryMainQuestion;//????????????
    @BindView(R.id.et_summary_improve_measure)
    ColorEditText etSummaryImproveMeasure;//????????????
    @BindView(R.id.et_summary_main_purpose)
    ColorEditText etSummaryMainPurpose;//????????????
    @BindView(R.id.ll_summary)
    LinearLayout llSummary;
    //????????????
    //????????????
    @BindView(R.id.rv_symptom)
    RecyclerView rvSymptom;
    @BindView(R.id.ll_symptom)
    LinearLayout llSymptom;
    //????????????
    //????????????
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
    //????????????
    //??????????????????
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
    //??????????????????
    //??????????????????
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
    //??????????????????
    //??????????????????
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
    //??????
    @BindView(R.id.lv_seven_day_blood_sugar)
    ListView lvSevenDayBloodSugar;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    //?????????
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
    //??????
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
    //??????????????????
    @BindView(R.id.ll_piss_tiny_albumin)
    LinearLayout llPissTinyAlbumin;
    @BindView(R.id.et_piss_tiny_albumin)
    ColorEditText etPissTinyAlbumin;
    //????????????
    @BindView(R.id.ll_serum)
    LinearLayout llSerum;
    @BindView(R.id.et_serum)
    ColorEditText etSerum;
    //?????????
    @BindView(R.id.ll_liver)
    LinearLayout llLiver;
    @BindView(R.id.tv_follow_up_visit_liver)
    TextView tvFollowUpVisitLiver;
    @BindView(R.id.rv_liver)
    RecyclerView rvLiver;
    @BindView(R.id.tv_liver_desc)
    ColorEditText tvLiverDesc;
    //??????????????????(TSH)
    @BindView(R.id.ll_tsh)
    LinearLayout llTsh;
    @BindView(R.id.et_tsh)
    ColorEditText etTsh;
    //?????????
    @BindView(R.id.ll_electrocardiogram)
    LinearLayout llHeart;
    @BindView(R.id.tv_follow_up_visit_electrocardiogram)
    TextView tvFollowUpVisitElectrocardiogram;
    @BindView(R.id.rv_heart)
    RecyclerView rvHeart;
    @BindView(R.id.tv_heart_desc)
    ColorEditText tvHeartDesc;
    //????????????
    @BindView(R.id.ll_fundus_photography)
    LinearLayout llEyes;
    @BindView(R.id.tv_follow_up_visit_fundus_photography)
    TextView tvFollowUpVisitFundusPhotography;
    @BindView(R.id.rv_eyes)
    RecyclerView rvEyes;
    @BindView(R.id.tv_eyes_desc)
    ColorEditText tvEyesDesc;
    //????????????????????????
    @BindView(R.id.ll_neuropathy_related_examination)
    LinearLayout llNeuropathy;
    @BindView(R.id.tv_follow_up_visit_neuropathy_related_examination)
    TextView tvFollowUpVisitNeuropathyRelatedExamination;
    @BindView(R.id.rv_neuropathy)
    RecyclerView rvNeuropathy;
    @BindView(R.id.tv_neuropathy_desc)
    ColorEditText tvNeuropathyDesc;

    //??????????????????
    private FollowUpVisitRvNew2Adapter adapter;
    private List<String> selectDatas = new ArrayList<>();

    private FollowUpVisitLvAdapterThree lvAdapter;

    //??????
    private List<FollowUpVisitBloodSugarAddBean> sevenSugarList;
    private FollowUpVisitBloodSugarAdd2Adapter sugarAddAdapter;
    //?????????
    private AddPic2Adapter liverAddPicAdapter;
    private List<String> liverPics;
    //?????????
    private AddPic2Adapter heartAddPicAdapter;
    private List<String> heartPics;
    //????????????
    private AddPic2Adapter eyesAddPicAdapter;
    private List<String> eyesPics;
    //????????????
    private AddPic2Adapter neuropathyAddPicAdapter;
    private List<String> neuropathyPics;

    /**
     * ??????????????????????????????
     */
    private NewFollowUpVisitDetailBean allInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_follow_up_visit_blood_sugar_submit;
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
     * ??????
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

    private void setIsClickable() {
        if (5 == allInfo.getStatus()) {
            /**
             * ??????
             */
            //???????????? ?????????
            etPhysicalSignHigh.setEnabled(false);
            etPhysicalSignLow.setEnabled(false);
            //????????????
            etHeight.setEnabled(false);
            etWeight.setEnabled(false);
            //??????????????????
            disableRadioGroup(rgPhysicalSign);
            //??????
            etPhysicalOther.setEnabled(false);

            /**
             * ????????????
             */
            //?????????
            etSmoke.setEnabled(false);
            etDrink.setEnabled(false);
            etSportCount.setEnabled(false);
            etSportTime.setEnabled(false);
            etMainFood.setEnabled(false);
            disableRadioGroup(rgLifeStylePsychologicalAdjust);
            disableRadioGroup(rgLifeStyleFollowDoctor);

            /**
             * ????????????
             */
            etEmptySugar.setEnabled(false);
            etBloodRed.setEnabled(false);
            llCheckTime.setEnabled(false);

            /**
             * ????????????
             */
            disableRadioGroup(rgDrugUseYieldTo);
            disableRadioGroup(rgDrugUseAdverseDrugReaction);
            disableRadioGroup(rgDrugUseHypoglycemicReaction);
            disableRadioGroup(rgDrugUseClassify);
            disableRadioGroup(rgDrugUseClassifySecond);
            etInsulin.setEnabled(false);
            etInsulinDosage.setEnabled(false);
            /**
             * ???????????????
             */
            etPissCommonOneLeft.setEnabled(false);
            etPissCommonOneRight.setEnabled(false);

            etPissCommonTwoLeft.setEnabled(false);
            etPissCommonTwoRight.setEnabled(false);

            etPissCommonThreeRight.setEnabled(false);
            etPissCommonThreeLeft.setEnabled(false);

            etPissCommonFourLeft.setEnabled(false);
            etPissCommonFourRight.setEnabled(false);

            etPissCommonFiveLeft.setEnabled(false);
            etPissCommonFiveRight.setEnabled(false);

            etPissCommonSixLeft.setEnabled(false);

            /**
             * ??????
             */
            etBloodFatOne.setEnabled(false);
            etBloodFatTwo.setEnabled(false);
            etBloodFatThree.setEnabled(false);
            etBloodFatFour.setEnabled(false);

            /**
             * ??????????????????
             */
            etPissTinyAlbumin.setEnabled(false);

            /**
             * ????????????
             */
            etSerum.setEnabled(false);

            etSummaryMainQuestion.setEnabled(false);
            etSummaryMainPurpose.setEnabled(false);

            tvNeuropathyDesc.setEnabled(false);
            tvEyesDesc.setEnabled(false);
            tvHeartDesc.setEnabled(false);
        }
    }

    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }


    /**
     * ???????????????????????????BMI???
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
                    DecimalFormat df = new DecimalFormat("0.0");
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
                    DecimalFormat df = new DecimalFormat("0.0");
                    tvBmi.setText(df.format(bmi));
                }
            }
        });
    }

    /**
     * ??????????????????
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
                        allInfo = data;
                        setIsClickable();
                        setVisibleOrGone(data);
                        setFifteenData(data);
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
     * ???????????????
     */
    private void initTitle() {
        tvPageTitle.setText("????????????");
    }


    /**
     * ??????????????????
     *
     * @param data
     */
    private void setFifteenData(NewFollowUpVisitDetailBean data) {
        //??????
        List<String> symptom = data.getSymptom();//??????
        if (symptom != null) {
            setRvSymptom(symptom);
        } else {
            List<String> symptomNew = new ArrayList<>();
            setRvSymptom(symptomNew);
        }
        //??????
        setPhysicalSign(data);
        //????????????
        setLifeStyle(data);
        //????????????
        setExamine(data);
        //????????????
        setDrugUse(data);
        //??????
        setBloodSugar(data);
        //?????????
        setPissCommon(data);
        //????????????
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
        //?????????????????????
        if (!TextUtils.isEmpty(data.getUrinemicro())) {
            etPissTinyAlbumin.setText(data.getUrinemicro());
        }
        //??????????????????
        if (!TextUtils.isEmpty(data.getCreatinine())) {
            etSerum.setText(data.getCreatinine());
        }
        //???????????????
        setLiver(data);
        //????????????????????????(TSH)
        if (!TextUtils.isEmpty(data.getStimulating())) {
            etTsh.setText(data.getStimulating());
        }
        //???????????????
        setHeart(data);
        //??????????????????
        setEyes(data);
        //????????????????????????
        setNeuropathy(data);
    }


    /**
     * ????????????
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
        //???????????????
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
        //??????????????????
        if (1 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(false);
            rgAdverseDrugReactionNot.setChecked(true);
        } else if (2 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(true);
            rgAdverseDrugReactionNot.setChecked(false);
        }
        //???????????????
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
        //??????????????????
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
        //?????????
        etInsulin.setText(insulin);
        etInsulinDosage.setText(insulinnum);
        //??????????????????
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
            lvAdapter = new FollowUpVisitLvAdapterThree(getPageContext(), list, medicdetail, this, allInfo.getStatus() + "");
            lvDrugUse.setAdapter(lvAdapter);
        }
    }


    /**
     * ????????????
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
     * ??????????????????
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
     * ????????????
     *
     * @param data
     */
    private void setPhysicalSign(NewFollowUpVisitDetailBean data) {
        String systolic = data.getSystolic();//?????????
        String diastolic = data.getDiastolic();//?????????
        double height = data.getHeight();
        double weight = data.getWeight();
        int pulsation = data.getPulsation();//?????????????????????1?????????2?????????
        String elseX = data.getOther();//??????
        etPhysicalSignHigh.setText(systolic);//?????????
        etPhysicalSignLow.setText(diastolic);//?????????
        if (0.0 == height) {
            etHeight.setText("");//??????
        } else {
            etHeight.setText(height + "");//??????
        }
        etWeight.setText(weight + "");//??????
        if (0.0 == weight) {
            etWeight.setText("");
        } else {
            etWeight.setText(weight + "");//??????
        }
        if (!(0.0 == height) && !(0.0 == weight)) {
            double doubleHeightM = height / 100;
            double bmi = weight / (doubleHeightM * doubleHeightM);
            DecimalFormat df = new DecimalFormat("0.00");
            tvBmi.setText(df.format(bmi) + "");//BMI
        } else {
            tvBmi.setText("");
        }
        //?????????????????????1?????????2?????????
        //?????????????????????1?????????2?????????
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

    //    /**
    //     * ????????????
    //     *
    //     * @param symptom
    //     */
    //    private void setRvSymptom(List<String> symptom) {
    //        String[] stringArray = getResources().getStringArray(R.array.follow_up_visit_blood_sugar_symptom);
    //        List<String> list = Arrays.asList(stringArray);
    //        adapter = new FollowUpVisitRvNewAdapter(list, symptom, allInfo.getStatus() + "");
    //        rvSymptom.setLayoutManager(new GridLayoutManager(getPageContext(), 3));
    //        rvSymptom.setAdapter(adapter);
    //        //???????????????
    //        selectDatas.addAll(symptom);
    //    }

    /**
     * ????????????
     *
     * @param symptom
     */
    private void setRvSymptom(List<String> symptom) {
        String[] stringArray = getResources().getStringArray(R.array.follow_up_visit_blood_sugar_symptom);
        List<String> list = Arrays.asList(stringArray);
        adapter = new FollowUpVisitRvNew2Adapter(getPageContext(), R.layout.item_include_follow_up_visit_symptom, list, symptom, allInfo.getStatus() + "");
        int symptomItemHeight = 50;
        int symptomSpanCount = 3;
        ViewGroup.LayoutParams params = rvSymptom.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ConvertUtils.dp2px(list.size() / symptomSpanCount * symptomItemHeight);
        rvSymptom.setLayoutParams(params);
        rvSymptom.setLayoutManager(new GridLayoutManager(getPageContext(), symptomSpanCount));
        rvSymptom.setAdapter(adapter);
        //???????????????
        selectDatas.addAll(symptom);
        //???????????????
        if (!"5".equals(allInfo.getStatus() + "")) {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!adapter.selectMap.get(position)) {
                        adapter.selectMap.put(position, true);//??????map??????????????????
                        adapter.notifyItemChanged(position);
                        selectDatas.add(position + 1 + "");
                    } else {
                        adapter.selectMap.put(position, false);//??????map??????????????????
                        adapter.notifyItemChanged(position);
                        selectDatas.remove(position + 1 + "");
                    }
                }
            });
        }
    }


    /**
     * ??????????????????
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
            //??????
            if (list.contains("1")) {
                llSymptom.setVisibility(View.VISIBLE);
            }
            //??????
            if (list.contains("2")) {
                llPhysicalSign.setVisibility(View.VISIBLE);
            }
            //????????????
            if (list.contains("3")) {
                llLifeStyle.setVisibility(View.VISIBLE);
            }
            //????????????
            if (list.contains("4")) {
                llExamine.setVisibility(View.VISIBLE);
            }
            //????????????
            if (list.contains("5")) {
                llDrugUse.setVisibility(View.VISIBLE);
            }
            //??????
            if (list.contains("6")) {
                llBloodSugar.setVisibility(View.VISIBLE);
            }
            //?????????
            if (list.contains("7")) {
                llPissCommon.setVisibility(View.VISIBLE);
            }
            //??????
            if (list.contains("8")) {
                llBloodFat.setVisibility(View.VISIBLE);
            }
            //??????????????????
            if (list.contains("9")) {
                llPissTinyAlbumin.setVisibility(View.VISIBLE);
            }
            //????????????
            if (list.contains("10")) {
                llSerum.setVisibility(View.VISIBLE);
            }
            //?????????
            if (list.contains("11")) {
                llLiver.setVisibility(View.VISIBLE);
            }
            //??????????????????(TSH)
            if (list.contains("12")) {
                llTsh.setVisibility(View.VISIBLE);
            }
            //?????????
            if (list.contains("13")) {
                llHeart.setVisibility(View.VISIBLE);
            }
            //????????????
            if (list.contains("14")) {
                llEyes.setVisibility(View.VISIBLE);
            }
            //????????????????????????
            if (list.contains("15")) {
                llNeuropathy.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * ????????????
     *
     * @param data
     */
    private void setBloodSugar(NewFollowUpVisitDetailBean data) {
        List<List<String>> sugars = data.getSugars();
        //??????????????????
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
        String status = data.getStatus() + "";
        sugarAddAdapter = new FollowUpVisitBloodSugarAdd2Adapter(getPageContext(),
                R.layout.item_seven_and_thirty_bottom_for_follow_up_visit, sevenSugarList, this, status);
        lvSevenDayBloodSugar.setAdapter(sugarAddAdapter);
    }

    /**
     * ???????????????
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

    //???????????????
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
        liverAddPicAdapter = new AddPic2Adapter(this, new AddPic2Adapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_LIVER);
            }
        });
        //liverAddPicAdapter.setList(liverPics);
        liverAddPicAdapter.setList(liverPics);
        liverAddPicAdapter.setStatus(data.getStatus() + "");
        rvLiver.setAdapter(liverAddPicAdapter);
        rvLiver.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //???????????????
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

        heartAddPicAdapter = new AddPic2Adapter(this, new AddPic2Adapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_HEART);
            }
        });
        heartAddPicAdapter.setList(heartPics);
        heartAddPicAdapter.setStatus(data.getStatus() + "");
        rvHeart.setAdapter(heartAddPicAdapter);
        rvHeart.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //??????????????????
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
        eyesAddPicAdapter = new AddPic2Adapter(this, new AddPic2Adapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_EYES);
            }
        });
        eyesAddPicAdapter.setList(eyesPics);
        eyesAddPicAdapter.setStatus(data.getStatus() + "");
        rvEyes.setAdapter(eyesAddPicAdapter);
        rvEyes.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //??????????????????
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
        neuropathyAddPicAdapter = new AddPic2Adapter(this, new AddPic2Adapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_NEUROPATHY);
            }
        });
        neuropathyAddPicAdapter.setList(neuropathyPics);
        neuropathyAddPicAdapter.setStatus(data.getStatus() + "");
        rvNeuropathy.setAdapter(neuropathyAddPicAdapter);
        rvNeuropathy.setLayoutManager(new GridLayoutManager(this, 3));
    }


    /**
     * ???????????????
     *
     * @param status
     */
    private void setTitleRight(int status) {
        if (5 == status) {
            tvMore.setVisibility(View.GONE);
        } else {
            tvMore.setText("??????");
            tvMore.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.bt_back, R.id.tv_more, R.id.ll_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_more:
                toDoSubmit();
                break;
            case R.id.ll_select_time:
                selectTime();
                break;
            default:
                break;
        }
    }

    /**
     * ????????????
     */
    private void selectTime() {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvCheckTime.setText(content);
            }
        });
    }

    /**
     * ????????????
     */
    private void toDoSubmitSuggest() {
        String question = etSummaryMainQuestion.getText().toString().trim();
        String mainPurpose = etSummaryMainPurpose.getText().toString().trim();
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("vid", id);
        map.put("paquest", question);
        Log.i("yys", "question==" + question);
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


    /**
     * ????????????
     */
    private void toDoSubmit() {
        String id = getIntent().getStringExtra("id");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("access_token", SPStaticUtils.getString("token"));
        builder.addFormDataPart("version", ConstantParam.SERVER_VERSION);
        builder.addFormDataPart("id", id);
        //        builder.addFormDataPart("data[status]", allInfo.getStatus() + "");
        builder.addFormDataPart("data[status]", "4");
        //????????????
        //???????????????????????????
        //??????
        if (View.VISIBLE == llSymptom.getVisibility()) {
            StringBuilder sbSymptom = new StringBuilder();
            for (int i = 0; i < selectDatas.size(); i++) {
                sbSymptom.append(selectDatas.get(i));
                sbSymptom.append(",");
            }
            if (sbSymptom.length() > 0) {
                String symptom = sbSymptom.substring(0, sbSymptom.length() - 1);
                builder.addFormDataPart("data[symptom]", symptom);
            }
            Log.i("yys", "sbSymptom===size" + selectDatas.size());
        }
        //??????
        if (View.VISIBLE == llPhysicalSign.getVisibility()) {
            String systolic = etPhysicalSignHigh.getText().toString().trim();
            String diastolic = etPhysicalSignLow.getText().toString().trim();
            String height = etHeight.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();
            builder.addFormDataPart("data[systolic]", systolic);
            builder.addFormDataPart("data[diastolic]", diastolic);
            builder.addFormDataPart("data[height]", height);
            builder.addFormDataPart("data[weight]", weight);
            if (rbPhysicalSignNot.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "1");
            } else if (rbPhysicalSignHave.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "2");
            } else if (rbPhysicalSignNotSecond.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "3");
            }
            String other = etPhysicalOther.getText().toString().trim();
            builder.addFormDataPart("data[other]", other);
        }
        //????????????
        if (View.VISIBLE == llLifeStyle.getVisibility()) {
            String smoke = etSmoke.getText().toString().trim();
            builder.addFormDataPart("data[smok]", smoke);
            String drink = etDrink.getText().toString().trim();
            builder.addFormDataPart("data[drink]", drink);
            String sportCount = etSportCount.getText().toString().trim();
            builder.addFormDataPart("data[sportnum]", sportCount);
            String sportTime = etSportTime.getText().toString().trim();
            builder.addFormDataPart("data[sporttime]", sportTime);
            String mainFood = etMainFood.getText().toString().trim();
            builder.addFormDataPart("data[mainfood]", mainFood);
            //????????????1:?????? 2????????? 3??????
            if (rbPsychologicalAdjustWell.isChecked()) {
                builder.addFormDataPart("data[psychological]", "1");
            } else if (rbPsychologicalAdjustCommon.isChecked()) {
                builder.addFormDataPart("data[psychological]", "2");
            } else if (rbPsychologicalAdjustBad.isChecked()) {
                builder.addFormDataPart("data[psychological]", "3");
            }
            //??????????????????1??????2??????3???
            if (rbFollowDoctorWell.isChecked()) {
                builder.addFormDataPart("data[behavior]", "1");
            } else if (rbFollowDoctorCommon.isChecked()) {
                builder.addFormDataPart("data[behavior]", "2");
            } else if (rbFollowDoctorBad.isChecked()) {
                builder.addFormDataPart("data[behavior]", "3");
            }
        }
        //????????????
        if (View.VISIBLE == llExamine.getVisibility()) {
            String emptySugar = etEmptySugar.getText().toString().trim();
            builder.addFormDataPart("data[fastingbloodsugar]", emptySugar);
            String bloodRed = etBloodRed.getText().toString().trim();
            builder.addFormDataPart("data[hemoglobin]", bloodRed);
            String checkTime = tvCheckTime.getText().toString().trim();
            builder.addFormDataPart("data[examinetime]", checkTime);
        }
        //????????????
        //??????????????? 1??????2?????????3?????????
        if (View.VISIBLE == llDrugUse.getVisibility()) {
            if (rbDrugUseYieldRule.isChecked()) {
                builder.addFormDataPart("data[compliance]", "1");
            } else if (rbDrugUseYieldGap.isChecked()) {
                builder.addFormDataPart("data[compliance]", "2");
            } else if (rbDrugUseYieldNotTakeMedicine.isChecked()) {
                builder.addFormDataPart("data[compliance]", "3");
            }
            if (rgAdverseDrugReactionHave.isChecked()) {
                builder.addFormDataPart("data[drugreactions]", "2");
            } else if (rgAdverseDrugReactionNot.isChecked()) {
                builder.addFormDataPart("data[drugreactions]", "1");
            }
            if (rbHypoglycemicReactionNot.isChecked()) {
                builder.addFormDataPart("data[reaction]", "1");
            } else if (rbHypoglycemicReactionOnce.isChecked()) {
                builder.addFormDataPart("data[reaction]", "2");
            } else if (rbHypoglycemicReactionOften.isChecked()) {
                builder.addFormDataPart("data[reaction]", "3");
            }
            if (rbClassifySatisfaction.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "1");
            } else if (rbClassifySatisfactionNot.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "2");
            } else if (rbClassifyAdverseReaction.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "3");
            } else if (rbClassifyComplication.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "4");
            }

            String insulin = etInsulin.getText().toString().trim();
            String insulinDosage = etInsulinDosage.getText().toString().trim();
            builder.addFormDataPart("data[insulin]", insulin);
            builder.addFormDataPart("data[insulinnum]", insulinDosage);
            //?????????????????????
            HashMap<Integer, String> saveMapName = lvAdapter.saveMapName;
            HashMap<Integer, String> saveMapCount = lvAdapter.saveMapCount;
            HashMap<Integer, String> saveMapDosage = lvAdapter.saveMapDosage;
            for (int i = 0; i < saveMapName.size(); i++) {
                String name = saveMapName.get(i);
                String count = saveMapCount.get(i);
                String dosage = saveMapDosage.get(i);
                builder.addFormDataPart("medicdetail[]", defaultValue(name));
                builder.addFormDataPart("medicdetail[]", defaultValue(count));
                builder.addFormDataPart("medicdetail[]", defaultValue(dosage));
            }
        }
        //???????????????????????????
        //?????????????????????
        //??????
        if (View.VISIBLE == llBloodSugar.getVisibility()) {
            for (int i = 0; i < sevenSugarList.size(); i++) {
                FollowUpVisitBloodSugarAddBean bean = sevenSugarList.get(i);
                builder.addFormDataPart("sugars[]", defaultValue(bean.getOne()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getTwo()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getThree()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getFour()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getFive()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getSix()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getSeven()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getEight()));
            }
        }
        //?????????
        if (View.VISIBLE == llPissCommon.getVisibility()) {
            String One = etPissCommonOneLeft.getText().toString().trim();
            String Two = etPissCommonOneRight.getText().toString().trim();
            String Three = etPissCommonTwoLeft.getText().toString().trim();
            String Four = etPissCommonTwoRight.getText().toString().trim();
            String Five = etPissCommonThreeLeft.getText().toString().trim();
            String Six = etPissCommonThreeRight.getText().toString().trim();
            String Seven = etPissCommonFourLeft.getText().toString().trim();
            String Eight = etPissCommonFourRight.getText().toString().trim();
            String Nine = etPissCommonFiveLeft.getText().toString().trim();
            String Ten = etPissCommonFiveRight.getText().toString().trim();
            String Elven = etPissCommonSixLeft.getText().toString().trim();
            builder.addFormDataPart("data[routine][]", defaultValue(One));
            builder.addFormDataPart("data[routine][]", defaultValue(Two));
            builder.addFormDataPart("data[routine][]", defaultValue(Three));
            builder.addFormDataPart("data[routine][]", defaultValue(Four));
            builder.addFormDataPart("data[routine][]", defaultValue(Five));
            builder.addFormDataPart("data[routine][]", defaultValue(Six));
            builder.addFormDataPart("data[routine][]", defaultValue(Seven));
            builder.addFormDataPart("data[routine][]", defaultValue(Eight));
            builder.addFormDataPart("data[routine][]", defaultValue(Nine));
            builder.addFormDataPart("data[routine][]", defaultValue(Ten));
            builder.addFormDataPart("data[routine][]", defaultValue(Elven));
        }
        //??????
        if (View.VISIBLE == llBloodFat.getVisibility()) {
            String one = etBloodFatOne.getText().toString().trim();
            String two = etBloodFatTwo.getText().toString().trim();
            String three = etBloodFatThree.getText().toString().trim();
            String four = etBloodFatFour.getText().toString().trim();
            builder.addFormDataPart("data[bloodfat][]", defaultValue(one));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(two));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(three));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(four));
        }
        //??????????????????
        if (View.VISIBLE == llPissTinyAlbumin.getVisibility()) {
            String urinemicro = etPissTinyAlbumin.getText().toString().trim();
            builder.addFormDataPart("data[urinemicro]", urinemicro);
        }
        //????????????
        if (View.VISIBLE == llSerum.getVisibility()) {
            String creatinine = etSerum.getText().toString().trim();
            builder.addFormDataPart("data[creatinine]", creatinine);
        }
        //?????????????????????
        if (View.VISIBLE == llLiver.getVisibility()) {
            if (liverPics != null) {
                if (liverPics.size() > 0) {
                    if (liverPics.get(0).contains("http:")) {
                        builder.addFormDataPart("liverfun1", liverPics.get(0));
                    } else {
                        File file = new File(liverPics.get(0));
                        builder.addFormDataPart("liverfun1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun1", "");
                }
                if (liverPics.size() > 1) {
                    if (liverPics.get(1).contains("http:")) {
                        builder.addFormDataPart("liverfun2", liverPics.get(1));
                    } else {
                        File file = new File(liverPics.get(1));
                        builder.addFormDataPart("liverfun2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun2", "");
                }
                if (liverPics.size() > 2) {
                    if (liverPics.get(2).contains("http:")) {
                        builder.addFormDataPart("liverfun3", liverPics.get(2));
                    } else {
                        File file = new File(liverPics.get(2));
                        builder.addFormDataPart("liverfun3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun3", "");
                }
            }
            String liverDesc = tvLiverDesc.getText().toString().trim();
            builder.addFormDataPart("data[livercon]", liverDesc);
        }
        //??????????????????
        if (View.VISIBLE == llTsh.getVisibility()) {
            String stimulating = etTsh.getText().toString().trim();
            builder.addFormDataPart("data[stimulating]", stimulating);
        }
        //?????????
        if (View.VISIBLE == llHeart.getVisibility()) {
            Log.i("yys", "heartPics==" + heartPics.size());
            if (heartPics != null) {
                if (heartPics.size() > 0) {
                    if (heartPics.get(0).contains("http:")) {
                        builder.addFormDataPart("heartpic1", heartPics.get(0));
                    } else {
                        File file = new File(heartPics.get(0));
                        builder.addFormDataPart("heartpic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                        Log.i("yys", "file==" + file);
                    }
                } else {
                    builder.addFormDataPart("heartpic1", "");
                }
                if (heartPics.size() > 1) {
                    if (heartPics.get(1).contains("http:")) {
                        builder.addFormDataPart("heartpic2", heartPics.get(1));
                    } else {
                        File file = new File(heartPics.get(1));
                        builder.addFormDataPart("heartpic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("heartpic2", "");
                }
                if (heartPics.size() > 2) {
                    if (heartPics.get(2).contains("http:")) {
                        builder.addFormDataPart("heartpic3", heartPics.get(2));
                    } else {
                        File file = new File(heartPics.get(2));
                        builder.addFormDataPart("heartpic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("heartpic3", "");
                }
            }
            String heartcontent = tvHeartDesc.getText().toString().trim();
            builder.addFormDataPart("data[heartcontent]", heartcontent);
        }
        //????????????
        if (View.VISIBLE == llEyes.getVisibility()) {
            if (eyesPics != null) {
                if (eyesPics.size() > 0) {
                    if (eyesPics.get(0).contains("http:")) {
                        builder.addFormDataPart("eyespic1", eyesPics.get(0));
                    } else {
                        File file = new File(eyesPics.get(0));
                        builder.addFormDataPart("eyespic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic1", "");
                }
                if (eyesPics.size() > 1) {
                    if (eyesPics.get(1).contains("http:")) {
                        builder.addFormDataPart("eyespic2", eyesPics.get(1));
                    } else {
                        File file = new File(eyesPics.get(1));
                        builder.addFormDataPart("eyespic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic2", "");
                }
                if (eyesPics.size() > 2) {
                    if (eyesPics.get(2).contains("http:")) {
                        builder.addFormDataPart("eyespic3", eyesPics.get(2));
                    } else {
                        File file = new File(eyesPics.get(2));
                        builder.addFormDataPart("eyespic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic3", "");
                }
            }
            String eyescontent = tvEyesDesc.getText().toString().trim();
            builder.addFormDataPart("data[eyescontent]", eyescontent);
        }
        //??????????????????
        if (View.VISIBLE == llNeuropathy.getVisibility()) {
            if (neuropathyPics != null) {
                if (neuropathyPics.size() > 0) {
                    if (neuropathyPics.get(0).contains("http:")) {
                        builder.addFormDataPart("neuropathypic1", neuropathyPics.get(0));
                    } else {
                        File file = new File(neuropathyPics.get(0));
                        builder.addFormDataPart("neuropathypic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic1", "");
                }
                if (neuropathyPics.size() > 1) {
                    if (neuropathyPics.get(1).contains("http:")) {
                        builder.addFormDataPart("neuropathypic2", neuropathyPics.get(1));
                    } else {
                        File file = new File(neuropathyPics.get(1));
                        builder.addFormDataPart("neuropathypic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic2", "");
                }
                if (neuropathyPics.size() > 2) {
                    if (neuropathyPics.get(2).contains("http:")) {
                        builder.addFormDataPart("neuropathypic3", neuropathyPics.get(2));
                    } else {
                        File file = new File(neuropathyPics.get(2));
                        builder.addFormDataPart("neuropathypic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic3", "");
                }
            }
            String neuropathycontent = tvNeuropathyDesc.getText().toString().trim();
            builder.addFormDataPart("data[neuropathycontent]", neuropathycontent);
        }
        //?????????????????????
        Request request = new Request.Builder()
                .url(XyUrl.FOLLOW_ADD)
                .post(builder.build())
                .build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogUtils.e("onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                toDoSubmitSuggest();
                LogUtils.e("onResponse");
                //                sendHandlerMessage(ADD_FOLLOW_OVER);
            }
        });
    }

    private String defaultValue(String value) {
        if (!TextUtils.isEmpty(value) && value != null) {
            return value;
        } else {
            return "";
        }
    }

    private void compress(List<String> photos, int type) {

        Luban.with(this)
                .load(photos)
                .ignoreBy(1024)
                .setTargetDir("")
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif") || path.contains("http"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    List<String> list = new ArrayList<>();

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        String path = file.getPath();
                        list.add(path);
                        if (photos.size() == list.size()) {
                            Message msg = Message.obtain();
                            msg.what = COMPRESS_OVER;
                            msg.arg1 = type;
                            msg.obj = list;
                            sendHandlerMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    @Override
    public void onAdapterClick(View view, int position) {

    }

    @Override
    public void onAdapterClickSearch(View view, int position) {
        switch (view.getId()) {
            case R.id.fl_one:
                showEdit(0, position);
                break;
            case R.id.fl_two:
                showEdit(1, position);
                break;
            case R.id.fl_three:
                showEdit(2, position);
                break;
            case R.id.fl_four:
                showEdit(3, position);
                break;
            case R.id.fl_five:
                showEdit(4, position);
                break;
            case R.id.fl_six:
                showEdit(5, position);
                break;
            case R.id.fl_seven:
                showEdit(6, position);
                break;
            case R.id.fl_eight:
                showEdit(7, position);
                break;
            default:
                break;
        }
    }

    /**
     * ??????????????????
     *
     * @param timeHorizontal (?????????)
     * @param timeVertical   (????????? ?????????)
     */
    private void showEdit(int timeHorizontal, int timeVertical) {
        FollowUpVisitBloodSugarAddBean bean = sevenSugarList.get(timeVertical);
        DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????",
                "???????????????", text1 -> {
                    switch (timeHorizontal) {
                        case 0:
                            bean.setOne(text1);
                            break;
                        case 1:
                            bean.setTwo(text1);
                            break;
                        case 2:
                            bean.setThree(text1);
                            break;
                        case 3:
                            bean.setFour(text1);
                            break;
                        case 4:
                            bean.setFive(text1);
                            break;
                        case 5:
                            bean.setSix(text1);
                            break;
                        case 6:
                            bean.setSeven(text1);
                            break;
                        case 7:
                            bean.setEight(text1);
                            break;
                    }
                    sugarAddAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case COMPRESS_OVER:
                List<String> list = ListCastUtils.castList(msg.obj, String.class);
                switch (msg.arg1) {
                    case LIVER:
                        liverPics = list;
                        break;
                    case HEART:
                        heartPics = list;
                        break;
                    case EYES:
                        eyesPics = list;
                        break;
                    case NEUROPATHY:
                        neuropathyPics = list;
                        break;
                }
                break;
            case ADD_FOLLOW_OVER:
                //??????????????????
                FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");
                ToastUtils.showShort("????????????");
                EventBusUtils.post(new EventMessage<>(ConstantParam.EventCode.FOLLOW_UP_VISIT_SUBMIT));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    photoPopu.dismiss();
                    if (file != null) {
                        String path = file.getPath();
                        liverPics.add(path);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_LIVER:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (pathList != null && 1 == pathList.size()) {
                        String path = pathList.get(0);
                        //???????????????????????????
                        if (mPosition < liverPics.size()) {
                            liverPics.set(mPosition, path);
                        } else if (liverPics.size() < 3) {
                            liverPics.add(path);
                        }
                        compress(liverPics, LIVER);
                        liverAddPicAdapter.setList(liverPics);
                        liverAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_HEART:
                    List<String> pathList1 = Matisse.obtainPathResult(data);
                    if (pathList1 != null && 1 == pathList1.size()) {
                        String path = pathList1.get(0);
                        //???????????????????????????
                        if (mPosition < heartPics.size()) {
                            heartPics.set(mPosition, path);
                        } else if (heartPics.size() < 3) {
                            heartPics.add(path);
                        }
                        compress(heartPics, HEART);
                        heartAddPicAdapter.setList(heartPics);
                        heartAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_EYES:
                    List<String> pathList2 = Matisse.obtainPathResult(data);
                    if (pathList2 != null && 1 == pathList2.size()) {
                        String path = pathList2.get(0);
                        //???????????????????????????
                        if (mPosition < eyesPics.size()) {
                            eyesPics.set(mPosition, path);
                        } else if (eyesPics.size() < 3) {
                            eyesPics.add(path);
                        }
                        compress(eyesPics, EYES);
                        eyesAddPicAdapter.setList(eyesPics);
                        eyesAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_NEUROPATHY:
                    List<String> pathList3 = Matisse.obtainPathResult(data);
                    if (pathList3 != null && 1 == pathList3.size()) {
                        String path = pathList3.get(0);
                        //???????????????????????????
                        if (mPosition < neuropathyPics.size()) {
                            neuropathyPics.set(mPosition, path);
                        } else if (neuropathyPics.size() < 3) {
                            neuropathyPics.add(path);
                        }
                        compress(neuropathyPics, NEUROPATHY);
                        neuropathyAddPicAdapter.setList(neuropathyPics);
                        neuropathyAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    void newAddPic(int type) {
        PermissionUtils
                .permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        selectPhoto(type);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("??????????????????????????????????????????");
                    }
                }).request();
    }

    /**
     * ??????????????????
     */
    private void selectPhoto(int type) {
        Matisse.from(FollowUpVisitBloodSugarSubmit2Activity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider"))
                //?????????1???
                .maxSelectable(1)
                //?????????????????? ???????????????????????????????????? ???????????????7.0 FileProvider
                //.capture(true)
                //.captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider", "Test"))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(type);
    }

}
