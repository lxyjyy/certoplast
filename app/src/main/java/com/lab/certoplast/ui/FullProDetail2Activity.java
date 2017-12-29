package com.lab.certoplast.ui;

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
import com.lab.certoplast.bean.ProductSearch;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.ResponseResult;
import com.lab.certoplast.bean.WareHouse;
import com.lab.certoplast.parser.ProductSearchParser;
import com.lab.certoplast.parser.ResponseParser;
import com.lab.certoplast.parser.WareHouseListParser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/12/10.
 *
 * 成品入库
 */

public class FullProDetail2Activity extends BaseActivity {


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

    //货位
    @BindView(R.id.tv_warehost_no)
    TextView tv_warehost_no;


    //目前状态
    @BindView(R.id.tv_status)
    TextView tv_status;

    //剩余数量
    @BindView(R.id.leave)
    TextView tv_leave;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_search)
    Button btn_search;

    private String p_id;
    private String ppid;
    private String cprk_scjh;
    private String rukuid;
    private String juanrk;
    private String warehost;
    private String sl_real_box;
    private String real_box;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_fullprodetail2, null, false);
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

    /**
     * 初始化数据
     */
    private void initialData(){

        warehost = getIntent().getExtras().getString("warehost");
        sl_real_box = getIntent().getExtras().getString("sl_real_box");
        real_box = getIntent().getExtras().getString("real_box");
        p_id = getIntent().getExtras().getString("p_id");
        ppid = getIntent().getExtras().getString("ppid");
        cprk_scjh = getIntent().getExtras().getString("cprk_scjh");
        rukuid = getIntent().getExtras().getString("rukuid");
        juanrk = getIntent().getExtras().getString("juanrk");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("WareHouse_Set", warehost);

        RequestVo vo = new RequestVo();
        vo.methodName = "GetWareHoust_Set";
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
                        setShow(list);
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


    private void setShow(List<WareHouse> list){
        int flag = 0;
        final WareHouse wareHouse = list.get(0);
        int size = list.size();

        if ("".equals(wareHouse.getProduct_ID()) && "".equals(wareHouse.getProduct_PID())){
            flag = 1;

        }

        if ("0".equals(wareHouse.getProduct_ID()) && "0".equals(wareHouse.getProduct_PID())){
            flag = 1;

        }

        if (p_id.equals(wareHouse.getProduct_ID()) && ppid.equals(wareHouse.getProduct_PID())){
            flag = 1;
        }

        if (flag == 0){
            emptyShowing();
            UiCommon.INSTANCE.showTip("货位已有其他批次的产品，不能混放！");
        }else {
            tv_warehost_no.setText("货位:" + warehost);

            int leave = StringUtils.toInt(real_box) - StringUtils.toInt(sl_real_box);
            tv_leave.setText("还有" + leave + juanrk + "产品需要入库!");
            final String amount = wareHouse.getShuliang();
            if ("卷".equals(juanrk)){
                if ("0".equals(wareHouse.getProduct_ID()) && "0".equals(wareHouse.getProduct_PID())){
                    tv_status.setText("目前状态:空");

                }else {
                    tv_status.setText("已有产品:"+wareHouse.getProduct_ID()+
                            "," + "已有批号;" + wareHouse.getProduct_PID() + ",已有数量:" + amount + juanrk);
                }
            }else {
                HashMap<String, String> map = new HashMap<>();
                map.put("product_id", p_id);

                RequestVo vo = new RequestVo();
                vo.methodName = "GetProductSearch";
                vo.requestDataMap = map;

                vo.jsonParser = new ProductSearchParser();

                doPost(vo, new DataCallback() {
                    @Override
                    public void processData(Object paramObject, boolean paramBoolean) {
                        if (paramObject == null){
                            emptyShowing();
                        }else {

                            hide();
                            ProductSearch productSearch = (ProductSearch) paramObject;
                            int show = 0;
                            if ("3".equals(productSearch.getType()) || "10".equals(productSearch.getType())){
                                show = StringUtils.toInt(amount)/StringUtils.toInt(productSearch.getBaozhuang_bili());
                            }
                            if ("0".equals(wareHouse.getProduct_ID()) && "0".equals(wareHouse.getProduct_PID())){
                                tv_status.setText("目前状态:空");

                            }else {
                                tv_status.setText("已有产品:"+wareHouse.getProduct_ID() +
                                "," + "已有批号;" + wareHouse.getProduct_PID() + ",已有数量:" + show + juanrk);
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

        }

    }

    @OnClick(R.id.btn_search)
    public void onClick(View view){
         String box = et_search.getText().toString().trim();

        if (TextUtils.isEmpty(box)){
            UiCommon.INSTANCE.showTip("入库箱数不能为空!");
            return;
        }

        int amount = StringUtils.toInt(box);
        if (amount == 0){
            UiCommon.INSTANCE.showTip("数量不能为0");
            return;
        }

        //cprksm4.asp?warehost=*&sl_real_box=*
        // &real_box=*&poid=*&ppid=*&
        // scjhid=*&slbox=*&rukuid=*&juanrk=*&username=*
        RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("warehost", warehost);
        map.put("sl_real_box", sl_real_box);
        map.put("poid", p_id);
        map.put("scjhid", cprk_scjh);
        map.put("slbox", box);
        map.put("rukuid", rukuid);
        map.put("juanrk", juanrk);
        map.put("username", appContext.getLoginInfo().getUser_Name());

        String url = "http://" + appContext.ipPort() + "/cprksm4.asp";

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
                        UiCommon.INSTANCE.showTip("");

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
}
