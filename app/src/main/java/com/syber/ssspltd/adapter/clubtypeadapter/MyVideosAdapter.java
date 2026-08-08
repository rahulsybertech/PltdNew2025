package com.syber.ssspltd.adapter.clubtypeadapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.response.clubtyperespo.Clubdetail;

import java.util.List;

public class MyVideosAdapter{

}
/*extends AAH_VideosAdapter {

    private final List<Clubdetail> list;
    private final Picasso picasso;
    public class MyViewHolder extends AAH_CustomViewHolder {
        final TextView tv;
        final ImageView img_vol, img_playback;
        boolean isMuted;

        public MyViewHolder(View x) {
            super(x);
            tv = x.findViewById(R.id.tv);
            img_vol = x.findViewById(R.id.img_vol);
            img_playback = x.findViewById(R.id.img_playback);
        }

        //override this method to get callback when video starts to play
        @Override
        public void videoStarted() {
            super.videoStarted();
            img_playback.setImageResource(R.drawable.ic_pause);
            if (isMuted) {
                muteVideo();
                img_vol.setImageResource(R.drawable.ic_mute);
            } else {
                unmuteVideo();
                img_vol.setImageResource(R.drawable.ic_unmute);
            }
        }

        @Override
        public void pauseVideo() {
            super.pauseVideo();
            img_playback.setImageResource(R.drawable.ic_play);
        }
    }


    public class MyTextViewHolder extends AAH_CustomViewHolder {
        final TextView tv;
        public MyTextViewHolder(View x) {
            super(x);
            tv = x.findViewById(R.id.tv);
        }
    }

    public MyVideosAdapter(List<Clubdetail> list_urls, Picasso p) {
        this.list = list_urls;
        this.picasso = p;
    }

    @Override
    public AAH_CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_card, parent, false);
            return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final AAH_CustomViewHolder holder, int position) {
//        if (list.get(position).getTitle().startsWith("text")) {
//            ((MyTextViewHolder) holder).tv.setText("Title");
//
//        } else {
            ((MyViewHolder) holder).tv.setText("Title");

            holder.setVideoUrl(Lazy.decode(list.get(position).getIconImage()));

            //load image into imageview
//            if (list.get(position).getImage_url() != null && !list.get(position).getImage_url().isEmpty()) {
//                picasso.load(holder.getImageUrl()).config(Bitmap.Config.RGB_565).into(holder.getAAH_ImageView());
//            }

            holder.setLooping(true);
            //optional - true by default
            //to play pause videos manually (optional)
            ((MyViewHolder) holder).img_playback.setOnClickListener(v -> {
                if (holder.isPlaying()) {
                    holder.pauseVideo();
                    holder.setPaused(true);
                } else {
                    holder.playVideo();
                    holder.setPaused(false);
                }
            });

            //to mute/un-mute video (optional)
            ((MyViewHolder) holder).img_vol.setOnClickListener(v -> {
                if (((MyViewHolder) holder).isMuted) {
                    holder.unmuteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_unmute);
                } else {
                    holder.muteVideo();
                    ((MyViewHolder) holder).img_vol.setImageResource(R.drawable.ic_mute);
                }
                ((MyViewHolder) holder).isMuted = !((MyViewHolder) holder).isMuted;
            });

            if (list.get(position).getIconImage() == null) {
                ((MyViewHolder) holder).img_vol.setVisibility(View.GONE);
                ((MyViewHolder) holder).img_playback.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).img_vol.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).img_playback.setVisibility(View.VISIBLE);
            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (list.get(position).getTitle().startsWith("text")) {
//            return TYPE_TEXT;
//        } else return TYPE_VIDEO;
        return 0;
    }
}*/
