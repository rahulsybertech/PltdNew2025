package com.syber.ssspltd.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.syber.ssspltd.R


object ProgressUtil {

    private var progressDialog: AlertDialog? = null

    fun showProgress(activity: Activity, message: String = "Please wait...") {
        if (progressDialog?.isShowing == true) return

        val inflater = LayoutInflater.from(activity)
        val view = inflater.inflate(R.layout.progress_dialog, null)

        // Optionally set the message dynamically
        view.findViewById<TextView>(R.id.tvProgressMessage)?.text = message

        progressDialog = AlertDialog.Builder(activity)
            .setView(view)
            .setCancelable(false)
            .create()

        progressDialog?.show()
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}
