package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.activitys.BookingRequestActivity;
import com.syber.ssspltd.model.booking.BookingData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder>{
    private Context mContext;
    private List<BookingData> stayBookingList;
    private OnBookingCancelListener cancelListener; // Callback interface


    public BookingListAdapter(Context mContext,/*, List<PendingOrderReportResult> detailList*/ArrayList<BookingData> stayBookingList,OnBookingCancelListener cancelListener) {
        this.mContext = mContext;
        this.stayBookingList = stayBookingList;
        this.cancelListener = cancelListener;
    }

    @Override
    public BookingListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list_adapter, parent, false);
        return new BookingListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookingListAdapter.MyViewHolder holder, final int position) {
        final BookingData datum = stayBookingList.get(position);
       /*
        holder.pendingSupplier_Name.setText(datum.getSupplier());
        holder.pendingOrder_no.setText(datum.getOrderNo());
        holder.pendingSub_party.setText(datum.getSubParty());
        holder.pending_date.setText(datum.getDate());
        holder.pendingItem.setText(datum.getItems());
        holder.pendingType.setText(datum.getPcs());
        holder.pendingQTY.setText(datum.getQty());
        holder.pendingAmt.setText(datum.getAmount());
        holder.marketer_pending.setText(datum.getMarketer());*/

        String companyID = datum.getId().toString();
        String cleanCompanyID = companyID.replace("-", "");
    //    println(cleanCompanyID)  // Output: 43029624ea4a434c9a14d7da24840bad
        holder.visitId.setText(cleanCompanyID);

        holder.checkInTimeAndDate.setText(String.format("%s %s", convertDateFormat(datum.getCheckInDate()), datum.getCheckInTime()));
        holder.checkOutDateAndTime.setText(String.format("%s %s", convertDateFormat(datum.getCheckoutDate()), datum.getCheckoutTime()));
        holder.noOfPerson.setText(datum.getNoOfPerson());
        holder.branchName.setText(datum.getBranchName());



        // Edit Button Click
        holder.edit.setOnClickListener(v ->
                mContext.startActivity(new Intent(mContext, BookingRequestActivity.class)
                        .putExtra("data",datum)
                        .putExtra(MyConstant.EXTRA_IS_EDIT,true)
                        .putExtra(MyConstant.USERTYPE, SharedPref.read(SharedPref.DASHBOARD_TYPE, ""))
                        .putExtra(MyConstant.SCREEN,MyConstant.BOOKINGlIST)

                )
        );
        // Cancel Button Click - Notify Activity via Callback
        holder.llCancel.setOnClickListener(v ->{
            if (cancelListener != null) {
                cancelListener.onBookingCancel(position,datum); // Notify activity
            }
                }

        );
    /*    holder.itemView.setOnClickListener(v->{
            if (datum.getOrderdetail().size()>0) {
                mContext.startActivity(new Intent(mContext, PendingOrderItemDetailsActivity.class)
                        .putExtra("orderlist",datum));
            }else {
                Toast.makeText(mContext, "No Details found for this order", Toast.LENGTH_SHORT).show();
            }
        });*/

//        if (datum.getDebitAmt().equals("")){
//            holder.dr_amt.setVisibility(View.GONE);
//            holder.dr_txt.setVisibility(View.GONE);
//        }else {
//            holder.dr_amt.setVisibility(View.VISIBLE);
//            holder.dr_txt.setVisibility(View.VISIBLE);
//            holder.dr_amt.setText(datum.getDebitAmt());
//        }
//
//        holder.bal_name.setText(tt);

    }
    // Remove item from the list
    public void removeItem(int position) {
        stayBookingList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, stayBookingList.size()); // Refresh list
    }

    public String convertDateFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle error case
        }
    }

    @Override
    public int getItemCount() {
        return  stayBookingList.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView visitId,checkInTimeAndDate,checkOutDateAndTime,noOfPerson,branchName,pendingAmt,marketer_pending;
        LinearLayout llCancel;
        LinearLayout edit;
        TextView netAmt_pending;
        public MyViewHolder(View itemView) {
            super(itemView);
            visitId = itemView.findViewById(R.id.visitId);
            checkInTimeAndDate = itemView.findViewById(R.id.checkInTimeAndDate);
            checkOutDateAndTime = itemView.findViewById(R.id.checkOutDateAndTime);
            noOfPerson = itemView.findViewById(R.id.noOfPerson);
            branchName = itemView.findViewById(R.id.branchName);
            edit = itemView.findViewById(R.id.edit);
            llCancel = itemView.findViewById(R.id.llCancel);

        }
    }
    //  the interface
    public interface OnBookingCancelListener {
        void onBookingCancel(int position,BookingData data); // Method to notify activity
    }
}
