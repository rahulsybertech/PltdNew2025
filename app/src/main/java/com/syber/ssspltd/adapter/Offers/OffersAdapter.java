package com.syber.ssspltd.adapter.Offers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ImageGalleryActivity;
import com.syber.ssspltd.activitys.NewGallery.SingleImgesGalleryActivity;
import com.syber.ssspltd.response.Offers.CouponList;

import org.sufficientlysecure.htmltextview.HtmlFormatter;
import org.sufficientlysecure.htmltextview.HtmlFormatterBuilder;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<CouponList>couponListList;
    boolean alertIsBeingShown = false;
    boolean alertIsViewMoreShown = false;

    public OffersAdapter(Context mContext, List<CouponList> couponListList) {
        this.mContext = mContext;
        this.couponListList = couponListList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_image,parent,false);
        return new OffersAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final CouponList datum=couponListList.get(position);
        holder.offersName.setText(datum.getOfferName());
        holder.couponCode.setText(datum.getCouponCode());
        holder.couponExpiryDate.setText(datum.getCouponExpiryDate());

        String path ="";
        if (datum.getOfferImage().equals("")){
            path ="http://ancd.png";
        }else {

            path = datum.getOfferImage();
        }
        try{
            if (datum.getOfferImageCategory().equals("Image")) {

                Picasso.with(mContext)
                        .load(path)
                        .priority(Picasso.Priority.HIGH)
                        .resize(500, 500)
                        //.memoryPolicy(MemoryPolicy.)
                        // .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.iamge_list);
            }
            else if (datum.getOfferImageCategory().equals("Videolink")) {
                holder.videoClicp.setImageResource(R.drawable.ic_play);
                String videoId=getYouTubeId(datum.getOfferImage());

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

            }
        }catch (Exception e)
        {

        }
        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alertIsViewMoreShown) {
                    alertIsViewMoreShown = true;
                    final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.ThemeOverlay_App_BottomSheetDialog);
                    dialog.setContentView(R.layout.dialog_offers);
                    ImageView viewImages = dialog.findViewById(R.id.viewImages);
                    ImageView cross = dialog.findViewById(R.id.cross);
                    TextView offersName = dialog.findViewById(R.id.offersName);
                    TextView couponExpiryDate = dialog.findViewById(R.id.couponExpiryDate);
                    TextView couponCode = dialog.findViewById(R.id.couponCode);
                    TextView offerDescription = dialog.findViewById(R.id.offerDescription);
                    WebView webView = dialog.findViewById(R.id.web_view);
                    Spanned formattedHtml = HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(datum.getOfferDescription()).setImageGetter(new HtmlResImageGetter(offerDescription.getContext())));
                    offerDescription.setText(formattedHtml);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    // List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
                    offersName.setText(datum.getOfferName());
                    couponExpiryDate.setText(datum.getCouponExpiryDate());
                    couponCode.setText(datum.getCouponCode());
                    if (datum.getOfferImageCategory().equals("Image")) {
                        Picasso.with(mContext)
                                .load(datum.getOfferImage())
                                .priority(Picasso.Priority.HIGH)
                                .resize(500, 500)
                                .into(viewImages, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        viewImages.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        viewImages.setVisibility(View.VISIBLE);
                                    }
                                });

                    } else if (datum.getOfferImageCategory().equals("Videolink")) {
                        webView.setVisibility(View.VISIBLE);
                        viewImages.setVisibility(View.GONE);
                        webView.setWebViewClient(new MyBrowser());
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webView.loadUrl(datum.getOfferImage());

                    }

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertIsViewMoreShown = false;
                            dialog.dismiss();
                        }
                    });
                    viewImages.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
                            mContext.startActivity(new Intent(mContext, SingleImgesGalleryActivity.class)
                                    .putExtra("imglist", new Gson().toJson(isSelected2))
                                    .putExtra("img", datum.getOfferImage())
                                    .putExtra("type", datum.getOfferImageCategory())
                                    .putExtra("pos", position));
                        }
                    });
                    dialog.show();
                }

//                    List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
//
//                    if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Image")) {
//                        mContext.startActivity(new Intent(mContext, ImageGalleryActivity.class)
//                                .putExtra("imglist", new Gson().toJson(isSelected2))
//                                .putExtra("img", couponListList.get(getAdapterPosition()).getOfferImage())
//                                .putExtra("type" ,couponListList.get(getAdapterPosition()).getOfferImageCategory())
//                                .putExtra("pos",getAdapterPosition()));
//                    }
//                    else if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Videolink"))
//                    {
//                        // SharedPref.write(SharedPref.IMG_VIDEO,"Video");
//                        mContext.startActivity(new Intent(mContext, ViewImageActivity.class)
//                                .putExtra("img", couponListList.get(getAdapterPosition()).getOfferImage())
//                                .putExtra("type" ,couponListList.get(getAdapterPosition()).getOfferImageCategory()));
//                    }
            }
        });


    }

    @Override
    public int getItemCount() {
        return couponListList.size();
    }

  class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iamge_list,videoClicp;
        TextView offersName,couponCode,couponExpiryDate,viewMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iamge_list = itemView.findViewById(R.id.setImage);
            videoClicp = itemView.findViewById(R.id.set_video);
            offersName = itemView.findViewById(R.id.offersName);
            couponCode = itemView.findViewById(R.id.couponCode);
            couponExpiryDate = itemView.findViewById(R.id.couponExpiryDate);
            viewMore = itemView.findViewById(R.id.viewMore);

            iamge_list.setOnClickListener(v -> {
                if (!alertIsBeingShown) {
                    alertIsBeingShown = true;
                    final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.ThemeOverlay_App_BottomSheetDialog);
                    dialog.setContentView(R.layout.dialog_offers);
                    ImageView viewImages = dialog.findViewById(R.id.viewImages);
                    ImageView cross = dialog.findViewById(R.id.cross);
                    TextView offersName = dialog.findViewById(R.id.offersName);
                    TextView couponExpiryDate = dialog.findViewById(R.id.couponExpiryDate);
                    TextView couponCode = dialog.findViewById(R.id.couponCode);
                    TextView offerDescription = dialog.findViewById(R.id.offerDescription);
                    WebView webView = dialog.findViewById(R.id.web_view);
                    Spanned formattedHtml = HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(couponListList.get(getLayoutPosition()).getOfferDescription()).setImageGetter(new HtmlResImageGetter(offerDescription.getContext())));
                    offerDescription.setText(formattedHtml);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    // List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
                    offersName.setText(couponListList.get(getAdapterPosition()).getOfferName());
                    couponExpiryDate.setText(couponListList.get(getAdapterPosition()).getCouponExpiryDate());
                    couponCode.setText(couponListList.get(getAdapterPosition()).getCouponCode());
                    if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Image")) {

                        Picasso.with(mContext)
                                .load(couponListList.get(getAdapterPosition()).getOfferImage())
                                .priority(Picasso.Priority.HIGH)
                                .resize(500, 500)
                                .into(viewImages, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        viewImages.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        viewImages.setVisibility(View.VISIBLE);
                                    }
                                });

                    } else if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Videolink")) {
                        webView.setVisibility(View.VISIBLE);
                        viewImages.setVisibility(View.GONE);
                        webView.setWebViewClient(new MyBrowser());
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webView.loadUrl(couponListList.get(getAdapterPosition()).getOfferImage());

                    }


                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertIsBeingShown=false;
                            dialog.dismiss();
                        }
                    });
                    viewImages.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onClick(View v) {
                            List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
                            mContext.startActivity(new Intent(mContext, SingleImgesGalleryActivity.class)
                                    .putExtra("imglist", new Gson().toJson(isSelected2))
                                    .putExtra("img", couponListList.get(getAdapterPosition()).getOfferImage())
                                    .putExtra("type", couponListList.get(getAdapterPosition()).getOfferImageCategory())
                                    .putExtra("pos", getAdapterPosition()));
                        }
                    });
                    dialog.show();
                }

//                    List<CouponList> isSelected2 = couponListList.stream().filter(p -> p.getOfferImageCategory().equals("Image")).collect(Collectors.toList());
//
//                    if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Image")) {
//                        mContext.startActivity(new Intent(mContext, ImageGalleryActivity.class)
//                                .putExtra("imglist", new Gson().toJson(isSelected2))
//                                .putExtra("img", couponListList.get(getAdapterPosition()).getOfferImage())
//                                .putExtra("type" ,couponListList.get(getAdapterPosition()).getOfferImageCategory())
//                                .putExtra("pos",getAdapterPosition()));
//                    }
//                    else if (couponListList.get(getAdapterPosition()).getOfferImageCategory().equals("Videolink"))
//                    {
//                        // SharedPref.write(SharedPref.IMG_VIDEO,"Video");
//                        mContext.startActivity(new Intent(mContext, ViewImageActivity.class)
//                                .putExtra("img", couponListList.get(getAdapterPosition()).getOfferImage())
//                                .putExtra("type" ,couponListList.get(getAdapterPosition()).getOfferImageCategory()));
//                    }
            });
        }
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
    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

