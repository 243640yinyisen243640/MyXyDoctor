package com.xy.xydoctor.ui.activity.healthrecord;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.CheckBean;
import com.xy.xydoctor.utils.engine.GlideImageEngine;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述: 健康记录 检查记录详情
 * 作者: LYD
 * 创建日期: 2019/3/4 17:50
 */
public class HealthRecordCheckDetailActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.gv_pic)
    GridView gvPic;
    private ArrayList<String> mImageList;

    private void initMiv() {
        CheckBean detailInfo = (CheckBean) getIntent().getExtras().getSerializable("detailInfo");
        mImageList = detailInfo.getPicurl();
        gvPic.setAdapter(new NineGridAdapter());
        tvTime.setText(String.format("检测时间：%s", detailInfo.getDatetime().substring(11)));
        setTitle(detailInfo.getHydname());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_record_check_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        initMiv();
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(HealthRecordCheckDetailActivity.this, R.layout.item_pic, mImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            ImageView imageView = viewHolder.getView(R.id.img_pic);
            //Glide加载图片
            int screenWidth = ScreenUtils.getScreenWidth();
            int imgWidth = (screenWidth - SizeUtils.dp2px(2 * 5 + 12 * 2)) / 3;
            LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(imgWidth, imgWidth);
            imageView.setLayoutParams(fp);
            Glide.with(Utils.getApp()).
                    load(item).error(R.drawable.img_viewer_placeholder)
                    .placeholder(R.drawable.img_viewer_placeholder)
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MNImageBrowser.with(getPageContext())
                            .setImageEngine(new GlideImageEngine())
                            .setImageList(mImageList)
                            .setCurrentPosition(position)
                            .setIndicatorHide(false)
                            .setFullScreenMode(true)
                            .show(imageView);
                }
            });
        }
    }
}
