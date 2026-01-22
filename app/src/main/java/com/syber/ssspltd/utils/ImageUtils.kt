package com.syber.ssspltd.utils


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageUtils {

    // -----------------------------------------
    // 1️⃣ Convert Bitmap → Base64
    // -----------------------------------------
    fun bitmapToBase64(bitmap: Bitmap, quality: Int = 80): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    // -----------------------------------------
    // 2️⃣ Convert Uri → Base64
    // -----------------------------------------
    fun uriToBase64(context: Context, uri: Uri, quality: Int = 80): String {
        val inputStream = context.contentResolver.openInputStream(uri )
        val bytes = inputStream?.readBytes() ?: return ""
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    // -----------------------------------------
    // 3️⃣ Convert ByteArray → Base64
    // -----------------------------------------
    fun byteArrayToBase64(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}
