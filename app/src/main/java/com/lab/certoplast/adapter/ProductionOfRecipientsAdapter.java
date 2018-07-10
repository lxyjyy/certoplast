package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.ProductionOfRecipients;

import java.util.List;

import static com.lab.certoplast.R.id.tv_purchase_no;
import static com.lab.certoplast.R.id.tv_warehousing_no;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class ProductionOfRecipientsAdapter extends RecyclerView.Adapter {

    List<ProductionOfRecipients> list;

    public ProductionOfRecipientsAdapter(List<ProductionOfRecipients> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        ProductionOfRecipientsAdapter.BodyViewHolder bodyViewHolder = new ProductionOfRecipientsAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.productionofrecipients_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final ProductionOfRecipientsAdapter.BodyViewHolder holder1 = (ProductionOfRecipientsAdapter.BodyViewHolder) holder;

            ProductionOfRecipients dataSource = list.get(position);

            holder1.warehousing_no.setText("领用单号: " + dataSource.getSingleno());
            holder1.purchase_no.setText(" 计划号: " + dataSource.getProduct_id());


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

        private TextView warehousing_no;
        private TextView purchase_no;
        public  BodyViewHolder(View itemView){
            super(itemView);

            warehousing_no = (TextView) itemView.findViewById(tv_warehousing_no);

            purchase_no = (TextView) itemView.findViewById(tv_purchase_no);

        }
    }



}
