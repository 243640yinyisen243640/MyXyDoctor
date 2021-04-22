package com.xy.xydoctor.ui.activity.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyGridView;
import com.lyd.baselib.widget.view.MyListView;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.ApplyToHospitalDetailLvAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.ApplyToHospitalDetailBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.engine.GlideImageEngine;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  申请住院详情
 * 作者: LYD
 * 创建日期: 2019/5/31 15:13
 */
public class ApplyToHospitalDetailActivity extends BaseActivity {
    protected ArrayList<String> mImageList;
    @BindView(R.id.lv_top_six)
    MyListView lvTopSix;
    @BindView(R.id.tv_disease)
    TextView tvDisease;
    @BindView(R.id.tv_goal)
    TextView tvGoal;
    @BindView(R.id.gv_pic)
    MyGridView gvPic;
    @BindView(R.id.cb_yes)
    CheckBox cbYes;
    @BindView(R.id.cb_no)
    CheckBox cbNo;
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_check_yes)
    LinearLayout llYes;
    @BindView(R.id.ll_check_no)
    LinearLayout llNo;


    /**
     * 获取详情
     */
    private void getApplyToHospitalDetail() {
        String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", "");
        map.put("reason", "");
        RxHttp.postForm(XyUrl.APPLY_TO_HOSPITAL_DETAIL)
                .addAll(map)
                .asResponse(ApplyToHospitalDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ApplyToHospitalDetailBean>() {
                    @Override
                    public void accept(ApplyToHospitalDetailBean data) throws Exception {
                        //ApplyToHospitalDetailBean data = (ApplyToHospitalDetailBean) baseBean.getData();
                        String[] stringArray = getResources().getStringArray(R.array.apply_to_hospital);
                        List<String> leftList = Arrays.asList(stringArray);
                        ArrayList<String> rightList = new ArrayList<>();
                        rightList.add(data.getName());
                        //住院类型 1：初次住院 2：再次住院
                        if ("1".equals(data.getType())) {
                            rightList.add("初次住院");
                        } else {
                            rightList.add("再次住院");
                        }
                        rightList.add(data.getAge());
                        if ("1".equals(data.getSex())) {
                            rightList.add("男");
                        } else {
                            rightList.add("女");
                        }
                        rightList.add(data.getTelephone());
                        rightList.add(data.getIntime());
                        lvTopSix.setAdapter(
                                new ApplyToHospitalDetailLvAdapter(getPageContext(), R.layout.item_apply_to_hospital_detail_list,
                                        leftList, leftList, rightList));
                        String describe = data.getDescribe();
                        String result = data.getResult();
                        tvDisease.setText(describe);
                        tvGoal.setText(result);
                        mImageList = data.getBlpic();
                        gvPic.setAdapter(new NineGridAdapter());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick({R.id.tv_submit, R.id.ll_check_yes, R.id.ll_check_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_check_yes:
                cbYes.setChecked(true);
                cbNo.setChecked(false);
                break;
            case R.id.ll_check_no:
                cbYes.setChecked(false);
                cbNo.setChecked(true);
                break;
            case R.id.tv_submit:
                toDoSubmit();
                break;
        }
    }

    /**
     * 提交
     */
    private void toDoSubmit() {
        String id = getIntent().getStringExtra("id");
        String status;
        if (cbYes.isChecked()) {
            status = "2";
        } else {
            status = "3";
        }
        String reason = etReply.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        map.put("reason", reason);
        RxHttp.postForm(XyUrl.APPLY_TO_HOSPITAL_DETAIL)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("获取成功");
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.APPLY_TO_HOSPITAL));//刷新列表
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_to_hospital_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("住院申请");
        getApplyToHospitalDetail();
    }

    private class NineGridAdapter extends CommonAdapter<String> {

        public NineGridAdapter() {
            super(ApplyToHospitalDetailActivity.this, R.layout.item_pic, mImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            ImageView imageView = viewHolder.getView(R.id.img_pic);
            //Glide加载图片
            int screenWidth = ScreenUtils.getScreenWidth();
            int imgWidth = (screenWidth - SizeUtils.dp2px(3 * 5 + 12 * 2)) / 4;
            LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(imgWidth, imgWidth);
            imageView.setLayoutParams(fp);
            Glide.with(Utils.getApp())
                    .load(item)
                    .error(R.drawable.img_viewer_placeholder)
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
