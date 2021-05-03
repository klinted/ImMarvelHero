package com.friple.immarvelhero.ui.recyclerview.viewholders

import com.friple.immarvelhero.ui.recyclerview.viewes.BaseView

interface BaseHolder {

    fun drawMessage(view: BaseView)

    fun onAttach(view: BaseView)
    fun onDetach()
}