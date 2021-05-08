package com.friple.immarvelhero.ui.recyclerview.viewholders

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.*
import com.friple.immarvelhero.utilits.adapter.*
import kotlinx.android.synthetic.main.item_marvel_hero.view.*

class HeroesViewViewHolder(view: View) : RecyclerView.ViewHolder(view), BaseViewHolder {

    // Views
    private val ivPhotoOfHero: ImageView = view.iv_hero_photo
    private val tvNameOfHero: TextView = view.tv_hero_name
    private val rbRatingBar: RatingBar = view.rating_bar
    private val tvRatingNum: TextView = view.tv_rating_in_numb
    private val cvMainBackCard: CardView = view.cv_main_back_card

    private val flMainButton: FrameLayout = view.fl_item_button
    private val tvStories: TextView = view.tv_item_count_stories

    private val hashMapOfViews = hashMapOf<String, View>()

    override fun drawMarvelHero(view: BaseView) {

        // For animation
        hashMapOfViews["cvMainBackCard"] = cvMainBackCard
        hashMapOfViews["ivPhotoOfHero"] = ivPhotoOfHero
        hashMapOfViews["rbRatingBar"] = rbRatingBar

        // Set transition names to views
        setTransitionNames(hashMapOfViews, view.id)

        // Our work....
        tvNameOfHero.text = view.name
        tvStories.text = makeString(view.stories.available.toString())
        rbRatingBar.rating = randFloat(0f, 5f)
        tvRatingNum.text = makeRatingString(rbRatingBar.rating)
        ivPhotoOfHero.downloadAndSetImage(makeRef(view))
    }

    override fun onAttach(view: BaseView, listener: AppHeroClickListener) {
        flMainButton.setOnClickListener {
            ID_OF_HERO = this.adapterPosition
            listener.onClickFromItem(
                MarvelCharacter(view.id, view.name, view.description, view.stories, view.thumbnail)
            )
        }
    }

    override fun onDetach() {
        flMainButton.setOnClickListener(null)
    }

}