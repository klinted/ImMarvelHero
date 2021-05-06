package com.friple.immarvelhero.utilits.states

import com.friple.domain.entities.heroes.MarvelCharacter

interface AppStatesController {

    fun onCreated()

    fun onLoading()

    fun onSuccess(listCharacter: List<MarvelCharacter>)

    fun onError()
}