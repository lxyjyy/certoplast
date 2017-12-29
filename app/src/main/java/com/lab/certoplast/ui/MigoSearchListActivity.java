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
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.parser.MigoListParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/9.
 *
 * 采购收货列表
 */

public class MigoSearchListActivity extends BaseActivity {


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



    private String ruku_id;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_migosearchlist, null, false);
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

        tv_title.setText("采购收货");

    }

    /**
     * 初始化数据
     */
    private void initialData(){

        ruku_id = getIntent().getExtras().getString("ruku_id");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("Ruku_ID", ruku_id);

        RequestVo vo = new RequestVo();
        vo.methodName = "GetKuInsearch";
        vo.requestDataMap = map;

        vo.jsonParser = new MigoListParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    List<Migo> list1 = (List<Migo>) paramObject;

                    if (list1.size() <= 0){
                        emptyShowing();
                    }else {
                        hide();

                        setShow(list1);

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


    private void setShow(List<Migo> list){

        Migo migo = list.get(0);
        int size = list.size();

        //采购单号
        tv_warehousing_no.setText("采购单号: " + migo.getCaigou_ID());

        //入库单号
        tv_purchase_no.setText("入库单号: " + migo.getRuku_ID());

        //剩余需要扫描的批次
        tv_leave.setText("还有" + size + "个批次产品需要入库!");

    }

    @OnClick(R.id.btn_search)
    public void onClick(View view){

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

        redictToActivity(MigoSearchListActivity.this, CgshDetail1Activity.class, bundle);

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
