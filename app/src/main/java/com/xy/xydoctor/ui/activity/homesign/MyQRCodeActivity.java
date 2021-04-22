package com.xy.xydoctor.ui.activity.homesign;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.DoctorInfoBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.zxing.MyZXingUtils;
import com.xy.xydoctor.view.popup.SlideFromBottomPopupSaveToAlbum;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  我的二维码
 * 作者: LYD
 * 创建日期: 2019/12/30 11:11
 */
public class MyQRCodeActivity extends BaseActivity {
    private static final int GET_BITMAP_FROM_URL = 10010;
    private static final int GET_USER_INFO = 10011;
    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_dep)
    TextView tvDep;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    private String guid;
    private Bitmap codeBmp;
    private SlideFromBottomPopupSaveToAlbum saveToAlbumPopup;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GET_BITMAP_FROM_URL:
                    Bitmap logoBmp = (Bitmap) msg.obj;
                    String result = "guid" + guid;
                    codeBmp = MyZXingUtils.createQRCodeWithLogo(result, logoBmp);
                    imgQrCode.setImageBitmap(codeBmp);
                    break;
            }
            return true;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_qrcode;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initPopup();
        initTitle();
        getUserInfo();
    }

    private void initPopup() {
        saveToAlbumPopup = new SlideFromBottomPopupSaveToAlbum(getPageContext());
        TextView tvSaveToAlbum = saveToAlbumPopup.findViewById(R.id.tv_save_to_album);
        tvSaveToAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils
                        .permission(PermissionConstants.STORAGE)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                ImageUtils.save2Album(codeBmp, Bitmap.CompressFormat.JPEG);
                                ToastUtils.showShort("图片已保存至相册");
                                saveToAlbumPopup.dismiss();
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用存储权限");
                            }
                        }).request();
            }
        });
    }

    private void initTitle() {
        setTitle("我的二维码");
        getTvMore().setText("");
        getTvMore().setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getPageContext(), R.drawable.home_scan_top_more), null, null, null);
        getTvMore().setCompoundDrawablePadding(5);
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToAlbumPopup.showPopupWindow();
            }
        });
    }


    private void getUserInfo() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_INFO)
                .addAll(map)
                .asResponse(DoctorInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<DoctorInfoBean>() {
                    @Override
                    public void accept(DoctorInfoBean userBean) throws Exception {
                        guid = userBean.getGuid();
                        String picUrl = userBean.getImgurl();
                        getBitmapFromUrl(picUrl);
                        Glide.with(Utils.getApp())
                                .load(picUrl)
                                .placeholder(R.drawable.default_img_head)
                                .error(R.drawable.default_img_head)
                                .into(imgHead);
                        String nickname = userBean.getDocname();
                        tvName.setText(nickname);
                        String depname = userBean.getDepname();
                        String doczhc = userBean.getDoczhc();
                        if (TextUtils.isEmpty(doczhc)) {
                            tvDep.setText(depname);
                        } else {
                            tvDep.setText(depname + "," + doczhc);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 请求图片
     *
     * @param urlStr
     */
    private void getBitmapFromUrl(String urlStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建Url对象
                    URL url = new URL(urlStr);
                    //根据url发送Http请求
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    urlConnection.setRequestMethod("GET");
                    //设置连接超时时间
                    urlConnection.setConnectTimeout(10 * 1000);
                    //得到服务器返回的相应码
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //获取输入流
                        InputStream is = urlConnection.getInputStream();
                        //将输入流转化成Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        //通知主线程
                        Message message = new Message();
                        message.what = GET_BITMAP_FROM_URL;
                        message.obj = bitmap;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
