package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.response.StockInOfficeReportRespo.StockInOfficeReportResult;


import java.util.List;

public class StockInOfficeAdapter  extends RecyclerView.Adapter<StockInOfficeAdapter.MyViewHolder>{

    private Context mContext;
    private List<StockInOfficeReportResult> stockInOfficeDetails;

    public StockInOfficeAdapter(Context mContext, List<StockInOfficeReportResult> detailList) {
        this.mContext = mContext;
        this.stockInOfficeDetails = detailList;
    }

    @Override
    public StockInOfficeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_in_office_recyclerview, parent, false);
        return new StockInOfficeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StockInOfficeAdapter.MyViewHolder holder, final int position) {

        final StockInOfficeReportResult datum = stockInOfficeDetails.get(position);
        holder.supplierP_stock.setText(datum.getSupplier());
        holder.subP_stock.setText(datum.getSubParty());
        holder.date_stock.setText(datum.getBillDate());
        holder.puchNo_stock.setText(datum.getPurchaseNo());
        holder.pice_stock.setText(datum.getPcs());
        holder.amt_stock.setText(datum.getPAmount());
        holder.billedSatus.setText("Status : "+datum.getBillStatus());
        holder.billNo_stock.setText(datum.getBillNo());
        holder.Marketer.setText(datum.getmMarketer());
            holder.puchNo_stock.setOnClickListener(v -> {
                if (!datum.getStockPDFPath().equals("")) {
                    mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                            .putExtra("pdfUrl", datum.getStockPDFPath()));

                } else {
                    Toast.makeText(mContext, "PDF File Not Available", Toast.LENGTH_SHORT).show();
                }
            });
    }


    @Override
    public int getItemCount() {
        return stockInOfficeDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView supplierP_stock,subP_stock,date_stock,puchNo_stock,pice_stock,amt_stock,billedSatus,billNo_stock,Marketer;

        public MyViewHolder(View itemView) {
            super(itemView);

            supplierP_stock = itemView.findViewById(R.id.supplierP_stock);
            subP_stock = itemView.findViewById(R.id.subP_stock);
            date_stock = itemView.findViewById(R.id.date_stock);
            puchNo_stock = itemView.findViewById(R.id.puchNo_stock);
            pice_stock = itemView.findViewById(R.id.pice_stock);
            amt_stock = itemView.findViewById(R.id.amt_stock);
            billedSatus = itemView.findViewById(R.id.billedSatus);
            billNo_stock = itemView.findViewById(R.id.billNo_stock);
            Marketer = itemView.findViewById(R.id.marketer_stock);
        }
    }

}
