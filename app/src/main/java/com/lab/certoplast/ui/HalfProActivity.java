package com.lab.certoplast.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.HalfProAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.HalfPro;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.HalfProParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/19.
 * 半成品入库
 */

public class HalfProActivity extends BaseActivity implements OnItemClickListener {



    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


    private List<HalfPro> list;

    private HalfProAdapter adapter;

    @Override
    protected void initData() {

        tv_title.setText("半成品入库");

        btn_left_white.setVisibility(View.VISIBLE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        postDelayed(et_search);

        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());


        list = new ArrayList<>();
        adapter = new HalfProAdapter(list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = et_search.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    search();
                }
            }
        });


        initialData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
        et_search.requestFocus();
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_halfpro, null, false);
    }

    @OnClick({R.id.btn_left_white, R.id.btn_search, R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;

            case R.id.btn_search:

                search();
                break;
            case R.id.tv_logout:
                appContext.cleanLoginInfo();
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(this, LoginActivity.class, null);
                break;
            case R.id.tv_main:
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(this, MainActivity.class, null);
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


        RequestVo vo = new RequestVo();
        vo.methodName = "halfpro.asp";
        vo.requestDataMap = null;

        vo.jsonParser = new HalfProParser();
        vo.isShowDialog = true;

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject != null){
                    Response response = (Response) paramObject;

                    if ("success".equals(response.getResponse())){
                        List<HalfPro> halfPros = (List<HalfPro>) response.getMsg();

                        list.clear();
                        list.addAll(halfPros);
//                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                closeProgress();
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}
