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
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.ProductionOfRecipients;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.ProductionOfRecipientsParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  生产领用列表
 */

public class PRActivity extends BaseActivity implements OnItemClickListener{


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

    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


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


    @OnClick({ R.id.tv_logout, R.id.tv_main})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_logout:
                appContext.cleanLoginInfo();
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(PRActivity.this, LoginActivity.class, null);
                break;
            case R.id.tv_main:
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(PRActivity.this, MainActivity.class, null);
                break;
        }
    }

    private void initShowing(){
        //获取生产领用数据
        showing();
        RequestVo vo  = new RequestVo();

        vo.methodName = "ProductionOfRecipients.asp";
        vo.jsonParser = new ProductionOfRecipientsParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    Response response = (Response)paramObject;

                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                    }else {
                        List<ProductionOfRecipients> list1 = (List<ProductionOfRecipients>) response.getMsg();

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
                emptyShowing(e.getErrorMsg());
            }

            @Override
            public void onError(ErrorMessage error) {
                emptyShowing(error.getText());
            }
        });
    }


    private void initConfig(){

        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        tv_title.setText("生产领用");

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

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
        bundle.putString("sy_id", pr.getSingleno());


        redictToActivity(this, PRDetailActivity.class, bundle);

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

    private void emptyShowing(String text){
        recyclerView.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        tv_content.setText(text);
    }


}
