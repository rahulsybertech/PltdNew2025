package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.CourierReport.CourierReportResult;

import java.util.List;


public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.MyViewHolder> {

    private Context mContext;
    private List<CourierReportResult> courierDetails;

    public CourierAdapter(Context mContext, List<CourierReportResult> detailList) {
        this.mContext = mContext;
        this.courierDetails = detailList;
    }

    @Override
    public CourierAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courier_report_recyclerview, parent, false);
        return new CourierAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourierAdapter.MyViewHolder holder, final int position) {

        final CourierReportResult datum = courierDetails.get(position);
        holder.courierName.setText(datum.getCourierName());
        holder.courierStation.setText(datum.getStation());
        holder.courierDate.setText(datum.getDate());
        holder.courierBillNo.setText(datum.getSaleBillNumber());
        holder.courierNo.setText(datum.getCourierNo());

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


    @Override
    public int getItemCount() {
        return courierDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView courierName,courierStation,courierDate,courierBillNo,courierNo;
        LinearLayout ll_sup,ll_sale;
        ImageView call;
        TextView dr_amt,bal_name;

        public MyViewHolder(View itemView) {
            super(itemView);

            courierName = itemView.findViewById(R.id.courierName);
            courierStation = itemView.findViewById(R.id.courierStation);
            courierDate = itemView.findViewById(R.id.courierDate);
            courierBillNo=itemView.findViewById(R.id.courierBillNo);
            courierNo = itemView.findViewById(R.id.courierNo);
        }
    }
}
