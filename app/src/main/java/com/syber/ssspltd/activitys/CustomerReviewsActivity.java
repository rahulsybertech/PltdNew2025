package com.syber.ssspltd.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.databinding.ActivityCustomerReviewsBinding;

public class CustomerReviewsActivity extends AppCompatActivity {

   private static   ActivityCustomerReviewsBinding binding;
    Context context = this;
    YouTubePlayerView youTubePlayerView;
   // private  static String abc="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "GVpGAYO26SE";
                youTubePlayer.loadVideo(videoId, 0);
                binding.includeProgress.progress.setVisibility(View.GONE);
                //youTubePlayer.cueVideo(videoId,0);
            }


            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
                super.onStateChange(youTubePlayer, state);

            }
        });
        if (Lazy.haveNetworkConnection(context)){
            binding.webview.setWebViewClient(new MyWebViewClient());
            WebSettings webSettings = binding.webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            binding.webview.loadUrl("https://ssspltd.com/app_testimonial");
        }else {
            networkConnetion3(context);
        }

        binding.rating.setScore(Float.parseFloat("9"));

        binding.supportChat.supportFab.setOnClickListener(v -> Lazy.openDialog(context));


        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(context));
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
//            Lazy.networkConnetion(view.getContext());

            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            binding.webview.setVisibility(View.GONE);
            binding.blenkText.setVisibility(View.VISIBLE);
            binding.includeProgress.progress.setVisibility(View.GONE);
            networkConnetion3(view.getContext());
//            abc="1";
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public static void  networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        binding.webview.setVisibility(View.GONE);
        binding.blenkText.setVisibility(View.VISIBLE);
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.webview.setWebViewClient(new MyWebViewClient());
                WebSettings webSettings = binding.webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                binding.webview.loadUrl("https://ssspltd.com/app_testimonial");
                binding.rating.setScore(Float.parseFloat("9"));
                binding.webview.setVisibility(View.VISIBLE);
                binding.blenkText.setVisibility(View.GONE);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}