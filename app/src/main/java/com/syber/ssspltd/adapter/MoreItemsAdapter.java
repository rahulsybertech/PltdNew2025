package com.syber.ssspltd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.AboutUsActivity;
import com.syber.ssspltd.activitys.BankDetailActivity;
import com.syber.ssspltd.activitys.FAQActivity;
import com.syber.ssspltd.activitys.FeedbackActivity2;
import com.syber.ssspltd.activitys.LoginPage;
import com.syber.ssspltd.activitys.O_BranchesActivity;
import com.syber.ssspltd.activitys.ProfileActivity;
import com.syber.ssspltd.activitys.clubtype.ClubTypeActivity;
import com.syber.ssspltd.response.MoreItems;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MoreItemsAdapter extends RecyclerView.Adapter<MoreItemsAdapter.MyViewHolder> {

    Context context;
    private List<MoreItems> OfferList;

    public MoreItemsAdapter(Context context, List<MoreItems> offerList) {
        this.OfferList = offerList;
        this.context = context;
    }

    @Override
    public MoreItemsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_items_list, parent, false);
        return new MoreItemsAdapter.MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return OfferList.size();

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final MoreItems lists;
        lists = OfferList.get(position);
        SharedPref.init(context);
        holder.itemImage.setImageResource(lists.getImg());
        holder.titleText.setText(lists.getName());
        holder.itemView.setOnClickListener(v -> {
            if (lists.getOnClickId().equals("1")) {
                if (!SharedPref.read(SharedPref.USER_TYPE,"").equals("new")) {
                    context.startActivity(new Intent(context, ProfileActivity.class));
                }else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    alertDialog.setConfirmButtonBackgroundColor(Color.parseColor("#FF725E"));
                    alertDialog.setTitleText("Alert!");
                    alertDialog.setContentText("Only for registered customer");
                    alertDialog.setConfirmText("OK!");
                    alertDialog.showCancelButton(false);
                    alertDialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
                    alertDialog.show();
                }
            } else if (lists.getOnClickId().equals("2")) {
                context.startActivity(new Intent(context, O_BranchesActivity.class));
            } else if (lists.getOnClickId().equals("3")) {
                context.startActivity(new Intent(context, BankDetailActivity.class));
            } else if (lists.getOnClickId().equals("4")) {
                context.startActivity(new Intent(context, AboutUsActivity.class));
            } else if (lists.getOnClickId().equals("5")) {
                context.startActivity(new Intent(context, FeedbackActivity2.class));
            } else if (lists.getOnClickId().equals("6")) {
                if (!SharedPref.read(SharedPref.USER_TYPE,"").equals("new")) {

                    context.startActivity(new Intent(context, FAQActivity.class));
                }else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    alertDialog.setConfirmButtonBackgroundColor(Color.parseColor("#FF725E"));
                    alertDialog.setTitleText("Alert!");
                    alertDialog.setContentText("Only for registered customer");
                    alertDialog.setConfirmText("OK!");
                    alertDialog.showCancelButton(false);
                    alertDialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
                    alertDialog.show();
                }
            } else if (lists.getOnClickId().equals("7")) {

                SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                alertDialog.setConfirmButtonBackgroundColor(Color.parseColor("#2bab1c"));
                alertDialog.setTitleText("Alert!");
                alertDialog.setContentText("Do you want to logout?");
                alertDialog.setConfirmText("Yes");
                alertDialog.setCancelText("No");
                alertDialog.showCancelButton(true);
                alertDialog.setConfirmClickListener(sweetAlertDialog -> {
                    String s = SharedPref.read(SharedPref.clubType,"");
                    SharedPref.clear();
                    Log.i("TaG","after logout -=-=-=-=> " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                    Intent logout = new Intent(context, LoginPage.class);
                    logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(logout);
                    ((Activity) context).finish();
                    SharedPref.write(SharedPref.clubType,s);
                    sweetAlertDialog.dismissWithAnimation();
                });
                alertDialog.setCancelClickListener(SweetAlertDialog::dismissWithAnimation);
                alertDialog.show();
            } else if (lists.getOnClickId().equals("8")) {
                Lazy.openDialog(context);
            }
        });


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView titleText;


        public MyViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            titleText = view.findViewById(R.id.item_name);

        }
    }
}
