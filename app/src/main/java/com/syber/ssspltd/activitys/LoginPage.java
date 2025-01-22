package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.NewErpUrls.LOGIN;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syber.ssspltd.BuildConfig;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.adapter.LoginNoAdap.LoginNumberAdapter;
import com.syber.ssspltd.response.LoginNoResponse.AccountDetail;
import com.syber.ssspltd.response.LoginNoResponse.LoginNoPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    public static EditText enter_mobile_number;
    public static String reg_status, mobile_number = "";
    TextView login;
    Context mContext = this;
    TextView ver_code, userType, haveReferl;
    RelativeLayout rlLogin;
    Dialog dialog;
    String referalCode = "";
    ImageView back;
    List<AccountDetail> accountDetails;
    LoginNumberAdapter loginNumberAdapter;
    Type listType;
    LinearLayoutManager linearLayoutManager;
    TextView numberName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        enter_mobile_number = findViewById(R.id.enter_mobile_number);
        login = findViewById(R.id.login);
        rlLogin = findViewById(R.id.rl_login);
        userType = findViewById(R.id.txt_user_type);
        numberName = findViewById(R.id.numberName);
        SharedPref.init(mContext);
        ver_code = findViewById(R.id.ver);
        enter_mobile_number.setInputType(InputType.TYPE_CLASS_NUMBER);
        listType = new TypeToken<LoginNoPojo>() {
        }.getType();
        accountDetails = new ArrayList<>();
        enter_mobile_number.setText(mobile_number);
        if (Lazy.haveNetworkConnection(mContext)) {

        } else {
            networkConnetion3(mContext, "Check Your Internet Connection");
        }

        ver_code.setText("Version - " + BuildConfig.VERSION_CODE + "");
        // ver_code.setText("Version - " + SharedPref.read(SharedPref.AppVersion,""));

        login.setOnClickListener(view -> {
            Log.e("list_type", SharedPref.read(SharedPref.LIST_TYPE, "221"));
            if (enter_mobile_number.getText().toString().length() >= 10) {
                getLogin();
            } else {
                numberName.setText("");
                showCustomDialog();

            }
        });
        enter_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 10) {
                    numberName.setText("");

                } else {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getLogin() {
        final ProgressDialog progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("Login");
        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN,
                response -> {
                    Log.e("loginRespo", response);
                    progressBar.dismiss();
                    LoginNoPojo pojo = new Gson().fromJson(response, listType);
                    if (pojo.getResponseStatus()) {
                        SharedPref.write(SharedPref.USERMOBILE, enter_mobile_number.getText().toString());
                        Log.e("login_sharedPref", enter_mobile_number.getText().toString());
                        if (pojo.getUserStatus().equals("REGUSER")) {
                            Toast.makeText(mContext, "OTP sent on mobile number", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, OTPActivity.class)
                                    .putExtra("reg_status", pojo.getUserStatus()));
                            finish();
                        } else if (pojo.getUserStatus().equals("NEWUSER")) {
                            startActivity(new Intent(mContext, MainActivity.class)
                                    .putExtra("reg_status", pojo.getUserStatus()));
                            SharedPref.write(SharedPref.isLogin, "true");
                            SharedPref.write(SharedPref.USER_TYPE, "new");
                            SharedPref.write(SharedPref.PARTY_CODE, "new");
                            SharedPref.write(SharedPref.DASHBOARD_TYPE, "New User");
                            SharedPref.write(SharedPref.SELECTED, "");
                            SharedPref.write(SharedPref.WHERE_TO_GO, "main_act");
                            SharedPref.write(SharedPref.TYPE, "notAdmin");
                            finish();
                        } else {
                            startActivity(new Intent(mContext, GST_NumberActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("reg_status", pojo.getUserStatus()));
                            finish();
                        }
                    } else if (pojo.getUserStatus().equals("Try With Register No")) {
                        accountDetails.clear();
                        accountDetails.addAll(pojo.getAccountDetail());
                        //loginNumberAdapter.notifyDataSetChanged();
                        dialog = new Dialog(mContext); // Context, this, etc.
                        dialog.setContentView(R.layout.dialog_login_no_reg);
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.CENTER);
                        RecyclerView loginRespon = dialog.findViewById(R.id.loginRespons);
                        ImageView cancelNum = dialog.findViewById(R.id.cancelNum);
                        TextView old_number = dialog.findViewById(R.id.old_number);
                        String oldNum = enter_mobile_number.getText().toString();
                        old_number.setText("\"Entered Number " + oldNum + " is not\n" + "  registred with us\"");
//                            TextView confirm_num = dialog.findViewById(R.id.confirm_num);
//                            TextView get_number = dialog.findViewById(R.id.get_number);
//                            login_num.setText(pojo.getResponseMessage());
//                            get_number.setText(enter_mobile_number.getText().toString());
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        reg_status = pojo.getUserStatus();
                        linearLayoutManager = new LinearLayoutManager(mContext);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        loginRespon.setLayoutManager(linearLayoutManager);
                        loginNumberAdapter = new LoginNumberAdapter(mContext, accountDetails);
                        loginRespon.setAdapter(loginNumberAdapter);
                        cancelNum.setOnClickListener(v -> dialog.dismiss());
                        dialog.show();
                    } else {
                        showCustomDialog();
                    }
                }, error -> {
            progressBar.dismiss();
            networkConnetion3(mContext, error.toString());
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = enter_mobile_number.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\"}";
                //key - new
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

    public void networkConnetion3(Context mContext, String errorMsg) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
        TextView errorMsgText = dialogView.findViewById(R.id.text);
        errorMsgText.setText(errorMsg);
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
                Toast.makeText(mContext, "fgsf", Toast.LENGTH_SHORT).show();
                networkConnetion3(mContext, "");
            }
        });
        alertDialog.show();
    }

    private void showCustomDialog() {
        // ViewGroup viewGroup = findViewById(android.R.id.content);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.RoundedDialog);

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_loginmsg, (RelativeLayout)
                findViewById(R.id.date_filter_di));
        ImageView cancel_button = dialogView.findViewById(R.id.all_clear_login);

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this, R.style.RoundedDialog);


        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(dialogView);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void setLoginNo(AccountDetail accountDetail) {
        dialog.dismiss();
        enter_mobile_number.setText(accountDetail.getMobileNo());
        numberName.setText(accountDetail.getAccountName());

    }

    public void filterDialog() {

        dialog = new Dialog(mContext, R.style.AppTheme);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogThemeUpDown;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.referal_dialog);
        ImageView bakc = dialog.findViewById(R.id.back);
        TextView submit = dialog.findViewById(R.id.submit);
        TextView referralCode_number = dialog.findViewById(R.id.referralCode_number);
        bakc.setOnClickListener(v -> {
            dialog.dismiss();
        });
//        submit.setOnClickListener(v -> {
//            REFERRAL(referralCode_number.getText().toString());
////            if (referralCode_number.getText().toString().length()>=7) {
////                REFERRAL(referralCode_number.getText().toString());
////            } else {
////                Toast.makeText(mContext, "Enter valid Referal Code", Toast.LENGTH_SHORT).show();
////            }
//        });

        dialog.show();
    }
//    private void REFERRAL(String referal) {
//        final ProgressDialog progressBar = new ProgressDialog(mContext);
//        progressBar.setTitle("REFERRAL");
//        progressBar.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/VerifyReferral",
//                response -> {
//                    Log.e("Data", response);
//                    progressBar.dismiss();
//                    //Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        Log.e("jsonObject", jsonObject + "");
//                        if (jsonObject.getBoolean("ResponseStatus") == true) {
//                            referalCode = referal;
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(mContext, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> progressBar.cancel()) {
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                String str = "{\"REFERRAL\":\"" + referal + "\"}";
//                Log.e("str", str);
//                return str.getBytes();
//            }
//
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//        };
//
//        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}