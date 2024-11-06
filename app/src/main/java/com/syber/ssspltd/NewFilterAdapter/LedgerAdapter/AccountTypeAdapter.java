package com.syber.ssspltd.NewFilterAdapter.LedgerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.NewFilterResponse.AccountType;
import com.syber.ssspltd.NewFilterResponse.EntryType;
import com.syber.ssspltd.NewFilterResponse.FilterType;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FilterCallback;
import com.syber.ssspltd.activitys.LedgerActivity;

import java.util.List;
import java.util.stream.Collectors;

public class AccountTypeAdapter extends RecyclerView.Adapter<AccountTypeAdapter.MyViewHolder> {
    private Context mContext;
    private List<AccountType> filterListDetails;
    private FilterCallback filterCallback;

    public AccountTypeAdapter(Context mContext, List<AccountType> detailList, FilterCallback filterCallback) {
        this.mContext = mContext;
        this.filterListDetails = detailList;
        this.filterCallback = filterCallback;
    }

    @Override
    public AccountTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recyler, parent, false);
        return new AccountTypeAdapter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(AccountTypeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final AccountType datum = filterListDetails.get(position);
        holder.filter.setText(datum.getAccountTypeName().equals("") ? "Without Branch" : datum.getAccountTypeName());

        if (datum.isSelected()) {
            holder.filter.setChecked(true);
        } else {
            holder.filter.setChecked(false);
        }
        holder.filter.setTag(position);

        holder.filter.setOnClickListener(v -> {
            Integer pos = (Integer) holder.filter.getTag();
            AccountType productDetails1 = filterListDetails.get(pos);
            if (productDetails1.isSelected()) {
                productDetails1.setSelected(false);
                holder.filter.setChecked(false);
                holder.filter.setTag(position);
                List<AccountType> sis = filterListDetails.stream().filter(AccountType :: isSelected).collect(Collectors.toList());
                LedgerActivity.count_account.setText(sis.size()+"");
               /* if(sis.isEmpty()) {
                    LedgerActivity.count_account.setVisibility(View.GONE);
                } else {
                    LedgerActivity.count_account.setVisibility(View.VISIBLE);
                }*/

            } else {
                productDetails1.setSelected(true);
                holder.filter.setChecked(true);
                holder.filter.setTag(position);
                List<AccountType> sis = filterListDetails.stream().filter(AccountType :: isSelected).collect(Collectors.toList());
                LedgerActivity.count_account.setText(sis.size()+"");
                /*if(sis.isEmpty()) {
                    LedgerActivity.count_account.setVisibility(View.GONE);
                } else {
                    LedgerActivity.count_account.setVisibility(View.VISIBLE);
                }*/
            }
            filterCallback.filterChanged(FilterType.ACCOUNT);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return filterListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox filter;

        public MyViewHolder(View itemView) {
            super(itemView);

            filter = itemView.findViewById(R.id.filter);

        }
    }
}
