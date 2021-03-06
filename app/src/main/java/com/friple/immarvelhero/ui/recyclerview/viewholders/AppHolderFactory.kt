package com.friple.immarvelhero.ui.recyclerview.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.ui.recyclerview.views.BaseView

object AppHolderFactory {

        fun getHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                BaseView.SCREEN_HEROES -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_marvel_hero, parent, false)
                    HeroesViewViewHolder(view)
                }
                BaseView.SCREEN_HERO_DETAIL -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.screen_hero, parent, false)
                    HeroDetailViewHolder(view)
                }
                BaseView.SCREEN_ERROR -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_without_internet, parent, false)
                    ErrorViewViewHolder(view)
                }
                else -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_without_internet, parent, false)
                    ErrorViewViewHolder(view)
                }
            }
        }
}