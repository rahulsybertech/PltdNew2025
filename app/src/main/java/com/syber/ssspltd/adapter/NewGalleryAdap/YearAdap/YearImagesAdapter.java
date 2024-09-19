package com.syber.ssspltd.adapter.NewGalleryAdap.YearAdap;

import static com.syber.ssspltd.activitys.Const.EVENTID;
import static com.syber.ssspltd.activitys.Const.EVENTNAME;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.Const;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.NewGallery.SingleImgesGalleryActivity;
import com.syber.ssspltd.activitys.NewGallery.ViewAllActivity;
import com.syber.ssspltd.activitys.NewGallery.ViewMoreActivity;
import com.syber.ssspltd.activitys.ViewImageActivity;
import com.syber.ssspltd.adapter.NewGalleryAdap.NewGalleryItemAdapter;
import com.syber.ssspltd.response.NewGalleryResponse.ImageList;
import com.syber.ssspltd.response.NewGalleryResponse.YearGallery.YearimageList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class YearImagesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<YearimageList> imageListDetails;
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_GROUP = 1;
    private String eventId;
    private String yearId;
    private String eventName;
    private String yearName;


    public YearImagesAdapter(Context mContext, List<YearimageList> detailList,String eventId,String yearId,String eventName,String yearName) {
        this.mContext = mContext;
        this.imageListDetails = detailList;
        this.eventId = eventId;
        this.yearId = yearId;
        this.eventName=eventName;
        this.yearName=yearName;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from ( parent.getContext ());
        switch ( viewType ) {
            case TYPE_IMAGE:
                ViewGroup vImage = ( ViewGroup ) mInflater.inflate ( R.layout.year_image_item, parent, false );
                MyViewHolder vhImage = new MyViewHolder ( vImage );
                return vhImage;
            case TYPE_GROUP:
                ViewGroup vGroup = ( ViewGroup ) mInflater.inflate ( R.layout.view_more, parent, false );
                ViewMoreHolder vhGroup = new ViewMoreHolder ( vGroup );
                return vhGroup;
            default:
                ViewGroup vGroup0 = ( ViewGroup ) mInflater.inflate ( R.layout.view_more, parent, false );
                ViewMoreHolder vhGroup0 = new ViewMoreHolder ( vGroup0 );
                return vhGroup0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch ( holder.getItemViewType () ) {
            case TYPE_IMAGE:
                MyViewHolder myViewHolder = ( MyViewHolder ) holder;
                final YearimageList datum = imageListDetails.get(position);
                Log.e("bannerImg",datum.getSourceUrl());

                String path ="";
                if (datum.getSourceUrl().equals("")){
                    path ="http://ancd.png";
                }else {

                    path = datum.getSourceUrl();
                }
                try{
                    if (datum.getLinktype().equals("image")) {

                        Glide
                                .with(mContext)
                                .load(path)
                                .placeholder(R.drawable.sss_logo)
                                .into((myViewHolder.iamge_list));
//
//                        Picasso.with(mContext)
//                                .load(path)
//                                .priority(Picasso.Priority.HIGH)
//                                .resize(500, 500)
//                                //.memoryPolicy(MemoryPolicy.)
//                                // .networkPolicy(NetworkPolicy.OFFLINE)
//                                .into(myViewHolder.iamge_list);
                    }
                    else if (datum.getLinktype().equals("videolink")) {
                        myViewHolder.videoClicp.setImageResource(R.drawable.ic_play);
                        String videoId=extractYTId(datum.getSourceUrl());

                        Glide
                                .with(mContext)
                                .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
                                .placeholder(R.drawable.sss_logo)
                                .into((myViewHolder.iamge_list));

//                        Picasso.with(mContext)
//                                .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
//                                .priority(Picasso.Priority.HIGH)
//                                .resize(500, 500)
//                                .into(myViewHolder.iamge_list, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//
//                                        myViewHolder.iamge_list.setVisibility(View.VISIBLE);
//                                    }
//                                    @Override
//                                    public void onError() {
//                                        /// holder.Img_erroe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_button));
//                                        myViewHolder.iamge_list.setVisibility(View.VISIBLE);
//                                        // holder.iamge_list.setImageResource(R.drawable.ic_user);
//
//                                    }
//                                });
                    }
                }catch (Exception e)
                {

                }
                break;

            case TYPE_GROUP:

                ViewMoreHolder viewMoreHolder = ( ViewMoreHolder ) holder;
                viewMoreHolder.viewMore.setText("View More");
                viewMoreHolder.clickViewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, ViewAllActivity.class);
                        i.putExtra("year_id",yearId);
                        i.putExtra(EVENTID,eventId);
                        i.putExtra(EVENTNAME,eventName);
                        i.putExtra("year_name",yearName);
                        mContext.startActivity(i);
                    }
                });

                break;

        }


//
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (imageListDetails.size() == position) {
            viewType = TYPE_GROUP;
        } else {
            viewType = TYPE_IMAGE;
        }

        return viewType;
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
    public int getItemCount()
    {
        if (imageListDetails.size() == 5) {
            return imageListDetails.size() + 1;
        }
        else {
            return imageListDetails.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iamge_list,videoClicp;
        RecyclerView imgVedio_recy;
        VideoView vedio_image;
        TextView viewMore;

        public MyViewHolder(View itemView) {
            super(itemView);

            iamge_list = itemView.findViewById(R.id.set_image);
            videoClicp = itemView.findViewById(R.id.set_video);

            iamge_list.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    List<YearimageList> isSelected2 = imageListDetails.stream().filter(p -> p.getLinktype().equals("image")).collect(Collectors.toList());

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
    class ViewMoreHolder extends RecyclerView.ViewHolder {

        TextView viewMore;
        LinearLayout clickViewMore;

        public ViewMoreHolder(View itemView) {
            super(itemView);

            viewMore = itemView.findViewById(R.id.readmore);
            clickViewMore = itemView.findViewById(R.id.clickViewMore);

        }
    }
}
