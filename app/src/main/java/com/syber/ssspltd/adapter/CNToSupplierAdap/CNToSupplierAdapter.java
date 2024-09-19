package com.syber.ssspltd.adapter.CNToSupplierAdap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.response.CNToSupplierResponse.CreditNoteToSupplierReportResult;

import java.util.List;

public class CNToSupplierAdapter  extends RecyclerView.Adapter<CNToSupplierAdapter.MyViewHolder>{
    private Context mContext;
    private List<CreditNoteToSupplierReportResult> CreditNoteToSupplierDetails;
    boolean setTrue=false;

    public CNToSupplierAdapter(Context mContext, List<CreditNoteToSupplierReportResult> detailList) {
        this.mContext = mContext;
        this.CreditNoteToSupplierDetails = detailList;
    }

    @Override
    public CNToSupplierAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cn_to_supplier_recyler, parent, false);
        return new CNToSupplierAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CNToSupplierAdapter.MyViewHolder holder, final int position) {

        final CreditNoteToSupplierReportResult datum = CreditNoteToSupplierDetails.get(position);
        holder.invoiceNo_CNsupplier.setText(datum.getBillNo());
        holder.bill_refNo_cnSupp.setText(datum.getPurchaseBillNo());
        holder.date_CNSupp.setText(datum.getDate());
        holder.invoice_dateCNSupp.setText(datum.getPurchaseBillDate());
        holder.custom_cnSupp.setText(datum.getCustomerName());
        if (datum.getItemsDetailsData().size()<0){
            holder.plusClick.setVisibility(View.GONE);
            holder.cnSupp_itemRecyler.setVisibility(View.GONE);
            holder.llItemDetails.setVisibility(View.GONE);
        }
        holder.invoiceNo_CNsupplier.setOnClickListener(v->{
            if (!datum.getPDFPath().equals(""))
            {
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getPDFPath()));
            }else {
                Toast.makeText(mContext, "PDF Not Available", Toast.LENGTH_SHORT).show();
            }
        });
        if (datum.getPDFPath().equals(""))
        {
            holder.invoiceNo_CNsupplier.setTextColor(mContext.getResources().getColor(R.color.solid_gray));

        }
        else
        {
            holder.invoiceNo_CNsupplier.setTextColor(mContext.getResources().getColor(R.color.light_red));

        }
        holder.plusClick.setOnClickListener(v -> {
            if (setTrue==false) {
                holder.cnSupp_itemRecyler.setVisibility(View.VISIBLE);
                holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_expand_less_24));
                setTrue = true;
            }
            else if (setTrue==true)
            {
                holder.cnSupp_itemRecyler.setVisibility(View.GONE);
                holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));
                setTrue = false;
            }



        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.cnSupp_itemRecyler.setLayoutManager(linearLayoutManager);
        CNToSupplierItemAdapter finanacialYearListAdapter = new CNToSupplierItemAdapter(mContext,datum.getItemsDetailsData());
        holder.cnSupp_itemRecyler.setAdapter(finanacialYearListAdapter);



    }


    @Override
    public int getItemCount() {
        return CreditNoteToSupplierDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView invoiceNo_CNsupplier,bill_refNo_cnSupp,date_CNSupp,invoice_dateCNSupp,custom_cnSupp;
        RecyclerView cnSupp_itemRecyler;
        ImageView plusClick;
        LinearLayout llItemDetails;

        public MyViewHolder(View itemView) {
            super(itemView);

            invoiceNo_CNsupplier = itemView.findViewById(R.id.invoiceNo_CNsupplier);
            bill_refNo_cnSupp = itemView.findViewById(R.id.bill_refNo_cnSupp);
            date_CNSupp = itemView.findViewById(R.id.date_CNSupp);
            llItemDetails = itemView.findViewById(R.id.ll_item_details);
            invoice_dateCNSupp = itemView.findViewById(R.id.invoice_dateCNSupp);
            custom_cnSupp = itemView.findViewById(R.id.custom_cnSupp);
            plusClick = itemView.findViewById(R.id.plusClick);
            cnSupp_itemRecyler = itemView.findViewById(R.id.cnSupp_itemRecyler);
        }
    }
}
