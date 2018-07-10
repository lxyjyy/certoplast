package com.lab.certoplast.ui;

import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.FullPro;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.parser.FullProParser;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/30.
 *
 * 半成品入库详情
 */

public class HalfProDetailActivity extends BaseActivity {


    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    @BindView(R.id.layout1)
    LinearLayout layout1;
    //生产计划号
    @BindView(R.id.tv_product_plan)
    TextView tv_product_plan;

    //产品编号
    @BindView(R.id.tv_product_no)
    TextView tv_product_no;
    //产品批次号
    @BindView(R.id.tv_serial_no)
    TextView tv_serial_no;
    //入库数量
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    @BindView(R.id.tv_unit)
    TextView tv_unit;

    @BindView(R.id.tv_location)
    TextView tv_location;
    //入库货位
    @BindView(R.id.et_search)
    EditText et_search;
    //查询
    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.sv)
    NestedScrollView sv;

    private String id;
    private String p_id;
    private String warehouse;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_halfprodetail, null, false);
    }

    @Override
    protected void initData() {
        initialView();
        initialData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
        et_search.requestFocus();
    }

    /**
     * 初始化控件
     */
    private void initialView(){

        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sv.setFillViewport(true);

        tv_title.setText("半成品入库");

        postDelayed(et_search);


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
    }


    @OnClick({R.id.btn_search,R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
         switch (v.getId()){
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
        final String warehost = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(warehost)){
            UiCommon.INSTANCE.showTip("请输入入库货位代码!");
            return;
        }

        RequestVo vo  = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("id", id);
        map.put("huoweiid", warehost);

        vo.methodName = "HalfProDetail1.asp";
        vo.jsonParser = new LoginParser();
        vo.requestDataMap = map;
        vo.isShowDialog = true;

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("无对应的货位!");
                    closeProgress();
                    et_search.setText("");
                }else {

                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){
                        closeProgress();
                        UiCommon.INSTANCE.showTip(response.getInfo());
                        et_search.setText("");
                    }else{
                        commit(warehost);
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                closeProgress();
                UiCommon.INSTANCE.showTip(e.getErrorMsg());
            }

            @Override
            public void onError(ErrorMessage error) {
                closeProgress();
                UiCommon.INSTANCE.showTip(error.getText());
            }
        });

    }



    private void commit(String huoweiid){
        RequestVo vo  = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("p_id", p_id);
        map.put("huoweiid", huoweiid);
        map.put("sid", id);

        vo.methodName = "HalfProDetail2.asp";
        vo.jsonParser = new LoginParser();
        vo.requestDataMap = map;

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败");
                    et_search.setText("");
                }else {
                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){
                        UiCommon.INSTANCE.showTip(response.getInfo());
                        et_search.setText("");
                    }else{
                        finish();
                        UiCommon.INSTANCE.showTip("提交成功!");
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                UiCommon.INSTANCE.showTip(e.getErrorMsg());
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                UiCommon.INSTANCE.showTip(error.getText());
                closeProgress();
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initialData(){
        String search = getIntent().getExtras().getString("search");


        //成品入库
        showing();
        RequestVo vo  = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("product_pid", search);

        vo.methodName = "HalfProDetail.asp";
        vo.jsonParser = new FullProParser();
        vo.requestDataMap = map;

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                    sendErrorClose();
                }else {

                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                        sendErrorClose();
                    }else{
                        FullPro halfpro = (FullPro) response.getMsg();

                            hide();
                            showInterface(halfpro);
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


    private void showInterface(FullPro halfPro){

        tv_product_plan.setText("生产计划号: " + halfPro.getProduct_plan_id());
        tv_product_no.setText("产品编号: " + halfPro.getProduct_ID());
        tv_serial_no.setText("产品批次号: " + halfPro.getProduct_PID());
        tv_amount.setText("入库数量: " + halfPro.getAmount() );
        tv_unit.setText("单位: " + halfPro.getFroms());
        tv_location.setText("工位: " + halfPro.getGongwei());


        id = halfPro.getId();
        p_id = halfPro.getProduct_ID();
        warehouse = halfPro.getWarehouse();
    }


    private void showing(){
        searching.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
    }

    private void hide(){
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        show_empty.setVisibility(View.GONE);
    }

    private void emptyShowing(){
        show_empty.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
        searching.setVisibility(View.GONE);
    }

    private void emptyShowing(String text){
        show_empty.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
        searching.setVisibility(View.GONE);
        tv_content.setText(text);
    }
}
