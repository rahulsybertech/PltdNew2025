package com.syber.ssspltd.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.syber.ssspltd.R;
import com.syber.ssspltd.response.BranchBillingResponse.BranchEmployeesResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
public class BranchBillingAdapter extends RecyclerView.Adapter<BranchBillingAdapter.MyViewHolder>{



    private Context mContext;
    private List<BranchEmployeesResult> branchesDetails;

    public BranchBillingAdapter(Context mContext, List<BranchEmployeesResult> detailList) {
        this.mContext = mContext;
        this.branchesDetails = detailList;
    }

    @Override
    public BranchBillingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_billing, parent, false);
        return new BranchBillingAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BranchBillingAdapter.MyViewHolder holder, final int position) {

        final BranchEmployeesResult datum = branchesDetails.get(position);
        holder.BillingName.setText(datum.getPersonName());
        holder.BillingMob.setText(datum.getMobileNo());

        holder.billingConcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + holder.BillingMob.getText().toString()));
                    mContext.startActivity(intent);

            }
        });
        holder.BillingMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + holder.BillingMob.getText().toString()));
                    mContext.startActivity(intent);


            }
        });
        String path ="";
        if (datum.getImagePath().equalsIgnoreCase("")){
            path ="http://ancd.png";
        }else {

            path = datum.getImagePath();
        }

        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.profile)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.Img_show.setVisibility(View.VISIBLE);
                        holder.Img_show.setImageResource(R.drawable.profile);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.Img_show.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.Img_show);
//
//        Picasso.with(mContext)
//                .load(path)
//                .priority(Picasso.Priority.HIGH)
//                .resize(500,500)
//                .placeholder(R.drawable.ic_supermarket)
//                //.memoryPolicy(MemoryPolicy.)
//                // .networkPolicy(NetworkPolicy.OFFLINE)
//                .into(holder.Img_show, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.Img_show.setVisibility(View.VISIBLE);
//
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                        /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
//                        holder.Img_show.setVisibility(View.VISIBLE);
//                        holder.Img_show.setImageResource(R.drawable.ic_user);
//
//                    }
//                });


    }


    @Override
    public int getItemCount() {
        return branchesDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView BillingName,BillingMob;
        ImageView Img_show,billingConcat;

        public MyViewHolder(View itemView) {
            super(itemView);

            BillingName = itemView.findViewById(R.id.BillingName);
            BillingMob = itemView.findViewById(R.id.BillingMob);
            billingConcat = itemView.findViewById(R.id.billingConcat);
            Img_show = itemView.findViewById(R.id.Img_show);
        }
    }

}
