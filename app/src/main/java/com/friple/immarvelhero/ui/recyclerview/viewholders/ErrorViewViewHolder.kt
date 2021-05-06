package com.friple.immarvelhero.ui.recyclerview.viewholders

import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.*
import com.friple.immarvelhero.utilits.adapter.AppHeroClickListener

class ErrorViewViewHolder(view: View) : RecyclerView.ViewHolder(view), BaseViewHolder {

    private val mClError: ConstraintLayout = view.findViewById(R.id.cl_error)
    private val mBtnRetry: Button = view.findViewById(R.id.btn_try_again)

    override fun drawMarvelHero(view: BaseView) {
        val lp = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, getHeightOfScreenInPx())
        mClError.layoutParams = lp
        toLightTheme()
    }

    override fun onAttach(view: BaseView, listener: AppHeroClickListener) {
        mBtnRetry.setOnClickListener {
            // If phone has internet nav to main screen, else just show toast
            if (isOnline(APP_ACTIVITY)) {
                listener.onClickFromItem()
                toDarkTheme()
                showToast("Back online")
            } else {
                showToast("Try later...")
            }
        }
    }

    override fun onDetach() {
    }
}