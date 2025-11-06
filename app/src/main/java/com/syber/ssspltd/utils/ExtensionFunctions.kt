package com.syber.ssspltd.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.text.Editable
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Context.dp2px(resource: Resources, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        resource.displayMetrics
    ).toInt()
}

fun Context.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
fun Context.editTextSetText(editText: EditText, message: String?) {
    message?.let {
        editText.text = Editable.Factory.getInstance().newEditable(it)
        // Toast.makeText(this, message, Toast.LENGTH_LONG).show() // optional toast
    }
}
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}


fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun String.removeZero(): String {
    val data = this
    return when {
        data.endsWith(".0") -> {
            data.substring(0, data.length - 2)
        }
        data.endsWith(".00") -> {
            data.substring(0, data.length - 3)
        }
        else -> {
            this
        }
    }
}

fun Double.roundToTwoDigits(): String {
    val df = DecimalFormat("#.##")
    val dfs = DecimalFormatSymbols()
    dfs.decimalSeparator = '.'
    df.decimalFormatSymbols = dfs
    return df.format(this).replace(",",".")
}


fun View.setBackgroundTint(@ColorRes colorResId: Int) {
    this.backgroundTintList = ContextCompat.getColorStateList(this.context, colorResId)
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}
// Extension for Activity
fun Activity.hideKeyboard() {
    val view = currentFocus ?: View(this)
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

// Extension for Fragment
fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

// Extension for View
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
