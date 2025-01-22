package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_SALE_AND_SERVICE_REPORT;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.SaleServiceAdapter;
import com.syber.ssspltd.databinding.ActivitySaleServiceBinding;
import com.syber.ssspltd.response.SaleServiceRespo.SaleServicePojo;
import com.syber.ssspltd.response.SaleServiceRespo.SaleServiceReportResult;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleService extends AppCompatActivity {
    Context mContext = this;
    RecyclerView sale_serviceRecyler;
    SaleServiceAdapter saleServiceAdapter;
    List<SaleServiceReportResult> saleServiceDetails;
    Type listType;
    private ActivitySaleServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaleServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        binding.toolbar.back3.setImageDrawable(ContextCompat.getDrawable(SaleService.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.toolbar.back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbar.back2.setText("SALE SERVICE ");
        saleServiceDetails = new ArrayList<>();

        listType = new TypeToken<SaleServicePojo>() {
        }.getType();


        saleServiceAdapter = new SaleServiceAdapter(mContext, saleServiceDetails);
        binding.saleServiceRecyler.setAdapter(saleServiceAdapter);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetSaleServiceReport();
        } else {
            networkConnetion3(mContext);
        }

    }

    private void GetSaleServiceReport() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_SALE_AND_SERVICE_REPORT, response -> {
            Log.e("Data", response);
            SaleServicePojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    saleServiceDetails.clear();
                    saleServiceDetails.addAll(pojo.getSaleServiceReportResult());
                    saleServiceAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetSaleServiceReport ", pojo.getResponseMessage() + "");
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "GetSaleServiceReport ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetSaleServiceReport ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";

                Log.e("str", str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void networkConnetion3(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
                GetSaleServiceReport();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}