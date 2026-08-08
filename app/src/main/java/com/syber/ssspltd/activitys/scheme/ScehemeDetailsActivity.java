package com.syber.ssspltd.activitys.scheme;
import static com.syber.ssspltd.Constants.NewErpUrls.GetSchemeDetails;
import static com.syber.ssspltd.Constants.NewErpUrls.GetSchemeName;
import static com.syber.ssspltd.Utils.AppController.mContext;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.Interface.RefreshOrderReport;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.SchemeDetailsAdapter;
import com.syber.ssspltd.adapter.supplierformadapter.SalePartyAdapter;
import com.syber.ssspltd.databinding.ActivitySchemeDatailsBinding;
import com.syber.ssspltd.model.scheme.scheme_detail.SchemeApiResponse;
import com.syber.ssspltd.model.scheme.scheme_detail.SchemeData;
import com.syber.ssspltd.model.scheme.scheme_detail.SchemeDetails;
import com.syber.ssspltd.model.scheme.scheme_name.SchemeItem;
import com.syber.ssspltd.model.scheme.scheme_name.SchemeListResponse;
import com.syber.ssspltd.response.SalepartyModel;
import com.syber.ssspltd.response.SupplierOrderReport.OrderDetail;
import com.syber.ssspltd.response.SupplierOrderReport.SupplierReportPojo;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ScehemeDetailsActivity extends AppCompatActivity implements RefreshOrderReport {
    private ActivitySchemeDatailsBinding binding;
    SchemeDetailsAdapter schemeDetailsAdapter;
   ArrayList<SchemeDetails> orderDetailList, orderListData,filterData;
    public static ArrayList<OrderDetail> oData = new ArrayList<>();
    Type listType1;
    ArrayList<SchemeItem> schemelist=new ArrayList<>();;
    private String selectSchemeID="";
    double totalSaleAmount = 0.0;
    double totalDiscountAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySchemeDatailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        orderDetailList = new ArrayList<>();
        filterData = new ArrayList<>();
        orderListData = new ArrayList<>();
        listType1 = new TypeToken<SupplierReportPojo>() {
        }.getType();

        schemeDetailsAdapter = new SchemeDetailsAdapter(mContext, orderDetailList);
        binding.recyclerView.setAdapter(schemeDetailsAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

     //   getPending("CONFIRM",true);
        getShemeName();

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterData.clear();
                for (int p = 0; p < orderDetailList.size(); p++) {
                    if (orderDetailList.get(p).getSalePartyName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getNickName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getSaleBillNo().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getCustomerName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getSupplierName().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getPurchaseSno().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || orderDetailList.get(p).getNickNameId().toLowerCase().contains(charSequence.toString().toLowerCase())

                    ) {
                        filterData.add(orderDetailList.get(p));
                    }
                }
                filterBc(filterData);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    void filterBc(ArrayList<SchemeDetails> filterData) {
        schemeDetailsAdapter = new SchemeDetailsAdapter(mContext, filterData);
        binding.recyclerView.setAdapter(schemeDetailsAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
    }


    private void getShemeDetails() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetSchemeDetails, response -> {
            Log.i("TaG", "Response " + GetSchemeDetails + "---> " + response);
            try {
                binding.includeProgress.progress.setVisibility(View.GONE);

                Gson gson = new Gson();
                SchemeApiResponse bookingResponse = gson.fromJson(response, SchemeApiResponse.class);

                orderDetailList.clear();
                orderDetailList.addAll(bookingResponse.getData().getSchemeDetailsResponse());

                if (orderDetailList == null || orderDetailList.isEmpty()) {
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                } else {
                    binding.ll.setVisibility(View.VISIBLE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                }

                double totalSaleAmount = 0.0;
                double totalDiscountAmount = 0.0;

                for (SchemeDetails item : orderDetailList) {
                    totalSaleAmount += item.getSaleAmount();
                    totalDiscountAmount += item.getDiscountAmount();
                }

                binding.tvTotalSaleAmount.setText("TotalSaleAmount:"+
                        String.format(Locale.getDefault(), "%.2f", totalSaleAmount)
                );

                binding.tvTotalDisAmount.setText("TotalDisAmount:"+
                        String.format(Locale.getDefault(), "%.2f", totalDiscountAmount)
                );

                schemeDetailsAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                binding.includeProgress.progress.setVisibility(View.GONE);
                binding.includeProgress.noData.setVisibility(View.VISIBLE);
                Log.e("Exce", e.toString());
            }


        }, error -> {
            Toast.makeText(this, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"schemeId\":\"" + selectSchemeID+ "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onOrderRefresh() {
        //    getPending();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void setSpinner() {

        List<String> items = new ArrayList<>();
        items.add("Select Scheme");

        for (SchemeItem branch : schemelist) {
            items.add(branch.getSchemeName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinner.setAdapter(adapter);

        // 🔥 Important line
        binding.spinner.setSelection(0, false);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    selectSchemeID = ""; // No scheme selected
                } else {

                    selectSchemeID = schemelist.get(position - 1).getSchemeId();
                    getShemeDetails();
              //      getPending();
              //      selectSchemeID = schemelist.get(position - 1).getSchemeId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void getShemeName() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetSchemeName, response -> {
            Log.i("TaG", "Response " + GetSchemeName + "---> " + response);
            try {
                Gson gson = new Gson();
                SchemeListResponse bookingResponse = gson.fromJson(response, SchemeListResponse.class);
                //   JSONObject jsonObject = new JSONObject(response);
                schemelist.addAll(bookingResponse.getData());
                setSpinner();
                // stationAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Exce", e.toString());
            }

        }, error -> {
            Toast.makeText(this, error.getMessage() + "", Toast.LENGTH_LONG).show();
            Log.e("Volly ", error.getMessage() + "");
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {

//                    jsonBody.put("SupplierAccountID", SharedPref.read(SharedPref.PARTY_CODE, ""));
                    //     jsonBody.put("SupplierAccountID", selectedAccountId);

                    Log.i("TaG", "Request " + GetSchemeName + "---> " + jsonBody);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RetryPolicy retryPolicy = new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
