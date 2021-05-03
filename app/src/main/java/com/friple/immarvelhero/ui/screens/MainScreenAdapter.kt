package com.friple.immarvelhero.ui.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.ui.recyclerview.viewes.BaseView
import com.friple.immarvelhero.utilits.*
import kotlinx.android.synthetic.main.item_marvel_hero.view.*
import java.util.*

// TODO: 4/25/2021 Divide adapter to viewHolders and views

class MainScreenAdapter(
    private val listener: AppHeroClickListener
) : RecyclerView.Adapter<MainScreenAdapter.MarvelCharacterHolder>() {

    private var mListHeroesCache = mutableListOf<MarvelCharacter>()
    private var mListHolders = mutableListOf<MarvelCharacterHolder>()

    // Set data and check it by DiffUtil
    fun setData(item: BaseView) {

        val diffUtil = MyDiffUtil(mListHeroesCache, newListMarvelCharacter)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        mListHeroesCache = newListMarvelCharacter
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelCharacterHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_marvel_hero, parent, false)

        return MarvelCharacterHolder(view)
    }

    override fun onBindViewHolder(holder: MarvelCharacterHolder, position: Int) {
        holder.drawMarvelHero(mListHeroesCache[position])
    }

    override fun onViewAttachedToWindow(holder: MarvelCharacterHolder) {

        // Make call to holder for attach
        holder.onAttach(mListHeroesCache[holder.adapterPosition], listener)
        mListHolders.add(holder)

        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: MarvelCharacterHolder) {

        // Make call to holder for detach
        holder.onDetach()
        mListHolders.remove(holder)

        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int = mListHeroesCache.size

    // For delete references
    fun onDestroy() {
        mListHolders.forEach {
            it.onDetach()
        }
    }

    inner class MarvelCharacterHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Views
        private val ivPhotoOfHero: ImageView = view.iv_hero_photo
        private val tvNameOfHero: TextView = view.tv_hero_name
        private val rbRatingBar: RatingBar = view.rating_bar
        private val tvRatingNum: TextView = view.tv_rating_in_numb
        private val cvMainBackCard: CardView = view.cv_main_back_card

        private val flMainButton: FrameLayout = view.fl_item_button
        private val tvStories: TextView = view.tv_item_count_stories

        private val hashMapOfViews = hashMapOf<String, View>()

        // For future. When we gonna have a lot of holders
        fun drawMarvelHero(marvelCharacter: MarvelCharacter) {

            // For animation
            hashMapOfViews["cvMainBackCard"] = cvMainBackCard
            hashMapOfViews["ivPhotoOfHero"] = ivPhotoOfHero
            hashMapOfViews["rbRatingBar"] = rbRatingBar

            // Set transition names to views
            setTransitionNames(hashMapOfViews, marvelCharacter.id)

            // Our work....
            tvNameOfHero.text = marvelCharacter.name
            tvStories.text = makeString(marvelCharacter)
            rbRatingBar.rating = randFloat()
            tvRatingNum.text = makeRatingString(rbRatingBar)

            val url =
                "${marvelCharacter.thumbnail.path}/standard_fantastic.${marvelCharacter.thumbnail.extension}"

            // If we have bitmap we set it, else download
            if (HERO_BITMAP != null && ID_FOR_BITMAP == marvelCharacter.id.toString()) {
                ivPhotoOfHero.setImageBitmap(HERO_BITMAP)
                Log.d("TAGGG", "drawMarvelHero: 11111")
            } else {
                ivPhotoOfHero.downloadAndSetImage(url)
            }
        }

        fun onAttach(marvelCharacter: MarvelCharacter, listener: AppHeroClickListener) {

            flMainButton.setOnClickListener {
                listener.onHeroClick(marvelCharacter, hashMapOfViews)
            }
        }

        fun onDetach() {
            flMainButton.setOnClickListener(null)
        }
    }
}