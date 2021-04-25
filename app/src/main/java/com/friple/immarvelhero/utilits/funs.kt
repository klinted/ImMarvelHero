package com.friple.immarvelhero.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BaseBundle
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.friple.immarvelhero.R
import com.friple.immarvelhero.network.entities.MarvelCharacter
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun ImageView.downloadAndSetImage(url: String) {
    val drawableImage = R.drawable.place_holder_for_image

    Glide.with(context)
        .load(url)
        .placeholder(drawableImage)
        .fitCenter()
        .into(this)
}

fun getTimeStamp(): String {
    return (System.currentTimeMillis() / 1000).toString()
}

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
        Log.d("TAG", "toMd5: ${e.message}")
        e.printStackTrace()
    }
    return ""
}

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