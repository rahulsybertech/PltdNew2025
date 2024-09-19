package com.syber.ssspltd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;

import java.util.List;

public class StockAdptr  extends RecyclerView.Adapter<StockAdptr.MyViewHolder>{

    private Context mContext;
    private List<String> detailList;
    public static int pq;


    public StockAdptr(Context mContext, List<String> detailList) {
        this.mContext = mContext;
        this.detailList = detailList;
    }

    @Override
    public StockAdptr.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list, parent, false);
        return new StockAdptr.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(StockAdptr.MyViewHolder holder, final int position) {
//        final Datum datum = detailList.get(position);
//        holder.supplier.setText(datum.getSupplierName());
//        holder.date.setText(datum.getBillDate());
//        holder.saleParty.setText(datum.getSalesParty());
//        holder.pcs.setText(datum.getPieces());
//        holder.amt.setText(datum.getNetAmt()+"");
//        holder.item.setText(datum.getItem());
//        holder.purchase_no.setText(datum.getGRSNo());

    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder /* implements View.OnClickListener*/ {
        TextView pcs,amt,item,supplier,saleParty,date,purchase_no;

        public MyViewHolder(View itemView) {
            super(itemView);
            supplier = itemView.findViewById(R.id.supplier);
            saleParty = itemView.findViewById(R.id.sale_party);
            date = itemView.findViewById(R.id.date_no);
            pcs = itemView.findViewById(R.id.pcs_no);
            amt = itemView.findViewById(R.id.amt_no);
            item = itemView.findViewById(R.id.item_no);
            purchase_no = itemView.findViewById(R.id.purchase_no);
            //purchase_no.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.purchase_no:
//                    Intent viewIntent =
//                            new Intent("android.intent.action.VIEW",
//                                    Uri.parse(detailList.get(getAdapterPosition()).getPDFFilePath()));
//                    mContext.startActivity(viewIntent);
//            }
//            // showDialogue(detailList.get(getAdapterPosition()).getItem());
//        }
    }

//    private void showDialogue(String text) {
//        final Dialog dialog = new Dialog(mContext);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.setCancelable(false);
//        TextView txt = dialog.findViewById(R.id.txt_dialog);
//        txt.setText(text);
//
//        final TextView submit = (TextView) dialog.findViewById(R.id.submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//
//        });
//        dialog.show();
//    }
}
