package com.syber.ssspltd.adapter.fairOrder;

import static com.syber.ssspltd.Utils.AppController.mContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;

import com.syber.ssspltd.model.fairOrder.OrderRequest;
import com.syber.ssspltd.model.fairOrder.model.OrderRequestNew;
import com.syber.ssspltd.model.fairOrder.model.PackTypeItem;

import java.util.ArrayList;
import java.util.List;

public class PackTypeAdapter extends RecyclerView.Adapter<PackTypeAdapter.PackViewHolder> {

    private final Context context;

    private List<OrderRequestNew> itemList;
    private OnActionClickListener listener;
    SubItemAdapter subItemAdapter;

    public interface OnActionClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }



    public PackTypeAdapter(Context context, ArrayList<OrderRequestNew> itemList, OnActionClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }







    public List<OrderRequestNew> getList() {
        return itemList;
    }

    @NonNull
    @Override
    public PackTypeAdapter.PackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pack_type_header, parent, false);
        return new PackTypeAdapter.PackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackTypeAdapter.PackViewHolder holder, int position) {
        OrderRequestNew item = itemList.get(position);

        holder.tvType.setText(item.getPcsId());
        holder.tvQuantity.setText("Qty :"+ item.getTotalQty());
        holder.tvAmount.setText("Amt :"+ item.getTotalAmount());
        holder.plusClickNew.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_down));

        holder.plusClickNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isOpenItem() == true) {
                    holder.plusClickNew.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_drop_down_24));
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.btnEditItem.setVisibility(View.GONE);
                    holder.btnDeleteItem.setVisibility(View.GONE);
                    holder.clSubTitle.setVisibility(View.GONE);

                    item.setOpenItem(false);
                   // notifyItemChanged(position);
                } else if (item.isOpenItem() == false) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.btnEditItem.setVisibility(View.VISIBLE);
                    holder.btnDeleteItem.setVisibility(View.VISIBLE);
                    holder.clSubTitle.setVisibility(View.VISIBLE);
                    item.setOpenItem(true);
                    holder.plusClickNew.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_drop_up_24));
                   // notifyItemChanged(position);

                }

            }
        });

        // SubItem RecyclerView
         subItemAdapter = new SubItemAdapter(context,item.getItemDetail(),
                new SubItemAdapter.OnActionClickListener() {
            @Override
            public void onEditClicked(int position) {
                // Handle edit
            }

            @Override
            public void onDeleteClicked(int position) {
                itemList.remove(position);
                subItemAdapter.notifyItemRemoved(position);
            }
        }
        );

        holder.recyclerView.setAdapter(subItemAdapter);

        holder.btnEditItem.setOnClickListener(v -> listener.onEditClicked(position));
        holder.btnDeleteItem.setOnClickListener(v -> listener.onDeleteClicked(position));


    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class PackViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvQuantity, tvAmount;
        ConstraintLayout clSubTitle;
        ImageView plusClickNew;
        RecyclerView recyclerView;
        TextView btnEditItem, btnDeleteItem;

        public PackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvType);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            clSubTitle = itemView.findViewById(R.id.clSubTitle);
            plusClickNew = itemView.findViewById(R.id.plusClickNew);
            btnEditItem = itemView.findViewById(R.id.btnEditItem);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);

            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.setNestedScrollingEnabled(false);
        }
    }
}



