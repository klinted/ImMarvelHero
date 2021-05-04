package com.friple.immarvelhero.ui.recyclerview.viewholders

import com.friple.immarvelhero.ui.recyclerview.viewes.BaseView
import com.friple.immarvelhero.utilits.AppHeroClickListener

interface BaseViewHolder {

    fun drawMarvelHero(view: BaseView)

    fun onAttach(view: BaseView,  listener: AppHeroClickListener)
    fun onDetach()
}