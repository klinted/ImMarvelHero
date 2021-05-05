package com.friple.immarvelhero.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.friple.immarvelhero.R
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var mClMainActivity: ConstraintLayout
    lateinit var mImageView: ImageView
    lateinit var mFmDarker: FrameLayout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APP_ACTIVITY = this

        initStatusBar()
        initToolbar()

        mClMainActivity = findViewById(R.id.cl_main_activity)
        mImageView = findViewById(R.id.iv_bg_iron_man)
        mFmDarker = findViewById(R.id.fl_darker)
    }

    private fun initToolbar() {
        mToolbar = main_toolbar
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        setSupportActionBar(mToolbar)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            APP_ACTIVITY.window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            APP_ACTIVITY.window.decorView.systemUiVisibility =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                } else {
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }

        }
    }
}