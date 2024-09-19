package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_BRANCHES_MARKETERS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonIOException;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.BranchMarketreAdapter;
import com.syber.ssspltd.databinding.ActivityBranchMarketerBinding;
import com.syber.ssspltd.response.BranchMarketerResponse.BranchEmployeesResult;
import com.syber.ssspltd.response.BranchMarketerResponse.BranchMarketerPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchMarketerActivity extends AppCompatActivity {
    ImageView back;
    Context mContext = this;
    RecyclerView BranchMarketrRecyclerview;
    BranchMarketreAdapter branchMarketreAdapter;
    List<BranchEmployeesResult> branchEmployeesDetails;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    ActivityBranchMarketerBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBranchMarketerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        branchEmployeesDetails=new ArrayList<>();
        listType=new TypeToken<BranchMarketerPojo>(){}.getType();

        BranchMarketrRecyclerview = findViewById(R.id.BranchMarketrRecyclerview);
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        BranchMarketrRecyclerview.setLayoutManager(linearLayoutManager);
        branchMarketreAdapter = new BranchMarketreAdapter(mContext,branchEmployeesDetails);
        BranchMarketrRecyclerview.setAdapter(branchMarketreAdapter);

        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MARKETER");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetBranchMarketer();
        if (Lazy.haveNetworkConnection(mContext)){
            GetBranchMarketer();
        }else {
            networkConnetion3(mContext);
        }
    }
   private void GetBranchMarketer() {
      binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_BRANCHES_MARKETERS,
                response -> {
                     Log.e("Data", response);
                   binding.includeProgress.progress.setVisibility(View.GONE);
                    BranchMarketerPojo pojo = new Gson().fromJson(response,listType);
                    try {
                        if (pojo.getResponseStatus()){
                            branchEmployeesDetails.clear();
                            branchEmployeesDetails.addAll(pojo.getBranchEmployeesResult());
                            branchMarketreAdapter.notifyDataSetChanged();
                        }
                        else {
                            AlertUtil.responseElse(mContext, "GetBranchMarketer ", pojo.getResponseMessage() + "");
                        }
                    }catch (JsonIOException e){
                        AlertUtil.responseExecption(mContext, "GetBranchMarketer ", e.toString());
                    }
                },
                error ->
                        AlertUtil.responseError(mContext, "GetBranchMarketer ", error.toString()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"BranchID\":\"" + SharedPref.read(SharedPref.D_ID,"") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
          GetBranchMarketer();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}