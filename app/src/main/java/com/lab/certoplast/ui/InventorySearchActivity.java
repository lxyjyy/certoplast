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


    /**
     * 初始化控件
     */
    protected View initView(){
        return LayoutInflater.from(this).inflate(R.layout.activity_pdsearch, null, false);

    }


    private void initListener(){

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
                String search_txt = et_search.getText().toString().trim();

                if (TextUtils.isEmpty(search_txt)){
                    UiCommon.INSTANCE.showTip("请输入待盘点货物批号!");
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString("search",search_txt);
                redictToActivity(InventorySearchActivity.this, InventoryListActivity.class, bundle);

            }
        });

    }


    @Override
    protected void initData() {
        initListener();
    }
}
