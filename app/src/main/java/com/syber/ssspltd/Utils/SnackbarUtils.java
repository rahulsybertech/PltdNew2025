package com.syber.ssspltd.Utils;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.syber.ssspltd.R;

public class SnackbarUtils {

    public static void showErrorSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.BLACK);  // Set background color
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.RED); // Set text color
        textView.setTextSize(16f);  // Optional: Change text size
        
        snackbar.show();
    }

    public static void showSuccessSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.RED);  // Set background color
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE); // Set text color
        textView.setTextSize(16f);
        
        snackbar.show();
    }

    public static void showCustomSnackbar(View view, String message, int backgroundColor, int textColor) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(backgroundColor);  // Set background color
        
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(textColor); // Set text color
        
        snackbar.show();
    }
}
