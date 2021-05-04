package com.friple.immarvelhero.utilits.states

import com.friple.immarvelhero.network.entities.MarvelCharacter

interface AppStatesController {

    fun onCreated()

    fun onLoading()

    fun onSuccess(listCharacter: List<MarvelCharacter>)

    fun onError()
}