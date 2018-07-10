package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.ScanList;

import java.util.List;

import static com.lab.certoplast.R.id.tv_purchase_no;
import static com.lab.certoplast.R.id.tv_warehousing_no;

/**
 * Created by lxyjyy on 17/11/13.
 */

public class ScanListDetailAdapter extends RecyclerView.Adapter {

    List<ScanList> list;

    public ScanListDetailAdapter(List<ScanList> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        ScanListDetailAdapter.BodyViewHolder bodyViewHolder = new ScanListDetailAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scanlistdetail_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final ScanListDetailAdapter.BodyViewHolder holder1 = (ScanListDetailAdapter.BodyViewHolder) holder;

            ScanList dataSource = list.get(position);

            holder1.warehousing_no.setText("批号: " + dataSource.getPid());
            holder1.purchase_no.setText(" 货位: " + dataSource.getWarehoust_set());

            holder1.tv_amount.setText("数量: " + dataSource.getOutbox() + dataSource.getUnit());


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

        private TextView warehousing_no;
        private TextView purchase_no;
        private TextView tv_amount;
        private Button btn_delete;
        public  BodyViewHolder(View itemView){
            super(itemView);

            warehousing_no = (TextView) itemView.findViewById(tv_warehousing_no);

            purchase_no = (TextView) itemView.findViewById(tv_purchase_no);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);

            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }



}
