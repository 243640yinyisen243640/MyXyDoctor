package com.xy.xydoctor.ui.activity.mydevice;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.utils.GifSizeFilter;
import com.xy.xydoctor.utils.zxing.MyZXingUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.view.ScanBoxView;
import me.devilsen.czxing.view.ScanListener;
import me.devilsen.czxing.view.ScanView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 描述: 扫一扫 页面
 * 作者: LYD
 * 创建日期: 2019/3/22 15:39
 */
public class ScanActivity extends BaseActivity implements ScanListener {
    private static final int CODE_SELECT_IMAGE = 10010;
    private static final String TAG = "ScanActivity";
    @BindView(R.id.scan_view)
    ScanView mScanView;
    @BindView(R.id.img_my_qrcode)
    ImageView imgMyQrcode;
    @BindView(R.id.img_to_select_pic)
    ImageView imgToSelectPic;

    private String userid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userid = getIntent().getStringExtra("userid");
        initScan();
        initTitle();
        setFrom();
    }

    private void setFrom() {
        int type = getIntent().getIntExtra("type", 0);
        if (4 == type) {
            imgToSelectPic.setVisibility(View.VISIBLE);
        } else {
            imgToSelectPic.setVisibility(View.GONE);
        }
    }

    private void initTitle() {
        setTitle("扫一扫");
        getTvMore().setTextColor(ColorUtils.getColor(R.color.main_red));
        getTvMore().setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        getTvMore().setPadding(10, 5, 10, 5);
        getTvMore().setText("手动绑定");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = getIntent().getIntExtra("type", -1);
                if (type == 4) {
                    Intent intent = new Intent(getPageContext(), MyDeviceListActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
                    intent.putExtra("imei", "");
                    intent.putExtra("type", type);
                    intent.putExtra("userid", userid);
                    startActivity(intent);
                }
            }
        });
    }


    /**
     * 扫描初始化
     */
    private void initScan() {
        ScanBoxView scanBox = mScanView.getScanBox();
        //设置扫码框上下偏移量，可以为负数
        scanBox.setBoxTopOffset(-BarCodeUtil.dp2px(this, 50));
        //设置边框大小
        scanBox.setBorderSize(BarCodeUtil.dp2px(this, 250), BarCodeUtil.dp2px(this, 250));
        //设置扫码框四周的颜色
        scanBox.setMaskColor(Color.parseColor("#9C272626"));
        scanBox.setScanNoticeText("将二维码/条形码放入框内，即可自动扫描。");
        //获取扫码回调
        mScanView.setScanListener(this);
    }

    @OnClick({R.id.img_to_select_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_to_select_pic:
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Matisse.from(ScanActivity.this)
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
    public void onScanSuccess(String resultText, me.devilsen.czxing.code.BarcodeFormat format) {
        if (TextUtils.isEmpty(resultText)) {
            return;
        }
        int type = getIntent().getIntExtra("type", -1);
        if (resultText.contains("IMEI=")) {
            String startText = resultText.substring(0, resultText.indexOf("IMEI="));
            resultText = resultText.substring(startText.length() + 5);
        }
        Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
        intent.putExtra("imei", resultText);
        intent.putExtra("type", type);
        startActivity(intent);
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
                                                            if (resultText[0].contains("IMEI=")) {
                                                                String startText = resultText[0].substring(0, resultText[0].indexOf("IMEI="));
                                                                resultText[0] = resultText[0].substring(startText.length() + 5);
                                                            }
                                                            Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
                                                            intent.putExtra("imei", resultText[0]);
                                                            intent.putExtra("type", 4);
                                                            startActivity(intent);
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
