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
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.FullProDetail2;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.FullProDetail2ListParser;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.utils.StringUtils;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lab.certoplast.R.id.leave;

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

    @BindView(R.id.tv_content)
    TextView tv_content;

    //剩余数量
    @BindView(leave)
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


    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.tv_in)
    TextView tv_in;

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

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());

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
        map.put("warehost", warehost);
        map.put("sl_real_box", sl_real_box);
        map.put("real_box", real_box);
        map.put("poid", p_id);
        map.put("ppid", ppid);
        map.put("scjhid", cprk_scjh);
        map.put("rukuid", rukuid);
        map.put("juanrk", juanrk);

        RequestVo vo = new RequestVo();
        vo.methodName = "FullProDetail2.asp";
        vo.requestDataMap = map;

        vo.jsonParser = new FullProDetail2ListParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    Response response = (Response) paramObject;
                    FullProDetail2 detail2 = (FullProDetail2) response.getMsg();

                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                        sendErrorClose();
                    }else{
                        hide();
                        setShow(detail2);
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


    private void setShow(FullProDetail2 fullProDetail2){

            tv_warehost_no.setText("货位:" + fullProDetail2.getWarehost());
            tv_leave.setText(fullProDetail2.getRemain());
            tv_status.setText("目前状态:" + fullProDetail2.getState());
            tv_in.setText(fullProDetail2.getAmount());
            et_search.setHint(fullProDetail2.getAmount());

    }

    @OnClick({R.id.btn_search, R.id.tv_logout, R.id.tv_main})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.btn_search:
                search();
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

    private void search(){
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
        map.put("ppid", ppid);
        map.put("real_box", real_box);
        map.put("scjhid", cprk_scjh);
        map.put("slbox", box);
        map.put("rukuid", rukuid);
        map.put("juanrk", juanrk);
        map.put("username", appContext.getLoginInfo().getUser_Name());


        vo.methodName = "FullProBoxIn.asp";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.jsonParser = new LoginParser();
        vo.Message = "正在提交...";

        doPost(vo, new DataCallback() {
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
        layout1.setVisibility(View.GONE);
        show_empty.setVisibility(View.GONE);
    }

    private void hide(){
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.VISIBLE);
        show_empty.setVisibility(View.GONE);
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
