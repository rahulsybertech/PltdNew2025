package com.syber.ssspltd.adapter.DebitNoteAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.DebitNoteResponse.ItemsDetailsDatum;


import java.util.List;

public class DebitNoteItemAdapter extends RecyclerView.Adapter<DebitNoteItemAdapter.MyViewHolder>{
    private Context mContext;
    private List<ItemsDetailsDatum> debitNoteDetails;

    public DebitNoteItemAdapter(Context mContext, List<ItemsDetailsDatum> detailList) {
        this.mContext = mContext;
        this.debitNoteDetails = detailList;
    }

    @Override
    public DebitNoteItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.debit_note_item, parent, false);
        return new DebitNoteItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DebitNoteItemAdapter.MyViewHolder holder, final int position) {

        final ItemsDetailsDatum datum = debitNoteDetails.get(position);
        holder.item_debit.setText(datum.getItem());
        holder.qty_debit.setText(datum.getQty());
        holder.netAmt_debit.setText(datum.getNetAmt());

    }


    @Override
    public int getItemCount() {
        return debitNoteDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_debit,qty_debit,netAmt_debit;
        RecyclerView ceateNote_itemRecyler;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_debit = itemView.findViewById(R.id.item_debit);
            qty_debit = itemView.findViewById(R.id.qty_debit);
            netAmt_debit = itemView.findViewById(R.id.netAmt_debit);
        }
    }
}
