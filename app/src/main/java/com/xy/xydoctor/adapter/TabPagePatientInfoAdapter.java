package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xy.xydoctor.R;

import java.util.List;

/**
 * 描述: TabLayout的Adapter
 * 作者: LYD
 * 创建日期: 2019/3/1 10:07
 */
public class TabPagePatientInfoAdapter extends FragmentPagerAdapter {
    private Context context;
    //type:patientInfo 患者详情
    //type:bloodSugar  7天和30天血糖
    private String type;
    private List<Fragment> mFragmentList;

    public TabPagePatientInfoAdapter(FragmentManager fm, Context context, String type, List<Fragment> list) {
        super(fm);
        this.context = context;
        this.type = type;
        this.mFragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //注意！！！这里就是我们自定义的布局tab_item
    public View getCustomView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_patient_info, null);
        TextView tvTitle = view.findViewById(R.id.tv_patient_info_title);
        View line = view.findViewById(R.id.view);
        switch (type) {
            case "patientInfo":
                switch (position) {
                    case 0:
                        tvTitle.setText("健康记录");
                        tvTitle.setTextSize(18);
                        line.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvTitle.setText("健康档案");
                        tvTitle.setTextSize(16);
                        line.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        tvTitle.setText("在线测量");
                        tvTitle.setTextSize(16);
                        line.setVisibility(View.INVISIBLE);
                        break;
                }
                break;
            case "bloodSugar":
                switch (position) {
                    case 0:
                        tvTitle.setText("7天");
                        tvTitle.setTextSize(18);
                        line.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvTitle.setText("30天");
                        tvTitle.setTextSize(16);
                        line.setVisibility(View.INVISIBLE);
                        break;
                }
                break;
        }
        return view;
    }
}