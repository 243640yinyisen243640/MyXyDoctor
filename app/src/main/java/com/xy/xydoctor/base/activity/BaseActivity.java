package com.xy.xydoctor.base.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.view.popup.LoginOutPopup;

import butterknife.ButterKnife;


/**
 * 描述: 基础Activity
 * 作者: LYD
 * 创建日期: 2019/3/25 10:05
 */
public abstract class BaseActivity extends AppCompatActivity {
    //修改为protected,不需要再提供额外的方法
    private Button btBack;
    private TextView tvTitle;
    private TextView tvMore;
    private RelativeLayout rlTitleBar;
    private View line;
    private Context mContext;

    private OtherEquipmentLoginReceiver receiver;
    private LoginOutPopup loginOutPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_base);
        btBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        tvMore = findViewById(R.id.tv_base_right);
        rlTitleBar = findViewById(R.id.rl_titlebar);
        btBack.setOnClickListener(view -> finish());
        line = findViewById(R.id.line);
        //添加内容布局
        RelativeLayout rlContent = findViewById(R.id.rl_content);
        View contentView = LayoutInflater.from(this).inflate(getLayoutId(), rlContent, false);
        rlContent.addView(contentView);
        //View注入
        ButterKnife.bind(this);
        //状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        //初始化数据
        init(savedInstanceState);
        setScreenOrientation(true);
        loginOutPopup = new LoginOutPopup(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        receiver = new OtherEquipmentLoginReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LoginOut");
        registerReceiver(receiver, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //解除广播
        unregisterReceiver(receiver);
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
     * 显示标题
     */
    public void showTitleBar() {
        rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏标题
     */
    public void hideTitleBar() {
        rlTitleBar.setVisibility(View.GONE);
    }

    /**
     * 隐藏返回键
     */
    public void hideBack() {
        btBack.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 返回
     *
     * @return
     */
    public Button getBack() {
        return btBack;
    }


    /**
     * 获取右边
     *
     * @return
     */
    public TextView getTvMore() {
        return tvMore;
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
     * 显示下划线
     */
    protected void showLine() {
        line.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏下划线
     */
    protected void hideLine() {
        line.setVisibility(View.GONE);
    }

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
     * 推送登录
     */
    private void otherEquipmentLogin() {
        loginOutPopup.showPopupWindow();
        ColorTextView tvSure = loginOutPopup.findViewById(R.id.tv_login_out);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //JPushUtils.deleteAlias();
                CloudPushService pushService = PushServiceFactory.getCloudPushService();
                pushService.unbindAccount(new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
                ToastUtils.showShort("您账号已过期，请重新登陆。");
                SPStaticUtils.clear();
                ActivityUtils.finishAllActivities();
                Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }

    /**
     * 在其他设备登录
     */
    public class OtherEquipmentLoginReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if ("LoginOut".equals(intent.getAction())) {
                //Log.e(TAG, "广播收到");
                otherEquipmentLogin();
            }
        }
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     * 禁止app字体大小跟随系统字体大小调节
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.setToDefaults();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
