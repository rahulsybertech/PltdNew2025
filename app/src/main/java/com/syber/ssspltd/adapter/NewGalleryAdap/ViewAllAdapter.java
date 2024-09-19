package com.syber.ssspltd.adapter.NewGalleryAdap;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.response.NewGalleryResponse.ViewAll.ImageList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.MyViewHolder>  {

    private Context mContext;
    private List<ImageList> imageListDetails;

    public ViewAllAdapter(Context mContext, List<ImageList> detailList) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public ViewAllAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.allview_page_dailog_recy, parent, false);
        return new ViewAllAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewAllAdapter.MyViewHolder holder, final int position) {

        final ImageList datum = imageListDetails.get(position);

        String path = "";
        if (datum.getSourceUrl().equalsIgnoreCase("")) {
            path = "http://ancd.png";
        } else {
            path = datum.getSourceUrl();
        }
        try {

            if (datum.getLinktype().equals("image")) {


                Glide
                        .with(mContext)
                        .load(path)
                        .placeholder(R.drawable.sss_logo)
                        .into((holder.set_image));

//                Picasso.with(mContext)
//                        .load(path)
//                        .priority(Picasso.Priority.HIGH)
//                        .resize(500, 500)
//                        .into(holder.set_image, new Callback() {
//                            @Override
//                            public void onSuccess() {
//                                holder.set_image.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onError() {
//                                holder.set_image.setVisibility(View.VISIBLE);
//                            }
//                        });
            } else if (datum.getLinktype().equals("videolink")) {
                holder.set_video.setImageResource(R.drawable.ic_play);
                String videoId=extractYTId(datum.getSourceUrl());
//
                Glide
                        .with(mContext)
                        .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
                        .placeholder(R.drawable.sss_logo)
                        .into((holder.set_image));

//                Picasso.with(mContext)
//                        .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
//                        .priority(Picasso.Priority.HIGH)
//                        .resize(500, 500)
//                        .into(holder.set_image, new Callback() {
//                            @Override
//                            public void onSuccess() {
//
//                                holder.set_image.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onError() {
//                                /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
//                                holder.set_image.setVisibility(View.VISIBLE);
//                                // holder.iamge_list.setImageResource(R.drawable.ic_user);
//
//                            }
//                        });

            }
        }catch (Exception e)
        {

        }


    }

    public static String extractYTId(String url) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }

    @Override
    public int getItemCount() {
        return imageListDetails.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView set_image,set_video;
        @RequiresApi(api = Build.VERSION_CODES.N)
        public MyViewHolder(View itemView) {
            super(itemView);

            set_image = itemView.findViewById(R.id.set_image);
            set_video = itemView.findViewById(R.id.set_video);
            set_image.setOnClickListener(v -> {
                Log.e("pos",getAdapterPosition()+"");
                List<ImageList> isSelected2 = imageListDetails.stream().collect(Collectors.toList());
               // if (imageListDetails.get(getAdapterPosition()).getLinktype().equals("image")) {
                    Intent intent = new Intent(v.getContext(), ImageGalleryActivity.class);
                    intent.putExtra("imglist", new Gson().toJson(isSelected2));
                    intent.putExtra("img", imageListDetails.get(getAdapterPosition()).getSourceUrl());
                    intent.putExtra("pos",getAdapterPosition());
                    v.getContext().startActivity(intent);
               /// }
//                else if (imageListDetails.get(getAdapterPosition()).getLinktype().equals("videolink"))
//                {
//                    Intent intent = new Intent(v.getContext(), ViewImageActivity.class)
//                            .putExtra("img", imageListDetails.get(getAdapterPosition()).getSourceUrl())
//                            .putExtra("type" ,imageListDetails.get(getAdapterPosition()).getLinktype());
//                    v.getContext().startActivity(intent);
//                }
            });
        }
        }

}
