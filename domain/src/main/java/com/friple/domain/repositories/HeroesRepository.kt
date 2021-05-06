package com.friple.domain.repositories

import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelResponse

interface HeroesRepository {

    suspend fun getHeroes(offset: Int, limit: Int): AppResult<MarvelResponse>
}