package com.syber.ssspltd.activitys;

import static com.veinhorn.scrollgalleryview.loader.picasso.dsl.DSL.images;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewImageActivity extends AppCompatActivity {
    ImageView iamgeShow;
    Context mcontext;
    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView YouTubePlayerView;
    String img,video;
    ScrollGalleryView galleryView;
    List<String>imageListSecondaryData;
    Context mContext=this;
    FloatingActionButton supportFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        YouTubePlayerView= findViewById(R.id.youtube_player_view);
        supportFab= findViewById(R.id.support_fab);
        supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        toolbar.setNavigationIcon(R.drawable.ic_cross2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String videoId=extractYTId(getIntent().getStringExtra("img"));

        getLifecycle().addObserver(YouTubePlayerView);
        YouTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
                //youTubePlayer.cueVideo(videoId,0);
            }


            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);

            }
        });


        // vedioshow.setVideoPath(getIntent().getStringExtra("video"));
//            webView.setWebViewClient(new MyBrowser());
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setJavaScriptEnabled(true);;
//            webView.loadUrl(getIntent().getStringExtra("img"));
           // Log.e("video_url",getIntent().getStringExtra("img"));
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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

//    @Override
//    public void videoStarted() {
//        super.videoStarted();
//        img_playback.setImageResource(R.drawable.ic_pause);
//        if (isMuted) {
//            muteVideo();
//            img_vol.setImageResource(R.drawable.ic_mute);
//        } else {
//            unmuteVideo();
//            img_vol.setImageResource(R.drawable.ic_unmute);
//        }
//    }
//
//    @Override
//    public void pauseVideo() {
//        super.pauseVideo();
//        img_playback.setImageResource(R.drawable.ic_play);
//    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}