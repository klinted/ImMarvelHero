package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.friple.immarvelhero.network.entities.MarvelCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private var offset = 0
    private var limit = 20

    private val isLoading = MutableLiveData(true)
    private val error = MutableLiveData(false)

    private val marvelListCharacters = MutableLiveData<List<MarvelCharacter>>()

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }


    fun getMarvelListCharacters(): LiveData<List<MarvelCharacter>> {
        return marvelListCharacters
    }

    fun updateData(function: () -> Unit) {
        isLoading.value = true
        loadHeroes {
            if (marvelListCharacters.value == null) {
                marvelListCharacters.value = it
            } else {
                marvelListCharacters.value = marvelListCharacters.value!!.plus(it)
            }
            offset += 20

            isLoading.value = false
        }

        function()
    }

    private fun loadHeroes(function: (List<MarvelCharacter>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            MainRepo.instance.load(offset, limit,
                {
                    function(it.data.results)
                }, {
                    error.value = true
                    isLoading.value = false
                    updateData {  }
                })
        }
    }
}