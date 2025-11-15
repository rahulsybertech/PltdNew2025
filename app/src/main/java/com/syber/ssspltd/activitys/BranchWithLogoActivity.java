package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRANCHES;
import static com.syber.ssspltd.Constants.NewErpUrls.GetBrands;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
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
import com.syber.ssspltd.adapter.BrancheWithLogoAdapter;
import com.syber.ssspltd.databinding.ActivityBranchWithLogoBinding;
import com.syber.ssspltd.response.BranchesResponse.BranchesPojo;
import com.syber.ssspltd.response.BranchesResponse.BranchesResult;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BranchWithLogoActivity extends AppCompatActivity {


    private static String brandName;
    Context mContext = this;
    RecyclerView branchRecyclerview;
    BrancheWithLogoAdapter brancheWithLogoAdapter;
    List<BranchesResult> branchrsDetails;
    Type listType;
    ActivityBranchWithLogoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBranchWithLogoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        brandName = getIntent().getStringExtra(BRANDNAME);

        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));


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
                // Date is within range
                if ("Brands".equalsIgnoreCase(title)) {
                    title = "Item Category";

                }
            toolbar.setTitle(title);

            } else {
            toolbar.setTitle("Brands");

            }

      //  toolbar.setTitle("Brands");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        branchrsDetails = new ArrayList<>();
        listType = new TypeToken<BranchesPojo>() {
        }.getType();
        branchRecyclerview = findViewById(R.id.branchRecyclerview);
        brancheWithLogoAdapter = new BrancheWithLogoAdapter(mContext, branchrsDetails, brandName);
        branchRecyclerview.setAdapter(brancheWithLogoAdapter);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetBranches();
        } else {
            networkConnetion3(mContext);
        }
        // GetBranches();

    }

    private void GetBranches() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetBrands, response -> {
            Log.e("TaG", "Url " + GetBrands);
            Util.getInstance().logLargeString("TaG", response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            BranchesPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    branchrsDetails.clear();
                    branchrsDetails.addAll(pojo.getBranchesResult());
                    brancheWithLogoAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetBranches ", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(mContext, "GetBranches ", e.toString());
            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetBranches ", error);
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
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{"
                        + "\"MOBILENO\":\"" + mob3 + "\","
                        + "\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\","
                        + "\"accountid\":\"" + SharedPref.read(SharedPref.ADMIN_ID, "") + "\""
                        + "}";
                Log.e("str", str);
                Log.i("TaG", "req   =====" + GET_BRANCHES + " " + str);
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
            GetBranches();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}