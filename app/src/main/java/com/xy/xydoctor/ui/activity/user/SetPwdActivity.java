package com.xy.xydoctor.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 修改密码
 * 作者: LYD
 * 创建日期: 2019/2/26 16:38
 */
public class SetPwdActivity extends BaseActivity {

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_second)
    EditText etNewPwdSecond;

    @BindView(R.id.bt_reset)
    Button btReset;


    @OnClick({R.id.bt_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_reset:
                checkArgs();
                break;
        }
    }

    private void checkArgs() {
        String oldPwd = etOldPwd.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showShort("请输入原密码");
            return;
        }
        String newPwd = etNewPwd.getText().toString().trim();
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showShort("请输入新密码");
            return;
        }
        String newPwdSecond = etNewPwdSecond.getText().toString().trim();
        if (TextUtils.isEmpty(newPwdSecond)) {
            ToastUtils.showShort("请再次输入新密码");
            return;
        }
        if (!newPwd.equals(newPwdSecond)) {
            ToastUtils.showShort("两次输入的密码不一致");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("oldpass", oldPwd);
        map.put("newpass", newPwd);
        map.put("repnewpass", newPwdSecond);
        RxHttp.postForm(XyUrl.EDIT_PWD)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ActivityUtils.finishAllActivities();
                        SPStaticUtils.clear();
                        startActivity(new Intent(getPageContext(), LoginActivity.class));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("修改密码");
    }
}
