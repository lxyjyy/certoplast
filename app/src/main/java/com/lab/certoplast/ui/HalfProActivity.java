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
import com.lab.certoplast.utils.UiCommon;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/19.
 * 半成品入库
 */

public class HalfProActivity extends BaseActivity {



    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.btn_search)
    Button btn_search;


    @Override
    protected void initData() {

        tv_title.setText("半成品入库");

        btn_left_white.setVisibility(View.VISIBLE);

        initialData();

    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_halfpro, null, false);
    }

    @OnClick({R.id.btn_left_white, R.id.btn_search})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;

            case R.id.btn_search:

                search();
                break;
        }
    }

    private void search(){

        String search = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(search)){
            UiCommon.INSTANCE.showTip("请输入待入库半成品批号");
            return;
        }

        String[] str = search.split("\\-");

        if (!(str.length >= 2 && !TextUtils.isEmpty(str[0]) && !TextUtils.isEmpty(str[1]))){
            UiCommon.INSTANCE.showTip("批号格式不正确,请重新扫描!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("search", search);

        redictToActivity(this, HalfProDetailActivity.class, bundle);
    }


    private void initialData(){

    }



}
