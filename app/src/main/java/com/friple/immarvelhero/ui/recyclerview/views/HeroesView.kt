package com.friple.immarvelhero.ui.recyclerview.views

import com.friple.domain.entities.heroes.Stories
import com.friple.domain.entities.heroes.Thumbnail

class HeroesView(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val stories: Stories,
    override val thumbnail: Thumbnail
) : BaseView {

    override fun getTypeOfView(): Int = BaseView.SCREEN_HEROES
}