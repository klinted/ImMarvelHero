package com.friple.immarvelhero.utilits

import android.view.View
import com.friple.immarvelhero.ui.recyclerview.viewes.BaseView

interface AppHeroClickListener {

    fun onClickFromItem(view: BaseView, hashMap: HashMap<String, View>)

    fun onClickFromItem()
}