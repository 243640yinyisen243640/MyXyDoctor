package com.xy.xydoctor.ui.activity.smart.smartmakepolicy;

import android.os.Bundle;
import android.widget.TextView;

import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MedicineDetailBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 用药详情
 * 作者: LYD
 * 创建日期: 2019/3/30 17:24
 * 参数:type 0:药品库 1:智能决策
 */
public class MedicineDetailActivity extends BaseActivity {
    @BindView(R.id.tv_medicine_name)
    TextView tvMedicineName;
    @BindView(R.id.tv_medicine_use)
    TextView tvMedicineUse;
    @BindView(R.id.tv_medicine_indication)
    TextView tvMedicineIndication;
    @BindView(R.id.tv_medicine_contraindication)
    TextView tvMedicineContraindication;
    @BindView(R.id.tv_medicine_untoward_effect)
    TextView tvMedicineUntowardEffect;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_medicine_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("用药要求");
        getData();
    }

    private void getData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        RxHttp.postForm(XyUrl.GET_DRUGS_DETAIL)
                .addAll(map)
                .asResponse(MedicineDetailBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<MedicineDetailBean>() {
                    @Override
                    public void accept(MedicineDetailBean data) throws Exception {
                        //MedicineDetailBean data = (MedicineDetailBean) msg.obj;
                        tvMedicineName.setText(data.getTitle());
                        tvMedicineUse.setText(data.getUsage());
                        tvMedicineIndication.setText(data.getIndication());
                        tvMedicineContraindication.setText(data.getContra());
                        tvMedicineUntowardEffect.setText(data.getBad());
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

}
