package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.syber.ssspltd.R;
import com.syber.ssspltd.response.BranchMarketerResponse.BranchEmployeesResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BranchMarketreAdapter extends RecyclerView.Adapter<BranchMarketreAdapter.MyViewHolder> {

    private Context mContext;
    private List<BranchEmployeesResult> branchEmployeesDetails;

    public BranchMarketreAdapter(Context mContext, List<com.syber.ssspltd.response.BranchMarketerResponse.BranchEmployeesResult> detailList) {
        this.mContext = mContext;
        this.branchEmployeesDetails = detailList;
    }

    @Override
    public BranchMarketreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_marketer, parent, false);
        return new BranchMarketreAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BranchMarketreAdapter.MyViewHolder holder, final int position) {

        final com.syber.ssspltd.response.BranchMarketerResponse.BranchEmployeesResult datum = branchEmployeesDetails.get(position);
        holder.marketerName.setText(datum.getPersonName());
        holder.marketerMob.setText(datum.getMobileNo());
        holder.marketerConcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + holder.marketerMob.getText().toString()));
                Intent chooseIntent=Intent.createChooser(intent,"");
                mContext.startActivity(chooseIntent);

            }
        });
        holder.marketerMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + holder.marketerMob.getText().toString()));
                Intent chooseIntent=Intent.createChooser(intent,"");
                mContext.startActivity(chooseIntent);
               // if (intent.resolveActivity(mContext.getPackageManager()) != null) {

              //  }

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
//                         /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
//                          holder.Img_show.setVisibility(View.VISIBLE);
//                        holder.Img_show.setImageResource(R.drawable.ic_user);
//
//                    }
//                });



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
        return branchEmployeesDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView marketerName,marketerMob;
        LinearLayout ll_sup,ll_sale;
        ImageView call;
        TextView dr_amt,bal_name;
        ImageView marketerConcat;
        CircleImageView Img_show;

        public MyViewHolder(View itemView) {
            super(itemView);

            marketerName = itemView.findViewById(R.id.marketerName);
            marketerMob = itemView.findViewById(R.id.marketerMob);
            marketerConcat = itemView.findViewById(R.id.marketerConcat);
            Img_show = itemView.findViewById(R.id.Img_show);
        }
    }
}
