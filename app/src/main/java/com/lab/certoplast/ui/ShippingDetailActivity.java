package com.lab.certoplast.ui;

import android.app.Dialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.Fahuo1Order2;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.ResponseResult;
import com.lab.certoplast.bean.Shipping;
import com.lab.certoplast.parser.Fahuo1Order2Parser;
import com.lab.certoplast.parser.ResponseParser;
import com.lab.certoplast.parser.ShippingParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/22.
 *
 * 发货出库详情
 */

public class ShippingDetailActivity extends BaseActivity {

    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout1)
    LinearLayout layout1;
    //发货单号
    @BindView(R.id.shipping_no)
    TextView shipping_no;
    //客户名
    @BindView(R.id.tv_customer)
    TextView tv_customer;
    //客户编号
    @BindView(R.id.tv_customer_num)
    EditText tv_customer_num;
    //产品编号
    @BindView(R.id.tv_product_num)
    EditText tv_product_num;
    //产品批号
    @BindView(R.id.tv_product_serial)
    EditText tv_product_serial;
    //货位编号
    @BindView(R.id.tv_goods_num)
    EditText tv_goods_num;
    //该货位出库数量
    @BindView(R.id.tv_num)
    EditText tv_num;

    @BindView(R.id.btn_done)
    Button btn_done;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;
    @BindView(R.id.rv_remain)
    RecyclerView rv_remain;

    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    //卷 箱
    @BindView(R.id.tv_box)
    TextView tv_box;
    @BindView(R.id.sv)
    NestedScrollView sv;


    private String fahuo_id;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_shippingdetail, null, false);
    }


    @Override
    protected void initData(){

        initialView();
        initialData();

    }

    @OnClick({R.id.btn_left_white, R.id.btn_done, R.id.btn_confirm})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;

            case R.id.btn_done:
                //完成发货单
                done();
                break;
            case R.id.btn_confirm:
                //提交
                confirm();
                break;

        }
    }


    private void confirm(){
        //提交

        String customer_num = tv_customer_num.getText().toString().trim();
        String product_num = tv_product_num.getText().toString().trim();
        String product_serial = tv_product_serial.getText().toString().trim();
        String goods_num = tv_goods_num.getText().toString().trim();
        String num = tv_num.getText().toString().trim();


        if (TextUtils.isEmpty(customer_num)){
            UiCommon.INSTANCE.showTip("请输入客户编号");
            return;
        }

        if (TextUtils.isEmpty(product_num)){
            UiCommon.INSTANCE.showTip("请输入产品编号!");
            return;
        }

        if (TextUtils.isEmpty(product_serial)){
            UiCommon.INSTANCE.showTip("请输入产品批号!");
            return;
        }

        if (TextUtils.isEmpty(goods_num)){
            UiCommon.INSTANCE.showTip("请输入货位编号!");
            return;
        }

        if (TextUtils.isEmpty(num)){
            UiCommon.INSTANCE.showTip("请输入出库数量!");
            return;
        }

//        saomiao3.asp?fahuoid=*&juanck=*&pid=*&huowei=*&customerid=*&productid=*&outshuliang_box=*&username
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);
        map.put("juanck", tv_box.getText().toString().trim());
        map.put("huowei", goods_num);
        map.put("customerid", customer_num);
        map.put("productid", product_num);
        map.put("pid", product_serial);
        map.put("outshuliang_box", num);
        map.put("username", appContext.getLoginInfo().getUser_Name());

        String url = "http://" + appContext.ipPort() + "/saomiao3.asp";

        vo.requestUrl = url;
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new ResponseParser();
        vo.Message = "正在提交...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败");
                }else {
                    ResponseResult result = (ResponseResult) paramObject;

                    if (result.getResponse().contains("成功")){
                        UiCommon.INSTANCE.showTip("扫描成功!");

                        tv_customer_num.setText("");
                        tv_product_num.setText("");
                        tv_product_serial.setText("");
                        tv_goods_num.setText("");
                        tv_num.setText("");

                    }else {
                        UiCommon.INSTANCE.showTip(result.getResponse());
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                UiCommon.INSTANCE.showTip("提交失败");
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                UiCommon.INSTANCE.showTip("提交失败");
                closeProgress();
            }
        });


    }

    private  void done(){
        //完成发货单

        openDialog();

    }


    public void openDialog() {
        final Dialog myDialog = new Dialog(this, R.style.CustomDialogStyle);
        myDialog.show();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        myDialog.getWindow().setAttributes(lp);
        myDialog.getWindow().setContentView(R.layout.change_num_dialog);

        myDialog.getWindow().findViewById(R.id.tv_confirm)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

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
            //closefahou.asp?fahuo_id=*


        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);

        String url = "http://" + appContext.ipPort() + "/closefahou.asp";

        vo.requestUrl = url;
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new ResponseParser();
        vo.Message = "正在提交...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败");
                }else {
                    ResponseResult result = (ResponseResult) paramObject;

                    if (result.getResponse().contains("完成")){
                        UiCommon.INSTANCE.showTip("成功完成发货单!");
                        finish();

                    }else {
                        UiCommon.INSTANCE.showTip(result.getResponse());
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                UiCommon.INSTANCE.showTip("提交失败");
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                UiCommon.INSTANCE.showTip("提交失败");
                closeProgress();
            }
        });
    }

    private void initialView(){

        tv_title.setText("发货出库");
        btn_left_white.setVisibility(View.VISIBLE);
        sv.setFillViewport(true);

    }

    private void initialData(){

        fahuo_id = getIntent().getExtras().getString("id");

        //获取发货信息
        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("BK01", fahuo_id);
        map.put("BK02", "");
        map.put("BK03", "");

        RequestVo vo = new RequestVo();
        vo.requestDataMap = map;

       //判断,发货单是否关闭
        /**
         * sqltext = "Select * From [fahuo_out]"
         sqltext = sqltext & " where fahuo_id='"&fahuo_id&"'"
         */
        vo.methodName = "BK1";
        vo.jsonParser = new ShippingParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {
                    Shipping shipping = (Shipping) paramObject;

                    if ("True".equals(shipping.getSaomiao_ok())){
                        emptyShowing();
                    }else {
                        showNext(shipping);
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


    private void showNext(Shipping shipping){
        String id = shipping.getFahuo_id();

        //获取发货信息
        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("Fahuo_ID", id);

        RequestVo vo = new RequestVo();
        vo.requestDataMap = map;

        /**
         * sqltext2="Select * From [Fahuo1Order2] where fahuo_id='"&fahuo_id&"'"
         */
        vo.methodName = "GetFahuo1_Order2";
        vo.requestDataMap = map;
        vo.jsonParser = new Fahuo1Order2Parser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    hide();
                    Fahuo1Order2 fahuo1Order2 = (Fahuo1Order2) paramObject;

                    showSecond(fahuo1Order2);

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


    private void showSecond(Fahuo1Order2 fahuo1Order2){
        shipping_no.setText(fahuo1Order2.getFahuo_id());
        String unit = fahuo1Order2.getFahuo_id().substring(0,1);
        if ("C".equals(unit)){
            tv_box.setText("卷");
        }else if ("D".equals(unit)){
            tv_box.setText("箱");
        }

        //还剩多少箱待扫描
        remainScan(fahuo1Order2);
    }



    private void remainScan(Fahuo1Order2 fahuo1Order2){

//        HashMap<String, String> map = new HashMap<>();
//        map.put("Product_ID", fahuo1Order2.getProduct_ID());
//        map.put("fahuo_id", fahuo1Order2.getFahuo_id());
//
//        RequestVo vo = new RequestVo();
//        vo.requestDataMap = map;
//
//        vo.methodName = "GetSaomiaoList";
//        vo.requestDataMap = map;
//        vo.jsonParser = new SaomiaoListParser();
//
//        doPost(vo, new DataCallback() {
//            @Override
//            public void processData(Object paramObject, boolean paramBoolean) {
//                if (paramObject == null){
//                    rv_remain.setVisibility(View.GONE);
//                }else {
//                    List<SaoMiao> saoMiaos = (List<SaoMiao>) paramObject;
//                }
//            }
//
//            @Override
//            public void onFailure(AppException e) {
//                emptyShowing();
//            }
//
//            @Override
//            public void onError(ErrorMessage error) {
//                emptyShowing();
//            }
//        });
    }


    private void showing(){
        searching.setVisibility(View.VISIBLE);
        show_empty.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
    }

    private void hide(){
        searching.setVisibility(View.GONE);
        show_empty.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
    }

    private void emptyShowing(){
        searching.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
    }
}
