package com.friple.immarvelhero.ui.presenter

import com.friple.domain.entities.ApiParams
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.domain.entities.heroes.MarvelResponse
import com.friple.domain.interactors.GetHeroesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HeroPresenter(private val heroesUseCase: GetHeroesUseCase) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var job: Job? = null

    fun getHeroes(
        params: ApiParams,
        onSuccess: (List<MarvelCharacter>) -> Unit,
        onError: (String) -> Unit
    ) {
        job = launch {

            getHeroesAsync( params,
                { response ->
                    onSuccess(response.data.results)
                },
                { errorMessage ->
                    onError(errorMessage)
                }
            )
        }
    }

    private suspend fun getHeroesAsync(
        params: ApiParams,
        onSuccess: (MarvelResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        when (val result = heroesUseCase.execute(params)) {
            is AppResult.Success -> {
                onSuccess(result.data)
            }
            is AppResult.Error -> result.throwable.message?.let { onError(it) }
        }
    }
}