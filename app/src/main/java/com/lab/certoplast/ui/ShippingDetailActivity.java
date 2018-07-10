package com.lab.certoplast.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
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
import com.lab.certoplast.adapter.SaoMiaoListAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.ResponseResult;
import com.lab.certoplast.bean.SaoMiao;
import com.lab.certoplast.bean.Shipping;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.parser.ResponseParser;
import com.lab.certoplast.parser.SaomiaoListParser;
import com.lab.certoplast.parser.ShippingParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lab.certoplast.R.id.rv_remain;

/**
 * Created by lxyjyy on 17/11/22.
 *
 * 发货出库详情
 */

public class ShippingDetailActivity extends BaseActivity implements OnItemClickListener {

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
    @BindView(rv_remain)
    RecyclerView recyclerView;

    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    //卷 箱
    @BindView(R.id.tv_box)
    TextView tv_box;
    @BindView(R.id.sv)
    NestedScrollView sv;


    @BindView(R.id.tv_content)
    TextView tv_content;

    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    //提货，已通知，已提货
    @BindView(R.id.tv_pick_up_goods)
    TextView tv_pick_up_goods;


    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    private String fahuo_id;

    private List<SaoMiao> list;

    private SaoMiaoListAdapter adapter;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_shippingdetail, null, false);
    }


    @Override
    protected void initData(){

        initialView();
//        initialData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initialData();
        initPickUpStatus();
    }

    @OnClick({R.id.btn_left_white, R.id.btn_done, R.id.btn_confirm,R.id.tv_logout, R.id.tv_main, R.id.tv_confirm, R.id.tv_pick_up_goods})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;

            case R.id.btn_done:
                //完成发货单
//                done();
                break;
            case R.id.btn_confirm:
                //提交
                confirm();
                break;

            case R.id.tv_confirm:
                done();
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
            case R.id.tv_pick_up_goods:
                String content = tv_pick_up_goods.getText().toString();
                if (!("已通知".equals(content) || "已提货".equals(content))){
                    openDialogForChangeStatus();
                }

                break;

        }
    }



    private void loading(){
        tv_pick_up_goods.setVisibility(View.GONE);
    }
    /**
     * 获取提货状态
     */
    private void initPickUpStatus(){
        loading();

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);

        RequestVo vo = new RequestVo();
        vo.requestDataMap = map;

        vo.methodName = "saomiao1.asp";
        vo.jsonParser = new LoginParser();
        vo.isShowDialog = true;
        vo.Message = "正在获取提货状态....";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject == null){
                }else {

                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){

                    }else {
                        String status = response.getInfo();
                        tv_pick_up_goods.setVisibility(View.VISIBLE);
                        tv_pick_up_goods.setText(status);

                    }

                }
            }

            @Override
            public void onFailure(AppException e) {
                UiCommon.INSTANCE.showTip("获取失败");
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                UiCommon.INSTANCE.showTip("获取失败");
                closeProgress();
            }
        });

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


        vo.methodName = "saomiao3.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new ResponseParser();
        vo.Message = "正在提交...";

        doPost(vo, new DataCallback() {
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

                        tv_customer_num.requestFocus();

                        remainScan();

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




    private void status(){

        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);

        vo.methodName = "tihuo.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new LoginParser();
        vo.Message = "正在提交...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败");
                }else {
                    Response result = (Response) paramObject;
                    if ("error".equals(result.getResponse())){
                        UiCommon.INSTANCE.showTip(result.getInfo());
                    }else {
                        UiCommon.INSTANCE.showTip("通知发货完成!");
                        tv_pick_up_goods.setVisibility(View.VISIBLE);
                        tv_pick_up_goods.setText("已通知");
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

    public void openDialogForChangeStatus() {
        final Dialog myDialog = new Dialog(this, R.style.CustomDialogStyle);
        myDialog.show();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        lp.width = (int) (2*(display.getWidth())/3); // 设置宽度
        myDialog.getWindow().setAttributes(lp);
        myDialog.getWindow().setContentView(R.layout.change_status_dialog);

        myDialog.getWindow().findViewById(R.id.tv_confirm1)
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        status();

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

    public void openDialog() {
        final Dialog myDialog = new Dialog(this, R.style.CustomDialogStyle);
        myDialog.show();
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        lp.width = (int)(2*(display.getWidth())/3); // 设置宽度
        myDialog.getWindow().setAttributes(lp);
        myDialog.getWindow().setContentView(R.layout.change_num_dialog);

        myDialog.getWindow().findViewById(R.id.tv_confirm1)
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

        vo.methodName = "closefahuo.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new LoginParser();
        vo.Message = "正在提交...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("提交失败");
                }else {
                    Response result = (Response) paramObject;
                    if ("error".equals(result.getResponse())){
                        UiCommon.INSTANCE.showTip(result.getInfo());
                    }else {
                        UiCommon.INSTANCE.showTip("发货单扫描完成!");
                        AppManager.getAppManager().finishAllActivity();
                        redictToActivity(ShippingDetailActivity.this, MainActivity.class, null);
                        redictToActivity(ShippingDetailActivity.this, ShippingActivity.class, null);
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
        tv_title.setVisibility(View.GONE);
        btn_left_white.setVisibility(View.VISIBLE);
        sv.setFillViewport(true);


        tv_confirm.setText("完成发货单");

        tv_confirm.setVisibility(View.VISIBLE);

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview


        list = new ArrayList<>();
        adapter = new SaoMiaoListAdapter(list);
        adapter.setOnItemClickListener(this);


        postDelayed(tv_customer_num);

        initialEditText();

        fahuo_id = getIntent().getExtras().getString("id");
    }

    /**
     * 初始化所有的编辑框
     */
    private void initialEditText(){


        //tv_customer_num 客户编号
        //tv_product_num 产品编号
        //tv_product_serial 产品批号
        // tv_goods_num 货位编号
        // tv_num  出库数量
        tv_customer_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                 //客户编号
                if (!hasFocus){
                    if (TextUtils.isEmpty(tv_product_num.getText().toString().trim())){
                        tv_product_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_product_serial.getText().toString().trim())){
                        tv_product_serial.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_goods_num.getText().toString().trim())){
                        tv_goods_num.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tv_num.getText().toString().trim())){
                        tv_num.requestFocus();
                        return;
                    }
                }
            }
        });


        tv_product_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    // 产品编号
                    if (TextUtils.isEmpty(tv_customer_num.getText().toString().trim())){
                        tv_customer_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_product_serial.getText().toString().trim())){
                        tv_product_serial.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_goods_num.getText().toString().trim())){
                        tv_goods_num.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tv_num.getText().toString().trim())){
                        tv_num.requestFocus();
                        return;
                    }
                }
            }
        });


        tv_product_serial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){
                    // 产品批号

                    if (TextUtils.isEmpty(tv_customer_num.getText().toString().trim())){
                        tv_customer_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_product_num.getText().toString().trim())){
                        tv_product_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_goods_num.getText().toString().trim())){
                        tv_goods_num.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tv_num.getText().toString().trim())){
                        tv_num.requestFocus();
                        return;
                    }
                }
            }
        });


        tv_goods_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    // 产品批号

                    if (TextUtils.isEmpty(tv_customer_num.getText().toString().trim())){
                        tv_customer_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_product_num.getText().toString().trim())){
                        tv_product_num.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(tv_product_serial.getText().toString().trim())){
                        tv_product_serial.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tv_num.getText().toString().trim())){
                        tv_num.requestFocus();
                        return;
                    }
                }
            }
        });

    }




    private void initialData(){

        //获取发货信息
        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);

        RequestVo vo = new RequestVo();
        vo.requestDataMap = map;

        vo.methodName = "Scan.asp";
        vo.jsonParser = new ShippingParser();

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
                        Shipping shipping = (Shipping) response.getMsg();
                        showSecond();
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

    private void showSecond(){
        shipping_no.setText(fahuo_id);
        String unit = fahuo_id.substring(0,1);
        if ("C".equals(unit)){
            tv_box.setText("卷");
        }else if ("D".equals(unit)){
            tv_box.setText("箱");
        }

        //还剩多少箱待扫描
        remainScan();
    }



    private void remainScan(){

        HashMap<String, String> map = new HashMap<>();
        map.put("fahuoid", fahuo_id);

        RequestVo vo = new RequestVo();
        vo.requestDataMap = map;

        vo.methodName = "ScanList.asp";
        vo.requestDataMap = map;
        vo.jsonParser = new SaomiaoListParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    recyclerView.setVisibility(View.GONE);
                }else {

                    Response response = (Response) paramObject;
                    if ("error".equals(response.getResponse())){
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        hide();
                        List<SaoMiao> saoMiaos = (List<SaoMiao>) response.getMsg();

                        list.clear();
                        list.addAll(saoMiaos);
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

    private void emptyShowing(String text){
        searching.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        layout1.setVisibility(View.GONE);
        tv_content.setText(text);
    }


    @Override
    public void onItemClick(View view, int position) {

        SaoMiao saoMiao = list.get(position);

        //跳转到ScanListDetailActivity
        Bundle bundle = new Bundle();
        bundle.putString("fahuoid", fahuo_id);
        bundle.putString("product_id", saoMiao.getProduct_id());

        redictToActivity(this, ScanListDetailActivity.class, bundle);

    }

}
