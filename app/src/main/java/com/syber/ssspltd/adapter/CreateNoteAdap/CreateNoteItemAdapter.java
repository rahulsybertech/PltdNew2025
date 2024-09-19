package com.syber.ssspltd.adapter.CreateNoteAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.CreditNoteReportRespo.ItemsDetailsDatum;

import java.util.List;

public class CreateNoteItemAdapter  extends RecyclerView.Adapter<CreateNoteItemAdapter.MyViewHolder>{

    private Context mContext;
    private List<ItemsDetailsDatum> itemsDetailsDetails;

    public CreateNoteItemAdapter(Context mContext, List<ItemsDetailsDatum> detailList) {
        this.mContext = mContext;
        this.itemsDetailsDetails = detailList;
    }

    @Override
    public CreateNoteItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_note_item, parent, false);
        return new CreateNoteItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CreateNoteItemAdapter.MyViewHolder holder, final int position) {

        final ItemsDetailsDatum datum = itemsDetailsDetails.get(position);
        holder.createNote_item.setText(datum.getItem());
        holder.createNote_qty.setText(datum.getQty());
        holder.createNote_netAmt.setText(datum.getNetAmt());



    }


    @Override
    public int getItemCount() {
        return itemsDetailsDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView createNote_item,createNote_qty,createNote_netAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            createNote_item = itemView.findViewById(R.id.createNote_item);
            createNote_qty = itemView.findViewById(R.id.createNote_qty);
            createNote_netAmt = itemView.findViewById(R.id.createNote_netAmt);
        }
    }
}
