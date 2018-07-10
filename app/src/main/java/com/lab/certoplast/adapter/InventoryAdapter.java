package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.Inventory;
import com.lab.certoplast.bean.OnItemClickListener;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class InventoryAdapter extends RecyclerView.Adapter {

    List<Inventory> list;

    public InventoryAdapter(List<Inventory> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        InventoryAdapter.BodyViewHolder bodyViewHolder = new InventoryAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final InventoryAdapter.BodyViewHolder holder1 = (InventoryAdapter.BodyViewHolder) holder;

            Inventory dataSource = list.get(position);

            holder1.tv_warehouse.setText("库房: " + dataSource.getWarehouse());
            holder1.tv_warehouse_set.setText("货位ID: " + dataSource.getWareHouse_set());
            holder1.tv_amount.setText("数量: " + dataSource.getAmount());
            holder1.tv_product_pid.setText("产品批号: " + dataSource.getProduct_pid());


            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder1.itemView, position);
                    }
                }
            });
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_warehouse;
        private TextView tv_warehouse_set;
        private TextView tv_amount;
        private TextView tv_product_pid;
        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_warehouse = (TextView) itemView.findViewById(R.id.tv_warehouse);

            tv_warehouse_set = (TextView) itemView.findViewById(R.id.tv_warehouse_set);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_product_pid = (TextView) itemView.findViewById(R.id.tv_product_pid);
        }
    }



}
