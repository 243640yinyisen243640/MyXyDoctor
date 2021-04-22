package com.xy.xydoctor.ui.activity.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lyd.baselib.util.TurnsUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgMainActivity;
import com.xy.xydoctor.ui.fragment.patientcount.PatientCountBloodPressureMainFragment;
import com.xy.xydoctor.ui.fragment.patientcount.PatientCountBloodSugarMainFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述: 患者情况统计主页面
 * 作者: LYD
 * 创建日期: 2019/9/26 13:59
 */
public class PatientCountMainActivity extends BaseActivity implements PatientCountBloodSugarMainFragment.CallBackValue {
    private static final String TAG = "PatientCountMainActivity";
    @BindView(R.id.stl)
    SegmentTabLayout stl;
    @BindView(R.id.fl_fragment_container)
    FrameLayout fragmentContainer;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = {"血糖", "血压"};

    private String beginTime;
    private String endTime;
    private String beginSugar;
    private String endSugar;
    private String style = "1";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_count_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTitleBar();
        setTableLayout();
    }

    private void initTitleBar() {
        setTitle("患者情况统计");
        getTvMore().setText("提醒");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPageContext(), MassMsgMainActivity.class);
                intent.putExtra("type", "1");
                //对应接口的type
                intent.putExtra("mainPosition", 0);
                //对应接口的style
                intent.putExtra("listPosition", TurnsUtils.getInt(style, 0));
                //血糖
                intent.putExtra("beginTime", beginTime);
                intent.putExtra("endTime", endTime);
                intent.putExtra("beginSugar", beginSugar);
                intent.putExtra("endSugar", endSugar);
                startActivity(intent);
            }
        });
    }

    private void setTableLayout() {
        listFragment.add(new PatientCountBloodSugarMainFragment());
        listFragment.add(new PatientCountBloodPressureMainFragment());
        stl.setTabData(listTitle, this, R.id.fl_fragment_container, listFragment);
        stl.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    getTvMore().setVisibility(View.VISIBLE);
                } else {
                    getTvMore().setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void sendValue(String style, String beginTime, String endTime, String beginSugar, String endSugar) {
        this.style = style;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.beginSugar = beginSugar;
        this.endSugar = endSugar;
    }
}
