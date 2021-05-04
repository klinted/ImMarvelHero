package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.repositories.MainRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: 4/25/2021 Change type of multithreading

class MainScreenViewModel : BaseViewModel() {

    // For BD
    // Offset for getting items
    private var offset = 0
    // Limit of getting items
    private var limit = 20

    private val marvelListCharacters = MutableLiveData<List<MarvelCharacter>>()

    fun getMarvelListCharacters(): LiveData<List<MarvelCharacter>> {
        return marvelListCharacters
    }

    // Load data from BD. Then we check marvel list. If it's empty we get it with new data.
    // Or get old data and plus new to it
    // Then call to function (Lambda)
    fun updateData() {
        setState(State.LOADING)

        loadHeroes {
            if (marvelListCharacters.value == null) {
                marvelListCharacters.value = it
            } else {
                marvelListCharacters.value = marvelListCharacters.value!!.plus(it)
            }
            // Add offset for next new items
            offset += 20
        }
    }

    // Load data from BD in background
    private fun loadHeroes(function: (List<MarvelCharacter>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            MainRepo.instance.load(offset, limit,
                {
                    function(it.data.results)
                    setState(State.SUCCESS)
                }, {
                    setState(State.ERROR)
                })
        }
    }
}