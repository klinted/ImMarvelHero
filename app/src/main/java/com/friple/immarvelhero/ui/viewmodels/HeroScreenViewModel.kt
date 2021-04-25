package com.friple.immarvelhero.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeroScreenViewModel : ViewModel() {

    private var heroDate = MutableLiveData<Map<String, Any>>()

    fun getHeroDate(): LiveData<Map<String, Any>> {
        return heroDate
    }

    fun setHeroDate(map: MutableMap<String, Any>) {
        heroDate.value = map
    }
}