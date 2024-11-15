package com.syber.ssspltd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ViewPDFActivity;
import com.syber.ssspltd.response.LedgerReportResponse.LedgerReportResult;


import java.util.List;

public class LedgerReportAdapter extends RecyclerView.Adapter<LedgerReportAdapter.MyViewHolder> {

    final private Context mContext;
    final private List<LedgerReportResult> ledgerReportDetails;
    public  static  String currentBal,avrgDay;

    public LedgerReportAdapter(Context mContext, List<LedgerReportResult> detailList) {
        this.mContext = mContext;
        this.ledgerReportDetails = detailList;
    }

    @NonNull
    @Override
    public LedgerReportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ledger_report_recy, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LedgerReportAdapter.MyViewHolder holder, final int position) {

        final LedgerReportResult datum = ledgerReportDetails.get(position);
        holder.ledger_date.setText(datum.getBillDate());
        holder.ac_id.setText(datum.getAccountID());
        holder.ledger_decri.setText(datum.getBLDescription());
        holder.balance_ledger.setText(datum.getBalance());
        currentBal=datum.getBalance();
        avrgDay=datum.getAvgDays();
        if (datum.getAccountID().equals(""))
        {
            holder.ac_id.setVisibility(View.GONE);
        }
        else
        {
            holder.ac_id.setVisibility(View.VISIBLE);
        }
           String str = datum.getCreditAmt();
          str.replaceAll(",", "");
          String strr = str.replaceAll(",", "");
          Log.e("strr",strr);
          if (Integer.parseInt(strr) > 0) {
              holder.ledger_credit.setText(datum.getCreditAmt());
              holder.ledger_credit.setTextColor(mContext.getResources().getColor(R.color.gerrn));
          }else {
              holder.ledger_credit.setText("");
          }

            String str1 = datum.getDebitAmt();
           String str2 = str1.replaceAll("[^a-zA-Z0-9]", "");
           Log.e("str1", str2);

        if (Integer.parseInt(str2) >0) {
            holder.ledger_debit.setText(datum.getDebitAmt());
            holder.ledger_debit.setTextColor(mContext.getResources().getColor(R.color.red));
        }else {
            holder.ledger_debit.setText("");
        }
            holder.ledger_decri.setOnClickListener(v -> {
                Log.i("TaG","ledger report pdf -=-=-=-=-=-=-=-=>" + datum.getPDFPath());
                if (!datum.getPDFPath().equals(""))
                {
                    mContext.startActivity(new Intent(mContext, ViewPDFActivity.class)
                            .putExtra("pdfUrl",datum.getPDFPath()));
                }
                else {
                    Toast.makeText(mContext, "PDF Not Available", Toast.LENGTH_SHORT).show();
                }

            });
        if (datum.getPDFPath().equals(""))
        {
            holder.ledger_decri.setTextColor(mContext.getResources().getColor(R.color.solid_gray));

        }
        else
        {
            holder.ledger_decri.setTextColor(mContext.getResources().getColor(R.color.light_red));

        }

//
//        holder.bal_name.setText(tt);

    }


    @Override
    public int getItemCount() {
        return ledgerReportDetails.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ledger_date,ac_id,ledger_debit,ledger_decri,ledger_credit,balance_ledger;


        public MyViewHolder(View itemView) {
            super(itemView);

            ledger_date = itemView.findViewById(R.id.billDate);
            ac_id = itemView.findViewById(R.id.accountID);
            ledger_debit = itemView.findViewById(R.id.ledger_debit);
            ledger_decri = itemView.findViewById(R.id.blDescription);
            balance_ledger = itemView.findViewById(R.id.balance_ledger);
            ledger_credit = itemView.findViewById(R.id.ledger_credit);
        }
    }
}
