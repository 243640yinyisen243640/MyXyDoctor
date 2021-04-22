package com.xy.xydoctor.ui.activity.patientadd;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.NewSearchUserBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.patient.PatientInfoActivity;
import com.xy.xydoctor.utils.DialogUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 添加患者首页
 * 作者: LYD
 * 创建日期: 2019/2/28 14:06
 */
public class PatientAddSearchActivity extends BaseActivity {
    private static final String TAG = "PatientAddSearchActivity";
    @BindView(R.id.et_input_tel)
    EditText etInputTel;
    @BindView(R.id.img_del)
    ImageView imgDel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_add_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("添加患者");
        initEdit();
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
        String tel = etInputTel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(tel)) {
            showCorrectTel(tel);
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("telphone", tel);
        RxHttp.postForm(XyUrl.SEARCH_USER)
                .addAll(map)
                .asClass(NewSearchUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<NewSearchUserBean>() {
                    @Override
                    public void accept(NewSearchUserBean response) throws Exception {
                        int code = response.getCode();
                        NewSearchUserBean.DataBean data = response.getData();
                        Intent intent = null;
                        switch (code) {
                            case 200:
                                intent = new Intent(getPageContext(), PatientAddActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("from", getIntent().getStringExtra("from"));
                                bundle.putSerializable("userBean", data);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case 30014://已绑定 其他医生
                                showHaveBind(tel);
                                break;
                            case 30017://已绑定 当前医生名下 跳详情
                                intent = new Intent(getPageContext(), PatientInfoActivity.class);
                                intent.putExtra("userid", data.getUserid() + "");
                                startActivity(intent);
                                break;
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 提示已经绑定
     *
     * @param tel
     */
    private void showHaveBind(String tel) {
        DialogUtils.getInstance().showDialog(getPageContext(), tel, "该用户已被绑定", false, () -> {
        });
    }

    /**
     * 提示请输入正确的手机号
     *
     * @param tel
     */
    private void showCorrectTel(String tel) {
        DialogUtils.getInstance().showDialog(getPageContext(), tel, "请输入正确的手机号", false, () -> {
        });
    }


}
