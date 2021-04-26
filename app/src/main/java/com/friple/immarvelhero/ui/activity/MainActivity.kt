package com.friple.immarvelhero.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.friple.immarvelhero.R
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        APP_ACTIVITY = this
    }

    private fun initToolbar() {
        mToolbar = main_toolbar
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))

        setSupportActionBar(mToolbar)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

}