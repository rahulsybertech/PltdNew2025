package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.MyConstant;
import com.syber.ssspltd.activitys.supplierorderform.OrderImageActivity;
import com.syber.ssspltd.model.booking.branchlist.GuestMasterDetail;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GuestListBookingAdapter extends RecyclerView.Adapter<GuestListBookingAdapter.MyViewHolder>{
    private Context mContext;
    private List<GuestMasterDetail> stayBookingList;
    private OnCancelListener cancelListener; // Callback interface
    private HashSet<Integer> selectedPositions = new HashSet<>(); // Stores selected items

    private static final int MAX_SELECTION = 9;
    private static final int MIN_SELECTION = 1;

    private String bookingPage;
    private Set<String> checkGuestIds; // Set of checked guest IDs

    public GuestListBookingAdapter(Context mContext,/*, List<PendingOrderReportResult> detailList*/ArrayList<GuestMasterDetail> stayBookingList, OnCancelListener cancelListener, List<String> checkGuestList, String bookingPage) {
        this.mContext = mContext;
        this.stayBookingList = stayBookingList;
        this.cancelListener = cancelListener;
        this.checkGuestIds = new HashSet<>(checkGuestList);
        this.bookingPage = bookingPage;
    }

    @Override
    public GuestListBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_list_booking_adapter, parent, false);
        return new GuestListBookingAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GuestListBookingAdapter.MyViewHolder holder, final int position) {
        final GuestMasterDetail datum = stayBookingList.get(position);
        holder.tvGuestName.setText(datum.getGuestName());
        holder.tvSrNo.setText(String.valueOf(position + 1));
        // Check if guest ID exists in checkGuestIds
        boolean isCheckedByDefault = checkGuestIds.contains(datum.getId());

        if(bookingPage.equals(MyConstant.BOOKING_PAGE)){
            holder.viewImage.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.viewImage.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }
        // Update CheckBox state
        if (isCheckedByDefault) {
            selectedPositions.add(position);
        }

       holder. viewImage.setOnClickListener(v -> {

            Intent i = new Intent(mContext, OrderImageActivity.class);
            i.putExtra(MyConstant.SCREEN,MyConstant.GUEST);
            i.putExtra("imgList",new Gson().toJson(datum));
            Log.e("imgList", new Gson().toJson(datum));
            mContext.startActivity(i);
        });
        holder.checkBox.setOnCheckedChangeListener(null); // Remove previous listener to prevent duplicate calls

        // Update CheckBox state
        holder.checkBox.setChecked(selectedPositions.contains(position));

        // Disable checkbox if max limit reached
        holder.checkBox.setEnabled(selectedPositions.size() < MAX_SELECTION || selectedPositions.contains(position));



        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.post(() -> {
                checkGuestIds.clear();
                if (isChecked) {
                    if (selectedPositions.size() < MAX_SELECTION) {
                        selectedPositions.add(position);
                    } else {
                        holder.checkBox.setChecked(false); // Prevent selection beyond max
                        Toast.makeText(mContext, "You can select a maximum of " + MAX_SELECTION + " guests", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    selectedPositions.remove(position);
                    if (selectedPositions.size() < MIN_SELECTION) {
                        holder.checkBox.setChecked(true); // Prevent deselection below min
                        selectedPositions.add(position);
                        Toast.makeText(mContext, "You must select at least " + MIN_SELECTION + " guest", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyItemChanged(position); // Safe update
            });
        });











        // Cancel Button Click - Notify Activity via Callback
        holder.delete.setOnClickListener(v ->{
                    if (cancelListener != null) {
                        cancelListener.onBookingCancel(position,datum); // Notify activity
                    }
                }
        );


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

        TextView tvGuestName,tvSrNo,viewImage;
        CheckBox checkBox;
        ImageView delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvGuestName = itemView.findViewById(R.id.tvGuestName);
            tvSrNo = itemView.findViewById(R.id.tvSrNo);
            delete = itemView.findViewById(R.id.delete);
            checkBox = itemView.findViewById(R.id.checkbox);
            viewImage = itemView.findViewById(R.id.viewImage);

        }
    }
    //  the interface
    public interface OnCancelListener {
        void onBookingCancel(int position, GuestMasterDetail data); // Method to notify activity
    }

    // Method to get selected items
    public List<GuestMasterDetail> getSelectedGuests() {
        List<GuestMasterDetail> selectedGuests = new ArrayList<>();
        for (int pos : selectedPositions) {
            selectedGuests.add(stayBookingList.get(pos));
        }
        return selectedGuests;
    }
}
