package com.xy.xydoctor.ui.activity.director;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.util.edittext.EditTextUtils;
import com.lyd.librongim.myrongim.GroupUserBeanPatient;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientForDoctorAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SearchUserIndexBean;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.imp.IAdapterViewClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Call;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 搜索
 * 作者: LYD
 * 创建日期: 2020/4/14 14:16
 */
public class SearchPatientActivity extends BaseActivity {
    @BindView(R.id.et_input_tel_remove)
    EditText etInputTel;
    @BindView(R.id.img_del_remove)
    ImageView imgDel;
    @BindView(R.id.ry_search_result_remove)
    RecyclerView lvSearchResult;
    @BindView(R.id.bt_search_remove)
    Button btSearch;
    private PatientForDoctorAdapter adapter;

    private List<GroupUserBeanPatient> allDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索患者");
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        lvSearchResult.setLayoutManager(layoutManager);
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
                .init();
        showKeyBoard();
    }


    private void showKeyBoard() {
        //弹出软键盘
        KeyboardUtils.showSoftInput();
        //设置Xml中设置InputType的键盘类型
        etInputTel.setFocusable(true);
        etInputTel.setFocusableInTouchMode(true);
        etInputTel.requestFocus();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_patient;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initEdit();
    }

    private void initEdit() {
        etInputTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    imgDel.setVisibility(View.VISIBLE);
                } else {
                    imgDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditTextUtils.setOnActionSearch(etInputTel, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                getGroupList();
            }
        });
    }

    @OnClick({R.id.img_del_remove, R.id.bt_search_remove})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_del_remove:
                etInputTel.setText("");
                imgDel.setVisibility(View.GONE);
                break;
            case R.id.bt_search_remove:
                if (btSearch.getText().toString().equals("搜索")) {
                    getGroupList();
                } else {

                    List<GroupUserBeanPatient> checkList = new ArrayList<>();
                    for (int i = 0; i < allDataBean.size(); i++) {
                        if (allDataBean.get(i).isCheck()) {
                            checkList.add(allDataBean.get(i));
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("checkList", (Serializable) checkList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }


    private void checkSearchArgs() {
        String keyWord = etInputTel.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入患者姓名、手机号");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyword", keyWord);
        RxHttp.postForm(XyUrl.SEARCH_USER_INDEX)
                .addAll(map)
                .asResponseList(SearchUserIndexBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<SearchUserIndexBean>>() {
                    @Override
                    public void accept(List<SearchUserIndexBean> searchUserIndexBeans) throws Exception {
                        KeyboardUtils.hideSoftInput(SearchPatientActivity.this);
                        //                        setLv(searchUserIndexBeans);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void getGroupList() {
        int doctorID = getIntent().getIntExtra("docid", 0);
        if (allDataBean != null) {
            allDataBean.clear();
        }
        String keyWord = etInputTel.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入患者姓名、手机号");
            return;
        }
        Call<String> requestCall = DataManager.getGroupListSearch(keyWord, doctorID + "",
                (call, response) -> {
                    if (response.code == 200) {
                        allDataBean = (List<GroupUserBeanPatient>) response.object;
                        if (allDataBean != null && allDataBean.size() > 0) {
                            btSearch.setText("确定");
                            KeyboardUtils.hideSoftInput(SearchPatientActivity.this);
                            adapter = new PatientForDoctorAdapter(getPageContext(), allDataBean, new IAdapterViewClickListener() {
                                @Override
                                public void adapterClickListener(int position, View view) {

                                    allDataBean.get(position).setCheck(!allDataBean.get(position).isCheck());
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void adapterClickListener(int position, int index, View view) {

                                }
                            });
                            lvSearchResult.setAdapter(adapter);

                        } else {
                            ToastUtils.showShort(getString(R.string.no_data));
                        }
                    }
                }, (call, t) -> {
                    ToastUtils.showShort(getString(R.string.network_error));
                });
    }


}
