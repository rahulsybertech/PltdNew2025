package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.BANK_DETAILS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.syber.ssspltd.databinding.ActivityBankDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BankDetailActivity extends AppCompatActivity {

    private ActivityBankDetailBinding binding;

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBankDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Bank Details");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.supportChat.supportFab.setOnClickListener((View.OnClickListener) v ->
                Lazy.openDialog(mContext));

        if (Lazy.haveNetworkConnection(mContext)){
            GetBankDetails();
        }else {
            networkConnetion3(mContext);
        }

    }

    private void GetBankDetails() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BANK_DETAILS,
                response -> {
//            Log.e("response",response);
            Log.i("TaG","url -=-=-= " + BANK_DETAILS);
            Log.i("TaG","response -=-= -= -= " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("ResponseStatus")) {
                            binding.includeProgress.progress.setVisibility(View.GONE);

                            JSONArray bankDetailsArray = jsonObject.getJSONArray("BankDetailsResult");

                            if (bankDetailsArray.length() > 0) {
                                JSONObject TotalCustomer = bankDetailsArray.getJSONObject(0);

                                binding.bankAccountNo.setText(TotalCustomer.optString("BankAccountNo"));
                                binding.accountName.setText(TotalCustomer.optString("AccountName"));
                                binding.bankName.setText(TotalCustomer.optString("BankName"));
                                binding.branchName.setText(TotalCustomer.optString("BranchName"));
                                binding.ifscCode.setText(TotalCustomer.optString("IFSC_Code"));
                            }
                        } else {
                            AlertUtil.responseElse(mContext, "GetBankDetails ", jsonObject.optString("ResponseMessage"));
                            binding.includeProgress.progress.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        AlertUtil.responseExecption(mContext, "GetBankDetails ", e.toString());
                        e.printStackTrace();
                    }

                }, error ->{
                        AlertUtil.responseError(mContext, "GetBankDetails ", error.toString());

                        }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN,""));
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                String partyCode ="";
                if (SharedPref.read(SharedPref.USER_TYPE,"").equals("new")){
                    partyCode = "new";
                }else {
                    partyCode = SharedPref.read(SharedPref.PARTY_CODE,"");
                }
                String mob3 = SharedPref.read(SharedPref.USERMOBILE,"");
                String str = "{\"MOBILENO\":\"" + mob3 + "\",\"PartyCode\":\"" + partyCode + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME,"") + "\"}";
//                Log.e("str", str);
                Log.i("TaG","request -=-=-==-=-=-=> " + str);
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
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            GetBankDetails();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

}