package com.syber.ssspltd.adapter.fairOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.model.fairOrder.model.ItemDetailNew;
import com.syber.ssspltd.model.fairOrder.model.PackTypeItem;

import java.util.List;

public class SubItemAdapter  extends RecyclerView.Adapter<SubItemAdapter.PackViewHolder> {

    private final Context context;

    private List<ItemDetailNew> itemList;
    private SubItemAdapter.OnActionClickListener listener;

    public interface OnActionClickListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }



    public SubItemAdapter(Context context, List<ItemDetailNew> itemList, SubItemAdapter.OnActionClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }







    public List<ItemDetailNew> getList() {
        return itemList;
    }

    @NonNull
    @Override
    public SubItemAdapter.PackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pack_list, parent, false);
        return new SubItemAdapter.PackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemAdapter.PackViewHolder holder, int position) {
        ItemDetailNew item = itemList.get(position);

    //    holder.tvType.setText(item.getItemQty());
        holder.tvItem.setText(String.valueOf(item.getItemName()));
        holder.tvQty.setText(String.valueOf(item.getItemQty()));



      //  holder.btnEditItem.setOnClickListener(v -> listener.onEditClicked(position));
       // holder.btnDeleteItem.setOnClickListener(v -> listener.onDeleteClicked(position));


    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class PackViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvItem, tvQty;
        RecyclerView recyclerView;
        TextView btnEditItem, btnDeleteItem;

        public PackViewHolder(@NonNull View itemView) {
            super(itemView);
          //  tvType = itemView.findViewById(R.id.tvType);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvQty = itemView.findViewById(R.id.tvQty);
         //   recyclerView = itemView.findViewById(R.id.recyclerView);
         //   btnEditItem = itemView.findViewById(R.id.btnEditItem);
         //   btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);

       //     recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
         //   recyclerView.setNestedScrollingEnabled(false);
        }
    }
}




