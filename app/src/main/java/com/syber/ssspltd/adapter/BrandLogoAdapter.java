package com.syber.ssspltd.adapter;

import static com.syber.ssspltd.activitys.Const.BRANDNAME;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BrandListActivity;
import com.syber.ssspltd.response.BranchesResponse.BrandLogoList;

import java.util.List;

public class BrandLogoAdapter extends RecyclerView.Adapter<BrandLogoAdapter.MyViewHolder> {

    private Context mContext;
    private List<BrandLogoList> list;
    private String branchId;
    private String name;

    public BrandLogoAdapter(Context mContext, List<BrandLogoList> list,String branchId,String name) {
        this.mContext = mContext;
        this.list = list;
        this.branchId = branchId;
        this.name = name;
    }

    @NonNull
    @Override
    public BrandLogoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_logo, parent, false);
        return new BrandLogoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandLogoAdapter.MyViewHolder holder, final int position) {
        SharedPref.init(mContext);

        final BrandLogoList datum = list.get(position);
        try{
            Glide
                    .with(mContext)
                    .load(!datum.getImage().isEmpty()?datum.getImage():"abc.png")
                    .placeholder(R.drawable.sss_logo)
                    .into((holder.brandImg));
//        Picasso.with(mContext)
//                .load(!datum.getImage().isEmpty()?datum.getImage():"abc.png")
//                .priority(Picasso.Priority.HIGH)
//                .placeholder(R.drawable.sss_logo)
//                .into(holder.brandImg, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError() {
//                    }
//                });

        }catch (Exception e){

        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView brandImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            brandImg = itemView.findViewById(R.id.brand_img);
            itemView.setOnClickListener(v -> {
                mContext.startActivity(new Intent(mContext, BrandListActivity.class)
                .putExtra("branch_id",branchId)
                        .putExtra(BRANDNAME,name));
            });
        }
    }
}
