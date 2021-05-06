package com.friple.immarvelhero.ui.recyclerview.viewholders

import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.adapter.AppHeroClickListener

interface BaseViewHolder {

    fun drawMarvelHero(view: BaseView)

    fun onAttach(view: BaseView,  listener: AppHeroClickListener)
    fun onDetach()
}