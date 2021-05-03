package com.friple.immarvelhero.ui.recyclerview.viewes

import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.utilits.TYPE_SCREEN_ERROR
import com.friple.immarvelhero.utilits.TYPE_SCREEN_HEROES

object AppViewFactory {

    fun getViewType(typeOfScreen: Int = 0, characterList: List<MarvelCharacter>): BaseView {
        return when (typeOfScreen) {
            TYPE_SCREEN_HEROES -> {
                HeroesView(
                    character.id,
                    character.name,
                    character.description,
                    character.stories,
                    character.thumbnail
                )
            }
            TYPE_SCREEN_ERROR -> {
                ErrorView()
            }
            else -> {
                HeroesView(
                    character.id,
                    character.name,
                    character.description,
                    character.stories,
                    character.thumbnail
                )
            }
        }
    }
}