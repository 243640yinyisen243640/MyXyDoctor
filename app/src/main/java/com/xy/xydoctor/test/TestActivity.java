package com.xy.xydoctor.test;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.lyd.baselib.widget.layout.pictureupload.PictureUploadCallback;
import com.lyd.baselib.widget.layout.pictureupload.PictureUploadView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.utils.GifSizeFilter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TestActivity extends BaseActivity {
    public static final int RESULT_PHOTO_SELECT = 101;
    private static final String TAG = "TestActivity";
    @BindView(R.id.puv)
    PictureUploadView puv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String str = ",,,";
        for (String s : str.split(",",-1)) {
            Log.e(TAG, "s=" + s);
        }


        puv.setMaxColumn(3);
        puv.setMaxSize(1);
        puv.setPicUploadCallback(new PictureUploadCallback<PictureModel>() {
            @Override
            public void click(int position, PictureModel pictureModel, List<PictureModel> list) {

            }

            @Override
            public void remove(int position, List<PictureModel> list) {

            }

            @Override
            public void onAddPic(int maxPic, List<PictureModel> list) {
                Matisse.from(TestActivity.this)
                        .choose(MimeType.ofImage(), false)
                        .countable(true)
                        .maxSelectable(maxPic)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(RESULT_PHOTO_SELECT);
                //PictureSelectUtils.SelectSystemPhoto(mContext, RESULT_PHOTO_SELECT, maxPic);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PHOTO_SELECT:
                    List<PictureModel> models = new ArrayList<>();
                    List<Uri> selectList = Matisse.obtainResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        models.add(new PictureModel(selectList.get(i)));
                    }
                    puv.addData(models);
                    //刷新相册图片
                    if (selectList.size() == 1) {
                        Uri contentUri = selectList.get(0);
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
                        sendBroadcast(mediaScanIntent);
                    }
                    break;
                default:
            }
        }
    }
}
