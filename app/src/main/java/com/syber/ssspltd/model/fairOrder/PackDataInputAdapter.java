package com.syber.ssspltd.model.fairOrder;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.syber.ssspltd.R;
import com.syber.ssspltd.model.fairOrder.model.ItemsData;
import com.syber.ssspltd.model.fairOrder.model.PackTypeItem;

import java.util.ArrayList;
import java.util.List;

public class PackDataInputAdapter extends RecyclerView.Adapter<PackDataInputAdapter.PackViewHolder> {

    private final Context context;
    private final List<PackTypeItem> itemList = new ArrayList<>();
    private final List<ItemsData> suggestions = new ArrayList<>();
    public String totalQuantity; // User input final qty
    public String userInputQuantity; // User input final qty
    private final ItemClickCallback callback;

    public interface ItemClickCallback {
        void onItemClick(String itemName,String selectedItemName,String itemID, int adapterPosition);
    }



    public PackDataInputAdapter(Context context, String totalQuantity, ItemClickCallback callback) {
        this.context = context;
        this.callback = callback;
        itemList.add(new PackTypeItem("", "", ""));
        this.totalQuantity = totalQuantity;


    }
    public void updateItemNameAt(int position, String name,String itemID) {
        if (position >= 0 && position < itemList.size()) {
            itemList.get(position).itemName = name;
            itemList.get(position).itemID = itemID;
           notifyItemChanged(position);
        } else {
            // Handle error: position out of bounds
            Log.e("Adapter", "Invalid position: " + position + ", size: " + suggestions.size());
        }
    }


    public void setSuggestions(List<ItemsData> data) {
        suggestions.clear();
        suggestions.addAll(data);
        notifyDataSetChanged();
    }
    public void setItemNameAtPosition(int position, String selectedItemName,String itemID) {
        if (position >= 0 && position < itemList.size()) {
            PackTypeItem item = itemList.get(position);
            item.itemName = selectedItemName;  // update data
            item.itemID = itemID;  // update data
            notifyItemChanged(position);       // refresh item in RecyclerView
        }
    }


    public List<PackTypeItem> getList() {
        return itemList;
    }

    @NonNull
    @Override
    public PackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pack_data_input, parent, false);
        return new PackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackViewHolder holder, int position) {
        PackTypeItem item = itemList.get(position);
        holder.etItem.setText(item.itemName);
        holder.etQuantity.setText(item.itemQuantity);

        boolean isLastRow = position == itemList.size() - 1;

        holder.etItem.setOnClickListener(v -> {
            if (callback != null) {
                callback.onItemClick("Some item name here","","",position); // You can pass dynamic name
            }
        });
// Disable editing if not last row
        holder.etItem.setEnabled(isLastRow);
        holder.etQuantity.setEnabled(isLastRow);

// Set background color
        int bgColor = isLastRow ? Color.TRANSPARENT : Color.parseColor("#F5F5DC");
       // holder.etItem.setBackgroundColor(bgColor);
      //  holder.etQuantity.setBackgroundColor(bgColor);

  /*      ArrayAdapter<ItemsData> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, suggestions);
        holder.etItem.setAdapter(adapter);
        holder.etItem.setThreshold(1);*/

// Only set listeners if editable
        if (isLastRow) {
            holder.etQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    String finalQtyStr = totalQuantity;
                    String availableStr = holder.etQuantity.getText().toString();

                    if (!finalQtyStr.isEmpty() && !availableStr.isEmpty()) {
                        int finalQty = Integer.parseInt(finalQtyStr);
                        int available = Integer.parseInt(availableStr);

                        if (available > finalQty) {

                            holder.etQuantity.setError("Cannot exceed available qty");
                            holder.etQuantity.setText("");
                        } else {
                          //  item.finalQuantity = finalQtyStr;
                        }
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
           /* holder.etQuantity.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    item.itemQuantity = s.toString();
                }
            });*/
        }

        holder.btnAction.setVisibility(View.VISIBLE); // Ensure it's visible
        holder.btnAction.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // For clarity
        holder.btnAction.setImageResource(
                isLastRow ? R.drawable.ic_plus : R.drawable.delete_new);

        holder.btnAction.setOnClickListener(v -> {
            if (isLastRow) {
                String itemName = holder.etItem.getText().toString().trim();
                String itemQty = holder.etQuantity.getText().toString().trim();

                if (!itemName.isEmpty() && !itemQty.isEmpty()) {
                    // Add the new item just before the last row
                    itemList.add(itemList.size() - 1, new PackTypeItem(item.itemID, itemName, itemQty));

                    // Clear data in the last (editable) item
                    PackTypeItem lastItem = itemList.get(itemList.size() - 1);
                    lastItem.itemName = "";
                    lastItem.itemQuantity = "";

                    // Notify adapter to refresh UI
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Fill item and quantity", Toast.LENGTH_SHORT).show();
                }
            } else {
                itemList.remove(position);
                notifyDataSetChanged();
            }
        });








    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class PackViewHolder extends RecyclerView.ViewHolder {
        EditText etItem;
        EditText etQuantity;
        AppCompatImageView btnAction;

        public PackViewHolder(@NonNull View itemView) {
            super(itemView);
            etItem = itemView.findViewById(R.id.etItem);
            etQuantity = itemView.findViewById(R.id.etQuantity);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}


