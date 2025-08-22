package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.CHECK_OTP;
import static com.syber.ssspltd.Constants.NewErpUrls.CHECK_OTP_GO;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_USER_TYPE_LIST;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poovam.pinedittextfield.SquarePinField;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.response.UsersTypeResponse.UsersTypePoojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class OTPActivity extends AppCompatActivity {

    ImageView next_page;
    Context mContext = this;
    SquarePinField enter_otp;
    Type listType;
    TextView resend_otp, number_show;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        next_page = findViewById(R.id.next_page);
        enter_otp = findViewById(R.id.enter_otp);
        resend_otp = findViewById(R.id.resend_otp);
        number_show = findViewById(R.id.number_show);
        SharedPref.init(mContext);


        Log.e("reg_status", getIntent().getStringExtra("reg_status"));


        listType = new TypeToken<UsersTypePoojo>() {
        }.getType();
        next_page.setOnClickListener(v -> {
            if (enter_otp.getText().toString().length() > 0) {
                OTP2();
            } else {
                Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
        number_show.setText(SharedPref.read(SharedPref.USERMOBILE, ""));

        resend_otp.setOnClickListener(v -> ResendOTPDialog());
        if (Lazy.haveNetworkConnection(mContext)) {

        } else {
            networkConnetion3(mContext);
        }
    }

    private void OTP() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("GENERATE OTP");
        progressBar.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_OTP,
                response -> {
                    Log.e("Data", response);
                    progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus")) {
                            Log.e("ResponseStatus", jsonObject.getBoolean("ResponseStatus") + "");
                            Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                            String StartDate = jsonObject.getString("StartDate");
                            String EndDate = jsonObject.getString("EndDate");
                            SharedPref.write(SharedPref.FY_StartDate, StartDate);
                            SharedPref.write(SharedPref.FY_EndDate, EndDate);
                            // finish();
                            SharedPref.write(SharedPref.isLogin, "true");
                            if (getIntent().getStringExtra("reg_status").equals("User Not Registered")) {
                                SharedPref.write(SharedPref.USER_TYPE, "new");
                                startActivity(new Intent(mContext, GST_NumberActivity.class)
                                        .putExtra("ref_code", getIntent().getStringExtra("ref_code")));
                                finish();
                            } else {
                                GetUsersTypeList();
                            }

                        } else {
                            Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> progressBar.cancel()) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String mob=mobile_no_otp.getText().toString();
                String otpp = enter_otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\",\"OTP\":\"" + otpp + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void OTP2() {
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("GENERATE OTP");
//        progressBar.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_OTP_GO, response -> {
            Log.e("OtpRespo", response);
            // progressBar.dismiss();
            // Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus")) {
                    Log.e("ResponseStatus", jsonObject.getBoolean("ResponseStatus") + "");
                    Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    String StartDate = jsonObject.getString("StartDate");
                    String EndDate = jsonObject.getString("EndDate");
                    SharedPref.write(SharedPref.FY_StartDate, StartDate);
                    SharedPref.write(SharedPref.FY_EndDate, EndDate);
                    //here_is_the_token
                    SharedPref.write(SharedPref.ACCCESS_TOKEN, jsonObject.getString("AccessToken"));
                    // finish();
                    SharedPref.write(SharedPref.isLogin, "true");
                    if (getIntent().getStringExtra("reg_status").equals("NOTUSER")) {
                        SharedPref.write(SharedPref.USER_TYPE, "new");
                        startActivity(new Intent(mContext, GST_NumberActivity.class)
                                .putExtra("ref_code", getIntent().getStringExtra("ref_code")));
                        finish();
                    } else {
                        GetUsersTypeList();
                    }

                } else {
                    Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error ->
                networkConnetion3(mContext)) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String mob=mobile_no_otp.getText().toString();

                String otpp = enter_otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\",\"OTP\":\"" + otpp + "\"}";
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

    private void GetUsersTypeList() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Fetching Data");
        // progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USER_TYPE_LIST,
                response -> {
                    Log.e("Data", response);
                    progressBar.dismiss();
                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show()
                    UsersTypePoojo pojo = new Gson().fromJson(response, listType);
                    if (pojo.getResponseStatus()) {
                        if (pojo.getUsersTypeListResult().size() == 1) {
                            Log.e("getUserType", pojo.getUsersTypeListResult().get(0).getUserType());
                            if (pojo.getUsersTypeListResult().get(0).getUserType().equals("5")) {
                                startActivity(new Intent(mContext, SplashActivity.class));
                                SharedPref.write(SharedPref.IS_BACK_VISIBLE, "true");
                                SharedPref.write(SharedPref.WHERE_TO_GO, "choose_cat");
                                finish();
                            } else if (pojo.getUsersTypeListResult().get(0).getUserType().equals("2")) {
                                SharedPref.write(SharedPref.PARTY_CODE, pojo.getUsersTypeListResult().get(0).getPartyCode());
                                SharedPref.write(SharedPref.SELECTED, pojo.getUsersTypeListResult().get(0).getName());
                                startActivity(new Intent(mContext, SplashActivity.class));
                                finish();
                                SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Supplier");
                                SharedPref.write(SharedPref.TYPE, "notAdmin");
                            } else if (pojo.getUsersTypeListResult().get(0).getUserType().equals("1")) {
                                startActivity(new Intent(mContext, SplashActivity.class));
                                SharedPref.write(SharedPref.PARTY_CODE, pojo.getUsersTypeListResult().get(0).getPartyCode());
                                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Customer");
                                SharedPref.write(SharedPref.SELECTED, pojo.getUsersTypeListResult().get(0).getName());
                                SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                                SharedPref.write(SharedPref.TYPE, "notAdmin");
                                finish();
                            } else if (pojo.getUsersTypeListResult().get(0).getUserType().equals("3")) {
                                startActivity(new Intent(mContext, SplashActivity.class));
                                SharedPref.write(SharedPref.PARTY_CODE, pojo.getUsersTypeListResult().get(0).getPartyCode());
                                SharedPref.write(SharedPref.DASHBOARD_TYPE, "Other");
                                SharedPref.write(SharedPref.SELECTED, pojo.getUsersTypeListResult().get(0).getName());
                                SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                                SharedPref.write(SharedPref.TYPE, "notAdmin");
                                finish();
                            } else if (pojo.getUsersTypeListResult().get(0).getUserType().equals("7")) {
                                Log.e("reg_status", getIntent().getStringExtra("reg_status"));
                                Log.e("getUserType", pojo.getUsersTypeListResult().get(0).getUserType());
                                startActivity(new Intent(mContext, SplashActivity.class));
                                SharedPref.write(SharedPref.USER_TYPE, "new");
                                SharedPref.write(SharedPref.PARTY_CODE, pojo.getUsersTypeListResult().get(0).getPartyCode());
                                SharedPref.write(SharedPref.DASHBOARD_TYPE, "New User");
                                SharedPref.write(SharedPref.SELECTED, pojo.getUsersTypeListResult().get(0).getName());
                                SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                                SharedPref.write(SharedPref.TYPE, "notAdmin");
                                finish();
                            } else if (pojo.getUsersTypeListResult().get(0).getUserType().equals("4")) {
                                startActivity(new Intent(mContext, SplashActivity.class));
                                SharedPref.write(SharedPref.IS_BACK_VISIBLE, "true");
                                SharedPref.write(SharedPref.WHERE_TO_GO, "choose_cat");
                                SharedPref.write(SharedPref.typeNumber, "4");
                                finish();
                            } else {
                                Toast.makeText(mContext, "Invalid User Type..", Toast.LENGTH_SHORT).show();
                            }

                        } else if (pojo.getUsersTypeListResult().size() > 1) {
                            startActivity(new Intent(mContext, SplashActivity.class));
                            SharedPref.write(SharedPref.PARTY_CODE, pojo.getUsersTypeListResult().get(0).getPartyCode());
                            SharedPref.write(SharedPref.DASHBOARD_TYPE, "Other");
                            SharedPref.write(SharedPref.SELECTED, pojo.getUsersTypeListResult().get(0).getName());
                            SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                            SharedPref.write(SharedPref.TYPE, "notAdmin");
                            SharedPref.write(SharedPref.WHERE_TO_GO, "reg_msg");
                            SharedPref.write(SharedPref.IS_BACK_VISIBLE, "true");
                             finish();
                        } else {
                            Log.e("Uaertype", pojo.getUsersTypeListResult().get(0).getUserType());
                            startActivity(new Intent(mContext, SplashActivity.class));
                            SharedPref.write(SharedPref.WHERE_TO_GO, "reg_msg");
                            SharedPref.write(SharedPref.IS_BACK_VISIBLE, "true");
                            finish();
                        }
                    } else {
                        Toast.makeText(mContext, pojo.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressBar.cancel();
            networkConnetion3(mContext);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob3 + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void ResendOTPDialog() {
        // ViewGroup viewGroup = findViewById(android.R.id.content);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.RoundedDialog);

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.resend_oto_dialogs, (RelativeLayout)
                findViewById(R.id.resend_otp_msg));
        TextView otpTextMsg = dialogView.findViewById(R.id.otpTextMsg);
        TextView whatsapp_otp = dialogView.findViewById(R.id.whatsapp_otp);

        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OTPActivity.this, R.style.RoundedDialog);


        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.setView(dialogView);


        otpTextMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP("");
                Toast.makeText(mContext, "OTP sent on Text", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
        whatsapp_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP("WHATSAPP");
                Toast.makeText(mContext, "OTP sent on Whatsapp", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }

        });
        alertDialog.show();
    }

    private void resendOTP(String resend_otp) {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Login");
        progressBar.show();
        //CHECKOTP
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_OTP, response -> {
            Log.e("Data", response);
            progressBar.dismiss();
            //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("jsonObject", jsonObject + "");
                if (jsonObject.getBoolean("ResponseStatus") == true) {
                    // SharedPref.write(SharedPref.USERMOBILE, SharedPref.read(SharedPref.USERMOBILE,""));
//                                startActivity(new Intent(mContext, OTPActivity.class));
//                                finish();
                } else
//                                if (jsonObject.getBoolean("ResponseStatus") == true){
                {
//                                showCustomDialog();
//                                Toast.makeText(mContext, "Not Reg...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            networkConnetion3(mContext);
            progressBar.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                System.out.println("READING_MOBILE_NO " + mob);
//              String str ="{\"MOBILENO\":\"" + mob + "\",\"OTP\":\"}";
                String str = "{\"MOBILENO\":\"" + mob + "\",\"OTPTYPE\":\"" + resend_otp + "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, LoginPage.class));
        finish();
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
            if (Lazy.haveNetworkConnection(mContext)) {
                alertDialog.dismiss();
            } else {
                networkConnetion3(mContext);
            }
        });
        alertDialog.show();
    }
}