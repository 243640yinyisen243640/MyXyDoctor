package com.xy.xydoctor.ui.activity.community_management;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.utils.zxing.MyZXingUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
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
public class CommunityScanActivity extends BaseActivity implements ScanListener {
    private static final int CODE_SELECT_IMAGE = 10010;
    private static final String TAG = "ScanActivity";
    @BindView(R.id.scan_view)
    ScanView mScanView;
    @BindView(R.id.img_my_qrcode)
    ImageView imgMyQrcode;
    @BindView(R.id.img_to_select_pic)
    ImageView imgToSelectPic;

    /**
     * 1：血压2：血糖
     */
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        initScan();
        initTitle();
    }


    private void initTitle() {
        setTitle("扫一扫");
        getTvMore().setTextColor(ColorUtils.getColor(R.color.main_red));
        getTvMore().setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        getTvMore().setPadding(10, 5, 10, 5);
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


    @Override
    public void onScanSuccess(String resultText, me.devilsen.czxing.code.BarcodeFormat format) {
        if (TextUtils.isEmpty(resultText)) {
            return;
        }
        if (resultText.contains("IMEI=")) {
            String startText = resultText.substring(0, resultText.indexOf("IMEI="));
            resultText = resultText.substring(startText.length() + 5);
        }
        Intent intent = new Intent();
        intent.putExtra("imei", resultText);
        intent.putExtra("type", type);
        setResult(RESULT_OK, intent);
        finish();
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
                                                            Intent intent = new Intent();
                                                            intent.putExtra("ime", resultText[0]);
                                                            setResult(RESULT_OK, intent);
                                                            finish();
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
