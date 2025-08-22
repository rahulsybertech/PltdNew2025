package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.SaleReportResponse.SaleReportSecondaryDatum;

import java.util.List;

public class SaleReportItemAdapter  extends RecyclerView.Adapter<SaleReportItemAdapter.MyViewHolder>{
    private Context mContext;
    private List<SaleReportSecondaryDatum> SaleReportSecondaryDetails;

    public SaleReportItemAdapter(Context mContext, List<SaleReportSecondaryDatum> detailList) {
        this.mContext = mContext;
        this.SaleReportSecondaryDetails = detailList;
    }

    @Override
    public SaleReportItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_report_itm_recy, parent, false);
        return new SaleReportItemAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaleReportItemAdapter.MyViewHolder holder, final int position) {

        final SaleReportSecondaryDatum datum = SaleReportSecondaryDetails.get(position);
        holder.salePuchaseNo.setText(datum.getPurchaseNo());
        holder.saleSupplier.setText(datum.getSupplier());
        holder.salePCS.setText(datum.getPcs());
        holder.saleAmount.setText(datum.getPAmount());
        if(datum.getPackingSlipPath().equals("")){
            holder.rlPdf.setVisibility(View.INVISIBLE);
        }else {
            holder.rlPdf.setVisibility(View.VISIBLE);
        }
        holder.salePuchaseNo.setOnClickListener(v -> {
            String originalUrl = datum.getPurPDFPath();

            if (originalUrl == null || originalUrl.isEmpty()) {
                Toast.makeText(mContext, "No PDF found.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add timestamp to bypass cache
            String finalUrl = originalUrl + (originalUrl.contains("?") ? "&" : "?") + "t=" + System.currentTimeMillis();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(finalUrl));
            mContext.startActivity(intent);

            Log.e("PDF_URL", finalUrl); // Check this in Logcat
        });


        holder.rlPdf.setOnClickListener(v -> {
            String originalUrl = datum.getPackingSlipPath();
            if (originalUrl == null || originalUrl.isEmpty())
            {
                Toast.makeText(mContext, "No P.Slip Found.", Toast.LENGTH_SHORT).show();
            }
            else {

                // Add timestamp to bypass cache
                String finalUrl = originalUrl + (originalUrl.contains("?") ? "&" : "?") + "t=" + System.currentTimeMillis();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(finalUrl));
                mContext.startActivity(i);
                Log.e("url",finalUrl);
            }

        });

    }


    @Override
    public int getItemCount() {
        return SaleReportSecondaryDetails.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView salePuchaseNo,saleSupplier,salePCS,saleAmount;
        RecyclerView ceateNote_itemRecyler;
        RelativeLayout rlPdf;

        public MyViewHolder(View itemView) {
            super(itemView);

            salePuchaseNo = itemView.findViewById(R.id.salePuchaseNo);
            rlPdf = itemView.findViewById(R.id.rlPdf);
            saleSupplier = itemView.findViewById(R.id.saleSupplier);
            salePCS = itemView.findViewById(R.id.salePCS);
            saleAmount = itemView.findViewById(R.id.saleAmount);
        }
    }
}
