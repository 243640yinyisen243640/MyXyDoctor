package com.xy.xydoctor.ui.activity.homesign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FamilyMemberNewAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.FamilyMemberBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.xy.xydoctor.view.popup.HomeSignSelectTypePopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/*
 * 包名:     com.xy.xydoctor.ui.activity.homesign
 * 类名:     FamilyActivity
 * 描述:     家庭签约详情
 * 作者:     ZWK
 * 创建日期: 2020/1/6 13:12
 */
@BindEventBus
public class FamilyActivity extends BaseEventBusActivity {

    private static final int GET_DATA = 0x0081;
    private static final int REFRESH = 0x0082;

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_remove)
    TextView tvRemove;
    @BindView(R.id.rv_family)
    RecyclerView rvFamily;
    int id;

    private List<FamilyMemberBean> personBeanList;
    private FamilyMemberNewAdapter adapter;
    private boolean bl = true;
    private HomeSignSelectTypePopup signPopup;

    private void setAdapterAndLayout() {
        //adapter = new FamilyMemberAdapter(getPageContext(), R.layout.item_home_sign, personBeanList);
        adapter = new FamilyMemberNewAdapter(personBeanList);
        rvFamily.setAdapter(adapter);
        rvFamily.setLayoutManager(new LinearLayoutManager(getPageContext()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getPageContext(), PatientInfoActivity.class);
                intent.putExtra("userid", personBeanList.get(position).getUserid());
                intent.putExtra("show_is_sign", true);
                startActivity(intent);
            }
        });

        //        adapter.setOnItemRemoveListener(new FamilyMemberAdapter.OnItemRemoveListener() {
        //            @Override
        //            public void OnItemRemove(int id) {
        //                RxHttp.postForm(Url.FAMILY_DEL)
        //                        .add("familyid", id)
        //                        .asResponse(String.class)
        //                        .as(RxLife.asOnMain(rvFamily))
        //                        .subscribe(new Consumer<String>() {
        //                            @Override
        //                            public void accept(String s) throws Exception {
        //                                ToastUtils.showShort(s);
        //                                personBeanList = new ArrayList<>();
        //                                initData(REFRESH);
        //                            }
        //                        });
        //
        //            }
        //        });
    }

    private void initData(int type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        RxHttp.postForm(XyUrl.GET_FAMILY_MEMBERS_LIST)
                .addAll(map)
                .asResponseList(FamilyMemberBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FamilyMemberBean>>() {
                    @Override
                    public void accept(List<FamilyMemberBean> myTreatPlanBeans) throws Exception {
                        switch (type) {
                            case GET_DATA:
                                personBeanList.addAll(myTreatPlanBeans);
                                adapter.notifyDataSetChanged();
                                break;
                            case REFRESH:
                                personBeanList = myTreatPlanBeans;
                                setAdapterAndLayout();
                                adapter.setRemoveButton(!bl);
                                if (!bl) {
                                    tvRemove.setText("完成");
                                    tvRemove.setTextColor(ContextCompat.getColor(getPageContext(), R.color.white));
                                    tvRemove.setBackground(ContextCompat.getDrawable(getPageContext(), R.drawable.shape_btn_remove_checked));

                                    //                                    adapter.setOnItemClickListener(new FamilyMemberAdapter.OnItemClickListener() {
                                    //                                        @Override
                                    //                                        public void OnItemClick(View v, int position) {
                                    //
                                    //                                        }
                                    //                                    });
                                } else {
                                    tvRemove.setText("移出");
                                    tvRemove.setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
                                    tvRemove.setBackground(ContextCompat.getDrawable(getPageContext(), R.drawable.shape_btn_remove_normal));

                                    //                                    adapter.setOnItemClickListener(new FamilyMemberAdapter.OnItemClickListener() {
                                    //                                        @Override
                                    //                                        public void OnItemClick(View v, int position) {
                                    //                                            Intent intent = new Intent(getPageContext(), PatientInfoActivity.class);
                                    //                                            intent.putExtra("userid", personBeanList.get(position).getUserid());
                                    //                                            intent.putExtra("show_is_sign", true);
                                    //                                            startActivity(intent);
                                    //                                        }
                                    //                                    });
                                }
                                break;
                        }
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
        if (event.getCode() == ConstantParam.EventCode.FAMILY_MEMBER_ADD) {
            initData(REFRESH);
        }
    }

    @OnClick({R.id.tv_add, R.id.tv_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                signPopup.showPopupWindow();
                break;
            case R.id.tv_remove:
                adapter.setRemoveButton(bl);
                if (bl) {
                    tvRemove.setText("完成");
                    tvRemove.setTextColor(ContextCompat.getColor(getPageContext(), R.color.white));
                    tvRemove.setBackground(ContextCompat.getDrawable(getPageContext(), R.drawable.shape_btn_remove_checked));

                    //                    adapter.setOnItemClickListener(new FamilyMemberAdapter.OnItemClickListener() {
                    //                        @Override
                    //                        public void OnItemClick(View v, int position) {
                    //
                    //                        }
                    //                    });
                } else {
                    tvRemove.setText("移出");
                    tvRemove.setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
                    tvRemove.setBackground(ContextCompat.getDrawable(getPageContext(), R.drawable.shape_btn_remove_normal));

                    //                    adapter.setOnItemClickListener(new FamilyMemberAdapter.OnItemClickListener() {
                    //                        @Override
                    //                        public void OnItemClick(View v, int position) {
                    //                            Intent intent = new Intent(getPageContext(), PatientInfoActivity.class);
                    //                            intent.putExtra("userid", personBeanList.get(position).getUserid());
                    //                            intent.putExtra("show_is_sign", true);
                    //                            startActivity(intent);
                    //                        }
                    //                    });
                }
                bl = !bl;
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_family;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("family_id", -1);

        String name = getIntent().getStringExtra("name");
        String title = String.format("%s的家庭", name);
        setTitle(title);

        int width = (ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(51)) / 2;
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) tvAdd.getLayoutParams();
        params1.width = width;
        tvAdd.setLayoutParams(params1);
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) tvRemove.getLayoutParams();
        params2.width = width;
        tvRemove.setLayoutParams(params2);

        signPopup = new HomeSignSelectTypePopup(this);
        signPopup.setText("请选择新增方式", "新增编辑", "从签约列表选择");
        signPopup.setTextColor(Color.parseColor("#FF0D0D0D"),
                Color.parseColor("#FF0D0D0D"),
                Color.parseColor("#FF0D0D0D"));
        signPopup.setOnTypeSelectListener(new HomeSignSelectTypePopup.OnTypeSelectListener() {
            @Override
            public void OnLeftSelected(View v) {
                signPopup.dismiss();
                Intent intent = new Intent(getPageContext(), SignRemoteActivity.class);
                intent.putExtra("family_id", id);
                startActivity(intent);
            }

            @Override
            public void OnRightSelected(View v) {
                signPopup.dismiss();
                Intent intent = new Intent(getPageContext(), MyHomeSignListActivity.class);
                intent.putExtra("select", true);
                intent.putExtra("family_id", id);
                startActivity(intent);
            }
        });

        personBeanList = new ArrayList<>();
        setAdapterAndLayout();
        initData(GET_DATA);
    }
}
