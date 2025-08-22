package com.syber.ssspltd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.model.booking.branchlist.Account;
import com.syber.ssspltd.model.booking.branchlist.NickNameList;

import java.util.ArrayList;
import java.util.List;

public class MainPartySubPartyAdapter extends RecyclerView.Adapter<MainPartySubPartyAdapter.MyViewHolder> {

    private Activity mContext;
    private List<Account> data;
    private MainPartySubPartyAdapter.OnItemClickListener onItemClickListener; // Click listener


    public MainPartySubPartyAdapter(Activity mContext, ArrayList<Account> data, MainPartySubPartyAdapter.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MainPartySubPartyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new MainPartySubPartyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainPartySubPartyAdapter.MyViewHolder holder, final int position) {

        final Account product;
        product = data.get(position);
        holder.name.setText(product.getName());
        if(product.getPartyType().equals("subParty")){

        }
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product);
            }
        });

        if (product.getPartyType().equals("subParty")) {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView emp, name;
        LinearLayout ll;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ll = itemView.findViewById(R.id.ll);
            cardView = itemView.findViewById(R.id.product_card);
            /*   itemView.setOnClickListener(v -> ((SupplierOrderFormActivity) mContext).setSaleParty(data.get(getAdapterPosition())));
             */
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Account account);
    }



}

