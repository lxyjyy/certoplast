package com.lab.certoplast.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lab.certoplast.R;
import com.lab.certoplast.bean.DataSource;
import com.lab.certoplast.bean.OnItemClickListener;

import java.util.List;

/**
 * Created by lxyjyy on 17/11/7.
 */

public class NineAdapter extends RecyclerView.Adapter {

    private List<DataSource> dataSources;

    public NineAdapter(List<DataSource> dataSources){
        this.dataSources = dataSources;
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe) {

        BodyViewHolder bodyViewHolder = new BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nine_item, parent, false));
        return bodyViewHolder;
    }


    @Override
    public int getItemCount() {
        return dataSources.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof BodyViewHolder){

            final BodyViewHolder holder1 = (BodyViewHolder) holder;

            DataSource dataSource = dataSources.get(position);

            holder1.iv_icon.setImageResource(dataSource.getDrawableId());
            holder1.tv_name.setText(dataSource.getText());


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

        private ImageView iv_icon;
        private TextView tv_name;
       public  BodyViewHolder(View itemView){
           super(itemView);

           iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);

           tv_name = (TextView) itemView.findViewById(R.id.tv_name);

       }
    }


}
