package com.xy.xydoctor.ui.activity.community_management;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HealthArchiveGroupLevelAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.HealthArchiveGroupLevelOneBean;
import com.xy.xydoctor.bean.HealthArchiveGroupLevelZeroBean;
import com.xy.xydoctor.bean.PersonalRecordBean;
import com.xy.xydoctor.bean.PersonalRecordMedicineHistoryDataBean;
import com.xy.xydoctor.bean.UserInfoBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.AdapterViewClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.healthrecordadd.PharmacyAddActivity;
import com.xy.xydoctor.utils.CityPickerUtils;
import com.xy.xydoctor.utils.DialogUtils;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.utils.TimeFormatUtils;
import com.xy.xydoctor.utils.XyScreenUtils;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * ??????: ?????????????????????
 * ??????: LYD
 * ????????????: 2019/3/4 14:19
 * ??????  userid??? username
 */
@BindEventBus
public class PatientHealthArchiveActivity extends BaseEventBusActivity implements AdapterViewClickListener, View.OnClickListener {
    @BindView(R.id.rv_health_archive)
    RecyclerView rvHealthArchive;

    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_person_age)
    TextView tvPersonAge;
    @BindView(R.id.tv_type)
    ColorTextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_type)
    LinearLayout llType;

    //???????????????
    @BindView(R.id.img_device_scan)
    ImageView imgDeviceScan;
    @BindView(R.id.ll_device_unbind)
    LinearLayout llDeviceUnbind;
    private PersonalRecordBean personalRecordBean;
    private int sexInt;
    private HealthArchiveGroupLevelAdapter adapter;
    //?????????
    private String tel;

    private String userid;
    private String username;
    private int isDead;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_health_archive;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("????????????");
        getTvMore().setText("????????????");
        userid = getIntent().getStringExtra("userid");
        username = getIntent().getStringExtra("username");
        isDead = getIntent().getIntExtra("isDead", 0);

        getTvMore().setOnClickListener(v -> {
            Intent intent = new Intent(getPageContext(), SpecialOperateActivity.class);

            intent.putExtra("username", username);
            intent.putExtra("userid", userid);
            intent.putExtra("isDead", isDead);
            startActivity(intent);
        });
        setTopInfo();
        setHideScan();
    }


    private void setHideScan() {
        imgDeviceScan.setVisibility(View.GONE);
        llDeviceUnbind.setVisibility(View.GONE);
    }

    /**
     * ??????????????????
     */
    private void setTopInfo() {
        llType.setVisibility(View.VISIBLE);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        RxHttp.postForm(XyUrl.GET_USER_INFO)
                .addAll(map)
                .asResponseList(UserInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<UserInfoBean>>() {
                    @Override
                    public void accept(List<UserInfoBean> myTreatPlanBeans) throws Exception {
                        List<UserInfoBean> data = myTreatPlanBeans;
                        UserInfoBean user = data.get(0);
                        if (1 == user.getSex()) {
                            Glide.with(Utils.getApp()).load(user.getPicture()).error(R.drawable.head_man).placeholder(R.drawable.head_man).into(imgHead);
                            imgSex.setImageResource(R.drawable.male);
                        } else {
                            Glide.with(Utils.getApp()).load(user.getPicture()).error(R.drawable.head_woman).placeholder(R.drawable.head_woman).into(imgHead);
                            imgSex.setImageResource(R.drawable.female);
                        }
                        if (TextUtils.isEmpty(user.getNickname())) {
                            tvPersonName.setText(user.getUsername());
                        } else {
                            tvPersonName.setText(user.getNickname());
                        }
                        tvPersonAge.setText(user.getAge() + "???");
                        //  // 1???1???  2???2???  3?????????  4 ??????
                        switch (user.getDiabeteslei()) {
                            case 1:
                                tvType.setText("1???");
                                break;
                            case 2:
                                tvType.setText("2???");
                                break;
                            case 3:
                                tvType.setText("??????");
                                break;
                            case 4:
                                tvType.setText("??????");
                                break;
                            default:
                                tvType.setText("??????");
                                break;
                        }
                        if (TextUtils.isEmpty(user.getDiabetesleitime())) {
                            tvTime.setText("????????????:" + "-- -- --");
                        } else {
                            tvTime.setText("????????????:" + user.getDiabetesleitime());
                        }
                        getPersonalShow(userid);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * ??????????????????
     *
     * @param userId
     */
    private void getPersonalShow(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userId + "");
        RxHttp.postForm(XyUrl.PERSONAL_RECORD)
                .addAll(map)
                .asResponse(PersonalRecordBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<PersonalRecordBean>() {
                    @Override
                    public void accept(PersonalRecordBean updateBean) throws Exception {
                        personalRecordBean = updateBean;
                        getHistory();
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
    private void getHistory() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        RxHttp.postForm(XyUrl.GET_MEDICINE_HISTORY)
                .addAll(map)
                .asResponseList(PersonalRecordMedicineHistoryDataBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<PersonalRecordMedicineHistoryDataBean>>() {
                    @Override
                    public void accept(List<PersonalRecordMedicineHistoryDataBean> userInfoBeans) throws Throwable {
                        addData(personalRecordBean, userInfoBeans);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        ToastUtils.cancel();
                        List<PersonalRecordMedicineHistoryDataBean> listEmpty = new ArrayList<>();
                        addData(personalRecordBean, listEmpty);
                    }
                });
    }


    /**
     * ??????????????????
     *
     * @param data
     * @param list
     */
    private void addData(PersonalRecordBean data, List<PersonalRecordMedicineHistoryDataBean> list) {
        List<HealthArchiveGroupLevelZeroBean> lv0 = new ArrayList<>();
        //??????????????????
        String[] stringRes = new String[]{"??????????????????", "????????????", "????????????",
                "???????????????", "?????????/?????????", "????????????", "?????????"};
        for (int i = 0; i < stringRes.length; i++) {
            lv0.add(new HealthArchiveGroupLevelZeroBean(stringRes[i]));
        }
        //??????????????????
        //??????????????????
        String[] stringResOneLeft = new String[]{
                "????????????", "??????", "??????", "????????????",
                "??????", "???????????????", "?????????????????????", "??????",
                "??????", "????????????", "????????????", "????????????", "????????????",
                "????????????", "??????", "??????", "??????????????????",
                "????????????", "????????????", "????????????", "???????????????",
                "?????????????????????", "??????", "????????????"};

        String nickname = data.getNickname();
        String petname = data.getPetname();
        String sex = data.getSex() + "";
        String birthTime = getTime(data.getBirthtime());

        String minzu = data.getMinzu();
        String diabeteslei = data.getDiabeteslei() + "";
        long diabetesleitime = data.getDiabetesleitime();
        String diabetesleiTime = getTime(diabetesleitime);

        String smoke = data.getSmoke() + "";

        String drink = data.getDrink() + "";
        String culture = data.getCulture() + "";
        String perofession = data.getProfession() + "";
        String marriage = data.getMarriage() + "";

        String bedrid = data.getBedrid() + "";
        String nativeplace = data.getNativeplace();
        String address = data.getAddress();
        String payWay = data.getZhifufangshi() + "";

        String idCard = data.getIdcard();
        String jzkahao = data.getJzkahao();
        String duju = data.getDuju() + "";
        String gxyzhenduan = data.getGxyzhenduan() + "";

        String gxyTime = getTime(data.getGxytime());
        String gestation = data.getGestation() + "";
        String gestationTime = getTime(data.getGestationtime());
        tel = data.getTel();


        String[] stringResOneRight = new String[]{
                nickname, petname, sex, birthTime,
                minzu, diabeteslei, diabetesleiTime, smoke,
                drink, culture, perofession, marriage, tel,
                bedrid, nativeplace, address, payWay,
                idCard, jzkahao, duju, gxyzhenduan,
                gxyTime, gestation, gestationTime};

        for (int i = 0; i < stringResOneLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = null;
            switch (i) {
                //??????
                case 2:
                    String sexGet = stringResOneRight[i];
                    if ("1".equals(sexGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                //??????????????? 1???1???  2???2???  3?????????  4??????
                case 5:
                    String tnbType = stringResOneRight[i];
                    switch (tnbType) {
                        case "0":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "1???");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "2???");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 7://??????   1??? 2???
                case 8://??????
                case 13://????????????
                case 19://??????
                case 22://??????
                    String yesOrNo = stringResOneRight[i];
                    if ("1".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                case 9://???????????? 1 ??????????????? 2?????? 3?????? 4???????????????
                    String cultureGet = stringResOneRight[i];
                    switch (cultureGet) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "???????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 10://1 ????????? 2 ????????? 3 ??????
                    String profession = stringResOneRight[i];
                    switch (profession) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 11://1?????? 2??????
                    String marriageGet = stringResOneRight[i];
                    if ("1".equals(marriageGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(marriageGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                case 16://??????????????????
                    String pay = stringResOneRight[i];
                    switch (pay) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "????????????????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????????????????????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "????????????????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "5":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "6":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "????????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "7":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "??????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 20: //???????????????
                    String gxyzhenduanGet = stringResOneRight[i];
                    if ("1".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("3".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "?????????");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                //????????????
                case 12:
                    lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], tel);
                    lv0.get(0).addSubItem(lv1);
                    break;
                default:
                    lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], stringResOneRight[i]);
                    lv0.get(0).addSubItem(lv1);
                    break;
            }
        }
        //??????????????????
        String[] stringResTwoLeft = new String[]{
                "??????", "??????", "BMI", "??????",
                "??????", "?????????", "?????????", "?????????",
                "????????????"};
        //????????????bmi
        double height = TurnsUtils.getDouble(data.getHeight(), 1);
        double weight = TurnsUtils.getDouble(data.getWeight(), 1);
        double heightM = height * 0.01;
        double bmi;
        String bmiStr;
        if ("?????????  ".equals(data.getWeight()) || "?????????  ".equals(data.getHeight())) {
            bmiStr = "";
        } else {
            bmi = weight / (heightM * heightM);
            //??????????????????
            String result = String.format("%.1f", bmi);
            bmiStr = result;
        }
        //???????????? ?????????
        double Waistline = TurnsUtils.getDouble(data.getWaistline(), 1);
        double Hipline = TurnsUtils.getDouble(data.getHipline(), 1);
        double wCompareH;
        String wCompareHStr;
        if ("?????????  ".equals(data.getWaistline()) || "?????????  ".equals(data.getHipline())) {
            wCompareHStr = "";
        } else {
            wCompareH = Waistline / Hipline;
            wCompareHStr = String.format("%.1f", wCompareH);
        }
        String[] stringResTwoRight = new String[]{
                data.getHeight(), data.getWeight(), bmiStr, data.getWaistline(),
                data.getHipline(), wCompareHStr, data.getSystolic(), data.getDiastolic(),
                data.getHeartrate()};
        String[] stringResTwoUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_physique_unit);
        for (int i = 0; i < stringResTwoLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResTwoLeft[i], stringResTwoRight[i], stringResTwoUnits[i]);
            lv0.get(1).addSubItem(lv1);
        }
        //??????????????????
        String[] stringResThreeLeft = new String[]{
                "OGTT2h??????", "H b A 1 c", "????????????", "????????????",
                "?????????2h????????????", "????????????", "????????????", "???????????????????????????"};
        String[] stringResThreeRight = new String[]{
                data.getXtogtt2h(), data.getXthbalc(), data.getXtsuiji(), data.getXtkongfu(),
                data.getXtcaihou(), data.getXtyejian(), data.getXtshuiqian(),
                data.getXtdi()};
        String[] stringResThreeUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_blood_sugar);
        for (int i = 0; i < stringResThreeLeft.length; i++) {
            if (i == 7) {
                String s = stringResThreeRight[i];
                if ("1".equals(s)) {
                    HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], "???");
                    lv0.get(2).addSubItem(lv1);
                } else {
                    HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], "???");
                    lv0.get(2).addSubItem(lv1);
                }
            } else {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], stringResThreeRight[i], stringResThreeUnits[i]);
                lv0.get(2).addSubItem(lv1);
            }
        }
        //???????????????   ????????????????????????  0???  1??????  2??????
        String[] stringResFourLeft = new String[]{
                "????????????(TC)", "????????????", "??????????????????(LDL-C)", "???????????????(ALT)",
                "???????????????(AST)", "????????????(T-BIL)", "??????????????????(HDL-C)", "???????????????(ALP)",
                "?????????(?????????)", "24??????????????????(24hUAE)", "????????????/??????(ACR)", "????????????(UAE)", "?????????(BUN)", "?????????????????????(Ccr)"};
        String[] stringResFourRight = new String[]{
                data.getSytc(), data.getSytg(), data.getSyldl(), data.getSyalt(),
                data.getSyast(), data.getSytbil(), data.getSyhdl(), data.getSyalp(),
                data.getSyncg(), data.getSyhuae(), data.getSyacr(), data.getSyuae(),
                data.getSynsd(), data.getSynsjg(), data.getSyxqjg()};
        String[] stringResFourUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_laboratory_unit);
        for (int i = 0; i < stringResFourLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFourLeft[i], stringResFourRight[i], stringResFourUnits[i]);
            lv0.get(3).addSubItem(lv1);
        }
        sexInt = data.getSex();
        if (1 == sexInt) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean("????????????(SRC)", data.getSyxqjg(), "mmol/L");
            lv0.get(3).addSubItem(lv1);
        } else {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean("????????????(SRC)", data.getSyxqjgg(), "mmol/L");
            lv0.get(3).addSubItem(lv1);
        }
        //?????????/?????????
        String[] stringResFiveLeft = new String[]{
                "???????????????", "????????????????????????", "?????????????????????", "???????????????????????????",
                "????????????", "????????????????????????", "????????????????????????"};
        String[] stringResFiveRight = new String[]{data.getNephropathy() + "", data.getRetina() + "", data.getNerve() + "", data.getLegs() + "",
                data.getDiabeticfoot() + "", data.getKetoacidosis() + "", data.getHypertonic() + ""};
        //1 ????????? 2 ????????? 3 ?????????
        for (int i = 0; i < stringResFiveLeft.length; i++) {
            String yesOrNo = stringResFiveRight[i];
            if ("1".equals(yesOrNo)) {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "?????????");
                lv0.get(4).addSubItem(lv1);
            } else if ("2".equals(yesOrNo)) {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "?????????");
                lv0.get(4).addSubItem(lv1);
            } else {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "?????????");
                lv0.get(4).addSubItem(lv1);
            }
        }


        //????????????
        String[] stringResSixLeft = new String[]{
                "?????????", "????????????", "?????????", "????????????",
                "??????????????????", "????????????????????????"};
        String[] stringResSixRight = new String[]{
                data.getHighblood() + "", data.getHlp() + "", data.getCoronary() + "", data.getCerebrovascular() + "",
                data.getDiabetes() + "", data.getAngiocarpy() + ""};
        for (int i = 0; i < stringResSixLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = null;
            switch (i) {
                case 0:
                    switch (data.getHighblood()) {
                        case 1://1 ????????? 2 ????????? 3 ?????????
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 1:
                    switch (data.getHlp()) {
                        case "1"://1 ????????? 2 ????????? 3 ?????????
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 2:
                    switch (data.getCoronary()) {
                        case "1"://1 ????????? 2 ????????? 3 ?????????
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 3:
                    switch (data.getCerebrovascular()) {
                        case 1://1 ????????? 2 ????????? 3 ?????????
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "?????????");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 4:
                case 5:
                    String yesOrNo = stringResSixRight[i];
                    if ("1".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "???");
                        lv0.get(5).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "???");
                        lv0.get(5).addSubItem(lv1);
                    }
                    break;
            }
        }


        //???????????????
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                PersonalRecordMedicineHistoryDataBean historyBean = list.get(i);
                String drugname = historyBean.getDrugname();
                String times = historyBean.getTimes();
                String dosage = historyBean.getDosage();
                String id = historyBean.getId() + "";
                String starttime = historyBean.getStarttime();
                String endtime = historyBean.getEndtime();
                HealthArchiveGroupLevelOneBean lv2 = new HealthArchiveGroupLevelOneBean(drugname, dosage, times, id, starttime, endtime);
                lv0.get(6).addSubItem(lv2);
            }
        }

        //??????0?????????
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        ArrayList<MultiItemEntity> multiItemEntityArrayList = new ArrayList<>(lv0);
        adapter = new HealthArchiveGroupLevelAdapter(getPageContext(), multiItemEntityArrayList, this);
        //??????FootView?????????
        adapter.addFooterView(getFooterView(this));
        rvHealthArchive.setAdapter(adapter);
        //??????????????????
        rvHealthArchive.setItemViewCacheSize(23);
        rvHealthArchive.setLayoutManager(linearLayoutManager);
    }

    private String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = "";
        if (0 == time) {
            strTime = "?????????";
        } else {
            strTime = TimeUtils.millis2String(time * 1000L, format);
        }
        return strTime;
    }


    /**
     * ???????????????
     *
     * @param listener
     * @return
     */
    private View getFooterView(View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.item_add_medicine, (ViewGroup) rvHealthArchive.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }


    @Override
    public void adapterViewClick(BaseViewHolder help) {
        TextView tvLeft = help.getView(R.id.tv_left);
        TextView tvRight = help.getView(R.id.tv_right);
        String text = tvLeft.getText().toString();
        switch (text) {
            case "????????????":
                //                showEditDialog("");
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("nickname", text1);
                });
                break;
            case "??????":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("petname", text1);
                });
                break;
            case "??????":
                ArrayList<String> sexList = new ArrayList<>();
                sexList.add("???");
                sexList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("sex", "1");
                        } else {
                            toDoSave("sex", "2");
                        }
                    }
                }, sexList);
                break;
            case "????????????":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("birthtime", timeS + "");
                    }
                });
                break;
            case "??????":
                String[] nations = Utils.getApp().getResources().getStringArray(R.array.nation_list);
                List<String> list = Arrays.asList(nations);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        toDoSave("minzu", content);
                    }
                }, list);
                break;
            case "???????????????"://1???1???  2???2???  3?????????  4 ??????
                ArrayList<String> diabetesTypeList = new ArrayList<>();
                diabetesTypeList.add("1???");
                diabetesTypeList.add("2???");
                diabetesTypeList.add("??????");
                diabetesTypeList.add("??????");
                diabetesTypeList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "1???":
                                toDoSave("diabeteslei", "1");
                                break;
                            case "2???":
                                toDoSave("diabeteslei", "2");
                                break;
                            case "??????":
                                toDoSave("diabeteslei", "3");
                                break;
                            case "??????":
                                toDoSave("diabeteslei", "4");
                                break;
                            case "???":
                                toDoSave("diabeteslei", "0");
                                break;
                        }

                    }
                }, diabetesTypeList);
                break;
            case "?????????????????????":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("diabetesleitime", timeS + "");

                    }
                });
                break;
            case "??????":
                ArrayList<String> smokeList = new ArrayList<>();
                smokeList.add("???");
                smokeList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("smoke", "1");
                        } else {
                            toDoSave("smoke", "2");
                        }
                    }
                }, smokeList);
                break;
            case "??????":
                ArrayList<String> drinkList = new ArrayList<>();
                drinkList.add("???");
                drinkList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("drink", "1");
                        } else {
                            toDoSave("drink", "2");
                        }
                    }
                }, drinkList);
                break;
            case "????????????"://1 ??????????????? 2 ?????? 3 ?????? 4 ???????????????
                ArrayList<String> cultureTypeList = new ArrayList<>();
                cultureTypeList.add("???????????????");
                cultureTypeList.add("??????");
                cultureTypeList.add("??????");
                cultureTypeList.add("???????????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "???????????????":
                                toDoSave("culture", "1");
                                break;
                            case "??????":
                                toDoSave("culture", "2");
                                break;
                            case "??????":
                                toDoSave("culture", "3");
                                break;
                            case "???????????????":
                                toDoSave("culture", "4");
                                break;
                        }
                    }
                }, cultureTypeList);
                break;
            case "????????????"://1 ????????? 2 ????????? 3 ??????
                ArrayList<String> professionTypeList = new ArrayList<>();
                professionTypeList.add("?????????");
                professionTypeList.add("?????????");
                professionTypeList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "?????????":
                                toDoSave("profession", "1");
                                break;
                            case "?????????":
                                toDoSave("profession", "2");
                                break;
                            case "?????????":
                                toDoSave("profession", "3");
                                break;
                        }
                    }
                }, professionTypeList);
                break;
            case "????????????"://???????????? 1 ?????? 2 ??????
                ArrayList<String> marriageList = new ArrayList<>();
                marriageList.add("??????");
                marriageList.add("??????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("??????".equals(content)) {
                            toDoSave("marriage", "1");
                        } else {
                            toDoSave("marriage", "2");
                        }
                    }
                }, marriageList);
                break;
            case "????????????":
                PhoneUtils.dial(tel);
                break;
            case "????????????"://1??? 2???
                ArrayList<String> bedridList = new ArrayList<>();
                bedridList.add("???");
                bedridList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("bedrid", "1");
                        } else {
                            toDoSave("bedrid", "2");
                        }
                    }
                }, bedridList);
                break;
            case "??????":
                showCityPickerView(tvRight);
                break;
            case "??????":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("address", text1);
                });
                break;
            case "??????????????????":// 1 ???????????????????????? 2 ?????????????????????????????? 3 ???????????????????????? 4 ???????????? 5 ???????????? 6 ???????????? 7 ??????
                ArrayList<String> payList = new ArrayList<>();
                payList.add("????????????????????????");
                payList.add("??????????????????????????????");
                payList.add("????????????????????????");
                payList.add("????????????");
                payList.add("????????????");
                payList.add("????????????");
                payList.add("??????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "????????????????????????":
                                toDoSave("zhifufangshi", "1");
                                break;
                            case "??????????????????????????????":
                                toDoSave("zhifufangshi", "2");
                                break;
                            case "????????????????????????":
                                toDoSave("zhifufangshi", "3");
                                break;
                            case "????????????":
                                toDoSave("zhifufangshi", "4");
                                break;
                            case "????????????":
                                toDoSave("zhifufangshi", "5");
                                break;
                            case "????????????":
                                toDoSave("zhifufangshi", "6");
                                break;
                            case "??????":
                                toDoSave("zhifufangshi", "7");
                                break;
                        }
                    }
                }, payList);
                break;
            case "????????????":
                DialogUtils.getInstance().showEditTextIdNumberDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    tvRight.setText(text1);
                    if (!RegexUtils.isIDCard18(text1)) {
                        ToastUtils.showShort("?????????????????????????????????");
                        return;
                    }
                    toDoSave("idcard", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "????????????", "?????????????????????", InputType.TYPE_CLASS_NUMBER, text1 -> {
                    tvRight.setText(text1);
                    toDoSave("jzkahao", text1);
                });
                break;
            case "????????????"://?????????1??? 2???
                ArrayList<String> dujuList = new ArrayList<>();
                dujuList.add("???");
                dujuList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("duju", "1");
                        } else {
                            toDoSave("duju", "2");
                        }
                    }
                }, dujuList);
                break;
            case "???????????????"://???????????????   1 ?????????  2 ?????????
                ArrayList<String> gxyzhenduanList = new ArrayList<>();
                gxyzhenduanList.add("?????????");
                gxyzhenduanList.add("?????????");
                gxyzhenduanList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSave("gxyzhenduan", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSave("gxyzhenduan", "2");
                        } else if ("?????????".equals(content)) {
                            toDoSave("gxyzhenduan", "3");
                        }
                    }
                }, gxyzhenduanList);
                break;
            case "?????????????????????":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("gxytime", timeS + "");
                    }
                });
                break;
            case "??????"://?????? ???1???  2???
                ArrayList<String> gestationList = new ArrayList<>();
                gestationList.add("???");
                gestationList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("gestation", "1");
                        } else {
                            toDoSave("gestation", "2");
                        }
                    }
                }, gestationList);
                break;
            case "????????????":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("gestationtime", timeS + "");
                    }
                });
                break;
            //????????????
            ////////////////////////////////////////////////////
            case "??????"://height
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    //tvRight.setText(text1);
                    SPStaticUtils.put("height", text1);
                    toDoSave("height", text1);
                    resetValue(help, tvRight, text1);
                    resetBmi(help);
                });
                break;
            case "??????"://weight
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    SPStaticUtils.put("weight", text1);
                    toDoSave("weight", text1);
                    resetBmi(help);
                });
                break;
            case "BMI"://??????,????????????
                String weight = SPStaticUtils.getString("weight");
                String height = SPStaticUtils.getString("height");
                break;
            case "??????"://waistline
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("waistline", text1);
                    SPStaticUtils.put("waistline", text1);
                    resetThr();
                });
                break;
            case "??????"://hipline
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????", "???????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("hipline", text1);
                    SPStaticUtils.put("hipline", text1);
                    resetThr();
                });
                break;
            case "?????????"://??????,????????????
                break;
            case "?????????"://systolic
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????", "??????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("systolic", text1);
                });
                break;
            case "?????????"://diastolic
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????", "??????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("diastolic", text1);
                });
                break;
            case "????????????"://heartrate
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("heartrate", text1);
                });
                break;
            //????????????
            //////////////////////////////////////////////////////////////////
            case "OGTT2h??????"://xtogtt2h
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "OGTT2h??????", "?????????OGTT2h??????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtogtt2h", text1);
                });
                break;
            case "H b A 1 c":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "H b A 1 c", "?????????H b A 1 c", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xthbalc", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtsuiji", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtkongfu", text1);
                });
                break;
            case "?????????2h????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????2h????????????", "??????????????????2h????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtcaihou", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtyejian", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtshuiqian", text1);
                });
                break;
            case "???????????????????????????"://??????
                ArrayList<String> xtogtt2hList = new ArrayList<>();
                xtogtt2hList.add("???");
                xtogtt2hList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSaveGlucose("xtdi", "1");
                        } else {
                            toDoSaveGlucose("xtdi", "2");
                        }
                    }
                }, xtogtt2hList);
                break;
            //????????????
            /////////////////////////////////////////////////////
            case "????????????(TC)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????(TC)", "?????????????????????(TC)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytc", text1);
                });
                break;
            case "????????????":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????", "?????????????????????", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytg", text1);
                });
                break;
            case "??????????????????(LDL-C)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????????????????(LDL-C)", "???????????????????????????(LDL-C)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syldl", text1);
                });
                break;
            case "???????????????(ALT)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "???????????????(ALT)", "????????????????????????(ALT)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syalt", text1);
                });
                break;
            case "???????????????(AST)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "???????????????(AST)", "????????????????????????(AST)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syast", text1);
                });
                break;
            case "????????????(T-BIL)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????(T-BIL)", "?????????????????????(T-BIL)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytbil", text1);
                });
                break;
            case "??????????????????(HDL-C)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "??????????????????(HDL-C)", "???????????????????????????(HDL-C)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syhdl", text1);
                });
                break;
            case "???????????????(ALP)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "???????????????(ALP)", "????????????????????????(ALP)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syalp", text1);
                });
                break;
            case "?????????(?????????)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????(?????????)", "??????????????????(?????????)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syncg", text1);
                });
                break;
            case "24??????????????????(24hUAE)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "24??????????????????(24hUAE)", "?????????24??????????????????(24hUAE)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syhuae", text1);
                });
                break;
            case "????????????/??????(ACR)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????/??????(ACR)", "?????????????????????/??????(ACR)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syacr", text1);
                });
                break;
            case "????????????(UAE)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????(UAE)", "?????????????????????(UAE)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syuae", text1);
                });
                break;
            case "?????????(BUN)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????(BUN)", "??????????????????(BUN)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("synsd", text1);
                });
                break;
            case "?????????????????????(Ccr)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "?????????????????????(Ccr)", "??????????????????????????????(Ccr)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("synsjg", text1);
                });
                break;
            case "????????????(SRC)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "????????????(SRC)", "?????????????????????(SRC)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    if (1 == sexInt) {
                        //??????
                        toDoSaveGlucose("syxqjg", text1);
                    } else {
                        //??????
                        toDoSaveGlucose("syxqjgg", text1);
                    }
                });
                break;
            //????????????
            ///////////////////////////////////////////
            case "???????????????"://nephropathy
                ArrayList<String> nephropathyList = new ArrayList<>();
                nephropathyList.add("?????????");
                nephropathyList.add("?????????");
                nephropathyList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("nephropathy", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("nephropathy", "2");
                        } else {
                            toDoSaveBINGFA("nephropathy", "3");
                        }
                    }
                }, nephropathyList);
                break;
            case "????????????????????????"://retina
                ArrayList<String> retinaList = new ArrayList<>();
                retinaList.add("?????????");
                retinaList.add("?????????");
                retinaList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("retina", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("retina", "2");
                        } else {
                            toDoSaveBINGFA("retina", "3");
                        }
                    }
                }, retinaList);
                break;
            case "?????????????????????"://nerve
                ArrayList<String> nerveList = new ArrayList<>();
                nerveList.add("?????????");
                nerveList.add("?????????");
                nerveList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("nerve", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("nerve", "2");
                        } else {
                            toDoSaveBINGFA("nerve", "3");
                        }
                    }
                }, nerveList);
                break;
            case "???????????????????????????"://legs
                ArrayList<String> legsList = new ArrayList<>();
                legsList.add("?????????");
                legsList.add("?????????");
                legsList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("legs", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("legs", "2");
                        } else {
                            toDoSaveBINGFA("legs", "3");
                        }
                    }
                }, legsList);
                break;
            case "????????????"://diabeticfoot
                ArrayList<String> diabeticfootList = new ArrayList<>();
                diabeticfootList.add("?????????");
                diabeticfootList.add("?????????");
                diabeticfootList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("diabeticfoot", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("diabeticfoot", "2");
                        } else {
                            toDoSaveBINGFA("diabeticfoot", "3");
                        }
                    }
                }, diabeticfootList);
                break;
            case "????????????????????????"://ketoacidosis
                ArrayList<String> ketoacidosList = new ArrayList<>();
                ketoacidosList.add("?????????");
                ketoacidosList.add("?????????");
                ketoacidosList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("ketoacidosis", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("ketoacidosis", "2");
                        } else {
                            toDoSaveBINGFA("ketoacidosis", "3");
                        }
                    }
                }, ketoacidosList);
                break;
            case "????????????????????????"://hypertonic
                ArrayList<String> hypertonicList = new ArrayList<>();
                hypertonicList.add("?????????");
                hypertonicList.add("?????????");
                hypertonicList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("hypertonic", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("hypertonic", "2");
                        } else {
                            toDoSaveBINGFA("hypertonic", "3");
                        }
                    }
                }, hypertonicList);
                break;
            //????????????
            /////////////////////////////////////
            case "?????????"://highblood
                ArrayList<String> highbloodList = new ArrayList<>();
                highbloodList.add("?????????");
                highbloodList.add("?????????");
                highbloodList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("highblood", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("highblood", "2");
                        } else {
                            toDoSaveBINGFA("highblood", "3");
                        }
                    }
                }, highbloodList);
                break;
            case "????????????"://hlp
                ArrayList<String> hlpList = new ArrayList<>();
                hlpList.add("?????????");
                hlpList.add("?????????");
                hlpList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("hlp", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("hlp", "2");
                        } else {
                            toDoSaveBINGFA("hlp", "3");
                        }
                    }
                }, hlpList);
                break;
            case "?????????"://coronary
                ArrayList<String> coronaryList = new ArrayList<>();
                coronaryList.add("?????????");
                coronaryList.add("?????????");
                coronaryList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("coronary", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("coronary", "2");
                        } else {
                            toDoSaveBINGFA("coronary", "3");
                        }
                    }
                }, coronaryList);
                break;
            case "????????????"://cerebrovascular
                ArrayList<String> cerebrovascularList = new ArrayList<>();
                cerebrovascularList.add("?????????");
                cerebrovascularList.add("?????????");
                cerebrovascularList.add("?????????");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("?????????".equals(content)) {
                            toDoSaveBINGFA("cerebrovascular", "1");
                        } else if ("?????????".equals(content)) {
                            toDoSaveBINGFA("cerebrovascular", "2");
                        } else {
                            toDoSaveBINGFA("cerebrovascular", "3");
                        }
                    }
                }, cerebrovascularList);
                break;
            case "??????????????????"://diabetes
                ArrayList<String> diabetesList = new ArrayList<>();
                diabetesList.add("???");
                diabetesList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("diabetes", "1");
                        } else {
                            toDoSave("diabetes", "2");
                        }
                    }
                }, diabetesList);
                break;
            case "????????????????????????"://angiocarpy
                ArrayList<String> angiocarpyList = new ArrayList<>();
                angiocarpyList.add("???");
                angiocarpyList.add("???");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("???".equals(content)) {
                            toDoSave("angiocarpy", "1");
                        } else {
                            toDoSave("angiocarpy", "2");
                        }
                    }
                }, angiocarpyList);
                break;
        }
    }

    /**
     * ???????????????
     */
    private void showEditDialog(String msg) {

        Dialog dialog = new Dialog(getPageContext(), R.style.HuaHanSoft_Dialog_Base);
        View view = View.inflate(getPageContext(), R.layout.activity_user_info_dialog, null);
        EditText msgEditText = view.findViewById(R.id.tv_dialog_msg);
        TextView cancelTextView = view.findViewById(R.id.tv_dialog_cancel);
        TextView sureTextView = view.findViewById(R.id.tv_dialog_sure);

        msgEditText.setText(msg);
        //  msgEditText.setSelection(msg.length());
        //??????14?????????
        msgEditText.setMaxWidth(14);
        dialog.setContentView(view);
        android.view.WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = XyScreenUtils.screenWidth(getPageContext()) - XyScreenUtils.dip2px(getPageContext(), 1);
        dialog.getWindow().setAttributes(attributes);
        cancelTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        sureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String nick = msgEditText.getText().toString().trim();
                if (TextUtils.isEmpty(nick)) {
                    ToastUtils.showShort("");

                    return;
                }

                dialog.dismiss();
            }
        });
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showInputMethod();
            }
        }, 100);

    }


    private void showInputMethod() {
        //??????????????????
        InputMethodManager inputManager = (InputMethodManager) getPageContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //????????????Android???????????????
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //????????????
            case R.id.ll_add:
                Intent intent = new Intent(getPageContext(), PharmacyAddActivity.class);
                intent.putExtra("type", "1");
                intent.putExtra("userid", userid);
                startActivity(intent);
                break;
        }
    }


    /**
     * ?????????????????????
     */
    private void showCityPickerView(TextView textView) {
        CityPickerUtils.show(getPageContext(), new CityPickerUtils.CityPickerCallBack() {
            @Override
            public void execEvent(String pName, String pId,
                                  String cName, String cId,
                                  String dName, String did) {
                toDoSave("jbprov", pId);
                toDoSave("jbcity", cId);
                toDoSave("jbdist", did);
                toDoSave("nativeplace", pName + cName + dName);
                textView.setText(pName + cName + dName);
            }
        });

    }

    /**
     * ?????? ??????????????????  ????????????
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSave(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        RxHttp.postForm(XyUrl.PERSONAL_SAVE)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("????????????");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * ?????????/?????????  ????????????
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSaveBINGFA(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        RxHttp.postForm(XyUrl.PERSONAL_SAVE_BINGFA)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("????????????");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * ????????????  ???????????????
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSaveGlucose(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        RxHttp.postForm(XyUrl.PERSONAL_SAVE_GLUCOSE)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("????????????");
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void resetValue(BaseViewHolder help, TextView textView, String value) {
        HealthArchiveGroupLevelOneBean bean = (HealthArchiveGroupLevelOneBean) adapter.getItem(help.getAdapterPosition());
        String unit = bean.getUnit();
        SpannableString spannableString = new SpannableString(value + " " + unit);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.gray_text));
        spannableString.setSpan(colorSpan, 0, value.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void resetBmi(BaseViewHolder help) {
        String height = SPStaticUtils.getString("height");
        String weight = SPStaticUtils.getString("weight");
        NumberFormat nf = NumberFormat.getNumberInstance();
        //??????????????????
        nf.setMaximumFractionDigits(1);
        //??????????????????????????????????????????RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.HALF_UP);
        if (height != "" && weight != "") {
            String str = nf.format(Double.valueOf(weight) / Double.valueOf(height) / Double.valueOf(height) * 10000);
            resetValue(help, adapter.getTvBmi(), str);
            SpannableString spannableString = new SpannableString(str + " " + "kg/m??");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(getPageContext(), R.color.gray_text));
            spannableString.setSpan(colorSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            adapter.getTvBmi().setText(spannableString);
        }
    }

    private void resetThr() {
        String waistline = SPStaticUtils.getString("waistline");
        String hipline = SPStaticUtils.getString("hipline");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        if (waistline != "" && hipline != "") {
            String str = nf.format(Double.valueOf(waistline) / Double.valueOf(hipline));
            adapter.getTvThr().setText(str);
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.ADD_MEDICINE:
                getHistory();
                break;
        }
    }
}
