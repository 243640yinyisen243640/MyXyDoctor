package com.xy.xydoctor.ui.activity.mydevice;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.NewSearchUserBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.homesign.MyQRCodeActivity;
import com.xy.xydoctor.ui.activity.patientadd.PatientAddActivity;
import com.xy.xydoctor.utils.GifSizeFilter;
import com.xy.xydoctor.utils.zxing.MyZXingUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import me.devilsen.czxing.code.BarcodeFormat;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.view.ScanBoxView;
import me.devilsen.czxing.view.ScanListener;
import me.devilsen.czxing.view.ScanView;
import rxhttp.wrapper.param.RxHttp;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 描述: 点击首页进来的扫码页面
 * 作者: LYD
 * 创建日期: 2020/11/9 14:40
 */
public class HomeScanActivity extends BaseActivity implements ScanListener {
    private static final String TAG = "HomeScanActivity";
    private static final int CODE_SELECT_IMAGE = 10010;
    @BindView(R.id.scan_view)
    ScanView mScanView;
    @BindView(R.id.img_my_qrcode)
    ImageView imgMyQrcode;
    @BindView(R.id.img_to_select_pic)
    ImageView imgToSelectPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setFrom();
        initScan();
    }

    private void initScan() {
        ScanBoxView scanBox = mScanView.getScanBox();
        //设置扫码框上下偏移量，可以为负数
        scanBox.setBoxTopOffset(-BarCodeUtil.dp2px(this, 50));
        //设置边框大小
        scanBox.setBorderSize(BarCodeUtil.dp2px(this, 250), BarCodeUtil.dp2px(this, 250));
        //设置扫码框四周的颜色
        scanBox.setMaskColor(Color.parseColor("#9C272626"));
        //scanBox.setHorizontalScanLine();
        scanBox.setScanNoticeText("将二维码/条形码放入框内，即可自动扫描。");
        // 获取扫码回调
        mScanView.setScanListener(this);
    }

    private void setFrom() {
        String type = getIntent().getStringExtra("type");
        if ("main".equals(type)) {
            imgMyQrcode.setVisibility(View.VISIBLE);
            imgToSelectPic.setVisibility(View.VISIBLE);
            setTitle("请扫患者二维码");
        } else {
            imgMyQrcode.setVisibility(View.GONE);
            imgToSelectPic.setVisibility(View.GONE);
            setTitle("扫一扫");
        }
    }


    @OnClick({R.id.img_my_qrcode, R.id.img_to_select_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_my_qrcode:
                startActivity(new Intent(getPageContext(), MyQRCodeActivity.class));
                break;
            case R.id.img_to_select_pic:
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Matisse.from(HomeScanActivity.this)
                                        .choose(MimeType.ofImage(), false)
                                        .countable(true)
                                        .maxSelectable(1)//最多选一张
                                        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                                        .capture(true)
                                        .captureStrategy(new CaptureStrategy(true, "com.xy.xydoctor.FileProvider", "Test"))
                                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                        .thumbnailScale(0.85f)
                                        .imageEngine(new GlideEngine())
                                        .forResult(CODE_SELECT_IMAGE);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
                break;
        }
    }

    @Override
    public void onScanSuccess(String result, BarcodeFormat format) {
        setResult(result);
    }

    private void setResult(String resultText) {
        Log.e(TAG, "resultText==" + resultText);
        String type = getIntent().getStringExtra("type");
        if ("main".equals(type)) {
            if (resultText.startsWith("guid")) {
                String result = resultText.substring(4);
                Log.e(TAG, "result==" + result);
                //扫患者二维码
                getUserInfo(result);
            } else {
                ToastUtils.showShort("请扫患者二维码");
            }
        } else {
            if (resultText.contains("IMEI=")) {
                String startText = resultText.substring(0, resultText.indexOf("IMEI="));
                resultText = resultText.substring(startText.length() + 5);
            }
            Intent intent = new Intent();
            intent.putExtra("result", resultText);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    /**
     * 获取患者信息
     *
     * @param result
     */
    private void getUserInfo(String result) {
        Log.e(TAG, "result==" + result);
        HashMap<String, Object> map = new HashMap<>();
        map.put("guid", result);
        RxHttp.postForm(XyUrl.GET_QRCODE_INFO)
                .addAll(map)
                .asClass(NewSearchUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<NewSearchUserBean>() {
                    @Override
                    public void accept(NewSearchUserBean response) throws Exception {
                        int code = response.getCode();
                        NewSearchUserBean.DataBean data = response.getData();
                        Intent intent = null;
                        switch (code) {
                            case 200:
                                intent = new Intent(getPageContext(), PatientAddActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "homePatient");
                                bundle.putSerializable("userBean", data);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            default:
                                String msg = response.getMsg();
                                ToastUtils.showShort(msg);
                                break;
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onOpenCameraError() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //打开后置摄像头开始预览，但是并未开始识别
        mScanView.openCamera();
        //显示扫描框，并开始识别
        mScanView.startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanView.stopScan();
        //关闭摄像头预览，并且隐藏扫描框
        mScanView.closeCamera();
    }

    @Override
    protected void onDestroy() {
        //销毁二维码扫描控件
        mScanView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case CODE_SELECT_IMAGE:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (pathList != null && 1 == pathList.size()) {
                        String path = pathList.get(0);
                        //压缩图片
                        Luban.with(this)
                                .load(path)
                                .ignoreBy(100)
                                .setTargetDir("")
                                .filter(new CompressionPredicate() {
                                    @Override
                                    public boolean apply(String path) {
                                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                    }
                                })
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final String[] resultText = {MyZXingUtils.syncDecodeQRCode(file.getPath())};
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (TextUtils.isEmpty(resultText[0])) {
                                                            ToastUtils.showShort("未发现二维码");
                                                        } else {
                                                            if (resultText[0].startsWith("guid")) {
                                                                String result = resultText[0].substring(4);
                                                                Log.e(TAG, "result==" + result);
                                                                //扫患者二维码
                                                                getUserInfo(result);
                                                            } else {
                                                                ToastUtils.showShort("请扫患者二维码");
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }).start();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();
                    }

                    break;
            }
        }
    }
}