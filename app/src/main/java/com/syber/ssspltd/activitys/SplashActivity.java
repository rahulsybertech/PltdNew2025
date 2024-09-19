package com.syber.ssspltd.activitys;

import static com.syber.ssspltd.Constants.URLConstants.CLUB_TYPE_BY_ACCOUNT_ID;
import static com.syber.ssspltd.Constants.URLConstants.CLUB_TYPE_DETAILS_OBJECT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.syber.ssspltd.Utils.Lazy;
import com.syber.ssspltd.R;
import com.syber.ssspltd.Utils.SharedPref;
import com.syber.ssspltd.Utils.VolleySingleton;
import com.syber.ssspltd.activitys.clubtype.ClubTypeActivity;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    Context mContext = this;
//    Button Button1,Button2,Button3,Button4;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPref.init(mContext);
        imageView = findViewById(R.id.logo);

        if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("DIAMOND")){
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher__new_diamond));
        } else if (SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("GOLD")) {
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher__new_gold));
        }else {
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher_sss_logo));
        }

//        Log.e("isLogin",SharedPref.read(SharedPref.isLogin,""));
//        Log.e("IS_ANY_CHOOSEN",SharedPref.read(SharedPref.IS_ANY_CHOOSEN,""));
//        Log.e("IS_SUPPER_SELECTED",SharedPref.read(SharedPref.IS_SUPPER_SELECTED,""));
//        Log.e("WHERE_TO_GO",SharedPref.read(SharedPref.WHERE_TO_GO,""));
//        Log.e("USERMOBILE",SharedPref.read(SharedPref.USERMOBILE,""));

        if (Lazy.haveNetworkConnection(mContext)) {
//            checkold();
           if (!SharedPref.read(SharedPref.PARTY_CODE,"").equals("")) {
               getClubType();
           }else {
               checkold();
           }
        } else {
            networkConnetion3(mContext);
        }
    }
    private void getClubType() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CLUB_TYPE_BY_ACCOUNT_ID, response -> {
            Log.e("Api Cat ", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("ResponseStatus")) {

                    if (SharedPref.read(SharedPref.clubType,"").equals(jsonObject.getString("ClubType"))){
                        checkold();
                    }else {
                        if ((SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("GOLD")
                                ||SharedPref.read(SharedPref.clubType,"").equalsIgnoreCase("DIAMOND"))
                                && (jsonObject.getString("ClubType").equalsIgnoreCase("SSSPLTD") ||
                                jsonObject.getString("ClubType").equalsIgnoreCase("N/A") ||
                                jsonObject.getString("ClubType").equalsIgnoreCase("NA") ||
                                jsonObject.getString("ClubType").equalsIgnoreCase(""))) {
                                responseSuccess(mContext, "Click here to Switch Club type to Normal", jsonObject.getString("ClubType"));
                        }
                        else if (jsonObject.getString("ClubType").equalsIgnoreCase("SSSPLTD") || jsonObject.getString("ClubType").equalsIgnoreCase("N/A") || jsonObject.getString("ClubType").equalsIgnoreCase("NA") || jsonObject.getString("ClubType").equalsIgnoreCase("")) {
                                SharedPref.write(SharedPref.clubType, jsonObject.getString("ClubType"));
                                SharedPref.write(SharedPref.noClubType,"true");
                                PackageManager pm0 = getPackageManager();
                                pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.DiamondActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                                pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.GoldActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                                pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.DefaultActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                            } else {
                                responseSuccess(mContext, jsonObject.getString("ResponseMessage"), jsonObject.getString("ClubType"));
                            }
                        }

                } else {
                    Toast.makeText(mContext, jsonObject.getString("ResponseMessage")+"", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Exce3rrr", e.toString());
                Log.e("Exce3rrr", e.getMessage());
            }
        }, error -> {
            Log.e("error", error.toString() + "");
            Log.e("error", error.getMessage() + "");
            // networkDialog();
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"MOBILENO\":\"" + SharedPref.read(SharedPref.USERMOBILE,"") + "\",\"AccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\"}";
//                String str = "{\"AccountID\":\"" + SharedPref.read(SharedPref.PARTY_CODE,"") + "\"}";
                Log.e("strrr",str);
                return str.getBytes();
            }
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void responseSuccess(Context context, String succesMsg,String clubType) {
        SweetAlertDialog sweetAlertDialog1 =  new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                //.setCustomImage(R.drawable.error)
        sweetAlertDialog1.setCancelable(false);
        sweetAlertDialog1.setConfirmButtonTextColor(ContextCompat.getColor(context, R.color.success_text));
                sweetAlertDialog1.setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.success_bg));
                sweetAlertDialog1.setTitleText(clubType.equals("N/A")?"":clubType + context.getString(R.string.happy_emoji));
                sweetAlertDialog1.setContentText( succesMsg);
                sweetAlertDialog1.setConfirmText("OK");
                sweetAlertDialog1.setConfirmClickListener(sweetAlertDialog -> {
                    if (clubType.equalsIgnoreCase("DIAMOND")){
                        SharedPref.write(SharedPref.clubType,clubType);
                        SharedPref.write(SharedPref.noClubType,"false");
                        PackageManager pmm = getPackageManager();
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.DiamondActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.GoldActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.DefaultActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                       sweetAlertDialog.dismissWithAnimation();

                    }else if (clubType.equalsIgnoreCase("GOLD")){
                        SharedPref.write(SharedPref.clubType,clubType);
                        SharedPref.write(SharedPref.noClubType,"false");
                        PackageManager pmm = getPackageManager();
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.DiamondActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.GoldActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                        pmm.setComponentEnabledSetting(new ComponentName(SplashActivity.this,"com.syber.ssspltd.helper.DefaultActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                        sweetAlertDialog.dismissWithAnimation();

                    }

                    else if (clubType.equalsIgnoreCase("SSSPLTD") || clubType.equalsIgnoreCase("N/A")|| clubType.equalsIgnoreCase("NA") || clubType.equalsIgnoreCase("")){
                        SharedPref.write(SharedPref.clubType,clubType);
                        PackageManager pm0 = getPackageManager();
                        pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.DiamondActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                        pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.GoldActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                        pm0.setComponentEnabledSetting(new ComponentName(SplashActivity.this, "com.syber.ssspltd.helper.DefaultActivityAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                        sweetAlertDialog.dismissWithAnimation();
                    }
                    else {
                        checkold();
                        sweetAlertDialog.dismissWithAnimation();
                    }

                })
                .show();
    }

    private void check() {
        Log.e("ChackMobile", SharedPref.read(SharedPref.USERMOBILE, ""));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetPltdVersion",
                response -> {
                    Log.e("res", response);
                    try {
                        JSONObject ob = new JSONObject(response);
                        String SoftVersion = "10";
                        String HardVersion = "10";
                        //Log.e("ver", ob.getString("AppVersion"));
                        if (HardVersion.equals("10")) {
                            new Handler().postDelayed(() -> goToNext(), 2600);
                        } else {
                            showVersion(HardVersion, SoftVersion);
                        }
                    } catch (Exception e) {
                        Log.e("Exce", e.toString());
                    }
                },
                error -> {
                    Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
                    Log.e("Volly ", error.getMessage() + "");
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"DATAKEY\":\"" + "SSSPLTD" + "\",\"DEVICE\":\"" + "ANDROID" + "\"}";
                Log.e("str", SharedPref.read(SharedPref.USERMOBILE, "") + "djsghgcb");
                return str.getBytes();
            }
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void checkold() {
        Log.e("ChackMobile", SharedPref.read(SharedPref.USERMOBILE, ""));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetAppVersion",
                response -> {
                    Log.e("res", response);
                    try {
                        JSONObject ob = new JSONObject(response);
                        int ver = Integer.parseInt(ob.optString("AppVersion"));
                        //Log.e("ver", ob.getString("AppVersion"));
                        if (ver <= 33) {
                            new Handler().postDelayed(this::goToNext, 2600);
                        } else {
                            //  if ()
                            showVersionold();
                        }
                        SharedPref.write(SharedPref.AppVersion, ver);
                    } catch (Exception e) {
                        Log.e("Exce", e.toString());
                    }
                },
                error -> {
                    networkConnetion3(mContext);
                    Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
                    Log.e("Volly ", error.getMessage() + "");
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\"DATAKEY\":\"" + "ssspltd" + "\"}";
                Log.e("str", SharedPref.read(SharedPref.USERMOBILE, "") + "djsghgcb");
                return str.getBytes();
            }
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void showVersionold() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.update_dailog);
        dialog.setCancelable(false);
        final TextView submit = dialog.findViewById(R.id.submit);
        final TextView skip = dialog.findViewById(R.id.skip);
        skip.setVisibility(View.GONE);
        submit.setOnClickListener(v -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private void showVersion(String hardver, String softver) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.update_dailog);
        dialog.setCancelable(false);
        final TextView submit = dialog.findViewById(R.id.submit);
        final TextView skip = dialog.findViewById(R.id.skip);
        if (hardver.equals(softver)) {
            skip.setVisibility(View.VISIBLE);
        } else {
            skip.setVisibility(View.GONE);
        }
        skip.setOnClickListener(v -> goToNext());
        submit.setOnClickListener(v -> {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    private void goToNext() {
        if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("true")) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_SUPPER_SELECTED, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("true")) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_SUPPER_SELECTED, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("")) {
            startActivity(new Intent(mContext, ChooseCategries.class));
            finish();
        } else if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_SUPPER_SELECTED, "").equals("false") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("")) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("") && SharedPref.read(SharedPref.WHERE_TO_GO, "").equals("choose_cat")) {
            startActivity(new Intent(mContext, ChooseCategries.class));
            finish();
        } else if (SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("") && SharedPref.read(SharedPref.WHERE_TO_GO, "").equals("reg_msg")) {
            startActivity(new Intent(mContext, registered_msg.class));
            finish();
        } else if (!SharedPref.read(SharedPref.USERMOBILE, "").equals("") && SharedPref.read(SharedPref.isLogin, "").equals("true") && SharedPref.read(SharedPref.IS_ANY_CHOOSEN, "").equals("") && SharedPref.read(SharedPref.WHERE_TO_GO, "").equals("main_act")) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(mContext, LoginPage.class));
            finish();
        }
    }

    public void networkConnetion3(Context mContext) {
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog, null);
        ImageView cross = dialogView.findViewById(R.id.cross);
        TextView try_button = dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);
        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        cross.setOnClickListener(v -> alertDialog.dismiss());
        try_button.setOnClickListener(view -> {
            checkold();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}