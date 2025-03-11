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
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;
import com.syber.ssspltd.model.booking.branchlist.Account;
import com.syber.ssspltd.response.SalepartyModel;

import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {

    private Activity mContext;
    private List<Account> data;
    private OnItemClickListener onItemClickListener; // Click listener


    public AccountListAdapter(Activity mContext, ArrayList<Account> data,OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AccountListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new AccountListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AccountListAdapter.MyViewHolder holder, final int position) {

        final Account product;
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
        void onItemClick(Account account);
    }



}

