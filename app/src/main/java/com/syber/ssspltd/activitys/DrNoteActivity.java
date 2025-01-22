package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_DEBIT_NOTE_REPORT;

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
import com.syber.ssspltd.adapter.DebitNoteAdap.DebitNoteAdapter;
import com.syber.ssspltd.databinding.ActivityDrNoteBinding;
import com.syber.ssspltd.response.DebitNoteResponse.DebitNotePojo;
import com.syber.ssspltd.response.DebitNoteResponse.DebitNoteReportResult;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrNoteActivity extends AppCompatActivity {
    Context mContext = this;
    RecyclerView DebitNoteRecyclerview;
    DebitNoteAdapter debitNoteAdapter;
    List<DebitNoteReportResult> debitNoteDetails;
    Type listType;
    private ActivityDrNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v -> Lazy.openDialog(mContext));
        debitNoteDetails = new ArrayList<>();
        listType = new TypeToken<DebitNotePojo>() {
        }.getType();

        debitNoteAdapter = new DebitNoteAdapter(mContext, debitNoteDetails);
        binding.DebitNoteRecyclerview.setAdapter(debitNoteAdapter);

        binding.l.back3.setImageDrawable(ContextCompat.getDrawable(DrNoteActivity.this, R.drawable.ic_baseline_keyboard_backspace));
        binding.l.back3.setOnClickListener(v -> onBackPressed());

        binding.l.download.setOnClickListener(v -> {
        });
        binding.l.back2.setText("DEBIT NOTE ");
        if (Lazy.haveNetworkConnection(mContext)) {
            GetDebitNoteReport();
        } else {
            networkConnetion3(mContext);
        }

    }

    private void GetDebitNoteReport() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_DEBIT_NOTE_REPORT, response -> {
            Log.e("Data", GET_DEBIT_NOTE_REPORT + "========" + response);

            DebitNotePojo pojo = new Gson().fromJson(response, listType);
            try {
                if (pojo.getResponseStatus()) {
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.GONE);
                    debitNoteDetails.clear();
                    debitNoteDetails.addAll(pojo.getDebitNoteReportResult());
                    debitNoteAdapter.notifyDataSetChanged();
                } else {
                    AlertUtil.responseElse(mContext, "GetDebitNoteReport ", pojo.getResponseMessage() + "");
                    binding.includeProgress.progress.setVisibility(View.GONE);
                    binding.includeProgress.noData.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                AlertUtil.responseExecption(mContext, "GetDebitNoteReport ", e.toString());
            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetDebitNoteReport ", error);
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
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
                Log.e("str", str);
                return str.getBytes();
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
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);
        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetDebitNoteReport();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}