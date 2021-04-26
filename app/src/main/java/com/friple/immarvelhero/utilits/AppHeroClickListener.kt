package com.friple.immarvelhero.utilits

import android.view.View
import com.friple.immarvelhero.network.entities.MarvelCharacter

interface AppHeroClickListener {

    fun onHeroClick(character: MarvelCharacter, hashMap: HashMap<String, View>)
}