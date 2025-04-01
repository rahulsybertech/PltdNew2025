package com.syber.ssspltd.Utils;



import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.syber.ssspltd.adapter.supplierformadapter.SupplierOrderReportAdptr;

public class CustomToast {

    // Method to show custom Toast with icon and message
    public static void show(Context context, String message,  int iconRes) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Create horizontal layout for icon and text
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(32, 16, 32, 16); // Padding for better appearance

        // Add icon to the Toast
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(iconRes); // Set custom icon
        layout.addView(imageView);

        // Add message to the Toast
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setPadding(16, 0, 0, 0); // Space between icon and message
        layout.addView(textView);

        // Create and show the Toast
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.setView(layout);
        toast.show();
    }

    // Overloaded method to support string resources
    public static void show(Context context, @StringRes int messageRes, @DrawableRes int iconRes) {
        show(context, context.getString(messageRes), iconRes);
    }

    // Overloaded method to show toast with default icon
    public static void show(Context context, String message) {
        show(context, message, android.R.drawable.ic_dialog_info);
    }

    // Overloaded method for string resource with default icon
    public static void show(Context context, @StringRes int messageRes) {
        show(context, context.getString(messageRes), android.R.drawable.ic_dialog_info);
    }



}

