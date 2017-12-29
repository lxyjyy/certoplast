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
import com.lab.certoplast.bean.Product;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.WareHouse;
import com.lab.certoplast.parser.MigoListParser;
import com.lab.certoplast.parser.WareHouseListParser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;
import java.util.List;

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

    private int real_box = 0;
    private int sl_real_box = 0;

    private String  p_id;
    private String ppid;
    private String cprk_scjh;
    private String rukuid;
    private String juanrk;

    private String xx;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_fullprodetail, null, false);
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

        tv_title.setText("成品入库");
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

        Bundle bundle = new Bundle();
        bundle.putString("warehost", warehost);
        bundle.putString("sl_real_box", sl_real_box + "");
        bundle.putString("real_box", real_box + "");
        bundle.putString("p_id", p_id);
        bundle.putString("ppid", ppid);
        bundle.putString("cprk_scjh", cprk_scjh);
        bundle.putString("rukuid", rukuid);
        bundle.putString("juanrk", xx);


    }


    /**
     * 初始化数据
     */
    private void initialData(){

        final FullPro fullPro = (FullPro) getIntent().getExtras().get("search");


        p_id = fullPro.getProduct_ID();
        ppid = fullPro.getProduct_PID();
        cprk_scjh = fullPro.getProduct_plan_id();
        rukuid = "RK-CP" + fullPro.getID();
        juanrk = xx;

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("Product_ID", fullPro.getProduct_ID());

        RequestVo vo = new RequestVo();
        vo.methodName = "GetProduct";
        vo.requestDataMap = map;

        vo.jsonParser = new MigoListParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    Product product = (Product) paramObject;
                    showInterface(fullPro, product);
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




    private void showInterface(final FullPro fullPro, final Product product){
        if ("3".equals(product.getType()) || "10".equals(product.getType())){
            final int baozhuang_bili = StringUtils.toInt(product.getBaozhuang_bili(), 0);

            int num_box = StringUtils.toInt(fullPro.getAmount(), 0)/baozhuang_bili;
            final int mod_box = StringUtils.toInt(fullPro.getAmount(), 0) % baozhuang_bili;

            if (mod_box != 0){
                real_box = StringUtils.toInt(fullPro.getAmount(), 0);
            }else {
                real_box = num_box;
            }


            tv_product_plan.setText("生产计划号: "  + fullPro.getProduct_plan_id());
            tv_purchase_no.setText("入库单号: " + "RK-CP" + fullPro.getID());
            tv_product_no.setText("产品编号: " + fullPro.getProduct_ID());
            tv_serial_no.setText("产品批次号: " + fullPro.getProduct_PID());

            if (mod_box != 0){
                tv_amount.setText("入库数量: " + real_box + "卷");
            }else {
                tv_amount.setText("入库数量: " + real_box + "箱");
            }


            HashMap<String, String> map = new HashMap<>();
            map.put("BK01", fullPro.getProduct_PID());
            map.put("BK02", "RK-CP" + fullPro.getID());


            RequestVo vo = new RequestVo();

            vo.methodName = "BK3";
            vo.requestDataMap = map;
            vo.jsonParser = new WareHouseListParser();

            doPost(vo, new DataCallback() {
                @Override
                public void processData(Object paramObject, boolean paramBoolean) {
                    if (paramObject == null){
                        emptyShowing();
                    }else {
                        List<WareHouse> list = (List<WareHouse>) paramObject;

                        if (list.size() <= 0){
                            emptyShowing();
                        }else {

                            hide();
                            int sl = 0;
                            for (WareHouse w : list) {
                                sl = sl + StringUtils.toInt(w.getShuliang(), 0);
                            }

                            int sybox = 0;

                            int sl_box = 0;
                            int sl_mode_box = 0;


                            if (mod_box != 0){
                                sybox = real_box - sl;
                                sl_real_box = sl;
                                xx = "卷";
                            }else {
                                xx = "箱";
                                sl_box = sl/baozhuang_bili;
                                sl_mode_box = sl % baozhuang_bili;

                                if (sl_mode_box != 0){
                                    sl_real_box = sl_box + 1;
                                }else {
                                    sl_real_box = sl_box;
                                }

                                sybox = real_box - sl_real_box;
                            }

                            if ("大卷".equals(product.getBaozhuang_froms())){
                                xx = "米";
                                real_box = StringUtils.toInt(fullPro.getAmount());
                                sybox = real_box - sl;
                                sl_real_box = sl;
                            }


                            leave.setText("还有" + sybox + xx + "产品需要入库!");
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




        }else {
            emptyShowing();
        }
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
