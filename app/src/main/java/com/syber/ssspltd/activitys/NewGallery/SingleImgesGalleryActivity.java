package com.syber.ssspltd.activitys.NewGallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ortiz.touchview.TouchImageView;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;

public class SingleImgesGalleryActivity extends AppCompatActivity {
    Context mContext=this;
    TouchImageView galleryImg;
    ProgressBar loading_progress;
    public static String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_imges_gallery);
        galleryImg=(TouchImageView) findViewById(R.id.galleryview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        toolbar.setNavigationIcon(R.drawable.ic_cross2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         a = getIntent().getStringExtra("img");
        loading_progress=findViewById(R.id.progress);
        if (Lazy.haveNetworkConnection(mContext)){
            try {
                Glide.with(mContext)
                        .load(a)
                        .placeholder(R.drawable.sss_logo)
                        .listener(new RequestListener<>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                galleryImg.setVisibility(View.VISIBLE);
                                loading_progress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                galleryImg.setVisibility(View.VISIBLE);
                                loading_progress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(galleryImg);
            }catch (Exception e)
            {

            }
        }else {
            networkConnetion3(mContext);
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

    public void  networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            if (Lazy.haveNetworkConnection(mContext)){
                try {

                    Glide.with(mContext)
                            .load(a)
                            .placeholder(R.drawable.sss_logo)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    galleryImg.setVisibility(View.VISIBLE);
                                    loading_progress.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    galleryImg.setVisibility(View.VISIBLE);
                                    loading_progress.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(galleryImg);
                }catch (Exception e)
                {

                }
            }else {
                networkConnetion3(mContext);
            }

            alertDialog.dismiss();
        });
        alertDialog.show();
    }
//
}