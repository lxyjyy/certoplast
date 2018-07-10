package com.lab.certoplast.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.NineAdapter;
import com.lab.certoplast.bean.DataSource;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnItemClickListener{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_title)
    TextView tv_title;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }



    public void initData(){

        User user = appContext.getLoginInfo();
//        //设置标题
        tv_title.setText(user.getUser_Name());



        DataSource dataSource1 = new DataSource();
        dataSource1.setDrawableId(R.mipmap.icon_cgsh);
        dataSource1.setText("采购收货");

        DataSource dataSource2 = new DataSource();
        dataSource2.setDrawableId(R.mipmap.icon_scly);
        dataSource2.setText("生产领用");


        DataSource dataSource3 = new DataSource();
        dataSource3.setDrawableId(R.mipmap.icon_cprk);
        dataSource3.setText("产品入库");

        DataSource dataSource4 = new DataSource();
        dataSource4.setDrawableId(R.mipmap.icon_fhck);
        dataSource4.setText("发货出库");

        DataSource dataSource5 = new DataSource();
        dataSource5.setDrawableId(R.mipmap.icon_pd);
        dataSource5.setText("盘点");


        DataSource dataSource6 = new DataSource();
        dataSource6.setDrawableId(R.mipmap.icon_zx);
        dataSource6.setText("注销");

        DataSource datasource7 = new DataSource();
        datasource7.setDrawableId(R.mipmap.icon_cgsh);
        datasource7.setText("原料TAG");

        List<DataSource> sources = new ArrayList<>();
        sources.add(dataSource1);
        sources.add(dataSource2);
        sources.add(dataSource3);
        sources.add(dataSource4);
        sources.add(dataSource5);
        sources.add(dataSource6);
        sources.add(datasource7);


        NineAdapter nineAdapter = new NineAdapter(sources);
        nineAdapter.setOnItemClickListener(this);
        recyclerview.setAdapter(nineAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);

        recyclerview.setLayoutManager(layoutManager);

    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:
                //采购收货
                redictToActivity(this, MigoSearchActivity.class, null);
                break;
            case 1:
                //生产领用
                redictToActivity(this, PRActivity.class, null);
                break;
            case 2:
                //产品入库
                redictToActivity(this, WareHousingActivity.class, null);
                break;
            case 3:
                //发货出库
                redictToActivity(this, ShippingActivity.class, null);
                break;
            case 4:
                //盘点
                redictToActivity(this, InventorySearchActivity.class, null);
                break;
            case 5:
                //注销
                redictToActivity(this, LoginActivity.class, null);
                appContext.cleanLoginInfo();
                finish();
                break;
            case 6:
                //material 原料
                redictToActivity(this, MaterialSearchActivity.class, null);
                break;
        }
    }
}
