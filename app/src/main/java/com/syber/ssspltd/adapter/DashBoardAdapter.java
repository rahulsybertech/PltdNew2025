package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BankDetailActivity;
import com.syber.ssspltd.activitys.BookingListActivity;
import com.syber.ssspltd.activitys.BranchWithLogoActivity;
import com.syber.ssspltd.activitys.CR_Note_Suppl;
import com.syber.ssspltd.activitys.CourierReportActivity;
import com.syber.ssspltd.activitys.CreditNoteActivity;
import com.syber.ssspltd.activitys.CustomerReviewsActivity;
import com.syber.ssspltd.activitys.DR_Note_Customer;
import com.syber.ssspltd.activitys.DrNoteActivity;
import com.syber.ssspltd.activitys.FeedbackActivity2;
import com.syber.ssspltd.activitys.GalleryActivity;
import com.syber.ssspltd.activitys.LedgerActivity;
import com.syber.ssspltd.activitys.NewDashBoardActivity;
import com.syber.ssspltd.activitys.O_BranchesActivity;
import com.syber.ssspltd.activitys.Offers.OffersActivity;
import com.syber.ssspltd.activitys.SaleReportActivity;
import com.syber.ssspltd.activitys.SaleService;
import com.syber.ssspltd.activitys.StockInOfficeActivity;
import com.syber.ssspltd.activitys.WhySSSActivity;
import com.syber.ssspltd.activitys.clubtype.ClubTypeActivity;
import com.syber.ssspltd.activitys.customer.CustomerListActivity;
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;
import com.syber.ssspltd.activitys.supplierorderform.SupplierReportActivity;
import com.syber.ssspltd.response.DeasbordListType;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    final private List<DeasbordListType> OfferList;
    Context context;
    boolean newuser;

    public DashBoardAdapter(Context context, List<DeasbordListType> offerList, boolean newuser) {
        this.OfferList = offerList;
        this.context = context;
        this.newuser = newuser;
    }

    @NonNull
    @Override
    public DashBoardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashbord_onclick_type_recy, parent, false);
        return new DashBoardAdapter.MyViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        return OfferList.size();

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DeasbordListType lists;
        lists = OfferList.get(position);
        System.out.println("GETTING_LIST " + new Gson().toJson(lists));
        holder.imageViewIMG.setImageResource(lists.getImg());
        holder.titleText.setText(lists.getName());
        if (lists.getOnClickId().equals("24") && (SharedPref.read(SharedPref.clubType, "").equalsIgnoreCase("SSSPLTD") || SharedPref.read(SharedPref.clubType, "").equalsIgnoreCase("N/A") || SharedPref.read(SharedPref.clubType, "").equalsIgnoreCase("NA") || SharedPref.read(SharedPref.clubType, "").equals(""))) {
            holder.rl.setVisibility(View.GONE);
        } else if (lists.getOnClickId().equals("24")) {
            holder.rl.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (lists.getOnClickId().equals("1")) {
                context.startActivity(new Intent(context, LedgerActivity.class));
            } else if (lists.getOnClickId().equals("2")) {
                context.startActivity(new Intent(context, DrNoteActivity.class));
            } else if (lists.getOnClickId().equals("3")) {
                context.startActivity(new Intent(context, CR_Note_Suppl.class));
            } else if (lists.getOnClickId().equals("4")) {
                context.startActivity(new Intent(context, SaleService.class));
            } else if (lists.getOnClickId().equals("5")) {
                context.startActivity(new Intent(context, NewDashBoardActivity.class));
            } else if (lists.getOnClickId().equals("6")) {
                context.startActivity(new Intent(context, SaleReportActivity.class).putExtra("filter", "no_filter"));
            } else if (lists.getOnClickId().equals("7")) {
                context.startActivity(new Intent(context, StockInOfficeActivity.class));
            } else if (lists.getOnClickId().equals("8")) {
                context.startActivity(new Intent(context, SupplierReportActivity.class));
            } else if (lists.getOnClickId().equals("9")) {
                context.startActivity(new Intent(context, CourierReportActivity.class));
            } else if (lists.getOnClickId().equals("10")) {
                context.startActivity(new Intent(context, DR_Note_Customer.class));
            } else if (lists.getOnClickId().equals("11")) {
                context.startActivity(new Intent(context, CreditNoteActivity.class));

            } else if (lists.getOnClickId().equals("12")) {
                context.startActivity(new Intent(context, WhySSSActivity.class));

            } else if (lists.getOnClickId().equals("13")) {
                context.startActivity(new Intent(context, BranchWithLogoActivity.class));
            }
//            else if (lists.getOnClickId().equals("17")) {
//                context.startActivity(new Intent(context, ApplyForKYCActivity.class));
//            }
            else if (lists.getOnClickId().equals("16")) {
                context.startActivity(new Intent(context, CustomerReviewsActivity.class));
            } else if (lists.getOnClickId().equals("14")) {
                context.startActivity(new Intent(context, O_BranchesActivity.class));
            } else if (lists.getOnClickId().equals("15")) {
                // loadFragment(new NewGalleryFragment());
                context.startActivity(new Intent(context, GalleryActivity.class));
            } else if (lists.getOnClickId().equals("19")) {
                context.startActivity(new Intent(context, FeedbackActivity2.class));
            } else if (lists.getOnClickId().equals("18")) {
                context.startActivity(new Intent(context, BankDetailActivity.class));
            } else if (lists.getOnClickId().equals("21")) {
                context.startActivity(new Intent(context, OffersActivity.class));
            } else if (lists.getOnClickId().equals("22")) {
                context.startActivity(new Intent(context, SupplierOrderFormActivity.class));
            } else if (lists.getOnClickId().equals("23")) {
                context.startActivity(new Intent(context, SupplierReportActivity.class));
            } else if (lists.getOnClickId().equals("24")) {
                context.startActivity(new Intent(context, ClubTypeActivity.class));
            }
            else if (lists.getOnClickId().equals("25")) {
                context.startActivity(new Intent(context, CustomerListActivity.class));
            } else if (lists.getOnClickId().equals("26")) {
                context.startActivity(new Intent(context, BookingListActivity.class));
            }
        });
        if (newuser && position > 7) {
            holder.imgFade.setImageResource(R.drawable.button_fade);
            holder.itemView.setOnClickListener(v -> {
                SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                alertDialog.setConfirmButtonBackgroundColor(Color.parseColor("#FF725E"));
                alertDialog.setTitleText("Alert!");
                alertDialog.setContentText("Only for registered customer");
                alertDialog.setConfirmText("OK!");
                alertDialog.showCancelButton(false);
                alertDialog.setConfirmClickListener(SweetAlertDialog::dismissWithAnimation);
                alertDialog.show();
            });
        } else {
            holder.imgFade.setVisibility(View.GONE);
        }

    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            fragmentManager.beginTransaction();
            return true;
        }
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewIMG;
        ImageView imgFade;
        TextView titleText;
        CircleImageView c_image;
        LinearLayout linear, ll;
        RelativeLayout rl;


        public MyViewHolder(View view) {
            super(view);

            imgFade = view.findViewById(R.id.img_fade);
            imageViewIMG = view.findViewById(R.id.imageViewIMG);
            titleText = view.findViewById(R.id.titleText);
            c_image = view.findViewById(R.id.image_color);
            rl = view.findViewById(R.id.rl);

        }

    }


}
