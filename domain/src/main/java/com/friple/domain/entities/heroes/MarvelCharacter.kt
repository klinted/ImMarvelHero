package com.friple.domain.entities.heroes


data class MarvelCharacter (

    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val stories : Stories = Stories(),
    val thumbnail: Thumbnail = Thumbnail()
)
