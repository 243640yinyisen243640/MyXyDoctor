package com.xy.xydoctor.ui.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SugarAbnormalAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SugarAbnormalBean;
import com.xy.xydoctor.imp.AdapterClickImp;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.healthrecord.HealthRecordBloodSugarListActivity;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.RongIM;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:血糖异常
 * 作者: LYD
 * 创建日期: 2019/6/11 19:54
 */
public class SugarAbnormalActivity extends BaseActivity implements AdapterClickImp {
    private static final int REPLY = 10086;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.lv_sugar_list)
    MyListView lvSugarList;
    @BindView(R.id.et_input)
    ColorEditText etInput;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_head_time)
    TextView tvHeadTime;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    private List<SugarAbnormalBean.SevenglucoseBean> sevenGlucoseList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sugar_abnormal;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血糖异常");
        getSugarAbnormal();
        setEt();
    }


    private void setEt() {
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                if (!TextUtils.isEmpty(content)) {
                    tvSend.setText("发送");
                    tvSend.setTextColor(getResources().getColor(R.color.white));
                    tvSend.setBackground(getResources().getDrawable(R.drawable.shape_enter_textview));
                } else {
                    tvSend.setText("快捷回复");
                    tvSend.setTextColor(getResources().getColor(R.color.gray_text));
                    tvSend.setBackground(getResources().getDrawable(R.drawable.shape_enter_textview_normal));
                }
            }
        });
    }

    private void getSugarAbnormal() {
        String userid = getIntent().getStringExtra("userid");
        String id = getIntent().getStringExtra("id");
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_SUGAR_ABNORMAL)
                .addAll(map)
                .asResponse(SugarAbnormalBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<SugarAbnormalBean>() {
                    @Override
                    public void accept(SugarAbnormalBean data) throws Exception {
                        llBottom.setVisibility(View.VISIBLE);
                        SugarAbnormalBean.GlucoseBean glucose = data.getGlucose();
                        String category = glucose.getCategory();//早餐后
                        double glucosevalue = glucose.getGlucosevalue();//血糖值
                        tvDesc.setText(category + "血糖值");
                        tvText.setText(glucosevalue + "");
                        sevenGlucoseList = data.getSevenglucose();
                        if (sevenGlucoseList != null && sevenGlucoseList.size() > 0) {
                            SugarAbnormalAdapter sugarAbnormalAdapter = new SugarAbnormalAdapter(getPageContext(), sevenGlucoseList, SugarAbnormalActivity.this);
                            lvSugarList.setAdapter(sugarAbnormalAdapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        llBottom.setVisibility(View.GONE);
                    }
                });
    }


    @OnClick({R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                String text = tvSend.getText().toString().trim();
                if ("发送".equals(text)) {
                    sendReply();
                } else {
                    Intent intent = new Intent(getPageContext(), FastReplyActivity.class);
                    startActivityForResult(intent, REPLY);
                }
                break;
        }
    }

    private void sendReply() {
        int imDocid = SPStaticUtils.getInt("imDocid", 0);
        String reply = etInput.getText().toString().trim();
        String id = getIntent().getStringExtra("id");
        HashMap map = new HashMap<>();
        map.put("wid", id);
        map.put("content", reply);
        map.put("redocid", imDocid);
        RxHttp.postForm(XyUrl.SEND_APPLY)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String updateBean) throws Exception {
                        ToastUtils.showShort("获取成功");
                        finish();
                        //获取并发送Id
                        int messageId = getIntent().getIntExtra("messageId", 0);
                        //融云删除消息
                        RongIM.getInstance().deleteMessages(new int[]{messageId}, null);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REPLY:
                    String content = data.getStringExtra("content");
                    etInput.setText(content);
                    etInput.setSelection(content.length());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAdapterClick(View view, int position) {
        Intent intent = new Intent(getPageContext(), HealthRecordBloodSugarListActivity.class);
        switch (view.getId()) {
            case R.id.fl_before_dawn://凌晨
                intent.putExtra("type", "凌晨");
                break;
            case R.id.fl_before_breakfast://早餐前
                intent.putExtra("type", "早餐空腹");
                break;
            case R.id.fl_after_the_breakfast://早餐后
                intent.putExtra("type", "早餐后");
                break;
            case R.id.fl_before_lunch://午餐前
                intent.putExtra("type", "午餐前");
                break;
            case R.id.fl_after_launch://午餐后
                intent.putExtra("type", "午餐后");
                break;
            case R.id.fl_before_dinner://晚餐前
                intent.putExtra("type", "晚餐前");
                break;
            case R.id.fl_after_dinner://晚餐后
                intent.putExtra("type", "晚餐后");
                break;
            case R.id.fl_after_sleep://睡前
                intent.putExtra("type", "睡前");
                break;
        }
        intent.putExtra("userid", getIntent().getStringExtra("userid"));
        intent.putExtra("time", sevenGlucoseList.get(position).getTime());
        startActivity(intent);
    }
}
