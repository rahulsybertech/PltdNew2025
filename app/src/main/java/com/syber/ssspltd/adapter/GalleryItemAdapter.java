package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.response.GalleryResponse.ImageListSecondaryDatum;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<ImageListSecondaryDatum> imageListDetails;

    public GalleryItemAdapter(Context mContext, List<ImageListSecondaryDatum> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @Override
    public GalleryItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false);
        return new GalleryItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GalleryItemAdapter.MyViewHolder holder, final int position) {

        final ImageListSecondaryDatum datum = imageListDetails.get(position);
        Log.e("bannerImg",datum.getBannerImage());
        String path ="";
        if (datum.getBannerImage().equals("")){
            path ="http://ancd.png";
        }else {

            path = datum.getBannerImage();
        }
        try {

        if (datum.getImageCategory().equals("image")) {

            Picasso.with(mContext)
                    .load(path)
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                    //.memoryPolicy(MemoryPolicy.)
                    // .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.iamge_list);
        }
        else if (datum.getImageCategory().equals("videolink")) {
            holder.videoClicp.setImageResource(R.drawable.ic_play);
            Picasso.with(mContext)
                    .load(datum.getVideoImgLink())
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                    //.placeholder(R.drawable.ic_supermarket)
                    //.memoryPolicy(MemoryPolicy.)
                    // .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.iamge_list, new Callback() {
                        @Override
                        public void onSuccess() {

                            holder.iamge_list.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
                            holder.iamge_list.setVisibility(View.VISIBLE);
                           // holder.iamge_list.setImageResource(R.drawable.ic_user);

                        }
                    });
        }
        }catch (Exception e)
        {

        }

//
    }

    @Override
    public int getItemCount()
    {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iamge_list,videoClicp;
        RecyclerView imgVedio_recy;


        public MyViewHolder(View itemView) {
            super(itemView);

            iamge_list = itemView.findViewById(R.id.set_image);
            videoClicp = itemView.findViewById(R.id.set_video);

            iamge_list.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    List<ImageListSecondaryDatum> isSelected2 = imageListDetails.stream().filter(p -> p.getImageCategory().equals("image")).collect(Collectors.toList());

                    if (imageListDetails.get(getAdapterPosition()).getImageCategory().equals("image")) {
                        mContext.startActivity(new Intent(mContext, ImageGalleryActivity.class)
                                .putExtra("imglist", new Gson().toJson(isSelected2))
                                .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getImageCategory())
                                .putExtra("img", imageListDetails.get(getAdapterPosition()).getBannerImage())
                                .putExtra("pos",getAdapterPosition()));
                    }
                    else if (imageListDetails.get(getAdapterPosition()).getImageCategory().equals("videolink"))
                    {
                        // SharedPref.write(SharedPref.IMG_VIDEO,"Video");
                        mContext.startActivity(new Intent(mContext, ViewImageActivity.class)
                                .putExtra("img", imageListDetails.get(getAdapterPosition()).getVideoLink())
                                .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getImageCategory()));
                    }
                }
            });

        }
    }
}
