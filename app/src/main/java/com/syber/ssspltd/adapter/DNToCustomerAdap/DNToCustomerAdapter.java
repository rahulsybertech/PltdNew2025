package com.syber.ssspltd.adapter.DNToCustomerAdap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.response.DNToCustomerResponse.DebitNoteToCustomerReportResult;


import java.util.List;

public class DNToCustomerAdapter extends RecyclerView.Adapter<DNToCustomerAdapter.MyViewHolder>{
    private Context mContext;
    private List<DebitNoteToCustomerReportResult> DebitNoteToCustomerDetails;
    boolean setTrue=false;

    public DNToCustomerAdapter(Context mContext, List<DebitNoteToCustomerReportResult> detailList) {
        this.mContext = mContext;
        this.DebitNoteToCustomerDetails = detailList;
    }

    @Override
    public DNToCustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dn_to_customer_recyler, parent, false);
        return new DNToCustomerAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DNToCustomerAdapter.MyViewHolder holder, final int position) {

        final DebitNoteToCustomerReportResult datum = DebitNoteToCustomerDetails.get(position);
        Log.e("data", String.valueOf(DebitNoteToCustomerDetails.get(position)));
        holder.invoiceNo_dnCustomer.setText(datum.getBillNo());
        holder.bill_refNo_dnCustomer.setText(datum.getSaleBillNo());
        holder.date_dnCustomer.setText(datum.getDate());
        holder.supplier_dnCustomer.setText(datum.getSupplierName());
        holder.invoice_datednCustomer.setText(datum.getSaleBillDate());
        holder.netAmt.setText(datum.getNetAmt());
        holder.invoiceNo_dnCustomer.setOnClickListener(v -> {
            if (!datum.getPDFPath().equals(""))
            {
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getPDFPath()));
            }
            else {
                Toast.makeText(mContext, "PDF Not Available", Toast.LENGTH_SHORT).show();
            }
        });

        if (datum.getPDFPath().equals(""))
        {
            holder.invoiceNo_dnCustomer.setTextColor(mContext.getResources().getColor(R.color.solid_gray));

        }
        else
        {
            holder.invoiceNo_dnCustomer.setTextColor(mContext.getResources().getColor(R.color.light_red));

        }

        holder.plusClick.setOnClickListener(v -> {
            if (setTrue==false) {
                holder.dncustomer_itemRecyler.setVisibility(View.VISIBLE);
                holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_expand_less_24));
                setTrue = true;
            }
            else if (setTrue==true)
            {
                holder.dncustomer_itemRecyler.setVisibility(View.GONE);
                holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));
                setTrue = false;
            }


        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.dncustomer_itemRecyler.setLayoutManager(linearLayoutManager);
        DNToCustomerItemAdapter finanacialYearListAdapter = new DNToCustomerItemAdapter(mContext,datum.getItemsDetailsData());
        holder.dncustomer_itemRecyler.setAdapter(finanacialYearListAdapter);



    }


    @Override
    public int getItemCount() {
        return DebitNoteToCustomerDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView invoiceNo_dnCustomer,bill_refNo_dnCustomer,date_dnCustomer,invoice_datednCustomer,supplier_dnCustomer;
        RecyclerView dncustomer_itemRecyler;
        ImageView plusClick;
        TextView netAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            invoiceNo_dnCustomer = itemView.findViewById(R.id.invoiceNo_dnCustomer);
            bill_refNo_dnCustomer = itemView.findViewById(R.id.bill_refNo_dnCustomer);
            date_dnCustomer = itemView.findViewById(R.id.date_dnCustomer);
            invoice_datednCustomer = itemView.findViewById(R.id.invoice_datednCustomer);
            supplier_dnCustomer = itemView.findViewById(R.id.supplier_dnCustomer);
            dncustomer_itemRecyler = itemView.findViewById(R.id.dncustomer_itemRecyler);
            plusClick = itemView.findViewById(R.id.plusClick);
            netAmt = itemView.findViewById(R.id.netAmt);
        }
    }
}
