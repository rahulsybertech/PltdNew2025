package com.syber.ssspltd.Utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.syber.ssspltd.R;
import com.syber.ssspltd.model.fairOrder.model.ItemDetailNew;
import com.syber.ssspltd.model.fairOrder.model.OrderRequestNew;
import com.syber.ssspltd.model.fairOrder.model.PackTypeItem;

import java.util.ArrayList;

public class EditOrderBottomSheet extends BottomSheetDialogFragment {

    public interface OnOrderEditedListener {
        void onOrderUpdated(OrderRequestNew updatedOrder, int position);
    }

    private OrderRequestNew order;
    private int position;
    private OnOrderEditedListener listener;

    public EditOrderBottomSheet(OrderRequestNew order, int position, OnOrderEditedListener listener) {
        this.order = order;
        this.position = position;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summery, container, false);

        EditText qtyEt = view.findViewById(R.id.qty);
        EditText amountEt = view.findViewById(R.id.amount);
        Spinner packTypeSpinner = view.findViewById(R.id.type);
        RecyclerView innerRecycler = view.findViewById(R.id.recyclerItem);
        Button saveBtn = view.findViewById(R.id.btnSave);

        // Fill with existing data
        qtyEt.setText(String.valueOf(order.getTotalQty()));
        amountEt.setText(String.valueOf(order.getTotalAmount()));
        
        // Spinner selection logic
        ArrayAdapter<String> spinnerAdapter = (ArrayAdapter<String>) packTypeSpinner.getAdapter();
        int selectedPos = spinnerAdapter.getPosition(order.getPcsId());
        packTypeSpinner.setSelection(selectedPos);

        // Setup inner item list
        ArrayList<PackTypeItem> packTypeItems = new ArrayList<>();
        for (ItemDetailNew itemDetail : order.getItemDetail()) {
            PackTypeItem item = new PackTypeItem();
            item.itemID = itemDetail.getItemId();
            item.itemName = itemDetail.getItemName();
            item.itemQuantity = itemDetail.getItemQty();
            packTypeItems.add(item);
        }

/*        PackTypeItemAdapter itemAdapter = new PackTypeItemAdapter(packTypeItems); // your existing adapter
        innerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        innerRecycler.setAdapter(itemAdapter);*/

        saveBtn.setOnClickListener(v -> {
            OrderRequestNew updatedOrder = new OrderRequestNew();
            updatedOrder.setTotalQty(Integer.parseInt(qtyEt.getText().toString()));
            updatedOrder.setTotalAmount(Integer.parseInt(amountEt.getText().toString()));
            updatedOrder.setPcsId(packTypeSpinner.getSelectedItem().toString());

          /*  ArrayList<ItemDetailNew> updatedDetails = new ArrayList<>();
            for (PackTypeItem i : itemAdapter.getList()) {
                if (!i.itemName.trim().isEmpty() && !i.itemQuantity.trim().isEmpty()) {
                    ItemDetailNew item = new ItemDetailNew();
                    item.setItemId(i.itemID);
                    item.setItemName(i.itemName);
                    item.setItemQty(i.itemQuantity);
                    item.setAmount("10"); // example
                    updatedDetails.add(item);
                }
            }*/

          //  updatedOrder.setItemDetail(updatedDetails);

            listener.onOrderUpdated(updatedOrder, position);
            dismiss();
        });

        return view;
    }
}
