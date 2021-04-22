package com.xy.xydoctor.ui.activity.director;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.constant.ConstantParam;
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

/**
 * 描述:  医生修改
 * 作者: LYD
 * 创建日期: 2020/5/18 15:36
 */
public class DoctorAddAndEditActivity extends BaseActivity {
    private static final int REQUEST_CODE_CHOOSE = 10010;
    private static final int CODE_RESULT_REQUEST = 10012;
    private static final String TAG = "DoctorAddAndEditActivity";
    @BindView(R.id.et_doctor_name)
    ColorEditText etDoctorName;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.et_account_name)
    ColorEditText etAccountName;
    @BindView(R.id.et_pwd)
    ColorEditText etPwd;
    @BindView(R.id.et_pwd_repeat)
    ColorEditText etPwdRepeat;
    @BindView(R.id.img_doctor_head)
    ImageView imgDoctorHead;
    @BindView(R.id.et_connect_way)
    ColorEditText etConnectWay;
    @BindView(R.id.bt_add)
    ColorTextView btAdd;
    private String url = "";
    private Uri cropImageUri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_add_and_edit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String type = getIntent().getStringExtra("type");
        if ("add".equals(type)) {
            setTitle("添加医生");
            imgEdit.setVisibility(View.GONE);
        } else {
            setTitle("修改信息");
            imgEdit.setVisibility(View.VISIBLE);
            etDoctorName.setEnabled(false);
            etAccountName.setEnabled(false);
            DoctorListBean doctorInfo = (DoctorListBean) getIntent().getSerializableExtra("doctorInfo");
            String docname = doctorInfo.getDocname();
            String username = doctorInfo.getUsername();
            String picture = doctorInfo.getPicture();
            etDoctorName.setText(docname);
            etAccountName.setText(username);
            Glide.with(Utils.getApp()).load(picture).into(imgDoctorHead);
            String telephone = doctorInfo.getTelephone();
            etConnectWay.setText(telephone);
        }
    }


    @OnClick({R.id.img_edit, R.id.img_doctor_head, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_edit:
                etDoctorName.setEnabled(true);
                break;
            case R.id.img_doctor_head:
                toSelectPhoto();
                break;
            case R.id.bt_add:
                toCheckAdd();
                break;
        }
    }

    private void toSelectPhoto() {
        PermissionUtils
                .permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        selectPhoto();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用存储权限");
                    }
                }).request();
    }

    private void selectPhoto() {
        Matisse.from(DoctorAddAndEditActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(1)//最多选一张
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.xy.xydoctor.FileProvider", "Test"))
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    private void toCheckAdd() {
        String doctorName = etDoctorName.getText().toString().trim();
        if (TextUtils.isEmpty(doctorName)) {
            ToastUtils.showShort("请输入医生名称");
            return;
        }
        String accountName = etAccountName.getText().toString().trim();
        if (TextUtils.isEmpty(accountName)) {
            ToastUtils.showShort("请输入医生账号");
            return;
        }
        String pwd = etPwd.getText().toString().trim();
        //        if (TextUtils.isEmpty(pwd)) {
        //            ToastUtils.showShort("请输入密码");
        //            return;
        //        }
        String pwdRepeat = etPwdRepeat.getText().toString().trim();
        //        if (TextUtils.isEmpty(pwdRepeat)) {
        //            ToastUtils.showShort("请输入确认密码");
        //            return;
        //        }
        String connectWay = etConnectWay.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put("docnum", accountName);
        map.put("docname", doctorName);
        map.put("password", pwd);
        map.put("repass", pwdRepeat);
        map.put("telphone", connectWay);
        String type = getIntent().getStringExtra("type");
        if (!"add".equals(type)) {
            DoctorListBean doctorInfo = (DoctorListBean) getIntent().getSerializableExtra("doctorInfo");
            map.put("docid", doctorInfo.getUserid() + "");
        }
        RxHttp.postForm(XyUrl.ADD_DOCTOR)
                .addFile("docimage", new File(url))
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String data) throws Exception {
                        //String msg = data.getMsg();
                        Log.e(TAG, "发送成功");
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.DOCTOR_ADD_SUCCESS));
                        ToastUtils.showShort("操作成功");
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            File fileCropUri = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE:
                    //path uri file 三者互相转换
                    cropImageUri = Uri.fromFile(fileCropUri);
                    List<String> pathList = Matisse.obtainPathResult(data);
                    File file = new File(pathList.get(0));
                    Uri newUri = Uri.fromFile(file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.xy.xydoctor.FileProvider", new File(newUri.getPath()));
                    }
                    //裁剪
                    cropImageUri(this, newUri, cropImageUri, 1, 1, 300, 300, CODE_RESULT_REQUEST);
                    break;
                case CODE_RESULT_REQUEST:
                    url = cropImageUri.getPath();
                    Glide.with(Utils.getApp()).load(url).into(imgDoctorHead);
                    break;
            }
        }
    }


    /**
     * 裁剪图片
     *
     * @param activity
     * @param orgUri
     * @param desUri
     * @param aspectX
     * @param aspectY
     * @param width
     * @param height
     * @param requestCode
     */
    private void cropImageUri(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        //发送裁剪信号
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        //1-false用uri返回图片
        //2-true直接用bitmap返回图片（此种只适用于小图片，返回图片过大会报错）
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }
}
