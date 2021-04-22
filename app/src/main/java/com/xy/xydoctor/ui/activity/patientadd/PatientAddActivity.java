package com.xy.xydoctor.ui.activity.patientadd;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.edittext.IdNumberKeyListener;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.bean.NewSearchUserBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.imp.EmptyTextWatcher;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.MainActivity;
import com.xy.xydoctor.ui.activity.mydevice.HomeScanActivity;
import com.xy.xydoctor.utils.PickerUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 添加患者
 * 作者: LYD
 * 创建日期: 2019/2/28 14:07
 */
public class PatientAddActivity extends BaseActivity {
    private static final int SELECT_DEVICE = 10010;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.rl_birthday)
    RelativeLayout rlBirthday;
    //主任端新增 Start
    @BindView(R.id.tv_select_doctor)
    TextView tvSelectDoctor;
    @BindView(R.id.rl_select_doctor)
    RelativeLayout rlSelectDoctor;
    //主任端新增 End
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.rl_group)
    RelativeLayout rlGroup;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.img_sex_male)
    ImageView imgSexMale;
    @BindView(R.id.tv_sex_male)
    TextView tvSexMale;
    @BindView(R.id.ll_sex_male)
    LinearLayout llSexMale;
    @BindView(R.id.img_sex_female)
    ImageView imgSexFemale;
    @BindView(R.id.tv_sex_female)
    TextView tvSexFemale;
    @BindView(R.id.ll_sex_female)
    LinearLayout llSexFemale;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.et_select_device)
    EditText etSelectDevice;
    @BindView(R.id.rl_select_device)
    RelativeLayout rlSelectDevice;
    //医生选择
    private List<DoctorListBean> doctorList = new ArrayList<>();
    private ArrayList<String> doctorNameList = new ArrayList<>();
    private int doctorId;
    //分组选择
    private List<GroupListBean> groupList;
    private ArrayList<String> groupNameList;
    private int groupId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        llSexFemale.setTag(0);
        llSexMale.setTag(0);
        setTitle("添加患者");
        judgeFrom();
        setUserInfo();
        setIdNumberChangeListener();
    }


    /**
     * 设置身份证号监听
     */
    private void setIdNumberChangeListener() {
        etIdNumber.setKeyListener(new IdNumberKeyListener());
        etIdNumber.addTextChangedListener(new EmptyTextWatcher() {
            @Override
            public void afterTextChanged(@Nullable Editable s) {
                super.afterTextChanged(s);
                if (!TextUtils.isEmpty(s) && 18 == s.toString().length()) {
                    String birthday = extractYearMonthDayOfIdCard(s.toString());
                    tvBirthday.setText(birthday);
                }
            }
        });
    }

    private void judgeFrom() {
        String from = getIntent().getStringExtra("from");
        if ("homeDoctor".equals(from)) {
            getDoctorList();
            rlSelectDoctor.setVisibility(View.VISIBLE);
        } else {
            getGroupList("homePatient");
            rlSelectDoctor.setVisibility(View.GONE);
        }
    }

    private void setUserInfo() {
        NewSearchUserBean.DataBean user = (NewSearchUserBean.DataBean) getIntent().getSerializableExtra("userBean");
        tvTel.setText(user.getUsername());
        if (!TextUtils.isEmpty(user.getNickname())) {
            etName.setText(user.getNickname());
            etName.setSelection(user.getNickname().length());
        }
        int sex = user.getSex();
        if (1 == sex) {
            llSexMale.setTag(1);
            llSexFemale.setTag(0);
            imgSexMale.setImageResource(R.drawable.patient_add_sex_checked);
            imgSexFemale.setImageResource(R.drawable.patient_add_sex_uncheck);
            tvSexMale.setTextColor(ColorUtils.getColor(R.color.main_red));
            tvSexFemale.setTextColor(ColorUtils.getColor(R.color.gray_text));
        } else if (2 == sex) {
            llSexMale.setTag(0);
            llSexFemale.setTag(1);
            imgSexMale.setImageResource(R.drawable.patient_add_sex_uncheck);
            imgSexFemale.setImageResource(R.drawable.patient_add_sex_checked);
            tvSexMale.setTextColor(ColorUtils.getColor(R.color.gray_text));
            tvSexFemale.setTextColor(ColorUtils.getColor(R.color.main_red));
        }
    }

    private void getGroupList(String type) {
        HashMap<String, Object> map = new HashMap<>();
        if ("homeDoctor".equals(type)) {
            map.put("userid", doctorId);
        }
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> list) throws Exception {
                        groupList = list;
                        groupNameList = new ArrayList<>();
                        if (groupList != null) {
                            for (int i = 0; i < groupList.size(); i++) {
                                groupNameList.add(groupList.get(i).getGname());
                            }
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getDoctorList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_LIST)
                .addAll(map)
                .asResponseList(DoctorListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DoctorListBean>>() {
                    @Override
                    public void accept(List<DoctorListBean> list) throws Exception {
                        doctorList = list;
                        if (doctorList != null && doctorList.size() > 0) {
                            for (int i = 0; i < doctorList.size(); i++) {
                                doctorNameList.add(doctorList.get(i).getDocname());
                            }
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.ll_sex_male, R.id.ll_sex_female, R.id.rl_select_device, R.id.img_select_device, R.id.rl_group, R.id.bt_add, R.id.rl_birthday, R.id.rl_select_doctor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sex_male:
                llSexMale.setTag(1);
                llSexFemale.setTag(0);
                imgSexMale.setImageResource(R.drawable.patient_add_sex_checked);
                imgSexFemale.setImageResource(R.drawable.patient_add_sex_uncheck);
                tvSexMale.setTextColor(ColorUtils.getColor(R.color.main_red));
                tvSexFemale.setTextColor(ColorUtils.getColor(R.color.gray_text));
                break;
            case R.id.ll_sex_female:
                llSexMale.setTag(0);
                llSexFemale.setTag(1);
                imgSexMale.setImageResource(R.drawable.patient_add_sex_uncheck);
                imgSexFemale.setImageResource(R.drawable.patient_add_sex_checked);
                tvSexMale.setTextColor(ColorUtils.getColor(R.color.gray_text));
                tvSexFemale.setTextColor(ColorUtils.getColor(R.color.main_red));
                break;
            case R.id.img_select_device:
            case R.id.rl_select_device:
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getPageContext(), HomeScanActivity.class);
                                intent.putExtra("type", "selectDevice");
                                startActivityForResult(intent, SELECT_DEVICE);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();
                break;
            //选择出生日期
            case R.id.rl_birthday:
                KeyboardUtils.hideSoftInput(rlBirthday);
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvBirthday.setText(content);
                    }
                });
                break;
            //选择医生
            case R.id.rl_select_doctor:
                KeyboardUtils.hideSoftInput(rlSelectDoctor);
                PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
                    @Override
                    public void execEvent(String content, int position) {
                        tvSelectDoctor.setText(content);
                        doctorId = doctorList.get(position).getUserid();
                        getGroupList("homeDoctor");
                    }
                }, doctorNameList);
                break;
            //选择分组
            case R.id.rl_group:
                if (groupList != null) {
                    KeyboardUtils.hideSoftInput(rlGroup);
                    PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
                        @Override
                        public void execEvent(String content, int position) {
                            tvGroup.setText(content);
                            groupId = groupList.get(position).getGid();
                        }
                    }, groupNameList);
                } else {
                    ToastUtils.showShort("请先选择医生");
                }
                break;
            case R.id.bt_add:
                toAddUser();
                break;
        }
    }

    private void toAddUser() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return;
        }
        String height = etHeight.getText().toString().trim();
        if (TextUtils.isEmpty(height)) {
            ToastUtils.showShort("请输入身高");
            return;
        }
        String idNumber = etIdNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(idNumber)) {
            if (!RegexUtils.isIDCard18(idNumber)) {
                ToastUtils.showShort("请输入合法的身份证号");
                return;
            }
        }
        String from = getIntent().getStringExtra("from");
        if ("homeDoctor".equals(from)) {
            String doctorName = tvSelectDoctor.getText().toString().trim();
            if (TextUtils.isEmpty(doctorName)) {
                ToastUtils.showShort("请选择医生");
                return;
            }
        }
        String groupName = tvGroup.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            ToastUtils.showShort("请选择分组");
            return;
        }
        toDoAddUser();
    }

    private void toDoAddUser() {
        NewSearchUserBean.DataBean user = (NewSearchUserBean.DataBean) getIntent().getSerializableExtra("userBean");
        String userID = user.getUserId();
        String reCode = user.getRegistercode();
        String name = etName.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String birthday = tvBirthday.getText().toString().trim();
        String imei = etSelectDevice.getText().toString().trim();
        String postSexString;
        int sexTag = (int) llSexMale.getTag();
        if (1 == sexTag) {
            postSexString = "1";
        } else {
            postSexString = "2";
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userID);
        map.put("gid", groupId);
        map.put("registercode", reCode);
        map.put("nickname", name);
        map.put("height", height);
        map.put("weight", weight);
        map.put("birthtime", birthday);
        map.put("idcard", idNumber);
        map.put("sex", postSexString);
        map.put("imei", imei);
        String from = getIntent().getStringExtra("from");
        if ("homeDoctor".equals(from)) {
            map.put("docid", doctorId);
        }
        RxHttp.postForm(XyUrl.ADD_USER)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String updateBean) throws Exception {
                        ToastUtils.showShort("添加成功");
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.PATIENT_ADD));
                        ActivityUtils.finishToActivity(MainActivity.class, false);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) {
                        switch (error.getErrorCode()) {
                            //添加消息已发送
                            case 20008:
                                finish();
                                ActivityUtils.finishToActivity(MainActivity.class, false);
                                break;
                        }
                    }
                });
    }


    /**
     * 根据身份证号提取出生日期
     *
     * @param idNumber 省份证号
     * @return 生日（yyyy-MM-dd）
     */
    public String extractYearMonthDayOfIdCard(String idNumber) {
        String year = null;
        String month = null;
        String day = null;
        //正则匹配身份证号是否是正确的
        if (RegexUtils.isIDCard18(idNumber)) {
            year = idNumber.substring(6, 10);
            month = idNumber.substring(10, 12);
            day = idNumber.substring(12, 14);
        }
        return year + "-" + month + "-" + day;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SELECT_DEVICE:
                    String result = data.getStringExtra("result");
                    etSelectDevice.setText(result);
                    break;
                default:
                    break;
            }
        }
    }
}
