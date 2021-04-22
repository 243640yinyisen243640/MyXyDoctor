package com.xy.xydoctor.ui.fragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.base.fragment.BaseEventBusFragment;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SelectGroupAdapter;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 分组管理之分组列表
 * 作者: LYD
 * 创建日期: 2019/2/27 14:52
 */
@BindEventBus
public class GroupListFragment extends BaseEventBusFragment {
    private static final String TAG = "GroupListFragment";
    @BindView(R.id.lv_group_list)
    ListView lvGroupList;
    @BindView(R.id.bt_del)
    Button btDel;

    private List<GroupListBean> data;
    private SelectGroupAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_group_list;
    }

    @Override
    protected void init(View rootView) {
        setButtonAdjustKeyBoard();
        getStoreList();
    }

    private void setButtonAdjustKeyBoard() {
        KeyboardUtils.registerSoftInputChangedListener(getActivity().getWindow(), new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btDel.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                btDel.setLayoutParams(layoutParams);
                if (height > 0) {
                    Log.e(TAG, "打开");
                } else {
                    Log.e(TAG, "隐藏");
                }
            }
        });
    }

    private void getStoreList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> myTreatPlanBeans) throws Exception {
                        data = myTreatPlanBeans;
                        if (data != null && data.size() > 0) {
                            data.remove(0);
                            lvGroupList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                            adapter = new SelectGroupAdapter(Utils.getApp(), data);
                            lvGroupList.setAdapter(adapter);
                            //CHOICE_MODE_SINGLE         --> android.R.layout.simple_list_item_single_choice
                            //CHOICE_MODE_MULTIPLE       --> android.R.layout.simple_list_item_multiple_choice
                            //CHOICE_MODE_MULTIPLE_MODAL --> android.R.layout.simple_list_item_multiple_choice
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @OnClick(R.id.bt_del)
    public void onViewClicked() {
        //删除分组
        SparseBooleanArray checkedItemPositions = lvGroupList.getCheckedItemPositions();
        ArrayList<Integer> checkedList = new ArrayList<>();
        for (int i = 0; i < checkedItemPositions.size(); i++) {
            int key = checkedItemPositions.keyAt(i);
            boolean value = checkedItemPositions.valueAt(i);
            if (value) {
                checkedList.add(key);
            }
        }
        if (checkedList.size() < 1) {
            ToastUtils.showShort("请先选择你要删除的分组");
        } else {
            ArrayList<String> listSelect = new ArrayList<>();
            //第一种
            for (int i = 0; i < data.size(); i++) {
                boolean b = checkedItemPositions.get(i);
                if (b) {
                    String gid = data.get(i).getGid() + "";
                    listSelect.add(gid);
                }
            }
            DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确认删除?", true, () -> {
                toDel(listSelect);
            });
        }

    }

    /**
     * 删除分组
     *
     * @param listSelect
     */
    private void toDel(ArrayList<String> listSelect) {
        Map<String, String> fieldMap = new IdentityHashMap<>();
        for (int i = 0; i < listSelect.size(); i++) {
            //new String不能省略
            fieldMap.put(new String("gid[]"), listSelect.get(i));
        }
        RxHttp.postForm(XyUrl.DEL_GROUP)
                .addAll(fieldMap)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        getStoreList();
                        EventBusUtils.post(new EventMessage(ConstantParam.EventCode.GROUP_DEL));//刷新列表
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.GROUP_ADD:
                getStoreList();
                break;
        }
    }
}
