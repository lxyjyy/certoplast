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
 *
 * 采购收货查询界面
 */

public class MaterialSearchActivity extends BaseActivity {

    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

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

    @BindView(R.id.tv_h1)
    TextView tv_h1;
    @Override
    protected void onResume() {
        super.onResume();

        et_search.setText("");
        et_search.requestFocus();
    }

    /**
     * 初始化
     */
    protected View initView(){
        return LayoutInflater.from(this).inflate(R.layout.activity_migosearch, null, false);
    }

    protected void initData(){

        btn_left_white.setVisibility(View.VISIBLE);


        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());


        tv_title.setText("原料TAG");
        tv_confirm.setText("原料退货");
        tv_h1.setText("原料电子标签绑定");
        tv_confirm.setVisibility(View.VISIBLE);


        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               jumpToSearch1();
            }
        });

        postDelayed(et_search);

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = et_search.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    jumpToSearch1();
                }
            }
        });


    }


    private void jumpToSearch1(){
        String search = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)){
            UiCommon.INSTANCE.showTip("请输入采购入库单号!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("ruku_id", search);
        redictToActivity(MaterialSearchActivity.this, MaterialSearch1Activity.class, bundle);
    }

    @OnClick({R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
        switch (v.getId()){

            case R.id.tv_logout:
                appContext.cleanLoginInfo();
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(MaterialSearchActivity.this, LoginActivity.class, null);
                break;
            case R.id.tv_main:
                finish();
                break;
        }

    }
}
