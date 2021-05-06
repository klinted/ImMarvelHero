package com.friple.data.repository

import android.util.Log
import com.friple.data.services.ApiProvider
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelResponse
import com.friple.domain.repositories.HeroesRepository
import retrofit2.await

class HeroesRepositoryImpl : HeroesRepository {

    override suspend fun getHeroes(offset: Int, limit: Int): AppResult<MarvelResponse> {
        return try {
            val heroes = ApiProvider.getMarvelService().getHeroes(offset, limit).await()
            AppResult.Success(heroes)
        } catch (e: Throwable) {
            AppResult.Error(e)
        }
    }

    companion object {
        val instance = HeroesRepositoryImpl()
    }
}