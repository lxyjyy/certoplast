package com.lab.certoplast.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by lxyjyy on 17/11/15.
 *
 * 盘点实体{"response":"success","info":"success",
 * "msg":[{"warehouse":"成品库","wareHouse_set":"FE131","product_id":"52502525","amount":"6[4]"},
 * {"warehouse":"成品库","wareHouse_set":"FI003","product_id":"52502525","amount":"14"},
 * {"warehouse":"成品库","wareHouse_set":"FD102B","product_id":"52502525","amount":"16"},
 * {"warehouse":"成品库","wareHouse_set":"FQ011","product_id":"52502525","amount":"16"}]
 * }

 */
@SmartTable(name="库存列表")
public class Inventory extends Base {
    @SmartColumn(id =1,name = "库房")
    private String warehouse;
    @SmartColumn(id =2,name = "货位ID")
    private String wareHouse_set;
    private String product_id;
    @SmartColumn(id =3,name = "数量")
    private String amount;

    @SmartColumn(id =3,name = "产品批号")
    private String product_pid;


    public String getProduct_pid() {
        return product_pid;
    }

    public void setProduct_pid(String product_pid) {
        this.product_pid = product_pid;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWareHouse_set() {
        return wareHouse_set;
    }

    public void setWareHouse_set(String wareHouse_set) {
        this.wareHouse_set = wareHouse_set;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
