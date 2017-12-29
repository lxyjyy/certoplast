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

/**
 * Created by lxyjyy on 17/11/8.
 *
 * 采购收货查询界面
 */

public class MigoSearchActivity extends BaseActivity {

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


    /**
     * 初始化
     */
    protected View initView(){
        return LayoutInflater.from(this).inflate(R.layout.activity_cgshsearch, null, false);
    }

    protected void initData(){

        btn_left_white.setVisibility(View.VISIBLE);

        tv_title.setText("入库查询");
        tv_confirm.setText("原料退货");
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
                String search = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(search)){
                    UiCommon.INSTANCE.showTip("请输入采购入库单号!");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("ruku_id", search);
                redictToActivity(MigoSearchActivity.this, MigoSearchListActivity.class, bundle);
            }
        });
    }
}
