package com.friple.immarvelhero.ui.recyclerview.viewholders

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.*
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
        rbRatingBar.rating = randFloat()
        tvRatingNum.text = makeRatingString(rbRatingBar)

        val url =
            "${view.thumbnail.path}/standard_fantastic.${view.thumbnail.extension}"

        // If we have bitmap we set it, else download
        if (HERO_BITMAP != null && ID_FOR_BITMAP == view.id.toString()) {
            ivPhotoOfHero.setImageBitmap(HERO_BITMAP)
            Log.d("TAGGG", "drawMarvelHero: 11111")
        } else {
            ivPhotoOfHero.downloadAndSetImage(url)
        }
    }

    override fun onAttach(view: BaseView, listener: AppHeroClickListener) {
        flMainButton.setOnClickListener {
            listener.onClickFromItem(view, hashMapOfViews)
        }
    }

    override fun onDetach() {
        flMainButton.setOnClickListener(null)
    }

}