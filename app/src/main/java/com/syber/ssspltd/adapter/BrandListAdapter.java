package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BrandDetailsActivity;
import com.syber.ssspltd.adapter.brandCategroy.BrandCategroyAdapter;
import com.syber.ssspltd.response.brand.BrandInsertingRequestDatum;

import java.util.List;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.MyViewHolder> {

    private Context mContext;
    private List<BrandInsertingRequestDatum> list;


    public BrandListAdapter(Context mContext, List<BrandInsertingRequestDatum> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public BrandListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_list, parent, false);
        return new BrandListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BrandListAdapter.MyViewHolder holder, final int position) {
        SharedPref.init(mContext);
        final BrandInsertingRequestDatum datum = list.get(position);

        holder.brandName.setText(datum.getBrandName());
        holder.brand_discrtion.setText(datum.getBrandDescription());
       holder.itemView_click.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               mContext.startActivity(new Intent(mContext, BrandDetailsActivity.class)
                       .putExtra("list", datum)
                       .putExtra("brand_name", datum.getBrandName()));

           }
       });
       holder.viewProductImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mContext.startActivity(new Intent(mContext, BrandDetailsActivity.class)
                       .putExtra("list", datum)
                       .putExtra("brand_name", datum.getBrandName()));
           }
       });
        holder.viewProductImg.setPaintFlags(holder.viewProductImg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
       try {

           Glide
                   .with(mContext)
                   .load(!datum.getBrandLogo().isEmpty()?datum.getBrandLogo():"abc.png")
                   .placeholder(R.drawable.sss_logo)
                   .into((holder.brandImg));
//           Picasso.with(mContext)
//                   .load(!datum.getBrandLogo().isEmpty()?datum.getBrandLogo():"abc.png")
//                   .priority(Picasso.Priority.HIGH)
//                   .placeholder(R.drawable.sss_logo)
//                   .into(holder.brandImg, new Callback() {
//                       @Override
//                       public void onSuccess() {
//                       }
//
//                       @Override
//                       public void onError() {
//                       }
//                   });
       }catch (Exception e){

       }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.bandCategory.setLayoutManager(linearLayoutManager);
        BrandCategroyAdapter BrandCategroyAdapter = new BrandCategroyAdapter(mContext,datum.getBrandCategoryA());
        holder.bandCategory.setAdapter(BrandCategroyAdapter);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg;
        TextView brandName,brand_discrtion,viewProductImg;
        RecyclerView bandCategory;
        LinearLayout itemView_click;

        public MyViewHolder(View itemView) {
            super(itemView);

            brandName = itemView.findViewById(R.id.brand_name);
            brandImg = itemView.findViewById(R.id.brand_img);
            brand_discrtion = itemView.findViewById(R.id.brand_discrtion);
            bandCategory = itemView.findViewById(R.id.bandCategory);
            itemView_click = itemView.findViewById(R.id.itemView);
            viewProductImg = itemView.findViewById(R.id.viewProductImg);

//            itemView.setOnClickListener(v -> {
//                mContext.startActivity(new Intent(mContext, BranchItemDetailsActivity.class));
//            });
        }
    }
}
