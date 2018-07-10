package com.lab.certoplast.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.PRDetailAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.PRDetail;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.parser.PRDetailParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/25.
 * 生产领用详情
 */

public class PRDetailActivity extends BaseActivity implements OnItemClickListener {


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

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;


    private List<PRDetail> list;

    private PRDetailAdapter adapter;


    private String scyl_id;
    private String product_id;


    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_productionofrecipientsdetail, null, false);
    }

    @Override
    protected void initData() {

        initialView();
        initialData();

    }

    public void openDialog() {
        final Dialog myDialog = new Dialog(this, R.style.CustomDialogStyle);
        myDialog.show();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        lp.width = (int) (11*display.getWidth()/12); // 设置宽度
        myDialog.getWindow().setAttributes(lp);
        myDialog.getWindow().setContentView(R.layout.change_num_dialog);

        myDialog.getWindow().findViewById(R.id.tv_confirm1)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    //sclysm3-1.asp?scyl_id=*&product_id=*

                        commit();

                        myDialog.dismiss();

                    }
                });

        myDialog.getWindow().findViewById(R.id.tv_cancel)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
    }


    private void commit(){
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", scyl_id);

        vo.methodName = "DonePR.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new LoginParser();
        vo.Message = "正在提交...";

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败!");
                }else {
                    Response response = (Response) paramObject;
                    if ("success".equals(response.getResponse())){
                        finish();
                        UiCommon.INSTANCE.showTip("生产领料成功!");
                    }else {
                        UiCommon.INSTANCE.showTip("提交失败!");
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                closeProgress();
                UiCommon.INSTANCE.showTip("提交失败!");
            }

            @Override
            public void onError(ErrorMessage error) {
                closeProgress();
                UiCommon.INSTANCE.showTip("提交失败!");
            }
        });
    }

    private void initialView(){
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

        tv_title.setText("生产领用");
        tv_confirm.setText("完成此领用单");
        tv_confirm.setVisibility(View.GONE);

        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        list = new ArrayList<>();
        adapter = new PRDetailAdapter(list);
        adapter.setOnItemClickListener(this);

    }

    private void initialData(){

        scyl_id = getIntent().getExtras().getString("sy_id");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", scyl_id);

        RequestVo vo = new RequestVo();

        vo.methodName = "ProductionOfRecipients1.asp";
        vo.requestDataMap = map;
        vo.jsonParser = new PRDetailParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                    }else {
                        List<PRDetail> list1 = (List<PRDetail>) response.getMsg();

                        for (PRDetail detail : list1) {
                            if ("1".equals(detail.getStatus())){
                                tv_confirm.setVisibility(View.VISIBLE);
                                break;
                            }
                        }

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


    @Override
    public void onItemClick(View view, int position) {

        PRDetail detail = list.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("scyl_id", detail.getScyl_ID());
        bundle.putString("poid", detail.getProduct_ID());

        redictToActivity(this, PRDetail1Activity.class, bundle);

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

    private void emptyShowing(String text){
        recyclerView.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
        tv_content.setText(text);
    }

    @OnClick({ R.id.tv_logout, R.id.tv_main})
    public void onClick(View view) {

        switch (view.getId()) {
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
}
