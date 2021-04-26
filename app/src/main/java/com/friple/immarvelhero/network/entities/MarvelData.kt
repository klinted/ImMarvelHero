package com.friple.immarvelhero.network.entities

data class MarvelData (

    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<MarvelCharacter>
)