package com.lab.certoplast.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.PRDetail;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.ResponseResult;
import com.lab.certoplast.bean.Scan;
import com.lab.certoplast.parser.PRDetail1Parser;
import com.lab.certoplast.parser.ResponseParser;
import com.lab.certoplast.parser.ScanParser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/29.
 *
 * 生产领用详情
 */

public class PRDetail1Activity extends BaseActivity {

    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.layout1)
    LinearLayout layout1;
    //领用单号
    @BindView(R.id.scyl_no)
    TextView scyl_no;
    //产品
    @BindView(R.id.tv_product_no)
    TextView tv_product_no;
    //申请数
    @BindView(R.id.tv_apply_no)
    TextView tv_apply_no;
    //产品批号
    @BindView(R.id.tv_product_id)
    TextView tv_product_id;
    //待出库原料批号
    @BindView(R.id.tv_wait_source)
    TextView tv_wait_source;



    @BindView(R.id.layout2)
    LinearLayout layout2;

    @BindView(R.id.tv_has_scan)
    TextView tv_has_scan;
    //批号
    @BindView(R.id.tv_productid_num)
    TextView tv_productid_num;
    //数量
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    //原货位
    @BindView(R.id.tv_origin_set)
    TextView tv_origin_set;
    //删除
    @BindView(R.id.btn_delete)
    Button btn_delete;


    //产品批号
    @BindView(R.id.ll_product_id)
    LinearLayout ll_product_id;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;

    @BindView(R.id.rl1)
    RelativeLayout rl1;

    @BindView(R.id.btn_done)
    Button btn_done;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    private String id;

    private String scylid;
    private String product_id;
    private String gongwei;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_prdetail1, null, false);
    }


    @Override
    protected void initData() {

        initialView();
        initialData();

    }

    private void initialData(){

        scylid = getIntent().getExtras().getString("scyl_id");
        product_id = getIntent().getExtras().getString("poid");

        show1(scylid, product_id);

    }


    private void show2(final PRDetail prDetail, String scyl_id, String poid){
        showing();
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("Scyl_id", scyl_id);
        map.put("Product_ID", poid);


        vo.methodName = "GetScjh_Ylsm";
        vo.requestDataMap = map;
        vo.jsonParser = new ScanParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                    if (paramObject == null){
                        layout2.setVisibility(View.GONE);
                    }else {
                        List<Scan> list = (List<Scan>) paramObject;

                        layout2.setVisibility(View.VISIBLE);
                        int amount = 0;

                        for (Scan scan: list) {
                            amount = amount + StringUtils.toInt(scan.getOutshuliang(), 0);
                        }

                        tv_has_scan.setText("已经扫描总数为: " + amount);
                        Scan scan = list.get(0);

                        tv_productid_num.setText("批号: " + scan.getOutshuliang());
                        tv_amount.setText("数量: " + scan.getOutshuliang());
                        tv_origin_set.setText("原货位: " + scan.getWarehouse_set());

                        if ("0".equals(prDetail.getStatus())){
                            btn_delete.setVisibility(View.GONE);
                        }
                    }
            }

            @Override
            public void onFailure(AppException e) {
                layout2.setVisibility(View.GONE);
            }

            @Override
            public void onError(ErrorMessage error) {
                layout2.setVisibility(View.GONE);
            }
        });
    }


    private void show1(final String scyl_id, final String poid){
        showing();
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("Scyl_id", scyl_id);
        map.put("Product_ID", poid);


        vo.methodName = "GetScjhylSearch";
        vo.requestDataMap = map;
        vo.jsonParser = new PRDetail1Parser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {

                if (paramObject == null){
                    emptyShowing();
                }else {

                    hide();
                    PRDetail prDetail = (PRDetail) paramObject;
                    id = prDetail.getID();
                    gongwei = prDetail.getGongwei();

                    showInterface(prDetail);

                    //请求
                    show2(prDetail, scyl_id, poid);
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


    private void showInterface(PRDetail prDetail){

        scyl_no.setText(prDetail.getScyl_ID());
        tv_product_no.setText(prDetail.getProduct_ID());
        tv_apply_no.setText(prDetail.getSc_shuliang() + prDetail.getFroms());
        tv_product_id.setText(prDetail.getProduct_pid());

        if ("0".equals(prDetail.getStatus())){
            tv_product_id.setVisibility(View.GONE);
            ll1.setVisibility(View.GONE);
        }

        if (!"0".equals(prDetail.getStatus())){
            rl1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
        }


    }

    private void initialView(){

    }


    @OnClick({R.id.btn_left_white, R.id.btn_done, R.id.btn_confirm, R.id.btn_delete})
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;
            case R.id.btn_confirm:
                //提交待出库原料批号
                confirm();
                break;
            case R.id.btn_done:
                //完成生产领用单
                done();
                break;
            case R.id.btn_delete:
                delete();
                break;
        }
    }


    private void delete(){
        //sclysm3-2.asp?id=*

        if (TextUtils.isEmpty(id)){
            UiCommon.INSTANCE.showTip("id不能为空!");
            return;
        }


        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);

        String url = "http://" + appContext.ipPort() + "/sclysm3-2.asp";

        vo.requestUrl = url;
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new ResponseParser();
        vo.Message = "正在删除...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("删除失败");
                }else {
                    ResponseResult result = (ResponseResult) paramObject;

                    if (result.getResponse().contains("OK")){
                        UiCommon.INSTANCE.showTip("删除成功!");

                        layout2.setVisibility(View.GONE);

                    }else {
                        UiCommon.INSTANCE.showTip("删除失败");
                    }
                }
            }

            @Override
            public void onFailure(AppException e) {
                UiCommon.INSTANCE.showTip("删除失败");
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                UiCommon.INSTANCE.showTip("删除失败");
                closeProgress();
            }
        });
    }

    private void confirm(){
            //sclysm4.asp?scyl_id=*&product_id=*&product_pid=*&gongwei=*&username=*

        //待出库原料批号
        String product_pid = tv_wait_source.getText().toString().trim();
        if (TextUtils.isEmpty(product_pid)){
            UiCommon.INSTANCE.showTip("清输入待出库原料批号!");
            return;
        }


        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("scyl_id", scylid);
        map.put("product_id", product_id);
        map.put("product_pid", product_pid);
        map.put("gongwei", gongwei);
        map.put("username", appContext.getLoginInfo().getUser_Name());


        String url = "http://" + appContext.ipPort() + "/sclysm4.asp";

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

                    if (result.getResponse().contains("OK")){
                        UiCommon.INSTANCE.showTip("提交成功!");

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

    private void done(){
        //完成生产领用单
        //sclysm3-1.asp?scyl_id=*&product_id=*
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("scyl_id", scylid);
        map.put("product_id", product_id);

        String url = "http://" + appContext.ipPort() + "/sclysm3-1.asp";

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

                    if ("false".equals(result.getResponse())){
                        UiCommon.INSTANCE.showTip("提交失败");


                    }else {
                        UiCommon.INSTANCE.showTip("提交成功");
                        finish();
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


    private void showing(){
        searching.setVisibility(View.VISIBLE);
        show_empty.setVisibility(View.GONE);
    }

    private void hide(){
        searching.setVisibility(View.GONE);
        show_empty.setVisibility(View.GONE);
    }

    private void emptyShowing(){
        searching.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
    }
}
