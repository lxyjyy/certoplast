package com.lab.certoplast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.lab.certoplast.R;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.Inventory;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.Response;
import com.lab.certoplast.bean.User;
import com.lab.certoplast.parser.InventoryParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/8.
 *
 * 盘点列表
 */

public class InventoryListActivity extends BaseActivity {

    @BindView(R.id.btn_left_white)
    ImageButton btn_left_white;
    @BindView(R.id.tv_title)
    TextView tv_title;

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerView;
    @BindView(R.id.searching)
    View searching;
    @BindView(R.id.show_empty)
    View show_empty;

    @BindView(R.id.table)
    SmartTable table;


    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_main)
    TextView tv_main;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_logout)
    TextView tv_logout;


    private List<Inventory> list;

//    private InventoryAdapter adapter;


    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_pdsearchlist, null, false);
    }

    @Override
    protected void initData() {

        initConfig();
        initialData();

    }


    private void initialData(){


       String product_id =  getIntent().getExtras().getString("search");

        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("ppid", product_id);

        RequestVo vo  = new RequestVo();

        vo.requestDataMap = map;
        vo.methodName = "inventory.asp";
        vo.jsonParser = new InventoryParser();

        doGet(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    Response response = (Response)paramObject;

                    if ("error".equals(response.getResponse())){
                        emptyShowing(response.getInfo());
                        sendErrorClose();
                    }else {
                        List<Inventory> list1 = (List<Inventory>) response.getMsg();


                        List<Inventory> copyList = caculateList(list1);

                        hide();
                        list.clear();
                        list.addAll(copyList);
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                        table.setData(list);
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


    private List<Inventory> caculateList(List<Inventory> sourceList){

        List<Inventory> copy = new ArrayList<>();

        Pattern p = Pattern.compile("^((BLK[\\s\\S]*)|(X0[0-3][0-9][\\s\\S]*))[\\s\\S]*$");

        for (int i = 0; i < sourceList.size(); i++){

            Inventory inventory = sourceList.get(i);
            Matcher m = p.matcher(inventory.getWareHouse_set());
            if (!m.matches()){
                copy.add(inventory);
            }
        }

        return copy;


    }


    @OnClick({R.id.tv_logout, R.id.tv_main})
    public void onClick(View v){
        switch (v.getId()){

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


    private void initConfig(){
//        recyclerView.addItemDecoration(new SpaceItemDecoration(15));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview

        tv_title.setText("库存盘点");

        User user = appContext.getLoginInfo();
        tv_username.setText(user.getUser_Name());



        btn_left_white.setVisibility(View.VISIBLE);
        btn_left_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = new ArrayList<>();
//        adapter = new InventoryAdapter(list);
    }

    private void showing(){
        table.setVisibility(View.GONE);
        searching.setVisibility(View.VISIBLE);
    }

    private void hide(){
        table.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
    }

    private void emptyShowing(){
        table.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
    }

    private void emptyShowing(String text){
        table.setVisibility(View.GONE);
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
        tv_content.setText(text);
    }

}
