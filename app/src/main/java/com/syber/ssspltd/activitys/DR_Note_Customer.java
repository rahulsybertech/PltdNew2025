package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_DEBIT_NOTE_TO_CUSTOMER_REPORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.DNToCustomerAdap.DNToCustomerAdapter;
import com.syber.ssspltd.databinding.ActivityDRNoteCustomerBinding;
import com.syber.ssspltd.response.DNToCustomerResponse.DNToCustomerPojo;
import com.syber.ssspltd.response.DNToCustomerResponse.DebitNoteToCustomerReportResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DR_Note_Customer extends AppCompatActivity {
    Context mContext = this;
    DNToCustomerAdapter dnToCustomerAdapter;
    List<DebitNoteToCustomerReportResult> dnCustomerDetails;
    Type listType;
    private ActivityDRNoteCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDRNoteCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener(v ->
                Lazy.openDialog(mContext));
        dnCustomerDetails=new ArrayList<>();
        listType=new TypeToken<DNToCustomerPojo>(){}.getType();

        dnToCustomerAdapter = new DNToCustomerAdapter(mContext,dnCustomerDetails);
        binding.dnToCustomerRecyclerview.setAdapter(dnToCustomerAdapter);

        ImageView backImage =findViewById(R.id.back3);
        backImage.setImageDrawable(ContextCompat.getDrawable(DR_Note_Customer.this, R.drawable.ic_baseline_keyboard_backspace));
        backImage.setOnClickListener(v -> onBackPressed());

        ImageView backImage3 =findViewById(R.id.download);
        backImage3.setOnClickListener(v -> {
        });
        TextView backImage2 =findViewById(R.id.back2);
        backImage2.setText("DEBIT NOTE TO CUSTOMER");
        if (Lazy.haveNetworkConnection(mContext)){
            GetDebitNoteToCustomerReport();
        }else {
            networkConnetion3(mContext);
        }
    }

    private void GetDebitNoteToCustomerReport() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DEBIT_NOTE_TO_CUSTOMER_REPORT,
                response -> {
            Log.e("response", GET_DEBIT_NOTE_TO_CUSTOMER_REPORT + "=======" + response);

                    DNToCustomerPojo pojo = new Gson().fromJson(response,listType);
                    try {
                        if (pojo.getResponseStatus()) {
                            dnCustomerDetails.clear();
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.GONE);
                            dnCustomerDetails.addAll(pojo.getDebitNoteToCustomerReportResult());
                            dnToCustomerAdapter.notifyDataSetChanged();
                        } else {
                            AlertUtil.responseElse(mContext, "GetDebitNoteToCustomerReport ", pojo.getResponseMessage() + "");
                            binding.includeProgress.progress.setVisibility(View.GONE);
                            binding.includeProgress.noData.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        AlertUtil.responseExecption(mContext, "GetDebitNoteToCustomerReport ", e.toString());
                    }
                }, error -> {
            AlertUtil.responseError(mContext, "GetDebitNoteToCustomerReport ", error.toString());
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
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
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
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener( view -> {
            GetDebitNoteToCustomerReport();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}