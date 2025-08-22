package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRAND_MASTER_DETAIL;
import static com.syber.ssspltd.activitys.Const.BRANDNAME;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.Util;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.BrandListAdapter;
import com.syber.ssspltd.databinding.ActivityBrandListBinding;
import com.syber.ssspltd.response.brand.BrandInsertingRequestDatum;
import com.syber.ssspltd.response.brand.BrandsPojo;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BrandListActivity extends AppCompatActivity {
    public static List<BrandInsertingRequestDatum> brandsList;
    Context mContext = this;
    Type listType;
    BrandListAdapter beBrandListAdapter;
    RecyclerView recyclerView;
    ActivityBrandListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrandListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);



/*
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        sdf.setLenient(false); // Avoids auto-fixing bad dates

// Format current date to remove time part
        String todayStr = sdf.format(new Date());
        Date currentDate = null; // currentDate now has only date part
        try {
            currentDate = sdf.parse(todayStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date startDate = null;
        try {
            startDate = sdf.parse("01-Jul-2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date endDate = null;
        try {
            endDate = sdf.parse("31-Jul-2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String title = "Brands";
        if (startDate != null && endDate != null && currentDate != null &&
                currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {


            toolbar.setTitle("Brands");

        } else {
            toolbar.setTitle(getIntent().getStringExtra(BRANDNAME));

        }*/
        toolbar.setTitle(getIntent().getStringExtra(BRANDNAME));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.brand_recyclerview);
        brandsList = new ArrayList<>();
        listType = new TypeToken<BrandsPojo>() {
        }.getType();

        beBrandListAdapter = new BrandListAdapter(mContext, brandsList);
        recyclerView.setAdapter(beBrandListAdapter);

//        int x=this. getResources().getDisplayMetrics().heightPixels*1/2;
//        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, x));

        if (Lazy.haveNetworkConnection(mContext)) {
            GetBranands(getIntent().getStringExtra("branch_id"));
        } else {
            networkConnetion3(mContext);
        }


    }

    private void GetBranands(String branchId) {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BRAND_MASTER_DETAIL, response -> {
            Log.e("Data", response);
            Util.getInstance().logLargeString("TaG", "====>" + response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            BrandsPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    brandsList.clear();
                    brandsList.addAll(pojo.getBrandInsertingRequestData());
                    beBrandListAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetBrandMasterDetails ", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(mContext, "GetBrandMasterDetails ", e.toString());
            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetBrandMasterDetails ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"BranchID\":\"" + branchId + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void networkConnetion3(Context mContext) {

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
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetBranands(getIntent().getStringExtra("branch_id"));
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}