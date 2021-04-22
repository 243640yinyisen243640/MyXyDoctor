package com.xy.xydoctor.ui.fragment.home;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.HomeSignUnReadMsgBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.homesign.MyHomeSignListActivity;
import com.xy.xydoctor.ui.activity.homesign.MyQRCodeActivity;
import com.xy.xydoctor.ui.activity.homesign.SignLocalActivity;
import com.xy.xydoctor.ui.activity.homesign.SignRemoteActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgMainActivity;
import com.xy.xydoctor.ui.activity.todo.ToDoListActivity;
import com.xy.xydoctor.utils.photo.PhotoUtils;
import com.xy.xydoctor.view.popup.HomeSignQuickPopup;
import com.xy.xydoctor.view.popup.HomeSignSelectTypePopup;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  家庭签约
 * 作者: LYD
 * 创建日期: 2019/12/30 10:53
 */

public class HomeSignFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeSignFragment";
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    //待办事项开始
    @BindView(R.id.ll_five)
    LinearLayout llFive;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_red_point)
    ColorTextView tvRedPoint;
    //待办事项结束
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    private HomeSignSelectTypePopup popupSelectType;
    private HomeSignQuickPopup popupQuick;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_sign;
    }

    @Override
    protected void init(View rootView) {
        initPopup();
        getUnReadCount();
        initTakePhoto();
    }

    private void initTakePhoto() {
        PhotoUtils.getInstance().init(this, false, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                Log.e(TAG, "outputFile==" + outputFile);
                Log.e(TAG, "outputUri==" + outputUri);
            }
        });
    }


    /**
     * 获取未读消息数量
     */
    private void getUnReadCount() {
        HashMap<String, String> map = new HashMap<>();
        RxHttp.postForm(XyUrl.HOME_SIGN_UN_READ_COUNT)
                .addAll(map)
                .asResponse(HomeSignUnReadMsgBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<HomeSignUnReadMsgBean>() {
                    @Override
                    public void accept(HomeSignUnReadMsgBean data) throws Exception {
                        int totalUnReadCount = data.getNum();
                        if (totalUnReadCount > 0) {
                            tvRedPoint.setVisibility(View.VISIBLE);
                            if (totalUnReadCount > 999) {
                                tvRedPoint.setText("...");
                            } else {
                                tvRedPoint.setText(totalUnReadCount + "");
                            }
                        } else {
                            tvRedPoint.setVisibility(View.GONE);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 弹窗
     */
    private void initPopup() {
        popupSelectType = new HomeSignSelectTypePopup(getPageContext());
        TextView tvLeft = popupSelectType.findViewById(R.id.tv_left);
        TextView tvRight = popupSelectType.findViewById(R.id.tv_right);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        popupQuick = new HomeSignQuickPopup(getPageContext());
        LinearLayout llLeft = popupQuick.findViewById(R.id.ll_left);
        LinearLayout llRight = popupQuick.findViewById(R.id.ll_right);
        llLeft.setOnClickListener(this);
        llRight.setOnClickListener(this);
    }

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six, R.id.tv_seven})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //签约+
            case R.id.tv_one:
                popupSelectType.showPopupWindow();
                break;
            //我的二维码
            case R.id.tv_two:
                intent = new Intent(getPageContext(), MyQRCodeActivity.class);
                startActivity(intent);
                break;
            //快速签约(扫一扫)
            case R.id.tv_three:
                popupQuick.showPopupWindow();
                break;
            //消息群发
            case R.id.tv_four:
                intent = new Intent(getActivity(), MassMsgMainActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            //待办事项
            case R.id.tv_five:
                intent = new Intent(getActivity(), ToDoListActivity.class);
                intent.putExtra("type", "homeSign");
                startActivity(intent);
                break;
            //签约列表
            case R.id.tv_six:
                startActivity(new Intent(getPageContext(), MyHomeSignListActivity.class));
                break;
            //患者统计
            case R.id.tv_seven:
                ToastUtils.showShort("患者统计");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            //面签
            case R.id.tv_left:
                intent = new Intent(getPageContext(), SignLocalActivity.class);
                startActivity(intent);
                popupSelectType.dismiss();
                break;
            //远程签约
            case R.id.tv_right:
                intent = new Intent(getPageContext(), SignRemoteActivity.class);
                startActivity(intent);
                popupSelectType.dismiss();
                break;
            //扫描
            case R.id.ll_left:
                //                intent = new Intent(getPageContext(), ScanActivity.class);
                //                intent.putExtra("type", 0);
                //                startActivity(intent);
                //                popupQuick.dismiss();
                break;
            //识别身份证
            case R.id.ll_right:
                popupQuick.dismiss();
                getToken();
                openCamera();
                break;
        }
    }

    private void openCamera() {
        PermissionUtils
                .permission(PermissionConstants.CAMERA)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        PhotoUtils.getInstance().takePhoto(HomeSignFragment.this);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用相机权限");
                    }
                }).request();
    }


    /**
     * 获取百度识别身份证的token
     */
    private void getToken() {
        String url = "https://aip.baidubce.com/oauth/2.0/token";
        HashMap<String, String> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        map.put("client_id", "ZwzxovfCNKHGflXn6HMyYjsG");
        map.put("client_secret", "FmfLsc76DDEQDUkMeyq51ngrYrUuBT3h");
        //        RxHttp.postForm(url)
        //                .addAll(map)
        //                .asClass(BaiduIdCardTokenBean.class)
        //                .to(RxLife.toMain(this))
        //                .subscribe(new Consumer<BaiduIdCardTokenBean>() {
        //                    @Override
        //                    public void accept(BaiduIdCardTokenBean baiduIdCardTokenBean) throws Exception {
        //                        String access_token = baiduIdCardTokenBean.getAccess_token();
        //                        SPStaticUtils.put("bd_token", access_token);
        //                    }
        //                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }
}
