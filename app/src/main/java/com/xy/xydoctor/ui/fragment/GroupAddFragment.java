package com.xy.xydoctor.ui.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 分组管理之新建分组
 * 作者: LYD
 * 创建日期: 2019/2/27 14:52
 */
public class GroupAddFragment extends BaseFragment {
    private static final String TAG = "GroupAddFragment";
    @BindView(R.id.et_input_group_name)
    EditText etInputGroupName;
    @BindView(R.id.bt_save)
    Button btSave;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_add;
    }

    @Override
    protected void init(View rootView) {
        setButtonAdjustKeyBoard();
    }

    private void setButtonAdjustKeyBoard() {
        KeyboardUtils.registerSoftInputChangedListener(getActivity().getWindow(), new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btSave.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                btSave.setLayoutParams(layoutParams);
                if (height > 0) {
                    Log.e(TAG, "打开");
                } else {
                    Log.e(TAG, "隐藏");
                }
            }
        });
    }


    @OnClick(R.id.bt_save)
    public void onViewClicked() {
        String groupName = etInputGroupName.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            ToastUtils.showShort("请输入分组名称");
            return;
        }
        toAddGroup(groupName);
    }

    private void toAddGroup(String groupName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupname", groupName);
        RxHttp.postForm(XyUrl.ADD_GROUP)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("添加成功");
                        etInputGroupName.setText("");
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.GROUP_ADD));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


}
