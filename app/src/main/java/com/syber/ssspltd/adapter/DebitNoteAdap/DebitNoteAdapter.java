package com.syber.ssspltd.adapter.DebitNoteAdap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.syber.ssspltd.response.DebitNoteResponse.DebitNoteReportResult;

import java.util.List;

public class DebitNoteAdapter extends RecyclerView.Adapter<DebitNoteAdapter.MyViewHolder>{

    private Context mContext;
    private List<DebitNoteReportResult> DebitNote;
    boolean setTrue=false;

    public DebitNoteAdapter(Context mContext, List<DebitNoteReportResult> detailList) {
        this.mContext = mContext;
        this.DebitNote = detailList;
    }

    @Override
    public DebitNoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.debit_note_recyle, parent, false);
        return new DebitNoteAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DebitNoteAdapter.MyViewHolder holder, final int position) {

        final DebitNoteReportResult datum = DebitNote.get(position);
        holder.invoiceNo_debit.setText(datum.getBillNo());
        holder.billRefNo_debit.setText(datum.getPurchaseBillNo());
        holder.date_debit.setText(datum.getDate());
        holder.invoice_datedebit.setText(datum.getPurchaseBillDate());
        holder.customName_debitNot.setText(datum.getCustomerName());
        holder.netAmt_debit.setText(datum.getNetAmt());

        holder.invoiceNo_debit.setOnClickListener(v -> {
            if (!datum.getPDFPath().equals("")){
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getPDFPath()));
            }else {
                Toast.makeText(mContext, "PDF Not Available", Toast.LENGTH_SHORT).show();
            }

        });

        holder.plusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setTrue==false) {
                    holder.debitNote_itemRecyler.setVisibility(View.VISIBLE);
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_expand_less_24));
                    setTrue = true;
                }
                else if (setTrue==true)
                {
                    holder.debitNote_itemRecyler.setVisibility(View.GONE);
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));
                    setTrue = false;
                }


            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.debitNote_itemRecyler.setLayoutManager(linearLayoutManager);
        DebitNoteItemAdapter finanacialYearListAdapter = new DebitNoteItemAdapter(mContext,datum.getItemsDetailsData());
        holder.debitNote_itemRecyler.setAdapter(finanacialYearListAdapter);


    }


    @Override
    public int getItemCount() {
        return DebitNote.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView invoiceNo_debit,billRefNo_debit,date_debit,invoice_datedebit,customName_debitNot,netAmt_debit;
        RecyclerView debitNote_itemRecyler;
        ImageView plusClick;

        public MyViewHolder(View itemView) {
            super(itemView);

            invoiceNo_debit = itemView.findViewById(R.id.invoiceNo_debit);
            billRefNo_debit = itemView.findViewById(R.id.billRefNo_debit);
            date_debit = itemView.findViewById(R.id.date_debit);
            invoice_datedebit = itemView.findViewById(R.id.invoice_datedebit);
            customName_debitNot = itemView.findViewById(R.id.customName_debitNot);
            netAmt_debit = itemView.findViewById(R.id.netAmt_debit);
            plusClick = itemView.findViewById(R.id.plusClick);
            debitNote_itemRecyler = itemView.findViewById(R.id.debitNote_itemRecyler);
        }
    }
}
