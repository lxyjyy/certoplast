package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.Scan;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class PRDetail1Adapter extends RecyclerView.Adapter {

    List<Scan> list;

    public PRDetail1Adapter(List<Scan> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        PRDetail1Adapter.BodyViewHolder bodyViewHolder = new PRDetail1Adapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prdetail1_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final PRDetail1Adapter.BodyViewHolder holder1 = (PRDetail1Adapter.BodyViewHolder) holder;

            Scan dataSource = list.get(position);

            holder1.tv_productid_num.setText("批号: "+ dataSource.getProduct_pid());
            holder1.tv_amount.setText("数量: " + dataSource.getOutshuliang());
            holder1.tv_origin_set.setText("原货位: " + dataSource.getWarehouse_set());

//            if (!"0".equals(dataSource.getStatus()))
//                 holder1.btn_delete.setVisibility(View.GONE);

            holder1.btn_delete.setOnClickListener(new View.OnClickListener() {
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

        private TextView tv_productid_num;
        private TextView tv_amount;
        private TextView tv_origin_set;
        private Button btn_delete;
        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_productid_num = (TextView) itemView.findViewById(R.id.tv_productid_num);

            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_origin_set = (TextView) itemView.findViewById(R.id.tv_origin_set);
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }



}
