package com.syber.ssspltd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.model.booking.branchlist.Account;
import com.syber.ssspltd.model.booking.branchlist.NickNameList;

import java.util.ArrayList;
import java.util.List;

public class NickNameListAdapter extends RecyclerView.Adapter<NickNameListAdapter.MyViewHolder> {

    private Activity mContext;
    private List<NickNameList> data;
    private NickNameListAdapter.OnItemClickListener onItemClickListener; // Click listener


    public NickNameListAdapter(Activity mContext, ArrayList<NickNameList> data, NickNameListAdapter.OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public NickNameListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new NickNameListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NickNameListAdapter.MyViewHolder holder, final int position) {

        final NickNameList product;
        product = data.get(position);
        holder.name.setText(product.getName());
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(product);
            }
        });

      /*  if (position % 2 == 0) {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.eee));
        }*/
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
        void onItemClick(NickNameList account);
    }



}

