package com.syber.ssspltd.activitys.supplierorderform;

import static com.syber.ssspltd.Constants.URLConstants.ORDER_REPORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Interface.RefreshOrderReport;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.supplierformadapter.SupplierOrderReportAdptr;
import com.syber.ssspltd.databinding.ActivitySupplierReportBinding;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;
import com.syber.ssspltd.response.SupplierOrderReport.SupplierReportPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SupplierReportActivity extends AppCompatActivity implements RefreshOrderReport {
    private ActivitySupplierReportBinding binding;

    SupplierOrderReportAdptr supplierOrderReportAdptr;
    List<OrderDetail> orderDetailList, orderListData;
    public static ArrayList<OrderDetail> oData = new ArrayList<>();
    Type listType1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupplierReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderDetailList = new ArrayList<>();
        orderListData = new ArrayList<>();
        listType1 = new TypeToken<SupplierReportPojo>() {
        }.getType();
//        binding.btnPending.setBackgroundColor(getResources().getColor(R.color.green));
        binding.btnConfirm.setBackgroundColor(getResources().getColor(R.color.green));
        binding.btnHold.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        supplierOrderReportAdptr = new SupplierOrderReportAdptr(this, orderDetailList, this);
        binding.recyclerView.setAdapter(supplierOrderReportAdptr);
        binding.btnPending.setOnClickListener(v -> {
                    getPending("APPROVAL PENDING",false);
                    binding.btnPending.setBackgroundColor(getResources().getColor(R.color.green));
                    binding.btnConfirm.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
                    binding.btnHold.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
                }
        );
        binding.btnConfirm.setOnClickListener(v -> {
            getPending("CONFIRM",false);
            binding.btnConfirm.setBackgroundColor(getResources().getColor(R.color.green));
            binding.btnPending.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
            binding.btnHold.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        });
        binding.btnHold.setOnClickListener(v -> {
            getPending("HOLD",false);
            binding.btnHold.setBackgroundColor(getResources().getColor(R.color.green));
            binding.btnPending.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
            binding.btnConfirm.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
        });
//        getPending("APPROVAL PENDING");
        getPending("CONFIRM",true);

    }

    private void getPending(String status , boolean isFirstTIme) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_REPORT, response -> {
//            loading.setVisibility(View.GONE);
            Log.e("pendingRespo", response);
            try {
                SupplierReportPojo pojo = new Gson().fromJson(response, listType1);
                orderDetailList.clear();
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    orderDetailList.addAll(pojo.getOrderDetails());
                    supplierOrderReportAdptr.notifyDataSetChanged();
//                    noData.setVisibility(View.GONE);
                } else {
                    supplierOrderReportAdptr.notifyDataSetChanged();
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                    if (isFirstTIme) {
                        getPending("HOLD",false);
                        binding.btnHold.setBackgroundColor(getResources().getColor(R.color.green));
                        binding.btnPending.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
                        binding.btnConfirm.setBackgroundColor(getResources().getColor(R.color.colorPrimary1));
                    }
//                    AlertUtil.responseElse(this, "Pending Order ", pojo.getResponseMessage() + "");
//                    noData.setVisibility(View.VISIBLE);

                }
            } catch (Exception e) {
                binding.includeProgress.progress.setVisibility(View.GONE);
                binding.includeProgress.noData.setVisibility(View.VISIBLE);
                supplierOrderReportAdptr.notifyDataSetChanged();
                AlertUtil.responseExecption(this, "Pending Oder", e.toString());
                Log.e("Exce", e.toString());
//                noData.setVisibility(View.VISIBLE);
//                noData.setText("Retry!");
//                noData.setOnClickListener(v -> {
//                    noData.setVisibility(View.GONE);
//                    getPending(SharedPref.read(SharedPref.USERMOBILE, ""), dateFrom, dateTo);
//                });
            }
        }, error -> {
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
            AlertUtil.responseError(this, "Pending Order", error.toString());
//            loading.setVisibility(View.GONE);
//            noData.setVisibility(View.VISIBLE);
//            noData.setText("Retry!");
//            noData.setOnClickListener(v -> {
//                noData.setVisibility(View.GONE);
//                getPending(SharedPref.read(SharedPref.USERMOBILE, ""), dateFrom, dateTo);
//            });
            //  progress.dismiss();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"AccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\"" +
                        ",\"OrderStatus\":\"" + status + "\"}";
                Log.e("str1", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setShouldCache(false);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onOrderRefresh() {
        getPending("APPROVAL PENDING",false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}