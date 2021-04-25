package com.friple.immarvelhero.ui.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.FragmentWithoutInternetBinding
import com.friple.immarvelhero.databinding.ScreenHeroBinding
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.isOnline

class FragmentWithoutInternet : Fragment() {

    private lateinit var mBinding: FragmentWithoutInternetBinding

    // Views
    private lateinit var mButtonTry: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_without_internet, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
    }

    private fun initFields() {
        mButtonTry = mBinding.btnTryAgain

        mButtonTry.setOnClickListener {
            if (isOnline(APP_ACTIVITY.applicationContext)) {
                findNavController().navigate(
                    R.id.action_fragmentWithoutInternet_to_mainScreen,
                    null,
                    null
                )
                onDestroy()
            } else {
                Toast.makeText(APP_ACTIVITY, "Check your connection!", Toast.LENGTH_LONG).show()
            }
        }
    }
}