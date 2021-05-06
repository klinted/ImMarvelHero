package com.friple.immarvelhero.ui.recyclerview.views

import com.friple.domain.entities.heroes.Stories
import com.friple.domain.entities.heroes.Thumbnail

interface BaseView {

    val id: Int
    val name: String
    val description: String
    val stories : Stories
    val thumbnail: Thumbnail

    companion object {
        val SCREEN_HEROES: Int
            get() = 0

        val SCREEN_ERROR: Int
            get() = 1
    }

    fun getTypeOfView(): Int
}