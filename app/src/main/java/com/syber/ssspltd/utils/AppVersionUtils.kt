package com.syber.ssspltd.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

object AppVersionUtils {
    fun getVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getVersionCode(context: Context): Long {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.longVersionCode
        } catch (e: Exception) {
            0
        }
    }
}
