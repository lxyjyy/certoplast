package com.lab.certoplast.ui;

import android.os.Bundle;
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
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.FullProParser;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/30.
 *
 * 成品入库详情
 */

public class FullProDetailActivity extends BaseActivity {


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
    //入库单号
    @BindView(R.id.tv_purchase_no)
    TextView tv_purchase_no;
    //产品编号
    @BindView(R.id.tv_product_no)
    TextView tv_product_no;
    //产品批次号
    @BindView(R.id.tv_serial_no)
    TextView tv_serial_no;
    //入库数量
    @BindView(R.id.tv_amount)
    TextView tv_amount;

    @BindView(R.id.leave)
    TextView leave;
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

    @BindView(R.id.layout3)
    LinearLayout layout3;

    @BindView(R.id.btn_done)
    Button btn_done;

    private FullPro fp;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_fullprodetail, null, false);
    }

    @Override
    protected void initData() {
        initialView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initialData();
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


        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

        sv.setFillViewport(true);
        tv_title.setText("成品入库");
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


    @OnClick({R.id.btn_search, R.id.tv_logout, R.id.tv_main, R.id.btn_done})
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

             case R.id.btn_done:
                 //完成此成品入库单作业
                 doneFullPro();
                 break;
         }
    }


    private void doneFullPro(){

        HashMap<String, String> map = new HashMap<>();
        map.put("yrkid", fp.getId());

        RequestVo vo = new RequestVo();
        vo.methodName = "DoneFullPro.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;

        vo.jsonParser = new LoginParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject != null){
                    Response response = (Response) paramObject;
                    if ("error".equals(response.getResponse())){
                        UiCommon.INSTANCE.showTip(response.getInfo());
                    }else {
                        UiCommon.INSTANCE.showTip("该单已经收货完成!");
                        finish();
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

    private void search(){
        String warehost = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(warehost)){
            UiCommon.INSTANCE.showTip("请输入入库货位!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("warehost", warehost);
        bundle.putString("sl_real_box", fp.getSl_real_box() );
        bundle.putString("real_box", fp.getRealbox());
        bundle.putString("p_id", fp.getProduct_id());
        bundle.putString("ppid", fp.getProduct_pid());
        bundle.putString("cprk_scjh", fp.getProduct_plan_id());
        bundle.putString("rukuid", fp.getRuku_id());
        bundle.putString("juanrk", fp.getUnit());

        redictToActivity(this, FullProDetail2Activity.class, bundle);

    }


    /**
     * 初始化数据
     */
    private void initialData(){

        String search =  getIntent().getExtras().getString("search");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("product_pid", search);

        RequestVo vo = new RequestVo();
        vo.methodName = "FullProDetail1.asp";
        vo.requestDataMap = map;

        vo.jsonParser = new FullProParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {
                    Response response = (Response) paramObject;
                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                        sendErrorClose();
                    }else {
                        hide();
                        FullPro fullPro = (FullPro) response.getMsg();
                        fp = fullPro;
                        showInterface(fullPro);
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




    private void showInterface(FullPro fullPro){
            tv_product_plan.setText("生产计划号: "  + fullPro.getProduct_plan_id());
            tv_purchase_no.setText("入库单号: " + fullPro.getRuku_id());
            tv_product_no.setText("产品编号: " + fullPro.getProduct_id());
            tv_serial_no.setText("产品批次号: " + fullPro.getProduct_pid());
            tv_amount.setText("入库数量: " + fullPro.getRealbox() + fullPro.getUnit());

            leave.setText("还有" + fullPro.getRemain()+fullPro.getUnit()+ "产品需要入库!");
            //判断剩余箱数
            int sybox = StringUtils.toInt(fullPro.getRemain());

            if (sybox > 0){
                btn_done.setVisibility(View.GONE);
            }else {
                btn_done.setVisibility(View.VISIBLE);
            }

    }


    private void showing(){
        searching.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
        show_empty.setVisibility(View.GONE);
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
