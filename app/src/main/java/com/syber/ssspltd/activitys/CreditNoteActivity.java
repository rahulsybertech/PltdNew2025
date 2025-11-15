package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_CREDIT_NOTE_REPORT;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.syber.ssspltd.adapter.CreateNoteAdap.CreateNoteAdapter;
import com.syber.ssspltd.databinding.ActivityCreditNoteBinding;
import com.syber.ssspltd.response.CreditNoteReportRespo.CreditNoteRepPojo;
import com.syber.ssspltd.response.CreditNoteReportRespo.CreditNoteReportResult;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditNoteActivity extends AppCompatActivity {

    ImageView back;
    Context mContext = this;
    RecyclerView CreateNoteRecyclerview;
    CreateNoteAdapter createNoteAdapter;
    List<CreditNoteReportResult> creditNoteDetails;
    Type listType;
    private ActivityCreditNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Handle system bars (status + nav bar) insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));
        creditNoteDetails = new ArrayList<>();
        listType = new TypeToken<CreditNoteRepPojo>() {
        }.getType();

        if (Lazy.haveNetworkConnection(mContext)) {
            GetCreditNoteReport();
        } else {
            networkConnetion3(mContext);
        }

        createNoteAdapter = new CreateNoteAdapter(mContext, creditNoteDetails);
        binding.CreateNoteRecyclerview.setAdapter(createNoteAdapter);

        binding.l.back3.setImageDrawable(ContextCompat.getDrawable(CreditNoteActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.l.back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.l.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        TextView backImage2 = findViewById(R.id.back2);
        backImage2.setText("CREDIT NOTE REPORT");

    }

    private void GetCreditNoteReport() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_CREDIT_NOTE_REPORT, response -> {
            Log.e("DataCreditNote", response);
            try {
                CreditNoteRepPojo pojo = new Gson().fromJson(response, listType);
                creditNoteDetails.clear();
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    creditNoteDetails.addAll(pojo.getCreditNoteReportResult());
                    createNoteAdapter.notifyDataSetChanged();
                } else {
                    binding.l.download.setVisibility(View.GONE);
                    AlertUtil.responseElse(mContext, "GetCreditNoteReport ", pojo.getResponseMessage() + "");
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.responseExecption(mContext, "GetCreditNoteReport ", e.toString());
            }
        }, error -> {
            System.out.println("GETTING_ERROR_CREDIT_NOTE : " + error.toString());
            try {
                Constants.convertByteToString(mContext, "GetCreditNoteReport ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            binding.includeProgress.progress.setVisibility(View.GONE);
            binding.includeProgress.noData.setVisibility(View.VISIBLE);
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
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\" ,\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void networkConnetion3(Context mContext) {

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
                GetCreditNoteReport();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}