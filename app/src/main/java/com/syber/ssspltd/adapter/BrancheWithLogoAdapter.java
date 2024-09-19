package com.syber.ssspltd.adapter;

import static com.syber.ssspltd.activitys.Const.BRANDNAME;

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
import com.syber.ssspltd.activitys.BrandListActivity;
import com.syber.ssspltd.response.BranchesResponse.BranchesResult;
import com.syber.ssspltd.response.BranchesResponse.BrandLogoList;

import java.util.ArrayList;
import java.util.List;

public class BrancheWithLogoAdapter extends RecyclerView.Adapter<BrancheWithLogoAdapter.MyViewHolder> {

    private Context mContext;
    private List<BranchesResult> branchesDetails;
    private String brandName;

    public BrancheWithLogoAdapter(Context mContext, List<BranchesResult> detailList,String brand_name) {
        this.mContext = mContext;
        this.branchesDetails = detailList;
        this.brandName=brand_name;
    }

    @Override
    public BrancheWithLogoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branches_list_with_logo, parent, false);
        return new BrancheWithLogoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BrancheWithLogoAdapter.MyViewHolder holder, final int position) {

        final BranchesResult datum = branchesDetails.get(position);
        holder.branchName.setText(datum.getBranchName());
        try {
            Glide
                    .with(mContext)
                    .load(!datum.getBranchImages().isEmpty()?datum.getBranchImages():"abc.png")
                    .placeholder(R.drawable.sss_logo)
                            .into((holder.branchImg));
//            Picasso.with(mContext)
//                    .load(!datum.getBranchImages().isEmpty()?datum.getBranchImages():"abc.png")
//                    .priority(Picasso.Priority.HIGH)
//                    .placeholder(R.drawable.sss_logo)
//                    .into(holder.branchImg ,new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                   public void onError() {
//                  }
//            });
        }
        catch (Exception e)
        {

        }
        SharedPref.init(mContext);
        BrandLogoList brandLogos;
        List<BrandLogoList> brandLogoList = new ArrayList<>();
        if (datum.getBrandDetail().size()<4) {
            for (int i = 0; i < datum.getBrandDetail().size(); i++) {
                String id = datum.getBrandDetail().get(i).getID();
                String img = datum.getBrandDetail().get(i).getBrandImage();
                brandLogos = new BrandLogoList(id, img);
                brandLogoList.add(brandLogos);
            }
        }else {
            for (int i = 0; i < 4; i++) {
                String id = datum.getBrandDetail().get(i).getID();
                String img = datum.getBrandDetail().get(i).getBrandImage();
                brandLogos = new BrandLogoList(id, img);
                brandLogoList.add(brandLogos);
            }
        }
        if (datum.getBrandDetail().size()>=0) {
            holder.itemView.setOnClickListener(v -> {
                Log.e("logoSize",brandLogoList.size()+"");
                mContext.startActivity(new Intent(mContext, BrandListActivity.class)
                        .putExtra("branch_id", datum.getID())
                        .putExtra("branch_name", datum.getBranchName())
                        .putExtra(BRANDNAME,datum.getBranchName())
                );
            });
        }
            BrandLogoAdapter brandLogoAdapter = new BrandLogoAdapter(mContext,brandLogoList,datum.getID(),datum.getBranchName());
            holder.recyclerView.setAdapter(brandLogoAdapter);

        }



    @Override
    public int getItemCount() {
        return branchesDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView branchName;
        ImageView branchImg;
        RecyclerView recyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            branchName = itemView.findViewById(R.id.branchname);
            branchImg = itemView.findViewById(R.id.branchImg);
            recyclerView = itemView.findViewById(R.id.bran_logo_recycler);
//            itemView.setOnClickListener(v -> {
//                mContext.startActivity(new Intent(mContext, BrandListActivity.class)
//                        .putExtra("branch_id",branchesDetails.get(getAbsoluteAdapterPosition()).getID())
//                        .putExtra("branch_name",branchesDetails.get(getAbsoluteAdapterPosition()).getBranchName())
//                );
//
//            });
        }
    }
}
