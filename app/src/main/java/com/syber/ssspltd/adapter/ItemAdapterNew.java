package com.syber.ssspltd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.activitys.FairOrderActivity;
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity;

import com.syber.ssspltd.response.ItemModel;

import java.util.List;

public class ItemAdapterNew extends RecyclerView.Adapter<ItemAdapterNew.MyViewHolder> {

    private Activity mContext;
    private List<ItemModel> data;
    public static boolean sType = false;
    public static int pq;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected(String selectedItemName,String itemID);
    }

    public ItemAdapterNew(Activity mContext, List<ItemModel> data,OnItemSelectedListener listener) {
        this.mContext = mContext;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ItemAdapterNew.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarch_list, parent, false);
        return new ItemAdapterNew.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemAdapterNew.MyViewHolder holder, final int position) {

        final ItemModel product;
        product = data.get(position);
        holder.name.setText(product.getItemName());

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

        TextView name;
        LinearLayout ll;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ll = itemView.findViewById(R.id.ll);
            cardView = itemView.findViewById(R.id.product_card);
          /*  itemView.setOnClickListener(v -> {
                if (mContext instanceof SupplierOrderFormActivity) {
                    ((SupplierOrderFormActivity) mContext).setItemName(data.get(getAdapterPosition()));
                }else if(mContext instanceof FairOrderActivity){
                    ((FairOrderActivity) mContext).setItemName(data.get(getAdapterPosition()));
                }


            });*/

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    String selectedName = data.get(pos).getItemName();
                    String selectId = data.get(pos).getItemID();
                    // Trigger callback when an item is clicked
                    listener.onItemSelected(selectedName,selectId);


                }
            });
        }
    }
}

