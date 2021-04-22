package com.xy.xydoctor.ui.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.widget.view.MyListView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.BloodPressureAbnormalAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.BloodPressureAbnormalBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import io.rong.imkit.RongIM;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:血压异常
 * 作者: LYD
 * 创建日期: 2019/6/11 20:45
 */
public class BloodPressureAbnormalActivity extends BaseActivity {
    private static final int REPLY = 10010;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.lv_blood_pressure_list)
    MyListView lvBloodPressureList;
    @BindView(R.id.et_input)
    ColorEditText etInput;
    @BindView(R.id.tv_send)
    TextView tvSend;


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

    private void getBloodPressureAbnormal() {
        String userid = getIntent().getStringExtra("userid");
        String id = getIntent().getStringExtra("id");
        HashMap map = new HashMap<>();
        map.put("userid", userid);
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_BLOOD_PRESSURE_ABNORMAL)
                .addAll(map)
                .asResponse(BloodPressureAbnormalBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<BloodPressureAbnormalBean>() {
                    @Override
                    public void accept(BloodPressureAbnormalBean data) throws Exception {
                        BloodPressureAbnormalBean.PressureBean pressure = data.getPressure();
                        List<BloodPressureAbnormalBean.SevenpressureBean> sevenPressureList = data.getSevenpressure();
                        int diastole = pressure.getDiastole();//舒张压
                        int systolic = pressure.getSystolic();//收缩压
                        tvLeft.setText(systolic + "");
                        tvRight.setText(diastole + "");
                        if (sevenPressureList != null && sevenPressureList.size() > 0) {
                            BloodPressureAbnormalAdapter adapter = new BloodPressureAbnormalAdapter(getPageContext(), R.layout.item_blood_pressure_abnormal, sevenPressureList);
                            lvBloodPressureList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

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

    /**
     * 发送
     */
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
    protected int getLayoutId() {
        return R.layout.activity_blood_pressure_abnormal;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血压异常");
        getBloodPressureAbnormal();
        setEt();
    }
}
