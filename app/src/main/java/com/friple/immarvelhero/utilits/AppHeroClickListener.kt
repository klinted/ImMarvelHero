package com.friple.immarvelhero.utilits

import android.view.View
import androidx.cardview.widget.CardView
import com.friple.immarvelhero.network.entities.MarvelCharacter

interface AppHeroClickListener {

    fun onHeroClick(character: MarvelCharacter, listOfViews: List<View>)
}