package com.syber.ssspltd.adapter.NewGalleryAdap.YearAdap;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.NewGallery.ViewAllActivity;
import com.syber.ssspltd.activitys.NewGallery.ViewMoreActivity;
import com.syber.ssspltd.adapter.NewGalleryAdap.NewGalleryAdapter;
import com.syber.ssspltd.adapter.NewGalleryAdap.NewGalleryItemAdapter;
import com.syber.ssspltd.response.NewGalleryResponse.Event;
import com.syber.ssspltd.response.NewGalleryResponse.YearGallery.Year;

import java.util.List;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.MyViewHolder>{


   final private Context mContext;
   final private List<Year> imageListDetails;
   private String eventId;
   private String eventName;

    public YearListAdapter(Context mContext, List<Year> detailList,String eventId,String eventName) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
        this.eventId = eventId;
        this.eventName =eventName;
    }

    @NonNull
    @Override
    public YearListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_gallery_recy, parent, false);
        return new YearListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(YearListAdapter.MyViewHolder holder, final int position) {

        final Year datum = imageListDetails.get(position);
        holder.yearText.setText(datum.getYear().toString());
//        holder.allView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mContext, ViewAllActivity.class);
//                i.putExtra("year_id",datum.getYearID().toString());
//                i.putExtra("event_id",ViewMoreActivity.eventId);
//                mContext.startActivity(i);
//
//            }
//        });

        if (!datum.getYear().equals(""))
        {
            YearImagesAdapter ListAdapter = new YearImagesAdapter(mContext, datum.getYearimageList(),eventId,datum.getYearID().toString(),eventName,datum.getYear().toString());
            holder.img_recy.setAdapter(ListAdapter);

        }
        else {

        }

    }
    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView yearText;
        RecyclerView img_recy;
        public MyViewHolder(View itemView) {
            super(itemView);

            yearText = itemView.findViewById(R.id.yearText);
            img_recy = itemView.findViewById(R.id.img_recy);
//            allView = itemView.findViewById(R.id.allView);

        }
    }
}
