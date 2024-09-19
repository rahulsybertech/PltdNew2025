package com.syber.ssspltd.adapter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BrandDetailsActivity;
import com.syber.ssspltd.activitys.BrandImageGalleryActivity;
import com.syber.ssspltd.response.brand.ArrayProductImageA;

import java.util.List;

public class BrandImageAdapter extends RecyclerView.Adapter<BrandImageAdapter.MyViewHolder> {

    final private Context mContext;
    final private List<ArrayProductImageA> list;

    public BrandImageAdapter(Context mContext, List<ArrayProductImageA> list)
    {

        this.mContext = mContext;
        this.list = list;

    }

    @NonNull
    public BrandImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_image, parent, false);
        //v.setLayoutParams(new ViewGroup.LayoutParams((int) (parent.getWidth() * 3),ViewGroup.LayoutParams.MATCH_PARENT));
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int devicewidth = (int) (displayMetrics.widthPixels * 0.5);
//        v.getLayoutParams().width = devicewidth;

        getDensityDpi(mContext);


//        final DisplayMetrics dm = new DisplayMetrics();
//        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        ((Activity) mContext).getWindow().setLayout((int) (width * .8), (int) (height * .5));

        return new BrandImageAdapter.MyViewHolder(v);

    }

    public static int getDensityDpi(Context context) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int dpi = metrics.densityDpi;


        if (dpi <= 120) {
            dpi = 170;
        } else if (dpi <= 160) {
            dpi = 170;
        } else if (dpi <= 213) {
            dpi = 170;
        } else if (dpi <= 240) {
            dpi = 170;
        } else if (dpi <= 320) {
            dpi = 170;
        } else if (dpi <= 480) {
            dpi = 170;
        } else {
            dpi = 640;
        }

        return dpi;
    }

    @Override
    public void onBindViewHolder(@NonNull BrandImageAdapter.MyViewHolder holder, final int position) {
        SharedPref.init(mContext);
        final ArrayProductImageA datum = list.get(position);
        try {
            Glide
                    .with(mContext)
                    .load(!datum.getProductImageA().isEmpty()?datum.getProductImageA():"abc.png")
                    .placeholder(R.drawable.sss_logo)
                    .into((holder.brandImg));

//            Picasso.with(mContext)
//                    .load(!datum.getProductImageA().isEmpty()?datum.getProductImageA():"abc.png")
//                    .priority(Picasso.Priority.HIGH)
//                    .placeholder(R.drawable.sss_logo)
//                    .into(holder.brandImg, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//                        @Override
//                        public void onError() {
//
//                        }
//                    });
//            Log.e("img", datum.getProductImageA());
        }catch (Exception e)
        {

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView brandImg;
        TextView brandName;

        public MyViewHolder(View itemView) {
            super(itemView);

            brandName = itemView.findViewById(R.id.brand_name);
            brandImg = itemView.findViewById(R.id.brand_img);

             itemView.setOnClickListener(v -> {

                     Intent intent = new Intent(v.getContext(), BrandImageGalleryActivity.class);
                     intent.putExtra("imglist", new Gson().toJson(list));
                     intent.putExtra("pos",getAbsoluteAdapterPosition());
                     intent.putExtra("titleName",BrandDetailsActivity.brndNmae);
                     v.getContext().startActivity(intent);

             });

            }

    }
}
