package com.syber.ssspltd.adapter.CreateNoteAdap;

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
import com.syber.ssspltd.response.CreditNoteReportRespo.CreditNoteReportResult;


import java.util.List;

public class CreateNoteAdapter extends RecyclerView.Adapter<CreateNoteAdapter.MyViewHolder>{

    private Context mContext;
    private List<CreditNoteReportResult> CreditNoteDetails;
    boolean setTrue=false;

    public CreateNoteAdapter(Context mContext, List<CreditNoteReportResult> detailList) {
        this.mContext = mContext;
        this.CreditNoteDetails = detailList;
    }

    @Override
    public CreateNoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_note_recyler, parent, false);
        return new CreateNoteAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CreateNoteAdapter.MyViewHolder holder, final int position) {

        final CreditNoteReportResult datum = CreditNoteDetails.get(position);
        holder.inVoiceNO_create.setText(datum.getBillNo());
        holder.bill_refNo_create.setText(datum.getSaleBillNo());
        holder.date_createNote.setText(datum.getDate());
        holder.supplierName_create.setText(datum.getSupplierName());
        holder.invoice_dateCreate.setText(datum.getSaleBillDate());
        holder.netAmt_credit.setText(datum.getNetAmt());

        holder.inVoiceNO_create.setOnClickListener(v -> {
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
                    holder.ceateNote_itemRecyler.setVisibility(View.VISIBLE);
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_expand_less_24));
                    setTrue = true;
                }
                else if (setTrue==true)
                {
                    holder.ceateNote_itemRecyler.setVisibility(View.GONE);
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));
                    setTrue = false;
                }


            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.ceateNote_itemRecyler.setLayoutManager(linearLayoutManager);
        CreateNoteItemAdapter finanacialYearListAdapter = new CreateNoteItemAdapter(mContext,datum.getItemsDetailsData());
        holder.ceateNote_itemRecyler.setAdapter(finanacialYearListAdapter);



    }


    @Override
    public int getItemCount() {
        return CreditNoteDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView inVoiceNO_create,bill_refNo_create,date_createNote,supplierName_create,invoice_dateCreate,netAmt_credit;
        RecyclerView ceateNote_itemRecyler;
        ImageView plusClick;

        public MyViewHolder(View itemView) {
            super(itemView);

            inVoiceNO_create = itemView.findViewById(R.id.inVoiceNO_create);
            bill_refNo_create = itemView.findViewById(R.id.bill_refNo_create);
            date_createNote = itemView.findViewById(R.id.date_createNote);
            supplierName_create = itemView.findViewById(R.id.supplierName_create);
            invoice_dateCreate = itemView.findViewById(R.id.invoice_dateCreate);
            netAmt_credit = itemView.findViewById(R.id.netAmt_credit);
            ceateNote_itemRecyler = itemView.findViewById(R.id.ceateNote_itemRecyler);
            plusClick = itemView.findViewById(R.id.plusClick);
        }
    }
}
