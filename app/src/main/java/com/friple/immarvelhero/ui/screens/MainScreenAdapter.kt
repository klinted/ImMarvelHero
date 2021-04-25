package com.friple.immarvelhero.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.AppHeroClickListener
import com.friple.immarvelhero.utilits.MyDiffUtil
import com.friple.immarvelhero.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.item_marvel_hero.view.*
import kotlinx.coroutines.*
import okhttp3.internal.Util
import java.util.*


class MainScreenAdapter (private val listener: AppHeroClickListener) : RecyclerView.Adapter<MainScreenAdapter.MarvelCharacterHolder>() {

    private var mListHeroesCache = mutableListOf<MarvelCharacter>()
    private var mListHolders = mutableListOf<MarvelCharacterHolder>()


    fun setData(newListMarvelCharacter: MutableList<MarvelCharacter>) {

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

        holder.onAttach(mListHeroesCache[holder.adapterPosition], listener)
        mListHolders.add(holder)

        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: MarvelCharacterHolder) {

        holder.onDetach()
        mListHolders.remove(holder)

        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int = mListHeroesCache.size

    fun onDestroy() {
        mListHolders.forEach {
            it.onDetach()
        }
    }

    override fun onViewRecycled(holder: MarvelCharacterHolder) {
        super.onViewRecycled(holder)
        holder.ivPhotoOfHero.setImageBitmap(null)
    }

    class MarvelCharacterHolder(view: View) : RecyclerView.ViewHolder(view) {

        val ivPhotoOfHero: ImageView = view.iv_hero_photo
        private val tvNameOfHero: TextView = view.tv_hero_name
        private val tvByMe: TextView = view.tv_by_me
        private val rbRatingBar: RatingBar = view.rating_bar
        private val tvRatingNum: TextView = view.tv_rating_in_numb
        private val cvMainBackCard: CardView = view.cv_main_back_card

        private val flMainButton: FrameLayout = view.fl_item_button
        private val tvStories: TextView = view.tv_item_count_stories

        private val listOfViews = mutableListOf<View>()

        fun drawMarvelHero(marvelCharacter: MarvelCharacter) {

            cvMainBackCard.transitionName = "cvMainBackCard${marvelCharacter.id}"
            ivPhotoOfHero.transitionName = "ivPhotoOfHero${marvelCharacter.id}"
            rbRatingBar.transitionName = "rbRatingBar${marvelCharacter.id}"

            listOfViews.add(0, cvMainBackCard)
            listOfViews.add(1, ivPhotoOfHero)
            listOfViews.add(2, rbRatingBar)

            tvNameOfHero.text = marvelCharacter.name
            tvStories.text = makeString(marvelCharacter)
            rbRatingBar.rating = randFloat(0f, 5f)
            tvRatingNum.text = makeRatingString()

            val url =
                "${marvelCharacter.thumbnail.path}/portrait_fantastic.${marvelCharacter.thumbnail.extension}"

            ivPhotoOfHero.downloadAndSetImage(url)
        }

        private fun randFloat(min: Float, max: Float): Float {
            val rand = Random()
            return rand.nextFloat() * (max - min) + min
        }

        private fun makeRatingString(): CharSequence {
            return rbRatingBar.rating.toString() + " Rating"
        }

        private fun makeString(marvelCharacter: MarvelCharacter): CharSequence {
            return marvelCharacter.stories.available.toString() + " stories" + " | " +
                    (0..700).random() + " Review"
        }

        fun onAttach(marvelCharacter: MarvelCharacter, listener: AppHeroClickListener) {
            flMainButton.setOnClickListener {

                listener.onHeroClick(marvelCharacter, listOfViews)
            }
        }

        fun onDetach() {
            flMainButton.setOnClickListener(null)
        }
    }
}