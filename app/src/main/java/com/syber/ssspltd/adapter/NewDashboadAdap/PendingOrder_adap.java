package com.syber.ssspltd.adapter.NewDashboadAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.NewDashboadRespo.PendingOrder.PendingOrderDetail;

import java.util.List;

public class PendingOrder_adap extends RecyclerView.Adapter<PendingOrder_adap.MyViewHolder> {


    private Context mContext;
    private List<PendingOrderDetail> detailList;

    public PendingOrder_adap(Context mContext, List<PendingOrderDetail> detailList) {
        this.mContext = mContext;
        this.detailList = detailList;
    }

    @Override
    public PendingOrder_adap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboad_recy, parent, false);
        return new PendingOrder_adap.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PendingOrder_adap.MyViewHolder holder, final int position) {

        final PendingOrderDetail datum = detailList.get(position);
        holder.comCode.setText(datum.getCompanyCode());
        holder.amount.setText(datum.getAmount());
        if (datum.getAmount().contains("Dr")){
            holder.amount.setTextColor(ContextCompat.getColor(mContext,R.color.red));
        }else if (datum.getAmount().contains("Cr")){
            holder.amount.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        }


    }


    @Override
    public int getItemCount() {
        return detailList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView comCode,amount;


        public MyViewHolder(View itemView) {
            super(itemView);

            comCode = itemView.findViewById(R.id.comCode);
            amount = itemView.findViewById(R.id.amount);

        }
    }
}
