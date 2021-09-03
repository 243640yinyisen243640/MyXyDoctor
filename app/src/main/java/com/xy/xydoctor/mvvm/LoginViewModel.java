package com.xy.xydoctor.mvvm;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.rxjava.rxlife.ScopeViewModel;
import com.xy.xydoctor.bean.LoginBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.util.HashMap;

import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;


/**
 * @author jingbin
 * @data 2018/5/7
 * @Description wanandroid登录的ViewModel
 */
public class LoginViewModel extends ScopeViewModel {

    public final ObservableField<String> username = new ObservableField<>();

    public final ObservableField<String> password = new ObservableField<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> login() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            //发送给他的观察者
            //主线程
            //data.setValue(false);
            //主线程 子线程都可以
            data.postValue(false);
            return data;
        }
        //网络请求
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username.get());
        map.put("password", password.get());
        RxHttp.postForm(XyUrl.LOGIN)
                .addAll(map)
                .asResponse(LoginBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        String token = loginBean.getAccess_token();
                        String docId = loginBean.getDocid() + "";
                        String docName = loginBean.getDocname();
                        String docHeadImg = loginBean.getPicture();
                        int type = loginBean.getType();
                        SPStaticUtils.put("token", token);
                        SPStaticUtils.put("docId", docId);
                        SPStaticUtils.put("docName", docName);
                        SPStaticUtils.put("docHeadImg", docHeadImg);
                        SPStaticUtils.put("docType", type);
                        SPStaticUtils.put("hospitalname", loginBean.getHospitalname());
                        SPStaticUtils.put("isproperty", loginBean.getIsproperty());
                        data.postValue(true);


                        //String token = SPStaticUtils.getString("token");
                        RxHttp.setOnParamAssembly(p -> {
                            Method method = p.getMethod();
                            if (method.isGet()) { //Get请求

                            } else if (method.isPost()) { //Post请求

                            }
                            return p.add("access_token", token).add("version", ConstantParam.SERVER_VERSION);
                        });
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        //error.show();
                        //ToastUtils.cancel();
                    }
                });
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(username.get())) {
            ToastUtils.showShort("请输入用户名");
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }
}
