package com.xy.xydoctor.ui.activity.homesign;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.f1reking.signatureview.SignatureView;
import com.xy.xydoctor.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  添加签名
 * 作者: LYD
 * 创建日期: 2019/12/30 16:52
 */
public class SignatureEditActivity extends AppCompatActivity {
    private static final String TAG = "SignatureEditActivity";
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.sv)
    SignatureView sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_edit);
        ScreenUtils.setLandscape(this);
    }


    @OnClick({R.id.tv_reset, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reset:
                sv.clear();
                break;
            case R.id.tv_save:
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                //保存照片
                                if (sv.getTouched()) {
                                    try {
                                        String path = Environment.getExternalStorageDirectory().getPath();
                                        String currentTime = TimeUtils.getNowMills() + "";
                                        //path==/sdcard
                                        //替换为
                                        //path==/storage/emulated/0
                                        String savePath = path + "/" + currentTime + ".png";
                                        Log.e(TAG, "savePath==" + savePath);
                                        sv.save(savePath, true, 10);
                                        //直接返回
                                        getIntent().putExtra("savePath", savePath);
                                        setResult(RESULT_OK, getIntent());
                                        finish();
                                        //ToastUtils.showShort("保存成功");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    ToastUtils.showShort("请先签名");
                                }
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
                break;
        }
    }
}
