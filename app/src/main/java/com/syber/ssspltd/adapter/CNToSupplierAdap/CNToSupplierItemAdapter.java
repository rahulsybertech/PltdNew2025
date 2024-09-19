package com.syber.ssspltd.adapter.CNToSupplierAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.CNToSupplierResponse.ItemsDetailsDatum;

import java.util.List;

public class CNToSupplierItemAdapter  extends RecyclerView.Adapter<CNToSupplierItemAdapter.MyViewHolder> {
    private Context mContext;
    private List<ItemsDetailsDatum> itemsDetailsDetails;

    public CNToSupplierItemAdapter(Context mContext, List<ItemsDetailsDatum> detailList) {
        this.mContext = mContext;
        this.itemsDetailsDetails = detailList;
    }

    @Override
    public CNToSupplierItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cn_to_supplier_item, parent, false);
        return new CNToSupplierItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CNToSupplierItemAdapter.MyViewHolder holder, final int position) {

        final ItemsDetailsDatum datum = itemsDetailsDetails.get(position);
        holder.cnSupp_item.setText(datum.getItem());
        holder.cnSupp_netAmt.setText(datum.getNetAmt());
        holder.cnSupp_qty.setText(datum.getQty());



    }


    @Override
    public int getItemCount() {
        return itemsDetailsDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cnSupp_item,cnSupp_qty,cnSupp_netAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            cnSupp_item = itemView.findViewById(R.id.cnSupp_item);
            cnSupp_netAmt = itemView.findViewById(R.id.cnSupp_netAmt);
            cnSupp_qty = itemView.findViewById(R.id.cnSupp_qty);

        }
    }
}
