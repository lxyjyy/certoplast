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
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.FullPro;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.parser.FullProParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;
import java.util.List;

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

    private String id;
    private String p_id;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_halfprodetail, null, false);
    }

    @Override
    protected void initData() {
        initialView();
        initialData();
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

        tv_title.setText("半成品入库");
    }


    @OnClick({R.id.btn_search})
    public void onClick(View v){
         switch (v.getId()){
             case R.id.btn_search:

                 search();
                 break;
         }
    }

    private void search(){
        String warehost = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(warehost)){
            UiCommon.INSTANCE.showTip("请输入入库货位!");
            return;
        }

        //货位ID
        //id
        //p_id

        // "Select * From [warehoustsearch]"
        //sql = sql & " where warehouse_set like '%"&huoweiid&"%'
        // and product_pid='0' and product_id='0' and
        // class='"&warehouse&"' order by warehouse_set"
        //sql = sql & " where warehouse_set='"&huoweiid&"'
        // and product_pid='0' and product_id='0' and
        // class='"&warehouse&"' order by warehouse_set"

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("p_id", p_id);
        bundle.putString("warehost", warehost);



    }


    /**
     * 初始化数据
     */
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
                        FullPro halfPro = list1.get(0);
                        showInterface(halfPro);

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


        id = halfPro.getID();
        p_id = halfPro.getProduct_ID();
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
        layout1.setVisibility(View.GONE);
    }
}
