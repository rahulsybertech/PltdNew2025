package com.syber.ssspltd.adapter.NewGalleryAdap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.NewGallery.SingleImgesGalleryActivity;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.adapter.GalleryItemAdapter;
import com.syber.ssspltd.response.GalleryResponse.ImageListSecondaryDatum;
import com.syber.ssspltd.response.NewGalleryResponse.ImageList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NewGalleryItemAdapter extends RecyclerView.Adapter<NewGalleryItemAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<ImageList> imageListDetails;
    //String rel = "rel";

    public NewGalleryItemAdapter(Context mContext, List<ImageList> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @Override
    public NewGalleryItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_carousel_item, parent, false);
        return new NewGalleryItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final ImageList datum = imageListDetails.get(position);
        Log.e("bannerImg",datum.getSourceUrl());
        String path ="";
        if (datum.getSourceUrl().equals("")){
            path ="http://ancd.png";
        }else {

            path = datum.getSourceUrl();
        }
        try{
        if (datum.getLinktype().equals("image")) {

            Glide.with(mContext)
                    .load(path)
                    .placeholder(R.drawable.ic_supermarket)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.iamge_list.setVisibility(View.VISIBLE);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.iamge_list.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(holder.iamge_list);

//            Picasso.with(mContext)
//                    .load(path)
//                    .priority(Picasso.Priority.HIGH)
//                    .resize(500, 500)
//                    //.memoryPolicy(MemoryPolicy.)
//                    // .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(holder.iamge_list, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                            holder.iamge_list.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
//                            holder.iamge_list.setVisibility(View.VISIBLE);
//                            // holder.iamge_list.setImageResource(R.drawable.ic_user);
//
//                        }
//                    });

        }
        else if (datum.getLinktype().equals("videolink")) {
            holder.videoClicp.setImageResource(R.drawable.ic_play);
            String videoId=getYouTubeId(datum.getSourceUrl());
            Log.e("log_id",videoId);
            Picasso.with(mContext)
                    .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
                    .priority(Picasso.Priority.HIGH)
                    .resize(500, 500)
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

//
//            RequestOptions requestOptions = new RequestOptions();
//            Glide.with(mContext)
//                    .load(datum.getSourceUrl())
//                    .apply(requestOptions)
//                    .thumbnail(Glide.with(mContext).load("https://i.ytimg.com/vi/"+datum.getSourceUrl()+"/hqdefault.jpg"))
//                    .into(holder.iamge_list);
        }
        }catch (Exception e)
        {

        }
//
    }

    public String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }

    @Override
    public int getItemCount()
    {
        return imageListDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iamge_list,videoClicp;
        RecyclerView imgVedio_recy;
        VideoView vedio_image;


        public MyViewHolder(View itemView) {
            super(itemView);

            iamge_list = itemView.findViewById(R.id.set_image);
            videoClicp = itemView.findViewById(R.id.set_video);

            iamge_list.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    List<ImageList> isSelected2 = imageListDetails.stream().filter(p -> p.getLinktype().equals("image")).collect(Collectors.toList());

                    if (imageListDetails.get(getAdapterPosition()).getLinktype().equals("image")) {
                        mContext.startActivity(new Intent(mContext, SingleImgesGalleryActivity.class)
                                .putExtra("imglist", new Gson().toJson(isSelected2))
                                .putExtra("img", imageListDetails.get(getAdapterPosition()).getSourceUrl())
                                .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getLinktype())
                                .putExtra("pos",getAdapterPosition()));
                    }
                    else if (imageListDetails.get(getAdapterPosition()).getLinktype().equals("videolink"))
                    {
                        // SharedPref.write(SharedPref.IMG_VIDEO,"Video");
                        mContext.startActivity(new Intent(mContext, ViewImageActivity.class)
                                .putExtra("img", imageListDetails.get(getAdapterPosition()).getSourceUrl())
                                .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getLinktype()));
                    }

                }
            });

        }
    }
}
