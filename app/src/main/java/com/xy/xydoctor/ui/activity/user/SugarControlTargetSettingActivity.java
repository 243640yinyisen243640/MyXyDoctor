package com.xy.xydoctor.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.util.TurnsUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SugarControlTargetAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.ResetTargetBean;
import com.xy.xydoctor.bean.ScopeBean;
import com.xy.xydoctor.bean.SugarControlBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OkHttpInstance;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import rxhttp.wrapper.param.RxHttp;

public class SugarControlTargetSettingActivity extends BaseActivity implements View.OnClickListener {
    private static final int GET_SCOPE = 10010;
    @BindView(R.id.lv_list)
    ListView lvList;
    @BindView(R.id.tv_reset)
    ColorTextView tvReset;

    private SugarControlTargetAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sugar_control_target_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("血糖目标设置");
        getTvMore().setText("保存");
        getTvMore().setOnClickListener(this);
        getControlTarget();
    }


    private void getControlTarget() {
        String userGuid = SPStaticUtils.getString("userGuid");
        HashMap map = new HashMap<>();
        map.put("guid", userGuid);
        RxHttp.postForm(XyUrl.GET_USERDATE)
                .addAll(map)
                .asResponse(ScopeBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ScopeBean>() {
                    @Override
                    public void accept(ScopeBean data) throws Exception {
                        ResetTargetBean target = data.getTarget();
                        setLv(target);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    private void setLv(ResetTargetBean target) {
        List<SugarControlBean> list = new ArrayList<>();
        SugarControlBean bean0 = getBean(target.getEmpstomach());
        SugarControlBean bean1 = getBean(target.getAftbreakfast());
        SugarControlBean bean2 = getBean(target.getBeflunch());
        SugarControlBean bean3 = getBean(target.getAftlunch());
        SugarControlBean bean4 = getBean(target.getBefdinner());
        SugarControlBean bean5 = getBean(target.getAftdinner());
        SugarControlBean bean6 = getBean(target.getBefsleep());
        SugarControlBean bean7 = getBean(target.getInmorning());
        list.add(bean0);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        adapter = new SugarControlTargetAdapter(getPageContext(), list);
        lvList.setAdapter(adapter);
    }

    private SugarControlBean getBean(List<Double> list) {
        String min = list.get(0) + "";
        String max = list.get(1) + "";
        SugarControlBean bean = new SugarControlBean(min, max);
        return bean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_base_right:
                toSubmit();
                break;
        }
    }

    private void toSubmit() {
        HashMap<Integer, String> saveMapMin = adapter.saveMapMin;
        HashMap<Integer, String> saveMapMax = adapter.saveMapMax;
        ArrayList<String> listMinAndMax = new ArrayList<>();
        for (int i = 0; i < saveMapMin.size(); i++) {
            String min = saveMapMin.get(i);
            listMinAndMax.add(min);
            String max = saveMapMax.get(i);
            listMinAndMax.add(max);
        }
        //为空判断
        if (16 == listMinAndMax.size()) {
            String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.data_sugar_time);
            String min0 = listMinAndMax.get(0);
            String min1 = listMinAndMax.get(1);
            if (!showToast(min0, min1, stringArray[0])) {
                return;
            }
            String min2 = listMinAndMax.get(2);
            String min3 = listMinAndMax.get(3);
            if (!showToast(min2, min3, stringArray[1])) {
                return;
            }
            String min4 = listMinAndMax.get(4);
            String min5 = listMinAndMax.get(5);
            if (!showToast(min4, min5, stringArray[2])) {
                return;
            }
            String min6 = listMinAndMax.get(6);
            String min7 = listMinAndMax.get(7);
            if (!showToast(min6, min7, stringArray[3])) {
                return;
            }
            String min8 = listMinAndMax.get(8);
            String min9 = listMinAndMax.get(9);
            if (!showToast(min8, min9, stringArray[4])) {
                return;
            }
            String min10 = listMinAndMax.get(10);
            String min11 = listMinAndMax.get(11);
            if (!showToast(min10, min11, stringArray[5])) {
                return;
            }
            String min12 = listMinAndMax.get(12);
            String min13 = listMinAndMax.get(13);
            if (!showToast(min12, min13, stringArray[6])) {
                return;
            }
            String min14 = listMinAndMax.get(14);
            String min15 = listMinAndMax.get(14);
            if (!showToast(min14, min15, stringArray[7])) {
                return;
            }
        }
        String token = SPStaticUtils.getString("token");
        String userGuid = SPStaticUtils.getString("userGuid");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("access_token", token);
        builder.addFormDataPart("guid", userGuid);
        builder.addFormDataPart("empstomach[]", listMinAndMax.get(0));
        builder.addFormDataPart("empstomach[]", listMinAndMax.get(1));

        builder.addFormDataPart("aftbreakfast[]", listMinAndMax.get(2));
        builder.addFormDataPart("aftbreakfast[]", listMinAndMax.get(3));

        builder.addFormDataPart("beflunch[]", listMinAndMax.get(4));
        builder.addFormDataPart("beflunch[]", listMinAndMax.get(5));

        builder.addFormDataPart("aftlunch[]", listMinAndMax.get(6));
        builder.addFormDataPart("aftlunch[]", listMinAndMax.get(7));

        builder.addFormDataPart("befdinner[]", listMinAndMax.get(8));
        builder.addFormDataPart("befdinner[]", listMinAndMax.get(9));

        builder.addFormDataPart("aftdinner[]", listMinAndMax.get(10));
        builder.addFormDataPart("aftdinner[]", listMinAndMax.get(11));

        builder.addFormDataPart("befsleep[]", listMinAndMax.get(12));
        builder.addFormDataPart("befsleep[]", listMinAndMax.get(13));

        builder.addFormDataPart("inmorning[]", listMinAndMax.get(14));
        builder.addFormDataPart("inmorning[]", listMinAndMax.get(15));
        Request request = new Request.Builder()
                .url(XyUrl.ADD_TARGET)
                .post(builder.build())
                .build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogUtils.e("onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res = response.body().string();
                try {
                    org.json.JSONObject object = new org.json.JSONObject(res);
                    String code = object.getString("code");
                    String msg = object.getString("msg");
                    if ("200".equals(code)) {
                        ToastUtils.showShort(msg);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Boolean showToast(String min, String max, String type) {
        if (TextUtils.isEmpty(min)) {
            ToastUtils.showShort("请输入" + type + "最低值");
            return false;
        }
        if (TextUtils.isEmpty(max)) {
            ToastUtils.showShort("请输入" + type + "最高值");
            return false;
        }
        double min0Double = TurnsUtils.getDouble(min, 0);
        double min1Double = TurnsUtils.getDouble(max, 0);
        if (min0Double > min1Double) {
            ToastUtils.showShort(type + "最低值必须得小于最高值哦");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_reset)
    public void onViewClicked() {
        toReset();
    }

    private void toReset() {
        //String id = getIntent().getStringExtra("id");
        HashMap<String, Object> map = new HashMap<>();
        //map.put("id", id);
        RxHttp.postForm(XyUrl.RESET_TARGET)
                .addAll(map)
                .asResponse(ResetTargetBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<ResetTargetBean>() {
                    @Override
                    public void accept(ResetTargetBean targetBean) throws Exception {
                        setLv(targetBean);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
}