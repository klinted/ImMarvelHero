package com.friple.immarvelhero.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeroScreenViewModel : ViewModel() {

    private var heroDate = MutableLiveData<Map<String, Any>>()
    private var bitmap = MutableLiveData<Bitmap>()

    fun getHeroDate(): LiveData<Map<String, Any>> {
        return heroDate
    }

    fun getBitmap(): LiveData<Bitmap> {
        return bitmap
    }

    fun setHeroDate(map: MutableMap<String, Any>) {
        heroDate.value = map
    }

    fun setBitmap(bitmapImage: Bitmap) {
        bitmap.value = bitmapImage
    }
}