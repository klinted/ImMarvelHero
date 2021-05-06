package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    enum class State { CREATED, LOADING, SUCCESS, ERROR }

    private val _state = MutableLiveData(State.CREATED)
    val state: LiveData<State> = _state

    protected fun setState(state: State) {
        _state.value = state
    }
}