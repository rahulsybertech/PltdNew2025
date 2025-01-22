package com.syber.ssspltd.Utils;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.VolleyError;
import com.syber.ssspltd.activitys.LoginPage;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Constants {

    public static void convertByteToString(Context context, String API_NAME, VolleyError error) throws JSONException {
        if (new JSONObject(new String(error.networkResponse.data)).getString("responseCode").equals("401")) {
            advancedResponseError(context, new JSONObject(new String(error.networkResponse.data)).getString("message"));
        } else {
            AlertUtil.responseError(context, API_NAME + " ", error.toString());
        }
    }

    public static String SettingHeader(){
        return "Bearer " + SharedPref.read(SharedPref.ACCCESS_TOKEN, "");
    }

    @SuppressLint("HardwareIds")
    public static String DEVICE_ID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static void advancedResponseError(Context context, String error) {
       SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Alert!");
        sweetAlertDialog.setContentText(error);
        sweetAlertDialog.setConfirmText("Understood!");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmClickListener(sweetAlertDial -> {
                    if (error.equals("Token is expired or invalid.")) {
                        String s = SharedPref.read(SharedPref.clubType, "");
                        SharedPref.clear();
                        SharedPref.write(SharedPref.clubType, s);
                        Log.i("TaG", "after logout -=-=-=-=> " + SharedPref.read(SharedPref.ACCCESS_TOKEN, ""));
                        Intent logout = new Intent(context, LoginPage.class);
                        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(logout);
                        ((Activity) context).finish();
                        sweetAlertDial.dismissWithAnimation();
                    } else {
                        sweetAlertDial.dismiss();
                    }
                });
        sweetAlertDialog.show();
    }
}
