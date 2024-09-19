package com.syber.ssspltd.Utils;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;


import com.syber.ssspltd.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AlertUtil {
//    AlertUtil.responseAlert(mContext,"Opps! "+getResources().getString(R.string.sad_emoji),"OrderCode api is getting false status. Please try after sometime","OK",
//    SweetAlertDialog.ERROR_TYPE,R.color.error_text,R.color.error_bg);


    public static String confirmText = "OK";
    public static String errorTitle = "Opps! ";
    public static String successTitle = "Thanks! ";
    public static String errorType = "Response Error :: ";
    public static String exceType = "Exception :: ";
    public static String statusFalseMsg = " api is getting false status. Please try after sometime";


    public static void successAlert() {

    }

    public static void responseElse(Context context, String apiName,String statusFalseMsg) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.error)
                .setConfirmButtonTextColor(ContextCompat.getColor(context, R.color.error_text))
                .setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.error_bg))
                .setTitleText(errorTitle + context.getString(R.string.sad_emoji))
                .setContentText(apiName + statusFalseMsg)
                .setConfirmText(confirmText)
                .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                .show();
    }

    public static void responseExecption(Context context, String apiName,String exception) {
       String execptionMsg = exceType +apiName +" api is getting "+exception+". "+"Please Try after sometime!";
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.error)
                .setConfirmButtonTextColor(ContextCompat.getColor(context, R.color.error_text))
                .setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.error_bg))
                .setTitleText(errorTitle + context.getString(R.string.sad_emoji))
                .setContentText( execptionMsg)
                .setConfirmText(confirmText)
                .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                .show();
    }


    public static void responseError(Context context, String apiName,String error) {
        String responseErrorMsg = errorType +apiName +" api is getting "+error+". "+"Please Try after sometime!";
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.error)
                .setConfirmButtonTextColor(ContextCompat.getColor(context, R.color.error_text))
                .setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.error_bg))
                .setTitleText(errorTitle + context.getString(R.string.sad_emoji))
                .setContentText( responseErrorMsg)
                .setConfirmText(confirmText)
                .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                .show();
    }

    public static void responseSuccess(Context context,String succesMsg) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                //.setCustomImage(R.drawable.error)
                .setConfirmButtonTextColor(ContextCompat.getColor(context, R.color.success_text))
                .setConfirmButtonBackgroundColor(ContextCompat.getColor(context, R.color.success_bg))
                .setTitleText(successTitle + context.getString(R.string.happy_emoji))
                .setContentText( succesMsg)
                .setConfirmText(confirmText)
                .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                .show();
    }

    public static void loadingDialog(Context context,boolean isShow) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
//        if (!isShow)
//        pDialog.dismissWithAnimation();
//        else
            pDialog.show();
    }

    public static void  deleteDialog(Context context){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation())
                .setCancelButton("Cancel", sDialog -> sDialog.dismissWithAnimation())
                .show();
    }
}


