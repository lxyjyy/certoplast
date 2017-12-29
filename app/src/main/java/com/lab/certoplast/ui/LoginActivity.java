package com.lab.certoplast.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.IP;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.LoginParser;
import com.lab.certoplast.utils.UiCommon;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by lxyjyy on 17/11/7.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login)
    EditText et_login;
    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.firstIP)
    EditText firstIP;
    @BindView(R.id.secondIP)
    EditText secondIP;
    @BindView(R.id.thirdIP)
    EditText thirdIP;
    @BindView(R.id.fourthIP)
    EditText fourthIP;
    @BindView(R.id.et_port)
    EditText et_port;

    private String first;
    private String second;
    private String third;
    private String fourth;
    private String requestPort;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
    }


    private void initListener(){

        et_login.addTextChangedListener(watcher0);

        firstIP.addTextChangedListener(watcher1);
        secondIP.addTextChangedListener(watcher2);
        thirdIP.addTextChangedListener(watcher3);
        fourthIP.addTextChangedListener(watcher4);
        et_port.addTextChangedListener(port);


        IP ip = appContext.getIPPort();

        if (!(TextUtils.isEmpty(ip.getIpAddress()) || TextUtils.isEmpty(ip.getPort()))){
            String[] str = ip.getIpAddress().split("\\.");
            firstIP.setText(str[0]);
            secondIP.setText(str[1]);
            thirdIP.setText(str[2]);
            fourthIP.setText(str[3]);

            et_port.setText(ip.getPort());

            et_login.setFocusable(true);
            et_login.requestFocus();
        }

    }


     public void initData(){

         initListener();


        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second)
                        || TextUtils.isEmpty(third) || TextUtils.isEmpty(third)){
                    UiCommon.INSTANCE.showTip("请输入IP地址");
                    return;
                }

                if (TextUtils.isEmpty(requestPort)){
                    UiCommon.INSTANCE.showTip("请输入端口号");
                    return;
                }

                save();

                final String userId = et_login.getText().toString().trim();

                HashMap<String,String> map = new HashMap<String, String>();

                map.put("UserID", userId);

                RequestVo vo = new RequestVo();

                vo.methodName = "GetHQ_User";
                vo.requestDataMap = map;
                vo.jsonParser = new LoginParser();
                vo.isShowDialog = true;
                vo.Message = "正在登录...";


                doPost(vo, new DataCallback() {
                    @Override
                    public void processData(Object paramObject, boolean paramBoolean) {
                        closeProgress();
                        if (paramObject != null){

                            save();

                            User user = (User) paramObject;
                            user.setUser_Name(userId);
                            appContext.saveLoginInfo(user);
                            redictToActivity(LoginActivity.this, MainActivity.class, null);
                            finish();
                        }else {
                            UiCommon.INSTANCE.showTip("用户不存在");
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
        });

    }


    private void save(){
        String ipWhole = first + "." + second + "." + third + "." + fourth;

        IP ip = new IP();
        ip.setIpAddress(ipWhole);
        ip.setPort(requestPort);
        appContext.saveIPPort(ip);
    }


    TextWatcher watcher0 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (et_login.getText().toString().length() > 0) {
                btn_login.setEnabled(true);
                btn_login.setTextColor(getResources().getColor(R.color.login_bg));
            }else {
                btn_login.setEnabled(false);
                btn_login.setTextColor(getResources().getColor(android.R.color.white));
            }
        }
    };


    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().trim().replace(".","");

            if (!TextUtils.isEmpty(result)){
                first = result;
                return;
            }


        }
    };

    TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().trim().replace(".","");

            if (!TextUtils.isEmpty(result)){
                second = result;
                return;
            }
        }
    };


    TextWatcher watcher3 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().trim().replace(".","");

            if (!TextUtils.isEmpty(result)){
                third = result;
                return;
            }
        }
    };


    TextWatcher watcher4 = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().trim().replace(".","");

            if (!TextUtils.isEmpty(result)){
                fourth = result;
                return;
            }
        }
    };


    TextWatcher port = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().trim();

            if (!TextUtils.isEmpty(result)) {
                requestPort = result;
                return;
            }

        }
    };

}
