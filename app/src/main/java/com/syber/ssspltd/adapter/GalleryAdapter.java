package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;

import com.syber.ssspltd.activitys.GalleryViewAllActivity;
import com.syber.ssspltd.activitys.OTPActivity;
import com.syber.ssspltd.response.GalleryResponse.ImageListAppResult;


import java.io.Serializable;
import java.util.List;

public class GalleryAdapter  extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private Context mContext;
    private List<ImageListAppResult> imageListDetails;

    public GalleryAdapter(Context mContext, List<ImageListAppResult> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler, parent, false);
        return new GalleryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.MyViewHolder holder, final int position) {

        final ImageListAppResult datum = imageListDetails.get(position);
        holder.imgText.setText(datum.getEVENTNAME());
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,GalleryViewAllActivity.class);
                i.putExtra("title", (Serializable) datum);
                mContext.startActivity(i);

            }
        });
        if (!datum.getEVENTNAME().equals("")) {

            GalleryItemAdapter ListAdapter = new GalleryItemAdapter(mContext, datum.getImageListSecondaryData());
            holder.imgVedio_recy.setAdapter(ListAdapter);
        }else
        {
            holder.allView.setVisibility(View.GONE);
        }
//
    }
    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView imgText,allView;
        RecyclerView imgVedio_recy;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgText = itemView.findViewById(R.id.imgText);
            imgVedio_recy = itemView.findViewById(R.id.imgVedio_recy);
            allView = itemView.findViewById(R.id.allView);

        }
    }
}
