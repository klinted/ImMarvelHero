package com.friple.immarvelhero.ui.recyclerview.views

import com.friple.domain.entities.heroes.Stories
import com.friple.domain.entities.heroes.Thumbnail

class ErrorView(
    override val id: Int = 0,
    override val name: String = "",
    override val description: String = "",
    override val stories: Stories = Stories(0),
    override val thumbnail: Thumbnail = Thumbnail(
        "",
        ""
    )
) : BaseView {

    override fun getTypeOfView(): Int = BaseView.SCREEN_ERROR
}