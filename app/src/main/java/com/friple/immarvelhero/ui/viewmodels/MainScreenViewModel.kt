package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.friple.data.repository.HeroesRepositoryImpl
import com.friple.domain.entities.ApiParams
import com.friple.domain.entities.AppResult
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.domain.repositories.HeroesRepository
import com.friple.domain.usecases.GetHeroesUseCase
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.TYPE_SCREEN_HEROES
import com.friple.immarvelhero.utilits.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: 5/11/2021 Make DI
class MainScreenViewModel : BaseViewModel() {

    private var params = ApiParams(0, 20)

    private val mHeroesRepository: HeroesRepository = HeroesRepositoryImpl.getInstance(Dispatchers.IO)
    private val mUseCase = GetHeroesUseCase(mHeroesRepository)

    private val _marvelListCharacters = MutableLiveData<List<MarvelCharacter>>()
    val marvelListCharacters: LiveData<List<MarvelCharacter>> = _marvelListCharacters

    val typeOfScreen = MutableLiveData<Int>()
    val recentDataOfDetailScreen = MutableLiveData<MarvelCharacter>()

    // Load data from BD. Then we check marvel list.
    fun updateData() {
        if (typeOfScreen.value == TYPE_SCREEN_HEROES) {
            setState(State.LOADING)

            viewModelScope.launch {
                getHeroesAsync()
            }
        } else {
            typeOfScreen.value = TYPE_SCREEN_HEROES
            setState(State.SUCCESS)
        }
    }

    private suspend fun getHeroesAsync() {
        when (val result = mUseCase.invoke(params)) {
            is AppResult.Success -> onSuccess(result.data.data.results)
            is AppResult.Error -> onError(result.throwable.message)
        }
    }

    // If _marvelListCharacters is empty we get it with new data.
    // Or get old data and plus new to it
    private fun onSuccess(listOfMarvelChars: List<MarvelCharacter>) {
        if (_marvelListCharacters.value == null) {
            _marvelListCharacters.value = listOfMarvelChars
        } else {
            _marvelListCharacters.value = _marvelListCharacters.value!!.plus(listOfMarvelChars)
        }

        // Add offset for next new items
        params.offset += 20
        setState(State.SUCCESS)
    }

    private fun onError(errorMessage: String?) {
        errorMessage?.let {
            showToast(errorMessage)
            setState(State.ERROR)
        }
    }
}