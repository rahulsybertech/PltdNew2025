package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Responses.customer.BlackListedName;

import java.util.List;


public class CustomerAdptr extends RecyclerView.Adapter<CustomerAdptr.MyViewHolder> {
    private Context mContext;
    private List<BlackListedName> detailList;


    public CustomerAdptr(Context mContext, List<BlackListedName> detailList) {
        this.mContext = mContext;
        this.detailList = detailList;
    }

    @Override
    public CustomerAdptr.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list, parent, false);
        return new CustomerAdptr.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomerAdptr.MyViewHolder holder, final int position) {
        final BlackListedName datum = detailList.get(position);
        if (datum.getStation().equalsIgnoreCase("")||datum.getStation().equalsIgnoreCase("null")){
            holder.llStation.setVisibility(View.GONE);
        }else {
            holder.llStation.setVisibility(View.VISIBLE);
            holder.station.setText(datum.getStation());
        }
        holder.firmName.setText(datum.getName().equals("")?"NA":datum.getName());
        holder.ownerName.setText(datum.getOwnerName().equals("")?"NA":datum.getOwnerName());
        holder.gstin.setText(datum.getGSTNo().equals("")?"NA":datum.getGSTNo());
        holder.address.setText(datum.getAddress().equals("")?"NA":datum.getAddress());
        holder.mobile.setText(datum.getMobileNo().equals("")?"NA":datum.getMobileNo());

    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firmName,gstin,ownerName,address,mobile,station;
        LinearLayout llStation;

        public MyViewHolder(View itemView) {
            super(itemView);
            firmName = itemView.findViewById(R.id.firm_name);
            gstin = itemView.findViewById(R.id.gstin);
            ownerName = itemView.findViewById(R.id.owner_name);
            address = itemView.findViewById(R.id.address);
            mobile = itemView.findViewById(R.id.mobile);
            station = itemView.findViewById(R.id.station);
            llStation = itemView.findViewById(R.id.ll_station);


        }
    }

}

