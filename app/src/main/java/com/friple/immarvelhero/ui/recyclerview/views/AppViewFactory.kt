package com.friple.immarvelhero.ui.recyclerview.views

import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.immarvelhero.utilits.TYPE_SCREEN_ERROR
import com.friple.immarvelhero.utilits.TYPE_SCREEN_HEROES
import com.friple.immarvelhero.utilits.TYPE_SCREEN_HERO_DETAIL

object AppViewFactory {

    fun getViewType(typeOfScreen: Int, character: MarvelCharacter = MarvelCharacter()): BaseView {
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
            TYPE_SCREEN_HERO_DETAIL -> {
                HeroDetailView(
                    character.id,
                    character.name,
                    character.description,
                    character.stories,
                    character.thumbnail
                )
            }
            else -> {
                ErrorView()
            }
        }
    }
}