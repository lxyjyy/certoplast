package com.lab.certoplast.ui;

import android.app.AlertDialog;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.adapter.ShowWareHouseAdapter;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.app.AppManager;
import com.lab.certoplast.bean.DataCallback;
import com.lab.certoplast.bean.ErrorMessage;
import com.lab.certoplast.bean.Migo;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.ResponseResult;
import com.lab.certoplast.bean.WareHouse;
import com.lab.certoplast.parser.MigoParser;
import com.lab.certoplast.parser.ResponseParser;
import com.lab.certoplast.parser.WareHouseListParser;
import com.lab.certoplast.utils.UiCommon;
import com.lab.certoplast.widget.MyListview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxyjyy on 17/11/8.
 * 采购收货界面1
 */

public class CgshDetail1Activity extends BaseActivity {



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
    @BindView(R.id.tv_rkdh)
    TextView tv_rkdh;
    @BindView(R.id.tv_cgdh)
    TextView tv_cgdh;
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.tv_product_serial_num)
    TextView tv_product_serial_num;
    @BindView(R.id.tv_product_type)
    TextView tv_product_type;
    @BindView(R.id.tv_ruku_amount)
    TextView tv_ruku_amount;
    @BindView(R.id.tv_kufang_type)
    TextView tv_kufang_type;
    @BindView(R.id.tv_now_type)
    TextView tv_now_type;

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.et_select)
    EditText et_select;
    @BindView(R.id.btn_commit)
    Button btn_commit;

    @BindView(R.id.view)
    View view;

    private List<WareHouse> wareHouseList;

    private Migo migo;

    /**
     * http://222.92.231.162:8002/caigousm5.asp?caigourk_id=*&caigou_id=*&p_id=*&selectid=*&id=*&username=*
     */
    private String caigourk_id;
    private String caigou_id;
    private String p_id;
    private String selectid;
    private String username;


    @BindView(R.id.sv)
    NestedScrollView sv;

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_cgshdetail1, null, false);
    }

    @Override
    protected void initData() {
        initialView();
        initialData();
    }


    private void initialView(){
        wareHouseList = new ArrayList<>();

        btn_left_white.setVisibility(View.VISIBLE);
        tv_title.setText("采购收货");
        sv.setFillViewport(true);
    }

    private void initialData(){

        String ruku_id = getIntent().getExtras().getString("Ruku_ID");
        String product_pid = getIntent().getExtras().getString("Product_PID");

        //请求数据
        showing();

        HashMap<String, String> map = new HashMap<>();
        map.put("Ruku_ID", ruku_id);
        map.put("Product_PID", product_pid);

        RequestVo vo = new RequestVo();
        vo.methodName = "GetKuInsearch_ProductID";
        vo.requestDataMap = map;
        vo.jsonParser = new MigoParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                if (paramObject == null){
                    emptyShowing();
                }else {

                    hide();
                    migo = (Migo) paramObject;
                    setShow(migo);
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


    private void setShow(Migo migo){

        //入库单号
        tv_rkdh.setText(migo.getRuku_ID());
        //采购单号
        tv_cgdh.setText(migo.getCaigou_ID());
        //产品名称
        tv_product_name.setText(migo.getProduct());
        //产品批号
        tv_product_serial_num.setText(migo.getProduct_PID());
        //产品类型
        tv_product_type.setText(migo.getType());
        //入库数量
        tv_ruku_amount.setText(migo.getRuku_Shuliang());
        //库房类型
        tv_kufang_type.setText(migo.getWarehouse());
        //当前状态
        tv_now_type.setText(migo.getStatus_Class());

        caigourk_id = migo.getRuku_ID();
        caigou_id = migo.getCaigou_ID();
        p_id = migo.getProduct_PID();

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
        show_empty.setVisibility(View.VISIBLE);
        searching.setVisibility(View.GONE);
        layout1.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_left_white, R.id.btn_search, R.id.et_select, R.id.btn_commit})
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.btn_left_white:
                finish();
                break;

            case R.id.btn_search:
                btn_search();
                break;

            case R.id.et_select:
                showSelect();
                break;
            case R.id.btn_commit:
                //提交

                commit();
                break;
        }
    }


    /**
     * http://222.92.231.162:8002/caigousm5.asp?caigourk_id=*&caigou_id=*&p_id=*&selectid=*&id=*&username=*
     */

    private void commit(){

        username = appContext.getLoginInfo().getUser_Name();
        if (TextUtils.isEmpty(caigourk_id) || TextUtils.isEmpty(caigou_id) ||
                TextUtils.isEmpty(p_id) || TextUtils.isEmpty(selectid) || TextUtils.isEmpty(username)){
            UiCommon.INSTANCE.showTip("参数不全");
            return;
        }

          RequestVo vo = new RequestVo();

        HashMap<String, String> map = new HashMap<>();
        map.put("caigourk_id", caigourk_id);
        map.put("caigou_id", caigou_id);
        map.put("p_id", p_id);
        map.put("selectid", selectid);
        map.put("username", username);

          String url = "http://" + appContext.ipPort() + "/caigousm5.asp";

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
                        UiCommon.INSTANCE.showTip("采购收货成功!");
                        //跳转到主页面
                        AppManager.getAppManager().finishAllActivity();
                        redictToActivity(CgshDetail1Activity.this, MainActivity.class, null);
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


    private void showSelect(){
        alertDialog(wareHouseList, new OnItemClick() {
            @Override
            public void ItemContent(List<WareHouse> areas) {

                hide_search();
                WareHouse wareHouse = findSelectedArea(areas);
                if (wareHouse == null){
                    UiCommon.INSTANCE.showTip("请选择货位代码!");
                }
                et_select.setText("");
                et_select.setText(wareHouse.getWareHouse_Set());
                selectid = wareHouse.getWareHouse_Set();
            }
        });
    }


    private void show_search(){
        et_search.setVisibility(View.VISIBLE);
        btn_search.setVisibility(View.VISIBLE);

        et_select.setVisibility(View.GONE);
        btn_commit.setVisibility(View.GONE);
    }

    private void hide_search(){
        et_search.setVisibility(View.GONE);
        btn_search.setVisibility(View.GONE);

        et_select.setVisibility(View.VISIBLE);
        btn_commit.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    private void btn_search(){

        String search = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(search)){
            UiCommon.INSTANCE.showTip("请输入入库货位代码!");
            return;
        }

        if (migo == null){
            UiCommon.INSTANCE.showTip("货位对象不能为空!");
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("Warehouse_Set", search);
        map.put("Warehouse_ID", migo.getClassId());


        RequestVo vo = new RequestVo();

        vo.methodName = "GetWarehoustSearch";
        vo.requestDataMap = map;
        vo.isShowDialog = true;
        vo.Message = "正在获取货位代码列表";
        vo.jsonParser = new WareHouseListParser();

        doPost(vo, new DataCallback() {
            @Override
            public void processData(Object paramObject, boolean paramBoolean) {
                closeProgress();
                if (paramObject == null){
                    UiCommon.INSTANCE.showTip("获取货位代码失败");
                }else {
                    List<WareHouse> list = (List<WareHouse>) paramObject;

                    if (list.size() <= 0){
                        UiCommon.INSTANCE.showTip("未查询到对应的货位!");
                    }else {
                        wareHouseList.clear();
                        wareHouseList.addAll(list);

                        alertDialog(wareHouseList, new OnItemClick() {
                            @Override
                            public void ItemContent(List<WareHouse> areas) {

                                hide_search();

                                WareHouse wareHouse = findSelectedArea(areas);
                                if (wareHouse == null){
                                    UiCommon.INSTANCE.showTip("请选择货位代码!");
                                }
                                et_select.setText("");
                                et_select.setText(wareHouse.getWareHouse_Set());
                                selectid = wareHouse.getWareHouse_Set();

                            }
                        });
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

    public interface OnItemClick {
        void ItemContent(List<WareHouse> areas);
    }


    private WareHouse findSelectedArea(List<WareHouse> areas) {
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).isFlag()) {
                return areas.get(i);
            }
        }
        return null;
    }

    private void alertDialog(final List<WareHouse> areas,
                             final OnItemClick onItemClick) {
        final AlertDialog myDialog = new AlertDialog.Builder(CgshDetail1Activity.this)
                .create();
        myDialog.show();
        WindowManager windowManager = CgshDetail1Activity.this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = myDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 11 / 12); // 设置宽度
        myDialog.getWindow().setAttributes(lp);
        myDialog.getWindow().setContentView(R.layout.show_area_list_dialog);

        MyListview myListView = (MyListview) myDialog.getWindow().findViewById(
                R.id.mylistview);
        // 确定按钮
        TextView tv_sure = (TextView) myDialog.getWindow().findViewById(
                R.id.tv_confirm);

        tv_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onItemClick.ItemContent(areas);
                myDialog.dismiss();

            }
        });
        final ShowWareHouseAdapter showWareHouseAdapter = new ShowWareHouseAdapter(CgshDetail1Activity.this, areas);
        myListView.setAdapter(showWareHouseAdapter);

        // 点击每一项
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // onItemClick.ItemContent(myDialog, areas, position);
                WareHouse wareHouse = areas.get(position);
                wareHouse.setFlag(!wareHouse.isFlag());
                // 点击当前都为选中状态，还原其他的为初始状态
                backToFirst(areas, wareHouse);
                showWareHouseAdapter.notifyDataSetChanged();
            }
        });

    }

    /**
     *
     * @param areas
     * @param area
     */
    private void backToFirst(List<WareHouse> areas, WareHouse area) {
        if (area != null && areas != null && areas.size() > 0) {

            for (int i = 0; i < areas.size(); i++) {
                if (areas.get(i).equals(area)) {

                } else {
                    areas.get(i).setFlag(false);
                }
            }

        }
    }

}
