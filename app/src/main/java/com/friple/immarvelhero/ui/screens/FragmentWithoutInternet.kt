package com.friple.immarvelhero.ui.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.FragmentWithoutInternetBinding
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.isOnline
import com.friple.immarvelhero.utilits.navTo
import com.friple.immarvelhero.utilits.showToast


class FragmentWithoutInternet : Fragment() {


    private lateinit var mBinding: FragmentWithoutInternetBinding

    // Views
    private lateinit var mButtonTry: Button

    private val window: Window = APP_ACTIVITY.window

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_without_internet,
            container,
            false
        )

        initToolbar()

        return mBinding.root
    }

    private fun initToolbar() {
        // This fragment mustn't has action bar
        APP_ACTIVITY.supportActionBar?.hide()

        // Back button
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    APP_ACTIVITY.findNavController(R.id.main_nav_container).navigateUp()
                }
            }
        APP_ACTIVITY.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
    }

    private fun initFields() {

        mButtonTry = mBinding.btnTryAgain

        mButtonTry.setOnClickListener {
            // If phone has internet nav to main screen, else just show toast
            if (isOnline(APP_ACTIVITY)) {
                navTo("MAIN_SCREEN")
                showToast("Back online")
                this.onDestroy()
            } else {
                showToast("Try later...")
            }
        }
    }

    override fun onResume() {
        super.onResume()


        initStatusBar()
    }

    // For setting color of statusBar
    // TODO: 4/25/2021 Make another check of build version
    private fun initStatusBar() {
        window.statusBarColor = ContextCompat.getColor(APP_ACTIVITY, R.color.white)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Activate actionBar
        APP_ACTIVITY.supportActionBar?.show()
        // Set color of
        window.statusBarColor = ContextCompat.getColor(
            APP_ACTIVITY,
            R.color.design_default_color_primary
        )

        // Change color of text on statusBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}