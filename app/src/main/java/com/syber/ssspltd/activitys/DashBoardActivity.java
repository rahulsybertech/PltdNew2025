package com.syber.ssspltd.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.DaskboadAdapter.PendingOrderBWAdap;
import com.syber.ssspltd.adapter.DaskboadAdapter.StockInOfficeBWAdap.StockInOfficeBWAdap;
import com.syber.ssspltd.databinding.ActivityDashBoardBinding;
import com.syber.ssspltd.response.DashboardAllData.PendingOrderBWPojo;
import com.syber.ssspltd.response.DashboardAllData.StockInOfficeBW.StockInOfficeBWPojo;
import com.syber.ssspltd.response.DashboardAllData.StockInOfficeBW.StockInOfficeDetail;
import com.syber.ssspltd.response.DashboardAllData.TotalPendingOrderDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
    Context mContext = this;
    TextView item_pending,item_stock;
    TextView currentBel,Tol_PendingOrder,tol_stockOffice,grandTotal;
    PendingOrderBWAdap pendingOrderBWAdap;
    StockInOfficeBWAdap stockInOfficeBWAdap;
    static List<TotalPendingOrderDetail> totalPendingOrderDetails;
    static List<StockInOfficeDetail> stockInOfficeDetails;
    Type listType;
    Type listType2;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout reclyVisibility,reclyVisibilityTolStock;
    boolean setTrue=false;
    private ActivityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        item_pending=findViewById(R.id.item_pending);
        item_stock=findViewById(R.id.item_stock);
        currentBel=findViewById(R.id.currentBel);
        Tol_PendingOrder=findViewById(R.id.Tol_PendingOrder);
        tol_stockOffice=findViewById(R.id.tol_stockOffice);
        grandTotal=findViewById(R.id.grandTotal);
        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));
        reclyVisibility=findViewById(R.id.reclyVisibility);
        reclyVisibilityTolStock=findViewById(R.id.reclyVisibilityTolStock);



        totalPendingOrderDetails = new ArrayList<>();
        stockInOfficeDetails = new ArrayList<>();

        listType = new TypeToken<PendingOrderBWPojo>(){}.getType();
        listType2 = new TypeToken<StockInOfficeBWPojo>(){}.getType();


        pendingOrderBWAdap = new PendingOrderBWAdap(mContext,totalPendingOrderDetails);
        binding.tolPendingOrder.setAdapter(pendingOrderBWAdap);

        stockInOfficeBWAdap = new StockInOfficeBWAdap(mContext,stockInOfficeDetails);
        binding.tolStockOfficeOrder.setAdapter(stockInOfficeBWAdap);





        ImageView backImage =findViewById(R.id.back3);
        backImage.setImageDrawable(ContextCompat.getDrawable(DashBoardActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v ->
                onBackPressed());
        TextView backImage2 =findViewById(R.id.back2);
        backImage2.setText("DASH BOARD ");

        item_pending.setOnClickListener(v -> {
            if (setTrue==false) {
                reclyVisibility.setVisibility(View.VISIBLE);
                setTrue = true;
            }
            else if (setTrue==true)
                {
                    reclyVisibility.setVisibility(View.GONE);
                    setTrue = false;
                }

        });
        item_stock.setOnClickListener(v -> {
            if (setTrue==false) {
                reclyVisibilityTolStock.setVisibility(View.VISIBLE);
                setTrue = true;
            }
            else if (setTrue==true)
            {
                reclyVisibilityTolStock.setVisibility(View.GONE);
                setTrue = false;
            }
        });
        if (Lazy.haveNetworkConnection(mContext)){
            DashboardAllData();
            GetPendingOrderBranchWise();
            GetStockInOfficeBranchWise();
        }else {
            networkConnetion3(mContext);
        }

    }
  boolean DashboardAllData() {
      binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetDashboardAllData",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("ResponseStatus") == true) {
                                binding.ll.setVisibility(View.VISIBLE);
                                binding.includeProgress.progress.setVisibility(View.GONE);
                                binding.includeProgress.noData.setVisibility(View.GONE);
                                JSONObject TotalCustomer = jsonObject.getJSONObject("Dashboard");
                                Log.e("test", TotalCustomer.optString("Active"));
                                currentBel.setText(TotalCustomer.optString("CurrentBal"));
                                Tol_PendingOrder.setText(TotalCustomer.optString("PendingOrderAmt"));
                                tol_stockOffice.setText(TotalCustomer.optString("OfficeStockAmt"));
                                grandTotal.setText(TotalCustomer.optString("GrandTotal"));


                            } else {
                                binding.ll.setVisibility(View.GONE);
                                binding.includeProgress.progress.setVisibility(View.GONE);
                                binding.includeProgress.noData.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkConnetion3(mContext);
                binding.ll.setVisibility(View.GONE);
                binding.includeProgress.progress.setVisibility(View.GONE);
                binding.includeProgress.noData.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob2 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob2 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"")+ "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";

                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
        return true;
    }



    private void GetPendingOrderBranchWise() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetPendingOrderBranchWise",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        PendingOrderBWPojo pojo = new Gson().fromJson(response,listType);
                        if (pojo.getResponseStatus()){
                            totalPendingOrderDetails.clear();
                            totalPendingOrderDetails.addAll(pojo.getTotalPendingOrderDetails());
                            pendingOrderBWAdap.notifyDataSetChanged();
                        }
                        else {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                // String otpp = otp.getText().toString();
                Object a = null;
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }


    private void GetStockInOfficeBranchWise() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetStockInOfficeBranchWise",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Data", response);
                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                        StockInOfficeBWPojo pojo = new Gson().fromJson(response,listType2);
                        if (pojo.getResponseStatus()){
                            stockInOfficeDetails.clear();
                            stockInOfficeDetails.addAll(pojo.getStockInOfficeDetail());
                            stockInOfficeBWAdap.notifyDataSetChanged();
                        }
                        else {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                Object a = null;
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"ACCOUNTID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
    public void  networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardAllData();
                GetPendingOrderBranchWise();
                GetStockInOfficeBranchWise();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }



}