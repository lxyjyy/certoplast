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
 * Created by lxyjyy on 17/11/8.
 * 盘点搜索界面
 */

public class InventorySearchActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_search)
    Button btn_search;


    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    /**
     * 初始化控件
     */
    protected View initView(){
        return LayoutInflater.from(this).inflate(R.layout.activity_pdsearch, null, false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
        et_search.requestFocus();
    }

    private void initListener(){


        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

        btn_left_white.setVisibility(View.VISIBLE);


        btn_left_white.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                jumpToList();
            }
        });

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = et_search.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    jumpToList();
                }
            }
        });


    }

    private void jumpToList(){
        String search_txt = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(search_txt)){
            UiCommon.INSTANCE.showTip("请输入待盘点货物批号!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("search",search_txt);
        redictToActivity(InventorySearchActivity.this, InventoryListActivity.class, bundle);

    }

    @OnClick({ R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
        switch (v.getId()){
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

    @Override
    protected void initData() {

        tv_title.setText("库房盘点");
        initListener();
    }
}
