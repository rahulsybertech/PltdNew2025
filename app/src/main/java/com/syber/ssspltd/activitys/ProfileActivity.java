package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.GET_LEDGER_REPORT_WITH_BALANCE;
import static com.syber.ssspltd.Constants.NewErpUrls.GET_PROFILR_DEATILS;
import static com.syber.ssspltd.Constants.NewErpUrls.UPDATE_POSTAGE_STATUS;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.syber.ssspltd.Interface.DismisDialogOnResponse;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.AlertUtil;
import com.syber.ssspltd.Utils.Constants;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.TabAdapter;
import com.syber.ssspltd.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Context mContext = this;
    TextView profileName, profileMob_No, profileEmail, profile_pirtyCode;
    int year;
    TextView finYear;
    FloatingActionButton support_flo;
    int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
    int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1);
    String financiyalYearFrom = "";
    String financiyalYearTo = "";
    ActivityProfileBinding binding;
    boolean currentPostegeStatus = false;
    boolean isFirstTime = true;
    Dialog sDialog;
    DismisDialogOnResponse dismisDialogOnResponse;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static int getMonthFromDate(Date date) {
        int result = -1;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.MONTH) + 1;
        }
        return result;
    }

    public static int getYearFromDate(Date date) {
        int result = -1;
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.YEAR);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        support_flo = findViewById(R.id.support_fab);
        support_flo.setOnClickListener(v ->
                Lazy.openDialog(mContext));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileName = findViewById(R.id.profileName);
        profileMob_No = findViewById(R.id.profileMob_No);
        profileEmail = findViewById(R.id.profileEmail);
        profile_pirtyCode = findViewById(R.id.profile_pirtyCode);
        finYear = findViewById(R.id.finYear);
        if (SharedPref.read(SharedPref.DASHBOARD_TYPE, "").equals("Customer")) {
            binding.postege.setVisibility(View.VISIBLE);
            binding.textPostage.setVisibility(View.VISIBLE);
            binding.postege.setOnClickListener(v -> {
                checkDialog("Confirmation", "Cancel", "", currentPostegeStatus);
            });
        } else {
            binding.postege.setVisibility(View.GONE);
            binding.textPostage.setVisibility(View.GONE);
        }


        if (SharedPref.read(SharedPref.SET_YEAR, "").equals("")) {
            if (CurrentMonth < 4) {
                financiyalYearFrom = "" + (CurrentYear - 1);
                financiyalYearTo = "" + (CurrentYear);
            } else {
                financiyalYearFrom = "" + (CurrentYear);
                financiyalYearTo = "" + (CurrentYear + 1);
            }
            finYear.setText(financiyalYearFrom + "-" + financiyalYearTo);
        } else {
            finYear.setText(SharedPref.read(SharedPref.SET_YEAR, ""));
        }

        if (Lazy.haveNetworkConnection(mContext)) {
            GetProfileDetails();
        } else {
            networkConnetion3(mContext);
        }


    }

    private void GetProfileDetails() {
        binding.includeProgress.progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_PROFILR_DEATILS, response -> {
            Log.e("Data", response);
            Log.e("TaG", "URL --->" + GET_PROFILR_DEATILS);
            binding.includeProgress.progress.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus") == true) {
                    JSONObject TotalCustomer = jsonObject.getJSONObject("ProfileDetailsResult");
                    Log.e("test", TotalCustomer.optString("ProfileDetailsResult"));
                    profileName.setText(TotalCustomer.optString("ProfileName"));
                    profileMob_No.setText(TotalCustomer.optString("MobileNo"));
                    profileEmail.setText(TotalCustomer.optString("Email"));
                    profile_pirtyCode.setText(TotalCustomer.optString("PartyCode"));
                    binding.postege.setChecked(TotalCustomer.getBoolean("Postage"));
                    currentPostegeStatus = TotalCustomer.getBoolean("Postage");
                } else {
                    AlertUtil.responseElse(mContext, "GetProfileDetails ", jsonObject.optString("ResponseMessage") + "");
                    Toast.makeText(mContext, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlertUtil.responseExecption(mContext, "GetProfileDetails ", e.toString());

            }
        }, error -> {
            try {
                Constants.convertByteToString(mContext, "GetProfileDetails ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
                String mob3 = SharedPref.read(SharedPref.USERMOBILE, "");
                // String otpp = otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE, "") + "\",\"PartyCode\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"DBNAME\":\"" + SharedPref.read(SharedPref.DB_NAME, "") + "\"}";
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
            GetProfileDetails();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    private void checkDialog(String titleText, String cancelText, String discText, boolean currentStatus) {
        Dialog sDialog = new Dialog(mContext);
        sDialog.setContentView(R.layout.confirmation_dialog);
        Window window = sDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        sDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        sDialog.setCancelable(true);
        TextView title, disc, cancelDialog, updateStatus;
        ImageView cancel;
        title = sDialog.findViewById(R.id.title);
        disc = sDialog.findViewById(R.id.disc);
        cancelDialog = sDialog.findViewById(R.id.stop);
        updateStatus = sDialog.findViewById(R.id.start);
        cancel = sDialog.findViewById(R.id.cancel);
        title.setBackgroundResource(R.color.warning_text);
        title.setText(titleText);
        disc.setText(currentStatus == true ? "Sale Bill/Bility Documents will not send Via Courier" : "Sale Bill/Bility Documents will send Via Courier.");
        cancelDialog.setText(cancelText);
        updateStatus.setText(currentStatus == true ? "Stop" : "Start");
        cancel.setOnClickListener(v -> {
            binding.postege.setChecked(currentStatus);
            sDialog.dismiss();
        });
        cancelDialog.setOnClickListener(v -> {
            binding.postege.setChecked(currentStatus);
            sDialog.dismiss();
        });
        updateStatus.setOnClickListener(v -> {
            if (currentPostegeStatus) {
                updatePostageStatus(binding.profilePirtyCode.getText().toString(), false, sDialog, true);

            } else {
                updatePostageStatus(binding.profilePirtyCode.getText().toString(), true, sDialog, false);
            }
        });


        sDialog.show();
    }

    private void updatePostageStatus(String partyCode, boolean postegeStatus, Dialog dialog, boolean cuttentStatus) {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Updating Status");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_POSTAGE_STATUS,
                response -> {
                    Log.e("Data", response);
                    Log.i("TaG", "URL -->" + UPDATE_POSTAGE_STATUS);
                    progressBar.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus")) {
                            dialog.dismiss();
                            GetProfileDetails();
                            AlertUtil.responseSuccess(mContext, jsonObject.getString("ResponseMessage"));
                        } else {
                            AlertUtil.responseElse(mContext, "UpdatePostageStatus ", jsonObject.getString("ResponseMessage"));
                            binding.postege.setChecked(cuttentStatus);
                        }
                    } catch (JSONException e) {
                        AlertUtil.responseExecption(mContext, "UpdatePostageStatus ", e.toString());
                        e.printStackTrace();
                        binding.postege.setChecked(cuttentStatus);
                    }
                }, error -> {
            try {
                Constants.convertByteToString(mContext, "UpdatePostageStatus ", error);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            progressBar.cancel();
            binding.postege.setChecked(cuttentStatus);
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"PartyCode\":\"" + partyCode + "\",\"Postage\":\"" + postegeStatus + "\"}";
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


//    public static boolean GetProfileDetails() {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetProfileDetails",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("Data", response);
//                        //   progressBar.cancel();
//                        //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getBoolean("ResponseStatus") == true) {
//                                JSONObject TotalCustomer = jsonObject.getJSONObject("ProfileDetailsResult");
//                                Log.e("test", TotalCustomer.optString("TotalCustomer"));
//                                .setText(TotalCustomer.optString("Active"));
//                                inactive.setText(TotalCustomer.optString("InActive"));
//
//
//                            } else {
//                                Toast.makeText(, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //  progressBar.cancel();
//                //  TotalCustomer(data_key);
//                Log.e("volly", error.toString());
//            }
//        }) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                //  String mob = SharedPref.read(SharedPref.USERMOBILE,"");
//               // String mob2 = SharedPref.read(SharedPref.USERMOBILE, "");
//                String str = "{\"MOBILENO\":\"" + "9810053201" + "\",\"PartyCode\":\"" + "DL6" + "\"}";
//
//                Log.e("str", str);
//                return str.getBytes();
//            }
//
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                80000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
//        return true;
//    }
}