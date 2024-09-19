package com.syber.ssspltd.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.BranchesAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.databinding.ActivityOBranchesBinding;
import com.syber.ssspltd.response.BranchesResponse.BranchesPojo;
import com.syber.ssspltd.response.BranchesResponse.BranchesResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class O_BranchesActivity extends AppCompatActivity {

    Context mContext = this;

    RecyclerView branchRecyclerview;
    BranchesAdapter branchesAdapter;
    List<BranchesResult> branchrsDetails;
    Type listType;
    GridLayoutManager linearLayoutManager;
    ActivityOBranchesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOBranchesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Branches");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        branchrsDetails = new ArrayList<>();
        listType = new TypeToken<BranchesPojo>() {
        }.getType();

        linearLayoutManager = new GridLayoutManager(mContext,3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.branchRecyclerview.setLayoutManager(linearLayoutManager);
        branchesAdapter = new BranchesAdapter(mContext, branchrsDetails);
        binding.branchRecyclerview.setAdapter(branchesAdapter);
        if (Lazy.haveNetworkConnection(mContext)){
            GetBranches();
        }else {
            networkConnetion3(mContext);
        }

    }

    private void GetBranches() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetBranches",
                response -> {
                    Log.e("Data", response);
                   binding.includeProgress.progress.setVisibility(View.GONE);
                    BranchesPojo pojo = new Gson().fromJson(response,listType);
                    try {
                        if (pojo.getResponseStatus()){
                            branchrsDetails.clear();
                            branchrsDetails.addAll(pojo.getBranchesResult());
                            branchesAdapter.notifyDataSetChanged();
                        }
                        else {
                            AlertUtil.responseElse(mContext, "GetBranches ", pojo.getResponseMessage() + "");
                        }
                    }catch (Exception e){
                        AlertUtil.responseExecption(mContext, "GetBranches ", e.toString());

                    }
                }, error ->
            AlertUtil.responseError(mContext, "GetBranches ", error.toString()))
                {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this,MainActivity.class));
//        finish();
//        super.onBackPressed();
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void  networkConnetion3(Context mContext) {

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