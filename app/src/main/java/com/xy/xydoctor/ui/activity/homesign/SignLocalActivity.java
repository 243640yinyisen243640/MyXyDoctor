package com.xy.xydoctor.ui.activity.homesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.RxTimer;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FamilyUserAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.FamilySelectUserBean;
import com.xy.xydoctor.bean.PersonalRecordBean;
import com.xy.xydoctor.bean.SignDoctorInfoBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.CityPickerUtils;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.utils.photo.PhotoUtils;
import com.xy.xydoctor.view.popup.FamilySelectUserPopup;
import com.xy.xydoctor.view.popup.HomeSignSuccessPopup;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  面签
 * 作者: LYD
 * 创建日期: 2019/12/30 15:26
 */
public class SignLocalActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int SIGN_ADD_PATIENT = 10086;
    private static final int SIGN_ADD_DOCTOR = 10087;
    private static final int REQUEST_CAMERA = 10088;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_relation)
    TextView tvRelation;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;

    @BindView(R.id.tv_select_family_group)
    TextView tvSelectFamilyGroup;
    @BindView(R.id.et_build_organ)
    EditText etBuildOrgan;
    @BindView(R.id.et_person_in_charge_name)
    EditText etPersonInChargeName;
    @BindView(R.id.ll_patient)
    LinearLayout llPatient;
    @BindView(R.id.img_sign_add_patient)
    ImageView imgSignAddPatient;
    @BindView(R.id.tv_sign_add_patient)
    TextView tvSignAddPatient;

    @BindView(R.id.img_sign_add_doctor)
    ImageView imgSignAddDoctor;
    @BindView(R.id.tv_sign_add_doctor)
    TextView tvSignAddDoctor;
    @BindView(R.id.img_patient_and_doctor_group_photo)
    ImageView imgPatientAndDoctorGroupPhoto;


    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;

    private String relationId;
    private String pId = "";
    private String cId = "";
    private String dId = "";


    //选择家庭组开始
    private FamilySelectUserPopup familySelectUserPopup;
    private EditText etSearch;
    private TextView tvSearch;
    private ListView lvList;
    private FamilyUserAdapter adapter;
    private List<FamilySelectUserBean> list;
    private int familyId;
    //选择家庭组结束

    private File filePatientAndDoctorGroupPhoto;
    private String filePatientPath;
    private String fileDoctorPath;

    private HomeSignSuccessPopup homeSignSuccessPopup;

    /**
     * 获取患者信息
     *
     * @param patientId
     */
    private void getUserInfo(String patientId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", patientId);
        RxHttp.postForm(XyUrl.PERSONAL_RECORD)
                .addAll(map)
                .asResponse(PersonalRecordBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<PersonalRecordBean>() {
                    @Override
                    public void accept(PersonalRecordBean data) throws Exception {
                        setPatientInfo(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    private void setPatientInfo(PersonalRecordBean data) {
        String nickname = data.getNickname();
        String idcard = data.getIdcard();
        String tel = data.getTel();
        String nativeplace = data.getNativeplace();
        String address = data.getAddress();
        pId = data.getJbprov();
        cId = data.getJbcity();
        dId = data.getJbdist();
        etName.setText(nickname);
        etIdNumber.setText(idcard);
        etPhone.setText(tel);
        tvAddress.setText(nativeplace);
        etAddressDetail.setText(address);
    }

    private void getDoctorInfo() {
        String docId = SPStaticUtils.getString("docId");
        HashMap<String, String> map = new HashMap<>();
        map.put("docid", docId);
        RxHttp.postForm(XyUrl.SIGN_DOCTOR_INFO)
                .addAll(map)
                .asResponse(SignDoctorInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SignDoctorInfoBean>() {
                    @Override
                    public void accept(SignDoctorInfoBean data) throws Exception {
                        setDoctorInfo(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void setDoctorInfo(SignDoctorInfoBean data) {
        String docname = data.getDocname();
        String hospitalname = data.getHospitalname();
        etPersonInChargeName.setText(docname);
        etBuildOrgan.setText(hospitalname);
    }

    private void initTakePhoto() {
        PhotoUtils.getInstance().init(this, false, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                //当拍照或从图库选取图片成功后回调
                filePatientAndDoctorGroupPhoto = outputFile;
                Glide.with(Utils.getApp()).load(outputUri).into(imgPatientAndDoctorGroupPhoto);
            }
        });
    }

    private void initPopup() {
        familySelectUserPopup = new FamilySelectUserPopup(getPageContext());
        etSearch = familySelectUserPopup.findViewById(R.id.et_search);
        tvSearch = familySelectUserPopup.findViewById(R.id.tv_search);
        lvList = familySelectUserPopup.findViewById(R.id.lv_list);
        lvList.setOnItemClickListener(this);
        tvSearch.setOnClickListener(this);
        homeSignSuccessPopup = new HomeSignSuccessPopup(getPageContext());
    }

    /**
     * 获取选择家庭组列表
     *
     * @param keyWord
     */
    private void getFamilyUsers(String keyWord) {
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", keyWord);
        RxHttp.postForm(XyUrl.FAMILY_LIST)
                .addAll(map)
                .asResponseList(FamilySelectUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FamilySelectUserBean>>() {
                    @Override
                    public void accept(List<FamilySelectUserBean> familySelectUserBeanList) throws Exception {
                        list = familySelectUserBeanList;
                        if (list != null && list.size() > 0) {
                            adapter = new FamilyUserAdapter(getPageContext(), R.layout.item_family_user, list);
                            lvList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.tv_relation, R.id.ll_select_address, R.id.ll_select_family_group,
            R.id.img_sign_add_patient, R.id.img_sign_add_doctor, R.id.img_patient_and_doctor_group_photo, R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_relation:
                KeyboardUtils.hideSoftInput(tvRelation);
                showSelectRelation();
                break;
            case R.id.ll_select_address:
                showSelectAddress();
                break;
            case R.id.ll_select_family_group:
                familySelectUserPopup.showPopupWindow();
                break;
            case R.id.img_sign_add_patient:
                intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_PATIENT);
                break;
            case R.id.img_sign_add_doctor:
                intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_DOCTOR);
                break;
            case R.id.img_patient_and_doctor_group_photo:
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                PhotoUtils.getInstance().takePhoto(SignLocalActivity.this);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();
                break;
            case R.id.tv_submit:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        String name = etName.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String addressDetail = etAddressDetail.getText().toString().trim();
        String relationName = tvRelation.getText().toString().trim();

        if (TextUtils.isEmpty(filePatientPath)) {
            ToastUtils.showShort("请添加患者签名照片");
            return;
        }
        if (TextUtils.isEmpty(fileDoctorPath)) {
            ToastUtils.showShort("请添加医生签名照片");
            return;
        }
        if (filePatientAndDoctorGroupPhoto == null) {
            ToastUtils.showShort("请上传医患合照");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("nickname", name);
        map.put("relation", relationId);
        map.put("relationname", relationName);
        map.put("idcard", idNumber);
        map.put("tel", phone);
        map.put("nativeplace", address);
        map.put("jbprov", pId);
        map.put("jbcity", cId);
        map.put("jbdist", dId);
        map.put("address", addressDetail);
        map.put("familyid", familyId + "");
        map.put("type", "2");
        RxHttp.postForm(XyUrl.FAMILY_DOCTOR_ADD)
                .addAll(map)
                .addFile("user_sign", new File(filePatientPath))
                .addFile("doc_sign", new File(fileDoctorPath))
                .addFile("group_photo", filePatientAndDoctorGroupPhoto)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        homeSignSuccessPopup.showPopupWindow();
                        RxTimer rxTimer = new RxTimer();
                        rxTimer.timer(3 * 1000, new RxTimer.RxAction() {
                            @Override
                            public void action(long number) {
                                homeSignSuccessPopup.dismiss();
                                finish();
                            }
                        });
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void showSelectAddress() {
        CityPickerUtils.show(getPageContext(), new CityPickerUtils.CityPickerCallBack() {
            @Override
            public void execEvent(String pName, String pId,
                                  String cName, String cId,
                                  String dName, String did) {
                tvAddress.setText(pName + cName + dName);
                SignLocalActivity.this.pId = pId;
                SignLocalActivity.this.cId = cId;
                SignLocalActivity.this.dId = did;
            }
        });
    }

    private void showSelectRelation() {
        String[] stringArray = getResources().getStringArray(R.array.home_sign_relation);
        List<String> relationList = ArrayUtils.asList(stringArray);
        PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
            @Override
            public void execEvent(String content, int position) {
                tvRelation.setText(content);
                relationId = position + 1 + "";
            }
        }, relationList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SIGN_ADD_PATIENT:
                    filePatientPath = data.getStringExtra("savePath");
                    Glide.with(Utils.getApp()).load(filePatientPath).into(imgSignAddPatient);
                    break;
                case SIGN_ADD_DOCTOR:
                    fileDoctorPath = data.getStringExtra("savePath");
                    Glide.with(Utils.getApp()).load(fileDoctorPath).into(imgSignAddDoctor);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.e(TAG, "onItemClick");
        for (FamilySelectUserBean data : list) {
            data.setSelected(false);
        }
        //点击的设置为选中
        FamilySelectUserBean data = list.get(position);
        data.setSelected(true);
        //刷新
        adapter.notifyDataSetChanged();
        //设置值
        String nickname = data.getNickname();
        tvSelectFamilyGroup.setText(nickname);
        familyId = data.getId();
        familySelectUserPopup.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                //Log.e(TAG, "onClick");
                String keyWord = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtils.showShort("请输入手机号或者身份证号");
                    return;
                }
                getFamilyUsers(keyWord);
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_local;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("新增编辑");
        initPopup();
        getFamilyUsers("");
        initTakePhoto();
        getDoctorInfo();
        //判断是否来自扫一扫
        String patientId = getIntent().getStringExtra("patientId");
        if (!TextUtils.isEmpty(patientId)) {
            getUserInfo(patientId);
        }
    }
}
