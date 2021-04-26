package com.friple.immarvelhero.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.friple.immarvelhero.R
import com.friple.immarvelhero.ui.screens.FragmentWithoutInternet
import com.friple.immarvelhero.ui.screens.MainScreen
import java.lang.RuntimeException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


// Download and set image by Glide
fun ImageView.downloadAndSetImage(url: String) {
    val drawableImage = R.drawable.place_holder_for_image

    Glide.with(context)
        .load(url)
        .placeholder(drawableImage)
        .fitCenter()
        .skipMemoryCache(true)
        .into(this)
}

// Get current time stamp
fun getTimeStamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

// Show toast
fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
}

// Crypter
fun String.toMd5(): String {
    val MD5 = "MD5"
    try {
        // Create MD5 Hash
        val digest: MessageDigest = MessageDigest.getInstance(MD5)
        digest.update(this.toByteArray())
        val messageDigest: ByteArray = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

// Internet checker
@SuppressLint("NewApi")
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

// Navigate to fragment
fun Fragment.navTo(screen: String) {

    findNavController().navigate(
         when (screen) {
            "MAIN_SCREEN" -> R.id.action_fragmentWithoutInternet_to_mainScreen
            "FRAGMENT_WITHOUT_INTERNET" -> R.id.action_mainScreen_to_fragmentWithoutInternet
            else -> 0
        },
        null,
        null,
        null,
    )
}