package com.friple.immarvelhero.utilits.adapter

import android.view.View
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.immarvelhero.ui.recyclerview.views.BaseView

interface AppHeroClickListener {

    fun onClickFromItem(character: MarvelCharacter)

    fun onClickFromItem()
}