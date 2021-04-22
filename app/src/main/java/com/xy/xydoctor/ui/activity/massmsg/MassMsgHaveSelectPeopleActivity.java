package com.xy.xydoctor.ui.activity.massmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.blankj.utilcode.util.Utils;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgSelectAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.utils.DialogUtils;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 描述: 已选择的消息接收人
 * 作者: LYD
 * 创建日期: 2019/3/12 9:22
 */
public class MassMsgHaveSelectPeopleActivity extends BaseActivity {
    @BindView(R.id.lv_have_select)
    ListView lvHaveSelect;

    @BindView(R.id.bt_sure)
    Button btSure;

    private CommonAdapter<GroupUserBean> adapter;
    private List<GroupUserBean> listObj;


    private void toDel(int position) {
        if (-1 == position) {
            listObj.clear();
            adapter.notifyDataSetChanged();
            //直接返回
            Intent intent = new Intent();
            intent.putExtra("selectUserInfo", (Serializable) listObj);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            listObj.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sure://确定
                Intent intent = new Intent();
                intent.putExtra("selectUserInfo", (Serializable) listObj);
                setResult(RESULT_OK, intent);
                finish();
                //List回传
                break;
        }
    }

    @OnItemClick(R.id.lv_have_select)
    void onItemClick(int position) {
        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除?", true, () -> {
            toDel(position);
        });
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("selectUserInfo", (Serializable) listObj);
            setResult(RESULT_OK, intent);
            finish();//结束av
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mass_msg_have_select_people;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("已选择的用户");
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("selectUserInfo", (Serializable) listObj);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        getTvMore().setText("全部删除");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要删除全部?", true, () -> {
                    toDel(-1);
                });
            }
        });
        listObj = (List<GroupUserBean>) getIntent().getSerializableExtra("haveSelectPeople");
        if (listObj != null && listObj.size() > 0) {
            adapter = new MassMsgSelectAdapter(Utils.getApp(), R.layout.item_group_member_list, listObj);
            lvHaveSelect.setAdapter(adapter);
        }
    }
}
