package com.xy.xydoctor.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPStaticUtils;
import com.xy.xydoctor.R;
import com.xy.xydoctor.mvvm.LoginActivity;
import com.xy.xydoctor.ui.activity.MainActivity;

/**
 * 描述:启动屏(设置SplashTheme)
 * 作者: LYD
 * 创建日期: 2019/4/4 14:34
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSplash();
    }


    /**
     * 设置启动页
     */
    private void setSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = SPStaticUtils.getString("token");
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 1000);
    }
}
