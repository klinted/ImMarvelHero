package com.friple.data.repository

import com.friple.data.services.ApiProvider
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelResponse
import com.friple.domain.repositories.HeroesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.await

class HeroesRepositoryImpl(private val ioDispatcher: CoroutineDispatcher) : HeroesRepository {

    override suspend fun getHeroes(offset: Int, limit: Int): AppResult<MarvelResponse> =
        withContext(ioDispatcher) {
            try {
                val heroes = ApiProvider.getMarvelService().getHeroes(offset, limit).await()
                AppResult.Success(heroes)
            } catch (e: Throwable) {
                AppResult.Error(e)
            }
        }

    companion object {
        fun getInstance(ioDispatcher: CoroutineDispatcher): HeroesRepositoryImpl {
            return HeroesRepositoryImpl(ioDispatcher)
        }
    }
}