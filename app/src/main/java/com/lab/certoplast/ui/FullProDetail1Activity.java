package com.lab.certoplast.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.FullProAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.FullPro;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.parser.FullProParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/30.
 *
 * 查询---成品入库
 */

public class FullProDetail1Activity extends BaseActivity implements OnItemClickListener {


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

    private List<FullPro> list;
    private FullProAdapter adapter;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_fullprodetail1, null, false);
    }

    @Override
    protected void initData() {

        initialView();
        initialData();

    }



    @OnClick({R.id.btn_left_white})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;
        }
    }

    private void initialView(){

        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        tv_title.setText("成品入库");

        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
        adapter = new FullProAdapter(list);
        adapter.setOnItemClickListener(this);
    }


    private void initialData(){

        String search = getIntent().getExtras().getString("search");

        String[] str = search.split("\\-");

        String pkid = str[0];
        String product_pid = str[1];

        //成品入库
        showing();
        RequestVo vo  = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("Product_PID", product_pid);
        map.put("Product_ID", pkid);

        vo.methodName = "GetScjhinSearch";
        vo.jsonParser = new FullProParser();
        vo.requestDataMap = map;

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    List<FullPro> list1 = (List<FullPro>) paramObject;

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

    @Override
    public void onItemClick(View view, int position) {

        FullPro fullPro = list.get(position);

        Bundle bundle = new Bundle();

        bundle.putSerializable("search", fullPro);

        redictToActivity(this, FullProDetailActivity.class, bundle);

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
