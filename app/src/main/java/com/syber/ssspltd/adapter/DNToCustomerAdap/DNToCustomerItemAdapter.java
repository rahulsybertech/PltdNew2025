package com.syber.ssspltd.adapter.DNToCustomerAdap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.DNToCustomerResponse.ItemsDetailsDatum;


import java.util.List;

public class DNToCustomerItemAdapter  extends RecyclerView.Adapter<DNToCustomerItemAdapter.MyViewHolder> {
    private Context mContext;
    private List<ItemsDetailsDatum> itemsDetailsDetails;

    public DNToCustomerItemAdapter(Context mContext, List<ItemsDetailsDatum> detailList) {
        this.mContext = mContext;
        this.itemsDetailsDetails = detailList;
    }

    @Override
    public DNToCustomerItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dn_to_customer_item, parent, false);
        return new DNToCustomerItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DNToCustomerItemAdapter.MyViewHolder holder, final int position) {

        final ItemsDetailsDatum datum = itemsDetailsDetails.get(position);
        holder.dnCustomer_item.setText(datum.getItem());
        holder.dnCustome_netAmt.setText(datum.getNetAmt());



    }


    @Override
    public int getItemCount() {
        return itemsDetailsDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dnCustomer_item,dnCustome_netAmt;

        public MyViewHolder(View itemView) {
            super(itemView);

            dnCustomer_item = itemView.findViewById(R.id.dnCustomer_item);
            dnCustome_netAmt = itemView.findViewById(R.id.dnCustome_netAmt);
        }
    }
}
