package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRANCHES_GODOWN_PACKING;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.GodownPackingAdapter;
import com.syber.ssspltd.databinding.ActivityGodownPackingBinding;
import com.syber.ssspltd.response.GodownPackingResponse.BranchEmployeesResult;
import com.syber.ssspltd.response.GodownPackingResponse.GodownPackingPojo;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GodownPackingActivity extends AppCompatActivity {
    ImageView back;
    Context mContext = this;
    RecyclerView GoDownRecyclerview;
    GodownPackingAdapter godownPackingAdapter;
    List<BranchEmployeesResult> branchEmployeesDetails;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    FloatingActionButton support_flo;
    ActivityGodownPackingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGodownPackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        support_flo = findViewById(R.id.support_fab);
        support_flo.setOnClickListener(v ->
                Lazy.openDialog(mContext));
        branchEmployeesDetails = new ArrayList<>();
        listType = new TypeToken<GodownPackingPojo>() {
        }.getType();

        GoDownRecyclerview = findViewById(R.id.GoDownRecyclerview);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GoDownRecyclerview.setLayoutManager(linearLayoutManager);
        godownPackingAdapter = new GodownPackingAdapter(mContext, branchEmployeesDetails);
        GoDownRecyclerview.setAdapter(godownPackingAdapter);

//        back=findViewById(R.id.branch_backpress);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(GodownPackingActivity.this,BranchItemDetailsActivity.class));
//                finish();
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GODOWN_PACKING");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetBranchGodownPacking();
        } else {
            networkConnetion3(mContext);
        }

    }

    private void GetBranchGodownPacking() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BRANCHES_GODOWN_PACKING, response -> {
            binding.includeProgress.progress.setVisibility(View.GONE);
            //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
            GodownPackingPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    branchEmployeesDetails.clear();
                    branchEmployeesDetails.addAll(pojo.getBranchEmployeesResult());
                    godownPackingAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetBranchGodownPacking ", pojo.getResponseMessage() + "");
                }
            } catch (JsonIOException e) {
                AlertUtil.responseExecption(mContext, "GetBranchGodownPacking ", e.toString());
            }

        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetBranchGodownPacking ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", Constants.SettingHeader());
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"BranchID\":\"" + SharedPref.read(SharedPref.D_ID, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(GodownPackingActivity.this,BranchItemDetailsActivity.class));
//        finish();
//        super.onBackPressed();
//    }

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
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetBranchGodownPacking();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

}