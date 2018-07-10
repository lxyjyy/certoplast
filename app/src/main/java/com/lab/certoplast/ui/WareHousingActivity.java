package com.lab.certoplast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.Stock;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.StockParser;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/19.
 * 产品入库
 */

public class WareHousingActivity extends BaseActivity {


    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.half_pro)
    RelativeLayout half_pro;
    @BindView(R.id.full_pro)
    RelativeLayout full_pro;

    @BindView(R.id.tv_half)
    TextView tv_half;
    @BindView(R.id.tv_full)
    TextView tv_full;



    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_warehouse, null, false);
    }

    @Override
    protected void initData() {
        initialView();
        initialData();
    }


    @OnClick({R.id.btn_left_white, R.id.half_pro, R.id.full_pro, R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){

        switch (v.getId()){

            case R.id.btn_left_white:
                finish();
                break;

            case R.id.half_pro:
                redictToActivity(WareHousingActivity.this, HalfProActivity.class, null);
                break;

            case R.id.full_pro:
                redictToActivity(WareHousingActivity.this, FullProActivity.class, null);
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



    private void initialView(){
        tv_title.setText("产品入库");
        btn_left_white.setVisibility(View.VISIBLE);

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());
    }


    private void initialData(){

        RequestVo vo = new RequestVo();
        vo.methodName = "stock.asp";
        vo.requestDataMap = null;

        vo.jsonParser = new StockParser();
        vo.isShowDialog = true;

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject != null){
                    Response response = (Response) paramObject;

                    if ("success".equals(response.getResponse())){
                        Stock stock = (Stock) response.getMsg();

                        tv_half.setText(stock.getHalf());
                        tv_full.setText(stock.getFull());
                    }

                }
            }

            @Override
            public void onFailure(AppException e) {
                closeProgress();
            }

            @Override
            public void onError(ErrorMessage error) {
                closeProgress();
            }
        });
    }
}
