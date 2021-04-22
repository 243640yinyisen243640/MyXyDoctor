package com.xy.xydoctor.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.util.TurnsUtils;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.GvSugarAddAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.view.popup.CenterPopup;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 添加血糖
 * 作者: LYD
 * 创建日期: 2019/6/18 9:49
 */

public class BloodSugarAddActivity extends BaseActivity {
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.gv_time_quantum)
    GridView gvTimeQuantum;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;
    @BindView(R.id.img_sugar)
    ImageView imgSugar;
    @BindView(R.id.et_sugar)
    EditText etSugar;

    private String gllater = "4.4-10";//控制目标
    private GvSugarAddAdapter adapter;

    //popu开始
    private CenterPopup popup;
    private TextView tvTitle;
    private TextView tvDesc;
    private ColorTextView tvKnow;
    //popu结束

    private int selectPosition;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bloodsugar_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("记录血糖");
        setGv();
        setTvMore();
        initPopu();
        setFirstTime();
    }

    private void setTvMore() {
        getTvMore().setText("保存");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSave();
            }
        });
    }

    private void setFirstTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvCheckTime.setText(nowString);
    }

    private void initPopu() {
        popup = new CenterPopup(getPageContext());
        tvTitle = popup.findViewById(R.id.tv_title);
        tvDesc = popup.findViewById(R.id.tv_desc);
        tvKnow = popup.findViewById(R.id.tv_know);
        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
                toDoSave();
            }
        });
    }


    private void toSave() {
        String time = tvCheckTime.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort("请选择时间");
            return;
        }
        String sugarValue = etSugar.getText().toString().trim();
        if (TextUtils.isEmpty(sugarValue)) {
            ToastUtils.showShort("请输入血糖值");
            return;
        }
        //判断血糖值
        String[] sugarArray = gllater.split("-");
        String sugarLow = sugarArray[0];
        String sugarHigh = sugarArray[1];
        double sugarLowDouble = TurnsUtils.getDouble(sugarLow, 0);
        double sugarHighDouble = TurnsUtils.getDouble(sugarHigh, 0);
        double contentDouble = TurnsUtils.getDouble(sugarValue, 0);
        tvDesc.setText("您上传" + contentDouble + "已超出正常范围");
        if (contentDouble > sugarHighDouble) {
            popup.showPopupWindow();
            tvTitle.setText("血糖高了");
            //return;
        } else if (contentDouble < sugarLowDouble) {
            tvTitle.setText("血糖低了");
            popup.showPopupWindow();
            //return;
        } else {
            toDoSave();
        }
    }

    private void toDoSave() {
        String time = tvCheckTime.getText().toString().trim();
        String sugarValue = etSugar.getText().toString().trim();
        HashMap map = new HashMap<>();
        map.put("uid", getIntent().getStringExtra("userId"));
        map.put("glucosevalue", sugarValue);
        map.put("category", selectPosition + 1);
        map.put("datetime", time);
        map.put("type", "2");

        RxHttp.postForm(XyUrl.ADD_SUGAR).addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort(s);
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    private void setGv() {
        setTimeAndSelection();
        String[] stringArray = getResources().getStringArray(R.array.data_sugar_time);
        List<String> listString = Arrays.asList(stringArray);
        adapter = new GvSugarAddAdapter(getPageContext(), R.layout.item_sugar_add, listString, selectPosition);
        gvTimeQuantum.setAdapter(adapter);
    }

    private void setTimeAndSelection() {
        SimpleDateFormat allFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), allFormat);
        tvCheckTime.setText(allTimeString);
        int time = Integer.parseInt(hour);
        if (time >= 5 && time < 8) {
            selectPosition = 0;
        } else if (time >= 8 && time < 10) {
            selectPosition = 1;
        } else if (time >= 10 && time < 12) {
            selectPosition = 2;
        } else if (time >= 12 && time < 15) {
            selectPosition = 3;
        } else if (time >= 15 && time < 18) {
            selectPosition = 4;
        } else if (time >= 18 && time < 21) {
            selectPosition = 5;
        } else if (time >= 21 && time <= 23) {
            selectPosition = 6;
        } else if (time >= 0 && time < 5) {
            selectPosition = 7;
        }

    }


    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvCheckTime.setText(content);
            }
        });
    }

    @OnTextChanged(value = R.id.et_sugar, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(CharSequence text) {
        if (gllater.contains("-")) {
            String[] sugarArray = gllater.split("-");
            String sugarLow = sugarArray[0];
            String sugarHigh = sugarArray[1];
            double sugarLowDouble = TurnsUtils.getDouble(sugarLow, 0);
            double sugarHighDouble = TurnsUtils.getDouble(sugarHigh, 0);
            String content = text.toString();
            double contentDouble = TurnsUtils.getDouble(content, 0);
            if (TextUtils.isEmpty(content)) {
                imgSugar.setImageResource(R.drawable.sugar_add_none);
            } else if (contentDouble > sugarHighDouble) {
                imgSugar.setImageResource(R.drawable.sugar_add_high);
            } else if (sugarLowDouble < contentDouble) {
                imgSugar.setImageResource(R.drawable.sugar_add_nomal);
            } else {
                imgSugar.setImageResource(R.drawable.sugar_add_low);
            }
        }
    }

    @OnItemClick(R.id.gv_time_quantum)
    void OnItemClick(int position) {
        adapter.setSelect(position);
        selectPosition = position;
    }


}
