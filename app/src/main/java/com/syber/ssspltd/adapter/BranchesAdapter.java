package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BranchItemDetailsActivity;
import com.syber.ssspltd.response.BranchesResponse.BranchesResult;

import java.util.List;

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.MyViewHolder> {



    private Context mContext;
    private List<BranchesResult> branchesDetails;

    public BranchesAdapter(Context mContext, List<BranchesResult> detailList) {
        this.mContext = mContext;
        this.branchesDetails = detailList;
    }

    @Override
    public BranchesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branches_list, parent, false);
        return new BranchesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BranchesAdapter.MyViewHolder holder, final int position) {

        final BranchesResult datum = branchesDetails.get(position);
        holder.branchName.setText(datum.getBranchName());
        try {

            Glide
                    .with(mContext)
                    .load(!datum.getBranchImages().isEmpty()?datum.getBranchImages():"abc.png")
                    .placeholder(R.drawable.sss_logo)
                    .into((holder.branchImg));

        }catch (Exception e)
        {

        }
        SharedPref.init(mContext);
        Log.e("url",datum.getBranchImages());

//        if (datum.getDebitAmt().equals("")){
//            holder.dr_amt.setVisibility(View.GONE);
//            holder.dr_txt.setVisibility(View.GONE);
//        }else {
//            holder.dr_amt.setVisibility(View.VISIBLE);
//            holder.dr_txt.setVisibility(View.VISIBLE);
//            holder.dr_amt.setText(datum.getDebitAmt());
//        }
//
//        holder.bal_name.setText(tt);

    }


    @Override
    public int getItemCount() {
        return branchesDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView branchName;
        ImageView branchImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            branchName = itemView.findViewById(R.id.branchname);
            branchImg = itemView.findViewById(R.id.branchImg);
            itemView.setOnClickListener(v -> {
                mContext.startActivity(new Intent(mContext, BranchItemDetailsActivity.class)
                .putExtra("branchName",branchesDetails.get(getAdapterPosition()).getBranchName()));
                SharedPref.write(SharedPref.D_ID,branchesDetails.get(getAdapterPosition()).getID());
                Log.e("log",branchesDetails.get(getAdapterPosition()).getID());

            });

        }
    }
}
