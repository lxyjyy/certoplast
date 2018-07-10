package com.lab.certoplast.ui;

import android.os.Bundle;
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
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.MigoSearch1Parser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/9.
 *
 * 采购收货列表
 */

public class MaterialSearch1Activity extends BaseActivity {


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

    //采购单号
    @BindView(R.id.tv_warehousing_no)
    TextView tv_warehousing_no;

    @BindView(R.id.tv_top_title)
    TextView tv_top_title;

    //入库单号
    @BindView(R.id.tv_purchase_no)
    TextView tv_purchase_no;

    //剩余数量
    @BindView(R.id.leave)
    TextView tv_leave;

    @BindView(R.id.et_search)
    EditText et_search;
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

    private String ruku_id;


    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
        et_search.requestFocus();
        initialData();
    }



    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_migosearch1, null, false);
    }

    @Override
    protected void initData() {

        initialView();
//        initialData();

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

        tv_top_title.setText("原料电子标签绑定");

        tv_title.setText("原料TAG");
        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

        postDelayed(et_search);

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = et_search.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    jumpToNext();
                }
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initialData(){

        ruku_id = getIntent().getExtras().getString("ruku_id");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("rkid", ruku_id);

        RequestVo vo = new RequestVo();
        vo.methodName = "MigoSearch.asp";
        vo.requestDataMap = map;

        vo.jsonParser = new MigoSearch1Parser();


        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject != null){

                    Response response = (Response) paramObject;
                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                        sendErrorClose();
                    }else {
                        hide();
                        setShow((Migo) response.getMsg());
                    }
                }else {
                    emptyShowing();
                    sendErrorClose();
                }
            }

            @Override
            public void onFailure(AppException e) {
                emptyShowing(e.getErrorMsg());
                sendErrorClose();
            }

            @Override
            public void onError(ErrorMessage error) {
                emptyShowing(error.getText());
                sendErrorClose();

            }
        });

    }


    private void setShow(Migo migo){


        et_search.setText("");
        et_search.requestFocus();


        //采购单号
        tv_warehousing_no.setText("采购单号: " + migo.getCaigou_id());
        //入库单号
        tv_purchase_no.setText("入库单号: " + migo.getRuku_id());
        //剩余需要扫描的批次
        tv_leave.setText("还有" + migo.getRemain() + "个批次产品需要入库!");

        int remain = StringUtils.toInt(migo.getRemain(), 0);
        if (remain <= 0){
            //没有剩余批次,则跳转到查询界面
            finish();
        }

    }

    @OnClick({R.id.btn_search, R.id.tv_logout, R.id.tv_main})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn_search:
                jumpToNext();
                break;
            case R.id.tv_logout:
                appContext.cleanLoginInfo();
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(MaterialSearch1Activity.this, LoginActivity.class, null);
                break;
            case R.id.tv_main:
                AppManager.getAppManager().finishAllActivity();
                redictToActivity(MaterialSearch1Activity.this, MainActivity.class, null);
                break;
        }

    }

    private void jumpToNext(){
        String search = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(search)){
            UiCommon.INSTANCE.showTip("请填写货品批次号!");
            return;
        }

        if (TextUtils.isEmpty(ruku_id)){
            UiCommon.INSTANCE.showTip("入库编号不能为空!");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("Product_PID", search);
        bundle.putString("Ruku_ID", ruku_id);

        redictToActivity(MaterialSearch1Activity.this, MaterialSearch2Activity.class, bundle);
    }


    private void showing(){
        searching.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
    }

    private void hide(){
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
    }

    private void emptyShowing(){
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
    }

    private void emptyShowing(String text){
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
        tv_content.setText(text);
    }

}
