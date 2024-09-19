package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.response.GalleryResponse.ImageListSecondaryDatum;

import java.util.List;

public class GalleryAllViewAdapter extends RecyclerView.Adapter<GalleryAllViewAdapter.MyViewHolder>{
    private Context mContext;
    private List<ImageListSecondaryDatum> imageListDetails;

    public GalleryAllViewAdapter(Context mContext, List<ImageListSecondaryDatum> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @Override
    public GalleryAllViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false);
        return new GalleryAllViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryAllViewAdapter.MyViewHolder holder, final int position) {

        final ImageListSecondaryDatum datum = imageListDetails.get(position);
        String path ="";
        if (datum.getBannerImage().equalsIgnoreCase("")){
            path ="http://ancd.png";
        }else {
            path = datum.getBannerImage();
        }
        if (datum.getImageCategory().equals("image")) {

            Picasso.with(mContext)
                    .load(path)
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                   // .placeholder(R.drawable.ic_supermarket)
                    //.memoryPolicy(MemoryPolicy.)
                    // .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.set_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.set_image.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {

                            /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
                            holder.set_image.setVisibility(View.VISIBLE);
                       //     holder.set_image.setImageResource(R.drawable.ic_user);

                        }
                    });
        }
        else if (datum.getImageCategory().equals("videolink")) {
            holder.set_video.setImageResource(R.drawable.ic_play);

            Picasso.with(mContext)
                    .load(datum.getVideoImgLink())
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                   // .placeholder(R.drawable.ic_supermarket)
                    //.memoryPolicy(MemoryPolicy.)
                    // .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.set_image, new Callback() {
                        @Override
                        public void onSuccess()
                        {
                            holder.set_image.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {

                            /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
                            holder.set_image.setVisibility(View.VISIBLE);
                          //  holder.set_image.setImageResource(R.drawable.ic_user);

                        }
                    });

        }

        holder.set_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if (datum.getImageCategory().equals("image")) {
                    //SharedPref.write(SharedPref.IMG_VIDEO,"img");
                    Intent intent = new Intent(mContext,ViewImageActivity.class);
                    mContext.startActivity(intent);
//                            .putExtra("img", datum.getBannerImage())
//                            .putExtra("type" ,datum.getImageCategory()));
//                }
//                else if (datum.getImageCategory().equals("videolink"))
//                {
//                    // SharedPref.write(SharedPref.IMG_VIDEO,"Video");
//                    mContext.startActivity(new Intent(mContext, ViewImageActivity.class)
//                            .putExtra("img", datum.getVideoLink())
//                            .putExtra("type" ,datum.getImageCategory()));
//                }
            }
        });
//


    }

    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView set_image,set_video;
        RecyclerView imgVedio_recy;


        public MyViewHolder(View itemView) {
            super(itemView);

            set_image = itemView.findViewById(R.id.set_image);
            set_video = itemView.findViewById(R.id.set_video);

        }
    }
}
