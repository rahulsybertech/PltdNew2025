package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.VERIFY_REFERRAL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReferralCodeActivity extends AppCompatActivity {
    Context mContext = this;
    EditText referralCode_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_code);

        referralCode_number=findViewById(R.id.referralCode_number);
        REFERRAL();
    }

    private void REFERRAL() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("REFERRAL");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, VERIFY_REFERRAL,
                response -> {
                    Log.e("Data", VERIFY_REFERRAL + " ======= "  + response);
                    progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("jsonObject", jsonObject + "");
                        if (jsonObject.getBoolean("ResponseStatus") == true) {
                            SharedPref.write(SharedPref.USERMOBILE, referralCode_number.getText().toString());
                            //Toast.makeText(mContext, "Otp Sent on reg Mobile no.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, OTPActivity.class));
                            finish();
                        } else
//                                if (jsonObject.getBoolean("ResponseStatus") == true){
                        {
                           // showCustomDialog();
//                                Toast.makeText(mContext, "Not Reg...", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.cancel();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = referralCode_number.getText().toString();

//              String str ="{\"MOBILENO\":\"" + mob + "\",\"OTP\":\"}";
                String str = "{\"REFERRAL\":\"" + mob + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

}