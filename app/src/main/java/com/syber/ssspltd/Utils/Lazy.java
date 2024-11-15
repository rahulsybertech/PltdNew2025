package com.syber.ssspltd.Utils;

import static com.syber.ssspltd.fragment.HomeFragment.taxType;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.syber.ssspltd.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lazy {
    Context mContext;
    public static String status;
    public static String toDate,formDate;
    private ProgressDialog mProgressDialog;
     public static TextView textShow;


    public static String NumberFormate(String amount){
        DecimalFormat formatter = new DecimalFormat("##,##,##,##,##,##,##0.00");
        String amt = formatter.format(Double.parseDouble(amount));
        return amt;
    }

    public static String NumberFormateTwoDecimal(String number) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        String num = twoDForm.format(Double.parseDouble(number));
        return num;
    }

    public static boolean openDialog(final Context mContext) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:7290097992"));
        Intent chooseIntent=Intent.createChooser(callIntent,"");
        mContext.startActivity(chooseIntent);

        return true;
    }

    public static String amountFormat(String amt){
        DecimalFormat formatter = new DecimalFormat("#,##0.0#");
        String formatedAmt =  formatter.format(Double.parseDouble(amt));
        return formatedAmt;
    }

    public static String extractYTId(String url) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
    public  static boolean TextTypeDialog(final Context mContext ,String taxType){
        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.loader_dialog,null);
        ImageView cross=dialogView.findViewById(R.id.cross);
        TextView update_button=dialogView.findViewById(R.id.update_button);
         textShow=dialogView.findViewById(R.id.textShow);

//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
//        cross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });

//        if (taxType.equals("1"))
//        {
//            getUserStatus="1";
//
//        }
//        else if (taxType.equals("0")){
//            getUserStatus="0";
//
//        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GetUserAppStatus(mContext);
                    alertDialog.dismiss();
               // alertDialog.dismiss();
            }
        });
        alertDialog.show();
        return true;

    }
    public static boolean networkConnetion(Context mContext) {

        final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.network_connetion_dailog,null);
        ImageView cross=dialogView.findViewById(R.id.cross);
        TextView try_button=dialogView.findViewById(R.id.try_button);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.BottomSheetDialogTheme2);
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.RoundedDialog);

        builder.setView(dialogView);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
//
//                ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//                if (netInfo != null) {
//                    if (netInfo.isConnected()) {
//
//
//
//                    } else {
//                        Log.d("Connection", "Network Connection Failed");
//                    }
//
//                } else {
//                    Log.d("Connection", "Network Connection Failed");
//                }

            }
        });
        alertDialog.show();
        return true;


    }
    public static void GetUserAppStatus(Context context) {
        // Toast.makeText(context, "goodtogo", Toast.LENGTH_SHORT).show();
//        final ProgressDialog progressBar = new ProgressDialog(getContext());
//        progressBar.setTitle(" GENERATE OTP");



//        progressBar.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://app.ssspltd.com/apipltd/GetUserAppStatus",
                response -> {
                    Log.e("Data", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("ResponseStatus") == true) {
                            Toast.makeText(context, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                            //   chooseCatagriesAdp.notifyDataSetChanged();
                        } else {
                            TextTypeDialog(context,taxType);
                            Toast.makeText(context, "Account Not Activated: Retry Again", Toast.LENGTH_SHORT).show();

                            // Toast.makeText(context, jsonObject.optString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
            TextTypeDialog(context,taxType);
            Toast.makeText(context, "Account Not Activated: Retry Again", Toast.LENGTH_SHORT).show();
            // progressBar.cancel();
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String mob = SharedPref.read(SharedPref.USERMOBILE, "");
                // String mob=mobile_no_otp.getText().toString();
                String str = "{\"MOBILENO\":\"" + mob + "\",\"PARTYCODE\":\"" + SharedPref.read(SharedPref.PARTY_CODE, "") + "\",\"STATUS\":\"" + taxType+ "\"}";
                Log.e("str", str);
                return str.getBytes();
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }








//        ProgressDialog.Builder builder = new ProgressDialog.Builder(mContext);
//        builder.setMessage("Poor Network Connection")
//                .setCancelable(false)
//                .setPositiveButton("Mobile Data", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        mContext.startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
//
//                    }
//                })
//                .setNegativeButton("Wifi", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                });
//
//        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext,"Cancel",Toast.LENGTH_LONG).show();
//                dialog.dismiss();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();

        //ProgressDialog.show(mContext, "Loading", "Poor Network Connection");
        //Toast.makeText(mContext, "Poor Network Connection", Toast.LENGTH_LONG).show();
//        ProgressDialog mProgressDialog = new ProgressDialog(mContext);
//        mProgressDialog.setTitle("Loading...");
//        mProgressDialog.setMessage("Poor Network Connection");
//        mProgressDialog.show();

       //ProgressDialog.show(mContext, "Poor Network Connection", "Loading...", true, false);


    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static String decode(String url) {
        try {
            String prevURL = "";
            String decodeURL = url;
            while (!prevURL.equals(decodeURL)) {
                prevURL = decodeURL;
                decodeURL = URLDecoder.decode(decodeURL, "UTF-8");
            }
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }
    public static void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e("TAG", "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e("TAG", "Was not able to restart application, PM null");
                }
            } else {
                Log.e("TAG", "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e("TAG", "Was not able to restart application");
        }
    }
}




