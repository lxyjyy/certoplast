package com.lab.certoplast.ui;

import android.view.LayoutInflater;
import android.view.View;

import com.lab.certoplast.R;

/**
 * Created by lxyjyy on 17/11/7.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected View initView() {

        return LayoutInflater.from(this).inflate(R.layout.activity_splash, null, false);

    }


    @Override
    protected void initData() {
        initLogin();

        redirectToInterface();

    }

    /**
     * 初始化登录信息
     */
    private void initLogin(){

        appContext.initLoginInfo();
    }

    /**
     * 跳转到对应界面
     */
    private void redirectToInterface(){

        if (appContext.isLogin()){
            //已经登录,跳转到主页面
            redictToActivity(this, MainActivity.class, null);
        }else {
            //未登录  跳转到用户登录界面
            redictToActivity(this, LoginActivity.class, null);
        }

        finish();

    }
}
