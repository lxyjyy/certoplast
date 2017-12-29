package com.lab.certoplast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.certoplast.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/19.
 * 产品入库
 */

public class WareHousingActivity extends BaseActivity {


    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.half_pro)
    RelativeLayout half_pro;
    @BindView(R.id.full_pro)
    RelativeLayout full_pro;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_warehouse, null, false);
    }

    @Override
    protected void initData() {
        initialView();
        initialData();
    }


    @OnClick({R.id.btn_left_white, R.id.half_pro, R.id.full_pro})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_left_white:
                finish();
                break;

            case R.id.half_pro:
                redictToActivity(WareHousingActivity.this, HalfProActivity.class, null);
                break;

            case R.id.full_pro:
                redictToActivity(WareHousingActivity.this, FullProActivity.class, null);
                break;

        }
    }



    private void initialView(){
        tv_title.setText("产品入库");
        btn_left_white.setVisibility(View.VISIBLE);
    }


    private void initialData(){

    }
}
