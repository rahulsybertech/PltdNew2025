package com.syber.ssspltd.adapter.saleFilterAdapter.SuppAdminAdapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.ChooseCategries;
import com.syber.ssspltd.response.ChooseCatagriesRespo.EmployeeListResult;

import java.util.List;

public class EmployeeDialogAdapte extends RecyclerView.Adapter<EmployeeDialogAdapte.MyViewHolder>   {


    private Activity mContext;
    private List<EmployeeListResult> data;

    public static int pq;

    public EmployeeDialogAdapte(Activity mContext, List<EmployeeListResult> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public EmployeeDialogAdapte.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new EmployeeDialogAdapte.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EmployeeDialogAdapte.MyViewHolder holder, final int position) {

        final EmployeeListResult product;
        product = data.get(position);
        holder.name.setText(product.getName());
        Log.e("name",product.getName());

        if (position % 2 == 0) {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        } else {
            holder.ll.setBackgroundColor(ContextCompat.getColor(mContext, R.color.eee));

        }


    }

    @Override
    public int getItemCount() {
        Log.e("Size",data.size()+"");
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
            cardView = itemView.findViewById(R.id.product_card_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChooseCategries) mContext).setEmployeeList(data.get(getAdapterPosition()));
                    ChooseCategries.partyCode=data.get(getAdapterPosition()).getPartyCode();
                    ChooseCategries.mobNo=data.get(getAdapterPosition()).getUserMobileNo();

                }
            });

        }
    }
}
