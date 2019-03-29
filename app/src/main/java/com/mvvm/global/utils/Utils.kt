package com.mvvm.global.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils


/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px      A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context?): Float {
    if (context == null) return 0f
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / (metrics.densityDpi / 160f)
}


/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into
 * pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context?): Float {
    if (context == null) return 0f
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi / 160f)
}

/**
 * ic_status_validated if user is connected to the Internet
 *
 * @param context Context to get resources and device specific display metrics
 * @return A boolean value
 */
fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}


/**
 * check if the target is a valid mail
 *
 * @return A boolean value
 */
fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}


/**
 * BitShift the entire string to obfuscate it further
 * and make it harder to guess the password.
 */
fun bitShiftEntireString(str: String): String {
    val msg = StringBuilder(str)
    for (i in 0 until msg.length) {
        msg.setCharAt(i, msg[i] + SERIAL_LENGTH)
    }
    return msg.toString()
}

/**
 * Gets the hardware serial number of this device.
 *
 * @return serial number or Settings.Secure.ANDROID_ID if not available.
 * Credit: SecurePreferences for Android
 */
fun getDeviceSerialNumber(context: Context): String {
    try {
        val deviceSerial = Build::class.java.getField(SERIAL).get(
                null
        ) as String
        return if (TextUtils.isEmpty(deviceSerial)) {
            Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
            )
        } else {
            deviceSerial
        }
    } catch (ignored: Exception) {
        return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
        )
    }

}