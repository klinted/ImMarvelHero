package com.friple.immarvelhero.utilits.adapter

import android.view.View
import com.friple.immarvelhero.ui.recyclerview.views.BaseView

interface AppHeroClickListener {

    fun onClickFromItem(view: BaseView, hashMap: HashMap<String, View>)

    fun onClickFromItem()
}