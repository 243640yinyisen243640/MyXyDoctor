package com.xy.xydoctor.ui.activity.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorButton;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.GifSizeFilter;
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
import rxhttp.wrapper.param.RxHttp;

public class FeedBackActivity extends BaseActivity {
    private static final int REQUEST_CODE_CHOOSE = 10010;
    private static final int REQUEST_CAMERA = 10011;
    private static final int COMPRESS_OVER = 10012;
    @BindView(R.id.et_question)
    ColorEditText etQuestion;
    @BindView(R.id.img_feed_back)
    ImageView imgFeedBack;
    @BindView(R.id.bt_submit)
    ColorButton btSubmit;

    //上传图片路径
    private String feedBackPath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("问题反馈");
    }


    @OnClick({R.id.img_feed_back, R.id.bt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_feed_back:
                selectPhoto();
                break;
            case R.id.bt_submit:
                toDoSubmit();
                break;
        }
    }

    private void toDoSubmit() {
        String content = etQuestion.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort("问题描述不能为空");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("content", content);
        RxHttp.postForm(XyUrl.ADD_FEED_BACK)
                .addAll(map)
                .addFile("pic", new File(feedBackPath))
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        //String msg = data.getMsg();
                        ToastUtils.showShort("操作成功");
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 选择照片
     */
    private void selectPhoto() {
        PermissionUtils
                .permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Matisse.from(FeedBackActivity.this)
                                .choose(MimeType.ofImage(), false)
                                .countable(true)
                                .maxSelectable(1)//最多选一张
                                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(true, "com.xy.xydoctor.FileProvider", "Test"))
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .imageEngine(new GlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用存储权限");
                    }
                }).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    File file = new File(pathList.get(0));
                    feedBackPath = file.getPath();

                    Glide.with(Utils.getApp()).load(feedBackPath).into(imgFeedBack);
                    break;
            }
        }
    }
}