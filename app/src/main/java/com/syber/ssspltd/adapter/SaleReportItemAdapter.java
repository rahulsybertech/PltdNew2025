package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.salePuchaseNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datum.getPurchaseNo().equals(""))
                {

                }
                else {
                    String url = (!datum.getPurPDFPath().equals("")) ? datum.getPurPDFPath() :"http://nopdffound";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    mContext.startActivity(i);
                    Log.e("url",url);
                }

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

        public MyViewHolder(View itemView) {
            super(itemView);

            salePuchaseNo = itemView.findViewById(R.id.salePuchaseNo);
            saleSupplier = itemView.findViewById(R.id.saleSupplier);
            salePCS = itemView.findViewById(R.id.salePCS);
            saleAmount = itemView.findViewById(R.id.saleAmount);
        }
    }
}
