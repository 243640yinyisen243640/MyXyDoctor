package com.lyd.baselib.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.R;
import com.lyd.baselib.databinding.ActivityBaseBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * 描述: 基于ViewBinding封装的BaseActivity
 * 作者: LYD
 * 创建日期: 2019/3/25 10:05
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends AppCompatActivity {
    private static final String TAG = "BaseViewBindingActivity";
    private Context mContext;
    //ViewBinding封装开始
    public ActivityBaseBinding baseBinding;
    public T mBinding;
    //ViewBinding封装结束


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //ViewBinding封装
        initViewBinding();
        baseBinding.btnBack.setOnClickListener(v -> finish());
        //状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        //初始化数据
        init(savedInstanceState);
        //横竖屏
        setScreenOrientation(true);
    }

    /**
     * 封装ViewBinding
     */
    private void initViewBinding() {
        baseBinding = ActivityBaseBinding.inflate(getLayoutInflater());
        View view = getLayoutInflater().inflate(getLayoutId(), null);
        baseBinding.rlContent.addView(view);
        setContentView(baseBinding.getRoot());
        try {
            //获得该类带有泛型的父类
            Type genericSuperclass = getClass().getGenericSuperclass();
            Log.e(TAG, "genericSuperclass==" + genericSuperclass);
            //ParameterizedType参数化类型，即泛型
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Log.e(TAG, "parameterizedType==" + parameterizedType);
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Class clazz = (Class) parameterizedType.getActualTypeArguments()[0];
            Log.e(TAG, "clazz==" + clazz);
            Method method = clazz.getDeclaredMethod("bind", View.class);
            Log.e(TAG, "method==" + method);
            mBinding = (T) method.invoke(null, view);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取内容布局Id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * 设置屏幕横竖屏切换
     *
     * @param screenRotate true  竖屏     false  横屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void setScreenOrientation(Boolean screenRotate) {
        if (screenRotate) {
            //设置竖屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //设置横屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 获取当前上下文
     *
     * @return
     */
    public Context getPageContext() {
        return mContext;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        baseBinding.tvTitle.setText(title);
    }
}
