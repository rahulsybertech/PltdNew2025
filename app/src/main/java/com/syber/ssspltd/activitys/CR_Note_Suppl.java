package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_CREDIT_NOTE_TO_SUPPLIER_REPORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonIOException;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.CNToSupplierAdap.CNToSupplierAdapter;
import com.syber.ssspltd.databinding.ActivityCRNoteSupplBinding;
import com.syber.ssspltd.response.CNToSupplierResponse.CNToSupplierPojo;
import com.syber.ssspltd.response.CNToSupplierResponse.CreditNoteToSupplierReportResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR_Note_Suppl extends AppCompatActivity {
    Context mContext = this;
    RecyclerView cnToSupplierRecyclerview;
    CNToSupplierAdapter cnToSupplierAdapter;
    List<CreditNoteToSupplierReportResult> cnSuppDetails;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    private ActivityCRNoteSupplBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCRNoteSupplBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));
        cnSuppDetails=new ArrayList<>();
        listType=new TypeToken<CNToSupplierPojo>(){}.getType();

        cnToSupplierAdapter = new CNToSupplierAdapter(mContext,cnSuppDetails);
        binding.cnToSupplierRecyclerview.setAdapter(cnToSupplierAdapter);

        ImageView backImage =findViewById(R.id.back3);
        backImage.setImageDrawable(ContextCompat.getDrawable(CR_Note_Suppl.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());

        ImageView backImage3 =findViewById(R.id.download);
        backImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        TextView backImage2 =findViewById(R.id.back2);
        backImage2.setText("CREDIT NOTE TO SUPPLIER");
        if (Lazy.haveNetworkConnection(mContext)){
            GetCreditNoteToSupplierReport();
        }else {
            networkConnetion3(mContext);
        }
    }

    private void GetCreditNoteToSupplierReport() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_CREDIT_NOTE_TO_SUPPLIER_REPORT,
                response -> {
                    Log.e("Data", response);
                    CNToSupplierPojo pojo = new Gson().fromJson(response,listType);
                    cnSuppDetails.clear();
                    try {
                        if (pojo.getResponseStatus()) {
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.GONE);
                            cnSuppDetails.addAll(pojo.getCreditNoteToSupplierReportResult());
                            cnToSupplierAdapter.notifyDataSetChanged();
                        } else {
                            AlertUtil.responseElse(mContext, "GetCreditNoteToSupplierReport ", pojo.getResponseMessage() + "");
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.VISIBLE);
                        }
                    }catch (JsonIOException e){
                        AlertUtil.responseExecption(mContext, "GetCreditNoteToSupplierReport ", e.toString());
                    }
                }, error -> {
                AlertUtil.responseError(mContext, "GetCreditNoteToSupplierReport ", error.toString());
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\",\"DBNAME\":\"" +SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
                GetCreditNoteToSupplierReport();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}