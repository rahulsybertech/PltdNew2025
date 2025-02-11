package com.syber.ssspltd.Utils
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.syber.ssspltd.R
import com.syber.ssspltd.activitys.supplierorderform.SupplierOrderFormActivity

class CustomDialogNew {



    fun showCustomDialog(context: Context) {
        // Inflate the custom layout from XML
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        // Create the AlertDialog with the custom layout
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Set to true if you want the dialog to be dismissed when tapping outside
            .create()

        // Access the custom views in the inflated layout
        val dialogTitle: TextView = dialogView.findViewById(R.id.dialog_title)
        val dialogContent: TextView = dialogView.findViewById(R.id.dialog_content)
        val confirmButton: Button = dialogView.findViewById(R.id.confirm_button)
       // val dialogIcon: ImageView = dialogView.findViewById(R.id.dialog_icon)

        // Set text or other properties dynamically
        dialogTitle.text = "Order Saved Successfully"
        dialogContent.text = "Please check WhatsApp for your PDF"
    //    dialogIcon.setImageResource(R.drawable.success_icon)  // Change icon dynamically

        // Set the behavior of the confirm button
        confirmButton.setOnClickListener {
            context.startActivity(Intent(context, SupplierOrderFormActivity::class.java))
            if (context is Activity) {
                context.finish()
            }
            alertDialog.dismiss()  // Dismiss the dialog
            // Handle any other actions (e.g., navigating to another screen)
        }

        // Show the dialog
        alertDialog.show()
    }

}