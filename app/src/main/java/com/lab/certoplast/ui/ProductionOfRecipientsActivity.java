package com.lab.certoplast.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.ProductionOfRecipientsAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.ProductionOfRecipients;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.parser.ProductionOfRecipientsParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *  生产领用列表
 */

public class ProductionOfRecipientsActivity extends BaseActivity implements OnItemClickListener{


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

    private List<ProductionOfRecipients> list;

    private ProductionOfRecipientsAdapter adapter;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_productionofrecipients, null, false);
    }


    @Override
    protected void initData() {

        initConfig();

        initShowing();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void updateData(){

    }

    private void initShowing(){
        //获取生产领用数据
        showing();
        RequestVo vo  = new RequestVo();

        vo.methodName = "GetScjh_Yl";
        vo.jsonParser = new ProductionOfRecipientsParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    List<ProductionOfRecipients> list1 = (List<ProductionOfRecipients>) paramObject;

                    if (list1.size() <= 0){
                        emptyShowing();
                    }else {
                        hide();
                        list.clear();
                        list.addAll(list1);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                emptyShowing();
            }

            @Override
            public void onError(ErrorMessage error) {
                emptyShowing();
            }
        });
    }


    private void initConfig(){

        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        tv_title.setText("生产领用");

        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
        adapter = new ProductionOfRecipientsAdapter(list);
        adapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(View view, int position) {

        ProductionOfRecipients pr = list.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("sy_id", pr.getTime());
        bundle.putString("product_id", pr.getProduct_id());


        redictToActivity(this, ProductionOfRecipientsDetailActivity.class, bundle);

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
    }


}
