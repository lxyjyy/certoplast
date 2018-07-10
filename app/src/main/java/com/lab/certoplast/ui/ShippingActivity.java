package com.lab.certoplast.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.utils.UiCommon;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/20.
 *
 * 发货出库
 */

public class ShippingActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_shipping, null, false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
        et_search.requestFocus();
    }

    @Override
    protected void initData() {

        tv_title.setText("发货出库");

        btn_left_white.setVisibility(View.VISIBLE);

        postDelayed(et_search);

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());


        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = et_search.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    search();
                }
            }
        });

    }

    @OnClick({R.id.btn_left_white, R.id.btn_search, R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;
            case R.id.btn_search:
                search();

                break;

            case R.id.tv_logout:
                appContext.cleanLoginInfo();
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(this, LoginActivity.class, null);
                break;
            case R.id.tv_main:
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(this, MainActivity.class, null);
                break;
        }
    }

    private void search(){

        String search = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(search)){
            UiCommon.INSTANCE.showTip("请输入发货单号!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("id", search);

        redictToActivity(this, ShippingDetailActivity.class, bundle);


    }


}
