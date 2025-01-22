package com.syber.ssspltd.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog {

    public static ProgressDialog showProgressDialog(Context context,
                                                    String msg){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static void hideProgressDialog(ProgressDialog dialog){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
