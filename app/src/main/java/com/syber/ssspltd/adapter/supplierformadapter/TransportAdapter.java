package com.syber.ssspltd.adapter.supplierformadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.response.TransportModel;
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;

import java.util.List;

public class TransportAdapter extends RecyclerView.Adapter<TransportAdapter.MyViewHolder> {

    private Activity mContext;
    private List<TransportModel> data;

    public static int pq;

    public TransportAdapter(Activity mContext, List<TransportModel> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final TransportModel product;
        product = data.get(position);
        holder.name.setText(product.getTransportName());

        if (position % 2 == 0) {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        } else {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.eee));

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView emp, name, mobile, date, sec_order_val;
        LinearLayout ll, ll2;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

            ll = itemView.findViewById(R.id.ll);
            cardView = itemView.findViewById(R.id.product_card);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((SupplierOrderFormActivity) mContext).setTransport(data.get(getAdapterPosition()));
                }
            });

        }


    }
}
