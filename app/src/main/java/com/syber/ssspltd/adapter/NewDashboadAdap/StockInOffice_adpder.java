package com.syber.ssspltd.adapter.NewDashboadAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.NewDashboadRespo.PendingOrderDetail;
import com.syber.ssspltd.response.NewDashboadRespo.StockPending.StockInOfficePojo;
import com.syber.ssspltd.response.NewDashboadRespo.StockPending.StockinOfficeDetail;

import java.util.List;

public class StockInOffice_adpder extends RecyclerView.Adapter<StockInOffice_adpder.MyViewHolder>{



    private Context mContext;
    private List<StockinOfficeDetail> detailList;

    public StockInOffice_adpder(Context mContext, List<StockinOfficeDetail> detailList) {
        this.mContext = mContext;
        this.detailList = detailList;
    }

    @Override
    public StockInOffice_adpder.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboad_recy, parent, false);
        return new StockInOffice_adpder.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockInOffice_adpder.MyViewHolder holder, final int position) {

        final StockinOfficeDetail datum = detailList.get(position);
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
