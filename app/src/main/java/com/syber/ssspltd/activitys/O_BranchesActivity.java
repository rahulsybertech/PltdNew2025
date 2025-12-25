package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.ConstantVariable.AUTH_TOKEN;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRANCHES;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.syber.ssspltd.adapter.BranchesAdapter;
import com.syber.ssspltd.databinding.ActivityOBranchesBinding;
import com.syber.ssspltd.response.BranchesResponse.BranchesPojo;
import com.syber.ssspltd.response.BranchesResponse.BranchesResult;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class O_BranchesActivity extends AppCompatActivity {

    Context mContext = this;

    RecyclerView branchRecyclerview;
    BranchesAdapter branchesAdapter,vOAdapter;
    List<BranchesResult> branchrsDetails;
    List<BranchesResult> boList = new ArrayList<>();
    List<BranchesResult> voList = new ArrayList<>();
    Type listType;
    GridLayoutManager linearLayoutManager,linearLayoutManagerVO;
    ActivityOBranchesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOBranchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Branches");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        branchrsDetails = new ArrayList<>();
        listType = new TypeToken<BranchesPojo>() {
        }.getType();


        linearLayoutManager = new GridLayoutManager(mContext, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.branchRecyclerview.setLayoutManager(linearLayoutManager);
        branchesAdapter = new BranchesAdapter(mContext, boList);
        binding.branchRecyclerview.setAdapter(branchesAdapter);

        linearLayoutManagerVO = new GridLayoutManager(mContext, 3);
        binding.vORecyclerview.setLayoutManager(linearLayoutManagerVO);
        vOAdapter = new BranchesAdapter(mContext, voList);
        binding.vORecyclerview.setAdapter(vOAdapter);
        if (Lazy.haveNetworkConnection(mContext)) {
            GetBranches();
        } else {
            networkConnetion3(mContext);
        }

    }

    private void GetBranches() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BRANCHES, response -> {
            Log.e("Data", GET_BRANCHES + "====" + response);
            binding.includeProgress.progress.setVisibility(View.GONE);
            BranchesPojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    branchrsDetails.clear();
                    branchrsDetails.addAll(pojo.getBranchesResult());
                    boList.clear();
                    voList.clear();

                    for (BranchesResult item : branchrsDetails) {
                        String name = item.getBranchName(); // example "Delhi Chandni Chowk (B.O.)"

                        if (name != null) {
                           if (name.contains("(V.O.)")) {
                                voList.add(item);
                            }else {
                               boList.add(item);
                           }
                        }
                    }
                    branchesAdapter.notifyDataSetChanged();
                    vOAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetBranches ", pojo.getResponseMessage() + "");
                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "GetBranches ", e.toString());

            }
        }, error ->
        {
            try {
                Constants.convertByteToString(mContext, "GetBranches ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                String str = "{"
                        + "\"MOBILENO\":\"" + mob3 + "\","
                        + "\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\","
                        + "\"accountid\":\"" + SharedPref.read(SharedPref.ADMIN_ID, "") + "\""
                        + "}";
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
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this,MainActivity.class));
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
            GetBranches();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}