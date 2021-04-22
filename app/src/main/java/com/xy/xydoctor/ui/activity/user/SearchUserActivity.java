package com.xy.xydoctor.ui.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.util.edittext.EditTextUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.SearchUserIndexAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.SearchUserIndexBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 搜索
 * 作者: LYD
 * 创建日期: 2020/4/14 14:16
 */
public class SearchUserActivity extends BaseActivity {
    @BindView(R.id.et_input_tel)
    EditText etInputTel;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.lv_search_result)
    ListView lvSearchResult;
    @BindView(R.id.bt_search)
    Button btSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("搜索患者");
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
        return R.layout.activity_search_user;
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
                checkSearchArgs();
            }
        });
    }

    @OnClick({R.id.img_del, R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_del:
                etInputTel.setText("");
                imgDel.setVisibility(View.GONE);
                break;
            case R.id.bt_search:
                checkSearchArgs();
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
                        KeyboardUtils.hideSoftInput(SearchUserActivity.this);
                        setLv(searchUserIndexBeans);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * @param searchUserIndexBeans
     */
    private void setLv(List<SearchUserIndexBean> searchUserIndexBeans) {
        SearchUserIndexAdapter adapter = new SearchUserIndexAdapter(getPageContext(), R.layout.item_search_user_index, searchUserIndexBeans);
        lvSearchResult.setAdapter(adapter);
    }
}
