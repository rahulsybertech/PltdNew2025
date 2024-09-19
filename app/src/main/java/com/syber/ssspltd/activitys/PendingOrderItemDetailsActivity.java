package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.adapter.PendingOrderItemDetailsAdapter;
import com.syber.ssspltd.response.PendingOrderReport.PendingOrderReportResult;

import java.text.DecimalFormat;

public class PendingOrderItemDetailsActivity extends AppCompatActivity {

    PendingOrderReportResult orderdetailList;
    PendingOrderItemDetailsAdapter pendingOrderItemDetailsAdapter;
    RecyclerView recyclerView;
    Context mContext = this;
    TextView totalAmt,totalQty,orderNo;
    DecimalFormat twoDForm = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order_item_details);

        ImageView backImage =findViewById(R.id.back3);
        totalAmt = findViewById(R.id.total_amt);
        totalQty = findViewById(R.id.total_qty);
        orderNo = findViewById(R.id.pendingOrder_no);

        backImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());

        TextView backImage2 =findViewById(R.id.back2);
        backImage2.setText("Order Details");

       // orderdetailList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        Log.e("list",new Gson().toJson(getIntent().getSerializableExtra("orderlist"))+"");
        orderdetailList = (PendingOrderReportResult) getIntent().getSerializableExtra("orderlist");
        Log.e("orderdetail",new Gson().toJson(orderdetailList));
        orderNo.setText(orderdetailList.getOrderNo());
        pendingOrderItemDetailsAdapter = new PendingOrderItemDetailsAdapter(mContext,orderdetailList.getOrderdetail());
        recyclerView.setAdapter(pendingOrderItemDetailsAdapter);
        pendingOrderItemDetailsAdapter.notifyDataSetChanged();

refreshMainList();


    }

    private void refreshMainList() {
        Double grand = 0.0;
        int totalqty = 0;
        try {
            for (int p = 0; p < orderdetailList.getOrderdetail().size(); p++) {
                double qty = Double.parseDouble(orderdetailList.getOrderdetail().get(p).getQty() + "");
                if (qty > 0) {
                    double price = Double.parseDouble(orderdetailList.getOrderdetail().get(p).getAmount() + "");
                   // Double total = (qty * price);
                    Double total = (price);
                    double ttqty = qty;
                    grand = Double.valueOf(twoDForm.format(grand + total));
                    totalqty = Integer.valueOf(twoDForm.format(totalqty + ttqty));

                }
            }
        } catch (Exception e) {
            Log.e("err", e.toString());
        }
        totalAmt.setText(grand + "");
        totalQty.setText(totalqty + "");
        if (grand != null) {
            totalAmt.setText(Lazy.NumberFormate(grand + ""));
        } else {
            totalAmt.setText("0.0");
        }

    }
}