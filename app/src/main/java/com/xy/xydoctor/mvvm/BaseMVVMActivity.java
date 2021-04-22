package com.xy.xydoctor.mvvm;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.xy.xydoctor.R;
import com.xy.xydoctor.databinding.ActivityBaseMvvmBinding;

/**
 * @author jingbin
 * @date 16/12/10
 */
public abstract class BaseMVVMActivity<VM extends AndroidViewModel, SV extends ViewDataBinding> extends AppCompatActivity {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;
    private ActivityBaseMvvmBinding mBaseBinding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base_mvvm, null, false);
        bindingView = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
        //bindingView.setLifecycleOwner(this);
        //content
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(layoutParams);
        RelativeLayout rlContent = mBaseBinding.getRoot().findViewById(R.id.rl_content);
        rlContent.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());
        //bindingView.getRoot().setVisibility(View.GONE);
        initViewModel();
    }


    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            //this.viewModel = ViewModelProviders.of(this).get(viewModelClass);
            this.viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
    }

    /**
     * 标题栏相关 Start
     */

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitle(CharSequence text) {
        mBaseBinding.tvTitle.setText(text);
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitleBar() {
        mBaseBinding.rlTitle.setVisibility(View.GONE);
    }
    /**
     * 标题栏相关 End
     */

    /**
     * 获取当前上下文
     *
     * @return
     */
    public Context getPageContext() {
        return mContext;
    }
}
