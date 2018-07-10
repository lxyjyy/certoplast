package com.lab.certoplast.ui;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.PRDetail1Adapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.PRDetail;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.Scan;
import com.lab.certoplast.bean.ScanResult;
import com.lab.certoplast.bean.SpaceItemDecoration;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.parser.PRDetail1Parser;
import com.lab.certoplast.parser.ScanParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lxyjyy on 17/11/29.
 *
 * 生产领用详情
 */

public class PRDetail1Activity extends BaseActivity implements OnItemClickListener {

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
    EditText tv_wait_source;

    @BindView(R.id.sv)
    NestedScrollView sv;

    @BindView(R.id.layout2)
    LinearLayout layout2;

    @BindView(R.id.tv_has_scan)
    TextView tv_has_scan;
//    //批号
//    @BindView(R.id.tv_productid_num)
//    TextView tv_productid_num;
//    //数量
//    @BindView(R.id.tv_amount)
//    TextView tv_amount;
//    //原货位
//    @BindView(R.id.tv_origin_set)
//    TextView tv_origin_set;
//    //删除
//    @BindView(R.id.btn_delete)
//    Button btn_delete;
    @BindView(R.id.rv_remain)
    RecyclerView rv_remain;


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


    private String scylid;
    private String product_id;
    private String gongwei;

    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


    @BindView(R.id.tv_confirm)
    TextView tv_confirm;


    private List<Scan> list;
    private PRDetail1Adapter adapter;

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


    private void show2(String scyl_id, String poid){
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();

        map.put("id", scyl_id);
        map.put("poid", poid);


        vo.methodName = "ProductionOfRecipientsDetail2.asp";
        vo.requestDataMap = map;
        vo.jsonParser = new ScanParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                    if (paramObject == null){
                        layout2.setVisibility(View.GONE);
                    }else {

                        Response response = (Response) paramObject;
                        if ("error".equals(response.getResponse())){
                            layout2.setVisibility(View.GONE);
                        }else {

                            ScanResult result = (ScanResult) response.getMsg();

                            layout2.setVisibility(View.VISIBLE);
                            tv_has_scan.setText("已经扫描总数为: " + result.getSl());

                            List<Scan> list1 = result.getMsg();

                            list.clear();
                            list.addAll(list1);
                            rv_remain.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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

        map.put("id", scyl_id);
        map.put("poid", poid);


        vo.methodName = "ProductionOfRecipientsDetail1.asp";
        vo.requestDataMap = map;
        vo.jsonParser = new PRDetail1Parser();

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
                        hide();
                        PRDetail prDetail = (PRDetail) response.getMsg();
                        gongwei = prDetail.getGongwei();

                        showInterface(prDetail);

                        //请求
                        show2(scyl_id, poid);
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


    private void showInterface(PRDetail prDetail){

        scyl_no.setText(prDetail.getScyl_ID());
        tv_product_no.setText(prDetail.getProduct_ID());
        tv_apply_no.setText(prDetail.getSc_shuliang() + prDetail.getFroms());

        tv_product_id.setText(prDetail.getProduct_pid());
        if ("0".equals(prDetail.getProduct_pid())){
            ll_product_id.setVisibility(View.GONE);
            ll1.setVisibility(View.GONE);
        }

        if (!"0".equals(prDetail.getStatus())){
            btn_done.setVisibility(View.GONE);
            tv_confirm.setVisibility(View.GONE);
            rl1.setVisibility(View.GONE);
            ll2.setVisibility(View.GONE);
        }

    }

    private void initialView(){

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());


        rv_remain.addItemDecoration(new SpaceItemDecoration(15));
        rv_remain.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        btn_left_white.setVisibility(View.VISIBLE);

        sv.setFillViewport(true);

        tv_title.setText("生产领用");

        tv_confirm.setText("完成");
        tv_confirm.setVisibility(View.VISIBLE);

        sv.setFillViewport(true);

        list = new ArrayList<>();
        adapter = new PRDetail1Adapter(list);
        adapter.setOnItemClickListener(this);


        postDelayed(tv_wait_source);
        tv_wait_source.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String search = tv_wait_source.getText().toString().trim();
                if (!hasFocus && !TextUtils.isEmpty(search)){
                    //提交
                    confirm();
                }
            }
        });


    }


    @OnClick({R.id.btn_left_white, R.id.btn_confirm, R.id.tv_logout, R.id.tv_main, R.id.tv_confirm})
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_left_white:
                finish();
                break;
            case R.id.btn_confirm:
                //提交待出库原料批号
                confirm();
                break;
            case R.id.tv_confirm:
                //完成生产领用单
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

        }
    }


    private void delete(Scan scan){

        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", scan.getId());


        vo.methodName = "DeletePR.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new LoginParser();
        vo.Message = "正在删除...";

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();

                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("删除失败");
                }else {
                    Response response = (Response) paramObject;

                    if ("error".equals(response.getResponse())){
                        UiCommon.INSTANCE.showTip(response.getInfo());
                    }else{
                        show2(scylid,product_id);
                        UiCommon.INSTANCE.showTip("删除成功!");
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
        final String product_pid = tv_wait_source.getText().toString().trim();
        if (TextUtils.isEmpty(product_pid)){
            UiCommon.INSTANCE.showTip("请输入待出库原料批号!");
            return;
        }


        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("scyl_id", scylid);
        map.put("product_id", product_id);
        map.put("product_pid", product_pid);
        map.put("gongwei", gongwei);
        map.put("username", appContext.getLoginInfo().getUser_Name());


        vo.methodName = "sclysm4.asp";
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
                    tv_wait_source.setText("");
                }else {
                    Response result = (Response) paramObject;

                    if ("error".equals(result.getResponse())){
                        UiCommon.INSTANCE.showTip(result.getInfo());
                        tv_wait_source.setText("");
                    }else {
                        UiCommon.INSTANCE.showTip("提交成功!");
                        show1(scylid, product_id);
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

        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", scylid);
        map.put("poid", product_id);

        vo.methodName = "completePR.asp";
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
                        UiCommon.INSTANCE.showTip("提交失败!");
                    }else {
                        UiCommon.INSTANCE.showTip("提交成功!");
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
        tv_content.setText(text);
    }


    @Override
    public void onItemClick(View view, int position) {

        Scan scan = list.get(position);
        delete(scan);


    }
}
