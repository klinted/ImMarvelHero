package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    enum class State { CREATED, LOADING, SUCCESS, ERROR }

    private val state = MutableLiveData(State.CREATED)

    protected fun setState(state: State) {
        this.state.value = state
    }

    fun getState(): LiveData<State> = state
}