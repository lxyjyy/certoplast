package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.FullPro;
import com.lab.certoplast.bean.OnItemClickListener;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class FullProAdapter extends RecyclerView.Adapter {

    List<FullPro> list;

    public FullProAdapter(List<FullPro> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        FullProAdapter.BodyViewHolder bodyViewHolder = new FullProAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fullpro_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final FullProAdapter.BodyViewHolder holder1 = (FullProAdapter.BodyViewHolder) holder;

            FullPro dataSource = list.get(position);



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
        private TextView tv_operation;
        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_product = (TextView) itemView.findViewById(R.id.tv_product);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            tv_operation = (TextView) itemView.findViewById(R.id.tv_operation);

        }
    }



}
