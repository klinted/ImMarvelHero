package com.friple.domain.usecases

import com.friple.domain.entities.ApiParams
import com.friple.domain.usecases.type.UseCaseWithParameter
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelResponse
import com.friple.domain.repositories.HeroesRepository

class GetHeroesUseCase (
    private val heroRepo: HeroesRepository
): UseCaseWithParameter<ApiParams, MarvelResponse> {

    override suspend fun invoke(parameter: ApiParams): AppResult<MarvelResponse> {
        return heroRepo.getHeroes(parameter.offset, parameter.limit)
    }
}