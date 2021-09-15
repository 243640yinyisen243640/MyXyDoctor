package com.xy.xydoctor.ui.activity.community_management;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPStaticUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.bean.community_manamer.CommunityAddBuildingInfo;
import com.xy.xydoctor.bean.community_manamer.UpLoadParamAddBuildingInfo;
import com.xy.xydoctor.imp.IACommunityUpLoadChoose;
import com.xy.xydoctor.net.OkHttpCallBack;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.window.CommunityAddBuildingPopupWindow;

import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 9:59
 * Description: 添加楼栋
 */
public class CommunityAddBuildingActivity extends XYSoftUIBaseActivity implements View.OnClickListener, IACommunityUpLoadChoose {

    private EditText numEditText;
    private EditText highEditText;
    private EditText unitEditText;
    private TextView sureTextView;

    private String buildingNum;
    private String buildingHigh;
    private String buildingUnit;


    private String comid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comid = getIntent().getStringExtra("comid");
        topViewManager().titleTextView().setText(R.string.add_building_title);
        containerView().addView(initView());
        initListener();
    }

    private void initListener() {

        sureTextView.setOnClickListener(this);
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_building_add, null);
        numEditText = view.findViewById(R.id.et_building_num);
        highEditText = view.findViewById(R.id.et_building_high);
        unitEditText = view.findViewById(R.id.et_building_unit_num);
        sureTextView = view.findViewById(R.id.tv_add_building_submit);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_building_submit:
                buildingNum = numEditText.getText().toString().trim();
                buildingHigh = highEditText.getText().toString().trim();
                buildingUnit = unitEditText.getText().toString().trim();
                Log.i("yys", "num==" + buildingNum + "hign==" + buildingHigh + "unit==" + buildingUnit);
                if (TextUtils.isEmpty(buildingNum)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_num);
                    return;
                }
                if (TextUtils.isEmpty(buildingHigh)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_high);
                    return;
                }
                if (TextUtils.isEmpty(buildingUnit)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_unit_num);
                    return;
                }

                if ("0".equals(buildingNum)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_zero);
                    return;
                }
                if ("0".equals(buildingHigh)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_zero);
                    return;
                }
                if ("0".equals(buildingUnit)) {
                    TipUtils.getInstance().showToast(getPageContext(), R.string.please_add_building_zero);
                    return;
                }

                CommunityAddBuildingPopupWindow addBuildingPopupWindow = new CommunityAddBuildingPopupWindow(getPageContext(), TurnsUtils.getInt(unitEditText.getText().toString(), 0));
                addBuildingPopupWindow.setOnChooseOkListener(this);
                addBuildingPopupWindow.showAtLocation(containerView(), Gravity.CENTER, 0, 0);

                //每次都是new一个没必要判断
                //                if (!addBuildingPopupWindow.isShowing()) {
                //                }
                break;
            default:
                break;
        }
    }

    @Override
    public void IAUpParamChoose(List<UpLoadParamAddBuildingInfo> list) {
        CommunityAddBuildingInfo info = new CommunityAddBuildingInfo();
        info.setAccess_token(SPStaticUtils.getString("token"));
        info.setCom_id(comid);
        info.setBuild_name(buildingNum);
        info.setLayer(buildingHigh);
        info.setUnit_data(list);
        String jsonResult = JSON.toJSONString(info);
        Log.i("yys", "json==" + jsonResult);

        XyUrl.okPostJson(XyUrl.ADD_BUILDING, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
            }
        });

        //        String result = new Gson().toJson(list);
        //        Call<String> requestCall = DataManager.ces(result, comid, buildingNum, buildingHigh,
        //                (call, response) -> {
        //                    if (response.code == 200) {
        //                        setResult(RESULT_OK);
        //                        finish();
        //                    }
        //
        //                }, (call, t) -> {
        //                    TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        //                });
        //        addRequestCallToMap("ces", requestCall);
    }


}
