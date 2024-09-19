package com.syber.ssspltd.adapter;

import android.annotation.SuppressLint;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.response.GalleryResponse.ImageListAppResult;
import com.syber.ssspltd.response.GalleryResponse.ImageListSecondaryDatum;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GalleryAllViewItemAdapter extends RecyclerView.Adapter<GalleryAllViewItemAdapter.MyViewHolder>{

    private Context mContext;
    private List<ImageListSecondaryDatum> imageListDetails;


    public GalleryAllViewItemAdapter(Context mContext, List<ImageListSecondaryDatum> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public GalleryAllViewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allview_page_dailog_recy, parent, false);
        return new GalleryAllViewItemAdapter.MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(GalleryAllViewItemAdapter.MyViewHolder holder, final int position) {

        final ImageListSecondaryDatum datum = imageListDetails.get(position);
        String path = "";
        if (datum.getBannerImage().equalsIgnoreCase("")) {
            path = "http://ancd.png";
        } else {
            path = datum.getBannerImage();
        }
        try {

        if (datum.getImageCategory().equals("image")) {

            Picasso.with(mContext)
                    .load(path)
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                    .into(holder.set_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.set_image.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            holder.set_image.setVisibility(View.VISIBLE);
                        }
                    });
        } else if (datum.getImageCategory().equals("videolink")) {
            holder.set_video.setImageResource(R.drawable.ic_play);
            Picasso.with(mContext)
                    .load(datum.getVideoImgLink())
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
                    .into(holder.set_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.set_image.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onError() {
                            /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
                            holder.set_image.setVisibility(View.VISIBLE);
                            // holder.iamge_list.setImageResource(R.drawable.ic_user);
                        }
                    });
        }
        }catch (Exception e)
        {

        }
    }

    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView set_image,set_video;
        RecyclerView imgVedio_recy;


        @RequiresApi(api = Build.VERSION_CODES.N)
        public MyViewHolder(View itemView) {
            super(itemView);

            set_image = itemView.findViewById(R.id.set_image);
            set_video = itemView.findViewById(R.id.set_video);
            set_image.setOnClickListener(v -> {
                Log.e("pos",getAdapterPosition()+"");
                List<ImageListSecondaryDatum> isSelected2 = imageListDetails.stream().filter(p -> p.getImageCategory().equals("image")).collect(Collectors.toList());
                if (imageListDetails.get(getAdapterPosition()).getImageCategory().equals("image")) {
                    Intent intent = new Intent(v.getContext(), ImageGalleryActivity.class);
                    intent.putExtra("img", imageListDetails.get(getAdapterPosition()).getBannerImage());
                    intent.putExtra("imglist", new Gson().toJson(isSelected2));
                    intent.putExtra("pos",getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
                else if (imageListDetails.get(getAdapterPosition()).getImageCategory().equals("videolink"))
                {
                    Intent intent = new Intent(v.getContext(), ViewImageActivity.class);
                    intent.putExtra("STORY", imageListDetails.get(getAdapterPosition()))
                            .putExtra("img", imageListDetails.get(getAdapterPosition()).getVideoLink())
                            .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getImageCategory());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
