package com.friple.immarvelhero.network.entities


data class MarvelCharacter (

    val id: Int,
    val name: String,
    val description: String,
    val stories : Stories,
    val thumbnail: Thumbnail
)
