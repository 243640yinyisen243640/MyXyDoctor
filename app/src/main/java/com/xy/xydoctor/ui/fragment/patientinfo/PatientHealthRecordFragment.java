package com.xy.xydoctor.ui.fragment.patientinfo;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.base.fragment.BaseEventBusFragment;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyGridView;
import com.lyd.baselib.widget.view.MyListView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.HealthRecordGvAdapter;
import com.xy.xydoctor.adapter.PatientInfoBottomTopThreeAdapter;
import com.xy.xydoctor.bean.PersonalRecordBean;
import com.xy.xydoctor.bean.UserInfoBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.mydevice.MyDeviceListActivity;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 健康记录
 * 作者: LYD
 * 创建日期: 2019/3/4 14:19
 */
@BindEventBus
public class PatientHealthRecordFragment extends BaseEventBusFragment {
    @BindView(R.id.gv_health_record)
    MyGridView gvHealthRecord;
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
    @BindView(R.id.lv_patient_info_top_three)
    MyListView lvPatientInfoTopThree;
    //新增我的设备
    @BindView(R.id.img_device_scan)
    ImageView imgDeviceScan;
    @BindView(R.id.ll_device_unbind)
    LinearLayout llDeviceUnbind;
    @BindView(R.id.tv_device_number)
    TextView tvDeviceNumber;
    private String imei;
    private String guid;
    private String userid;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_health_record;
    }

    @Override
    protected void init(View rootView) {
        userid = getArguments().getString("userid");
        //设置11个记录
        setEightRecord(userid);
        //设置头部信息
        setTopInfo(userid);
        setBottomEightRecord(userid);
    }


    /**
     * 设置八个记录以下
     *
     * @param userid
     */
    private void setBottomEightRecord(String userid) {
        ArrayList<String> list = new ArrayList<>();
        list.add(userid);
        list.add(userid);
        list.add(userid);
        PatientInfoBottomTopThreeAdapter adapter = new PatientInfoBottomTopThreeAdapter(getPageContext(), R.layout.item_patient_info_bottom_top_three, list);
        lvPatientInfoTopThree.setAdapter(adapter);
    }

    /**
     * 设置八个记录
     *
     * @param userid
     */
    private void setEightRecord(String userid) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(i + "");
        }
        HealthRecordGvAdapter adapter = new HealthRecordGvAdapter(Utils.getApp(), R.layout.item_gv_health_record, list, userid);
        gvHealthRecord.setAdapter(adapter);
    }


    /**
     * 设置头部信息
     *
     * @param userid
     */
    private void setTopInfo(String userid) {
        //1糖尿病 2 肝病
        String archivestyle = getArguments().getString("archivestyle");
        if ("1".equals(archivestyle)) {
            llType.setVisibility(View.VISIBLE);
        } else {
            llType.setVisibility(View.INVISIBLE);
        }
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
                        guid = user.getUserId();

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
                        tvPersonAge.setText(user.getAge() + "岁");
                        //  // 1：1型  2：2型  3：妊娠  4 其他
                        switch (user.getDiabeteslei()) {
                            case 1:
                                tvType.setText("1型");
                                break;
                            case 2:
                                tvType.setText("2型");
                                break;
                            case 3:
                                tvType.setText("妊娠");
                                break;
                            case 4:
                                tvType.setText("其他");
                                break;
                            default:
                                tvType.setText("未知");
                                break;
                        }
                        if (TextUtils.isEmpty(user.getDiabetesleitime())) {
                            tvTime.setText("确诊日期:" + "-- -- --");
                        } else {
                            tvTime.setText("确诊日期:" + user.getDiabetesleitime());
                        }
                        getPersonalShow(userid);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getPersonalShow(String userid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userid + "");
        RxHttp.postForm(XyUrl.PERSONAL_RECORD)
                .addAll(map)
                .asResponse(PersonalRecordBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<PersonalRecordBean>() {
                    @Override
                    public void accept(PersonalRecordBean personalRecordBean) throws Exception {
                        imei = personalRecordBean.getImei();
                        if (TextUtils.isEmpty(imei)) {
                            imgDeviceScan.setVisibility(View.VISIBLE);
                            llDeviceUnbind.setVisibility(View.GONE);
                        } else {
                            imgDeviceScan.setVisibility(View.GONE);
                            llDeviceUnbind.setVisibility(View.VISIBLE);
                            tvDeviceNumber.setText("设备号:" + imei);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @OnClick({R.id.img_device_scan, R.id.ll_device_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_device_scan:
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                //                                Intent intent = new Intent(getPageContext(), ScanActivity.class);
                                //                                intent.putExtra("type", 4);
                                //                                intent.putExtra("userid", userid);
                                //                                startActivity(intent);
                                //听IOS说直接进入到选择设备的页面
                                Intent intent = new Intent(getPageContext(), MyDeviceListActivity.class);
                                intent.putExtra("isSelf", 3);
                                startActivity(intent);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();
                break;
            case R.id.ll_device_unbind:
                DialogUtils.getInstance().showCommonDialog(getPageContext(), "提示", "确定要解绑设备吗?", "解除绑定", "我再想想", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        //执行解绑
                        toDoUnbind();
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {

                    }
                });
                break;
        }
    }

    private void toDoUnbind() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("imei", imei);
        RxHttp.postForm(XyUrl.DEVICE_UN_BIND_PATIENT)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort("获取成功");
                        String userid = getArguments().getString("userid");
                        setTopInfo(userid);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.PATIENT_INFO_DEVICE_BIND:
                String userid = getArguments().getString("userid");
                setTopInfo(userid);
                break;
        }
    }
}
