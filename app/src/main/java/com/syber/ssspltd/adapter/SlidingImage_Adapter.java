package com.syber.ssspltd.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ortiz.touchview.TouchImageView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.response.GalleryModel;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {

    private final ArrayList<GalleryModel> list;
    private final LayoutInflater inflater;
    private final Context context;

    public SlidingImage_Adapter(Context context, ArrayList<GalleryModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        GalleryModel galleryModel = list.get(position);

        assert imageLayout != null;

        final TouchImageView imageView = imageLayout
                .findViewById(R.id.image);

        final ImageView play = imageLayout
                .findViewById(R.id.play);

        if (galleryModel.getLinktype().equals("image")) {
            play.setVisibility(View.GONE);
        } else {
            play.setVisibility(View.VISIBLE);
        }

        if (galleryModel.getLinktype().equals("image")) {

            Picasso.with(context)
                    .load(galleryModel.getSource_url())
                    .into(imageView);

        } else {

            final String videoId = Lazy.extractYTId(galleryModel.getSource_url());

            Picasso.with(context)
                    .load("https://i.ytimg.com/vi/"+videoId+"/hqdefault.jpg")
                    .into(imageView);
            imageView.setOnClickListener(v-> videoDialog(videoId));

        }
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private void videoDialog(String videoId){

        final Dialog dialog = new Dialog(context, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_dialog);
        dialog.setCancelable(false);

        final YouTubePlayerView youTubePlayerView = dialog
                .findViewById(R.id.youtube_player);

        final ImageView cancel = dialog
                .findViewById(R.id.cancle);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId,0);
                    youTubePlayer.play();
                }
            });

        cancel.setOnClickListener(v-> dialog.dismiss());

        dialog.show();
    }

}