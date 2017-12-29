package com.lab.certoplast.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.SpaceItemDecoration;

import butterknife.BindView;

/**
 * Created by lxyjyy on 17/11/8.
 *
 * 盘点列表
 */

public class InventoryListActivity extends BaseActivity {

    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_pdsearchlist);

        initView();
        initData();

    }


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_pdsearchlist, null, false);
    }

    @Override
    protected void initData() {

        initConfig();
        initialData();

    }


    private void initialData(){


       String product_id =  getIntent().getExtras().getString("search");

        if (product_id.contains("-")){
            product_id = product_id.substring(0,product_id.indexOf("-"));
        }




    }


    private void initConfig(){
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        tv_title.setText("库存盘点");


        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showing(){
        recyclerView.setVisibility(View.GONE);
        searching.setVisibility(View.VISIBLE);
    }

    private void hide(){
        recyclerView.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
    }

    private void emptyShowing(){
        recyclerView.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
    }

}
