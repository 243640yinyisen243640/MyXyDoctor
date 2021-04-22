package com.xy.xydoctor.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SignDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  签约患者信息页面
 * 作者: LYD
 * 创建日期: 2020/1/16 13:55
 */
public class SignPatientInfoActivity extends BaseActivity {
    private static final int SIGN_ADD_DOCTOR = 10087;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_relation)
    TextView tvRelation;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;

    @BindView(R.id.et_build_organ)
    EditText etBuildOrgan;
    @BindView(R.id.et_person_in_charge_name)
    EditText etPersonInChargeName;
    @BindView(R.id.img_sign_add_patient)
    ImageView imgSignAddPatient;
    @BindView(R.id.tv_sign_add_patient)
    TextView tvSignAddPatient;
    @BindView(R.id.ll_patient)
    LinearLayout llPatient;
    @BindView(R.id.img_sign_add_doctor)
    ImageView imgSignAddDoctor;
    @BindView(R.id.tv_sign_add_doctor)
    TextView tvSignAddDoctor;
    @BindView(R.id.tv_cancel)
    ColorTextView tvCancel;
    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;

    private String fileDoctorPath;


    /**
     * 获取
     */
    private void getSignData() {
        String id = getIntent().getStringExtra("id");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.HOME_SIGN_DETAIL)
                .addAll(map)
                .asResponse(SignDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SignDetailBean>() {
                    @Override
                    public void accept(SignDetailBean data) throws Exception {
                        setDetail(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 设置
     *
     * @param data
     */
    private void setDetail(SignDetailBean data) {
        String nickname = data.getNickname();
        etName.setText(nickname);
        etName.setEnabled(false);
        String relationname = data.getRelationname();
        tvRelation.setText(relationname);
        String idcard = data.getIdcard();
        etIdNumber.setText(idcard);
        etIdNumber.setEnabled(false);
        String tel = data.getTel();
        etPhone.setText(tel);
        etPhone.setEnabled(false);
        String nativeplace = data.getNativeplace();
        tvAddress.setText(nativeplace);
        String address = data.getAddress();
        etAddressDetail.setText(address);
        etAddressDetail.setEnabled(false);
        String hospitalname = data.getHospitalname();
        String docname = data.getDocname();
        etBuildOrgan.setText(hospitalname);
        etPersonInChargeName.setText(docname);
        String doc_sign = data.getDoc_sign();
        String user_sign = data.getUser_sign();
        Glide.with(Utils.getApp())
                .load(user_sign)
                .error(R.drawable.img_sign_add_patient)
                .placeholder(R.drawable.img_sign_add_patient)
                .into(imgSignAddPatient);

        Glide.with(Utils.getApp())
                .load(doc_sign)
                .error(R.drawable.img_sign_add_doctor)
                .placeholder(R.drawable.img_sign_add_doctor)
                .into(imgSignAddDoctor);
    }

    @OnClick({R.id.img_sign_add_doctor, R.id.tv_cancel, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_sign_add_doctor:
                Intent intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_DOCTOR);
                break;
            case R.id.tv_cancel:
                toDoOperate("1");
                break;
            case R.id.tv_submit:
                toDoOperate("2");
                break;
        }
    }


    /**
     * 1拒绝2同意
     *
     * @param status
     */
    private void toDoOperate(String status) {
        if ("2".equals(status)) {
            if (TextUtils.isEmpty(fileDoctorPath)) {
                ToastUtils.showShort("请添加医生签名照片");
                return;
            }
        }
        String id = getIntent().getStringExtra("id");
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        if ("2".equals(status)) {
            RxHttp.postForm(XyUrl.HOME_SIGN_DEAL)
                    .addAll(map)
                    .addFile("doc_sign", new File(fileDoctorPath))
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String data) throws Exception {
                            //String msg = data.getMsg();
                            ToastUtils.showShort("操作成功");
                            finish();
                            EventBusUtils.post(new EventMessage(ConstantParam.EventCode.SIGN_DEAL));
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        } else {
            RxHttp.postForm(XyUrl.HOME_SIGN_DEAL)
                    .addAll(map)
                    .asResponse(String.class)
                    .to(RxLife.toMain(this))
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String data) throws Exception {
                            //String msg = data.getMsg();
                            ToastUtils.showShort("操作成功");
                            finish();
                            EventBusUtils.post(new EventMessage(ConstantParam.EventCode.SIGN_DEAL));
                        }
                    }, new OnError() {
                        @Override
                        public void onError(ErrorInfo error) throws Exception {

                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
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
    protected int getLayoutId() {
        return R.layout.activity_sign_patient_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("患者信息");
        getSignData();
    }
}
