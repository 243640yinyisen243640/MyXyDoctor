package com.xy.xydoctor.ui.activity.massmsg;

import android.os.Bundle;
import android.widget.ListView;

import com.blankj.utilcode.util.Utils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgAffiliatedPersonAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.MassHistoryBean;

import java.util.List;

import butterknife.BindView;

/**
 * 描述: 群发消息更多参与人
 * 作者: LYD
 * 创建日期: 2019/3/1 17:49
 */
public class MassMsgAffiliatedPersonActivity extends BaseActivity {
    @BindView(R.id.lv_mass_msg_affiliated_person)
    ListView lvMassMsgAffiliatedPerson;

    private void initValue() {
        List<MassHistoryBean.UserBean> listObj = (List<MassHistoryBean.UserBean>) getIntent().getSerializableExtra("listUser");
        MassMsgAffiliatedPersonAdapter adapter = new MassMsgAffiliatedPersonAdapter(Utils.getApp(), R.layout.item_group_member_list, listObj);
        lvMassMsgAffiliatedPerson.setAdapter(adapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_affiliated_person;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("更多参与人");
        initValue();
    }
}
