package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.friple.data.repository.HeroesRepositoryImpl
import com.friple.domain.entities.ApiParams
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.domain.interactors.GetHeroesUseCase
import com.friple.domain.repositories.HeroesRepository
import com.friple.immarvelhero.ui.presenter.HeroPresenter
import com.friple.immarvelhero.utilits.showToast

// TODO: 4/25/2021 Change type of multithreading

class MainScreenViewModel : BaseViewModel() {

    // For BD
    // Offset for getting items
    private var offset = 0

    // Limit of getting items
    private var limit = 20

    private var params = ApiParams(offset, limit)

    private val mHeroesRepository: HeroesRepository = HeroesRepositoryImpl.instance

    private val mUseCase = GetHeroesUseCase(mHeroesRepository)

    private val mHeroPresenter = HeroPresenter(mUseCase)

    private val marvelListCharacters = MutableLiveData<List<MarvelCharacter>>()

    fun getMarvelListCharacters(): LiveData<List<MarvelCharacter>> {
        return marvelListCharacters
    }

    // Load data from BD. Then we check marvel list. If it's empty we get it with new data.
    // Or get old data and plus new to it
    // Then call to function (Lambda)
    fun updateData() {
        setState(State.LOADING)

        mHeroPresenter.getHeroes(params,
            {
                if (marvelListCharacters.value == null) {
                    marvelListCharacters.value = it
                } else {
                    marvelListCharacters.value = marvelListCharacters.value!!.plus(it)
                }
                // Add offset for next new items
                params.offset += 20

                setState(State.SUCCESS)
            },
            { errorMessage ->
                showToast(errorMessage)
                setState(State.ERROR)
            })
    }
}