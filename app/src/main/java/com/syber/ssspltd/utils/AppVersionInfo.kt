package com.syber.ssspltd.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppVersionInfo() {
    val context = LocalContext.current
    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }

    Column(modifier = Modifier.padding(20.dp)) {
        Text("Version Name: ${packageInfo.versionName}")
        Text("Version Code: ${packageInfo.longVersionCode}")
    }
}