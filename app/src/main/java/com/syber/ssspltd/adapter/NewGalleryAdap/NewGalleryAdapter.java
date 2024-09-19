package com.syber.ssspltd.adapter.NewGalleryAdap;

import static com.syber.ssspltd.activitys.Const.EVENTID;
import static com.syber.ssspltd.activitys.Const.EVENTNAME;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.GalleryViewAllActivity;
import com.syber.ssspltd.activitys.NewGallery.ViewMoreActivity;
import com.syber.ssspltd.adapter.GalleryAdapter;
import com.syber.ssspltd.adapter.GalleryItemAdapter;
import com.syber.ssspltd.response.GalleryResponse.ImageListAppResult;
import com.syber.ssspltd.response.NewGalleryResponse.Event;

import java.io.Serializable;
import java.util.List;

public class NewGalleryAdapter extends RecyclerView.Adapter<NewGalleryAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<Event> imageListDetails;

    public NewGalleryAdapter(Context mContext, List<Event> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @Override
    public NewGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_gallery_recycler, parent, false);
        return new NewGalleryAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Event datum = imageListDetails.get(position);
        holder.eventName.setText(datum.getEventName());
        String frstLetter = holder.eventName.getText().toString();
        String firstTen = frstLetter.substring(0, 1);
        holder.firstLetName.setText(firstTen);

        holder.viewMore.setOnClickListener(view -> {
            Intent i = new Intent(mContext, ViewMoreActivity.class);
           i.putExtra(EVENTID, datum.getEventID().toString());
           i.putExtra(EVENTNAME,datum.getEventName());
            mContext.startActivity(i);


        });

        if (!datum.getEventName().equals(""))
        {
            NewGalleryItemAdapter ListAdapter = new NewGalleryItemAdapter(mContext, datum.getImageList());
            holder.imgEvent_recy.setAdapter(ListAdapter);

        }
        else {

        }
//
    }
    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventName,firstLetName;
        RecyclerView imgEvent_recy;
        TextView viewMore;
        public MyViewHolder(View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.eventName);
            firstLetName = itemView.findViewById(R.id.firstLetName);
            imgEvent_recy = itemView.findViewById(R.id.imgEvent_recy);
            viewMore = itemView.findViewById(R.id.viewMore);

        }
    }
}
