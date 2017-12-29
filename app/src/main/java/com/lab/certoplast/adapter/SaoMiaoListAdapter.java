package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.SaoMiao;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class SaoMiaoListAdapter extends RecyclerView.Adapter {

    List<SaoMiao> list;

    public SaoMiaoListAdapter(List<SaoMiao> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        SaoMiaoListAdapter.BodyViewHolder bodyViewHolder = new SaoMiaoListAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.saomiao_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final SaoMiaoListAdapter.BodyViewHolder holder1 = (SaoMiaoListAdapter.BodyViewHolder) holder;

            SaoMiao dataSource = list.get(position);

            holder1.tv_product.setText("产品编号: " + dataSource.getProduct_id());



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
        private TextView tv_remain;

        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_product = (TextView) itemView.findViewById(R.id.tv_product);
            tv_remain = (TextView) itemView.findViewById(R.id.tv_remain);


        }
    }



}
