package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.HalfPro;
import com.lab.certoplast.bean.OnItemClickListener;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class HalfProAdapter extends RecyclerView.Adapter {

    List<HalfPro> list;

    public HalfProAdapter(List<HalfPro> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        HalfProAdapter.BodyViewHolder bodyViewHolder = new HalfProAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.halfpro_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final HalfProAdapter.BodyViewHolder holder1 = (HalfProAdapter.BodyViewHolder) holder;

            HalfPro dataSource = list.get(position);

            if (!TextUtils.isEmpty(dataSource.getReal_box())){
                holder1.tv_amount.setVisibility(View.VISIBLE);
                holder1.tv_amount.setText("箱数: " + dataSource.getReal_box());
            }
            holder1.tv_product.setText("产品: " + dataSource.getProduct_id());
            holder1.tv_unit.setText("库单位: " + dataSource.getAmount() + dataSource.getFroms());
            holder1.tv_location.setText("工位: " + dataSource.getGongwei());
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

        private TextView tv_product;
        private TextView tv_unit;
        private TextView tv_location;
        private TextView tv_amount;
        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_product = (TextView) itemView.findViewById(R.id.tv_product);

            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            tv_location = (TextView) itemView.findViewById(R.id.tv_location);

            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);

        }
    }



}
