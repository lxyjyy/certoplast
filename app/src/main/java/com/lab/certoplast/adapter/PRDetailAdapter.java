package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.OnItemClickListener;
import com.lab.certoplast.bean.PRDetail;

import java.util.List;


/**
 * Created by lxyjyy on 17/11/13.
 */

public class PRDetailAdapter extends RecyclerView.Adapter {

    List<PRDetail> list;

    public PRDetailAdapter(List<PRDetail> list){

        this.list = list;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        PRDetailAdapter.BodyViewHolder bodyViewHolder = new PRDetailAdapter.BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.prdetail_item, parent, false));
        return bodyViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BodyViewHolder){

            final PRDetailAdapter.BodyViewHolder holder1 = (PRDetailAdapter.BodyViewHolder) holder;

            PRDetail dataSource = list.get(position);


            holder1.tv_warehousing_no.setText("编号: " + dataSource.getProduct_ID());
            holder1.tv_need.setText("需求: " + dataSource.getSc_shuliang() + dataSource.getFroms());

            if ("0".equals(dataSource.getProduct_pid())){
                holder1.tv_serial_no.setVisibility(View.GONE);
            }else {
                holder1.tv_serial_no.setText("批号: " + dataSource.getProduct_pid());
            }

            holder1.tv_real_out.setText("实出: " + dataSource.getSl());

            holder1.tv_location.setText("工位: "+ dataSource.getGongwei());



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

        private TextView tv_warehousing_no;
        private TextView tv_serial_no;
        private TextView tv_need;
        private TextView tv_real_out;

        private TextView tv_location;




        public  BodyViewHolder(View itemView){
            super(itemView);

            tv_warehousing_no = (TextView) itemView.findViewById(R.id.tv_warehousing_no);

            tv_serial_no = (TextView) itemView.findViewById(R.id.tv_serial_no);

            tv_need = (TextView) itemView.findViewById(R.id.tv_need);
            tv_real_out = (TextView) itemView.findViewById(R.id.tv_real_out);

            tv_location = (TextView) itemView.findViewById(R.id.tv_location);

        }
    }



}
