package com.syber.ssspltd.adapter.supplierformadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.response.SupplierOrderReport.ImageList;

import java.util.List;

/**
 * Created by Aran on 22/06/2023.
 */

public class OrderImageAdapter extends RecyclerView.Adapter<OrderImageAdapter.MyViewHolder> {

    private Context mContext;
    private List<ImageList> galleryList;

    public OrderImageAdapter(Context mContext, List<ImageList> galleryList) {
        this.mContext = mContext;
        this.galleryList = galleryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_thumbnai, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageList gallery = galleryList.get(position);
        Glide.with(mContext)
                .load(gallery.getImagepath())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_supermarket)
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.iamge_list.setVisibility(View.VISIBLE);
//                        Log.e("GlideException",e.toString());
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.iamge_list.setVisibility(View.VISIBLE);
//                        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .into(holder.thumbnail);
    }
    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         ImageView thumbnail;
        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.image);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
