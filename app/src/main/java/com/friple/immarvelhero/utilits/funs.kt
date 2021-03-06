package com.friple.immarvelhero.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.friple.immarvelhero.R
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

fun getHeightOfScreenInPx(): Int {
    val displayMetrics: DisplayMetrics = APP_ACTIVITY.resources.displayMetrics
    return displayMetrics.heightPixels
}

// Show toast
fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_LONG).show()
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

fun toLightTheme() {

    APP_ACTIVITY.apply {
        mToolbar.background = ContextCompat.getDrawable(APP_ACTIVITY, R.color.white)
        mToolbar.elevation = 0f
        window.statusBarColor = ContextCompat.getColor(APP_ACTIVITY, R.color.white)
        mLayerBackground.visibility = View.GONE
    }
}

fun toDarkTheme() {

    APP_ACTIVITY.apply {
        mToolbar.background = ContextCompat.getDrawable(APP_ACTIVITY, R.color.red)
        mToolbar.elevation = 7f
        window.statusBarColor = ContextCompat.getColor(APP_ACTIVITY, R.color.red)
        mLayerBackground.visibility = View.VISIBLE
    }

    // Change color of text on statusBar
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        APP_ACTIVITY.window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
}

fun NestedScrollView.goToTop() {
    this.postDelayed({ this.fullScroll(ScrollView.FOCUS_UP) }, 0)
}