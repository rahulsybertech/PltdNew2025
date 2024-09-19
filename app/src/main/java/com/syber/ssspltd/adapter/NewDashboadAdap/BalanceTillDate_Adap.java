package com.syber.ssspltd.adapter.NewDashboadAdap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.AccountAdapter;
import com.syber.ssspltd.response.AccountResponse.BranchEmployeesResult;
import com.syber.ssspltd.response.NewDashboadRespo.PendingOrderDetail;

import java.util.List;

public class BalanceTillDate_Adap extends RecyclerView.Adapter<BalanceTillDate_Adap.MyViewHolder>{


    private Context mContext;
    private List<PendingOrderDetail> detailList;

    public BalanceTillDate_Adap(Context mContext, List<PendingOrderDetail> detailList) {
        this.mContext = mContext;
        this.detailList = detailList;
    }

    @Override
    public BalanceTillDate_Adap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboad_recy, parent, false);
        return new BalanceTillDate_Adap.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BalanceTillDate_Adap.MyViewHolder holder, final int position) {

        final PendingOrderDetail datum = detailList.get(position);
        holder.comCode.setText(datum.getCompanyCode());
        holder.amount.setText(datum.getAmount());
        if (datum.getAmount().contains("Dr")){
            holder.amount.setTextColor(ContextCompat.getColor(mContext,R.color.red));
        }else if (datum.getAmount().contains("Cr")){
            holder.amount.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        }


    }


    @Override
    public int getItemCount() {
        return detailList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView comCode,amount;


        public MyViewHolder(View itemView) {
            super(itemView);

            comCode = itemView.findViewById(R.id.comCode);
            amount = itemView.findViewById(R.id.amount);

        }
    }
}
