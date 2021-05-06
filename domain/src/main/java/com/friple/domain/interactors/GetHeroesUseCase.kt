package com.friple.domain.interactors

import com.friple.domain.entities.ApiParams
import com.friple.domain.interactors.type.UseCaseWithParameter
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelResponse
import com.friple.domain.repositories.HeroesRepository
import sun.rmi.runtime.Log

class GetHeroesUseCase (
    private val heroRepo: HeroesRepository
): UseCaseWithParameter<ApiParams, MarvelResponse> {

    override suspend fun execute(parameter: ApiParams): AppResult<MarvelResponse> {
        return heroRepo.getHeroes(parameter.offset, parameter.limit)
    }
}