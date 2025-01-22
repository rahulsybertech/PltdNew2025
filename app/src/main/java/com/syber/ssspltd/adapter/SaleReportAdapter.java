package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.SaleReportResponse.SaleReportResult;
import com.syber.ssspltd.activitys.ViewPDFActivity;

import java.util.List;

public class SaleReportAdapter extends RecyclerView.Adapter<SaleReportAdapter.MyViewHolder> {

    boolean setTrue = false;
    private Context mContext;
    private List<SaleReportResult> saleReportDetails;

    public SaleReportAdapter(Context mContext, List<SaleReportResult> detailList) {
        this.mContext = mContext;
        this.saleReportDetails = detailList;
    }

    @Override
    public SaleReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_report_recyclerview, parent, false);
        return new SaleReportAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaleReportAdapter.MyViewHolder holder, final int position) {

        final SaleReportResult datum = saleReportDetails.get(position);
        holder.saleBillNo.setText(datum.getBillNo());
        holder.saleSubParty.setText(datum.getSubParty());
        holder.saleDate.setText(datum.getBillDate());
        holder.saleTransport.setText(datum.getTransport());
        holder.saleLR_Date.setText(datum.getLRDate());
        holder.netAmt_sale.setText(datum.getSAmount());
        if (datum.getPackingVideoURL().equals("") || datum.getPackingVideoURL().equalsIgnoreCase("null")) {
            holder.videoLink.setVisibility(View.GONE);
        } else {
            holder.videoLink.setVisibility(View.VISIBLE);
        }
        // holder.videoLink.setText(datum.getPackingVideoURL().equals("")?"NA":datum.getPackingVideoURL());
        if (datum.getBiltyNo().equals("")) {
            holder.saleBiltyNo.setText("--");
            //holder.saleLR_Date.setText("-");

        } else {
            holder.saleBiltyNo.setText(datum.getBiltyNo());
            // holder.saleLR_Date.setText(datum.getLRDate());
        }

        holder.saleBillNo.setOnClickListener(v -> {

            if (!datum.getBillNo().equals("")) {
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getPDFPath()));
            } else {
                Toast.makeText(mContext, "No PDF File Available", Toast.LENGTH_SHORT).show();
            }

        });
        holder.saleBiltyNo.setOnClickListener(v -> {

            if (!datum.getBiltyNo().equals("")) {
                mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                        .putExtra("pdfUrl", datum.getBiltyPDFPath()));
            } else {
                Toast.makeText(mContext, "No PDF File Available", Toast.LENGTH_SHORT).show();
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.reclerItem.setLayoutManager(linearLayoutManager);
        SaleReportItemAdapter finanacialYearListAdapter = new SaleReportItemAdapter(mContext, datum.getSaleReportSecondaryData());
        holder.reclerItem.setAdapter(finanacialYearListAdapter);
        holder.videoLink.setOnClickListener(view -> {

            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(datum.getPackingVideoURL()));
            mContext.startActivity(viewIntent);
        });
//        holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_down));

        if (datum.isOpenItem() == true) {
            holder.reclerItem.setVisibility(View.VISIBLE);
            datum.setOpenItem(true);
            holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));

        } else {
            holder.reclerItem.setVisibility(View.GONE);
            datum.setOpenItem(false);
            holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));


        }
        holder.plusClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datum.isOpenItem() == true) {
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_down_24));
                    holder.reclerItem.setVisibility(View.GONE);
                    datum.setOpenItem(false);
                } else if (datum.isOpenItem() == false) {
                    holder.reclerItem.setVisibility(View.VISIBLE);
                    datum.setOpenItem(true);
                    holder.plusClick.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_arrow_drop_up_24));

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.e("Size", saleReportDetails.size() + "");
        return saleReportDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView saleBillNo, saleSubParty, saleDate, saleTransport, saleBiltyNo, saleLR_Date, netAmt_sale, videoLink;
        LinearLayout ll_sup, videoLinkLL;
        ImageView plusClick;
        TextView dr_amt, bal_name;
        RecyclerView reclerItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            videoLink = itemView.findViewById(R.id.videoLink);
            saleBillNo = itemView.findViewById(R.id.saleBillNo);
            saleSubParty = itemView.findViewById(R.id.saleSubParty);
            saleDate = itemView.findViewById(R.id.saleDate);
            saleTransport = itemView.findViewById(R.id.saleTransport);
            saleBiltyNo = itemView.findViewById(R.id.saleBiltyNo);
            saleLR_Date = itemView.findViewById(R.id.saleLR_Date);
            netAmt_sale = itemView.findViewById(R.id.netAmt_sale);
            plusClick = itemView.findViewById(R.id.plusClick);
            reclerItem = itemView.findViewById(R.id.reclerItem);
        }
    }
}
