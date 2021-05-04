package com.friple.immarvelhero.ui.screens

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.ui.recyclerview.viewes.BaseView
import com.friple.immarvelhero.ui.recyclerview.viewholders.AppHolderFactory
import com.friple.immarvelhero.ui.recyclerview.viewholders.BaseViewHolder
import com.friple.immarvelhero.utilits.*

// TODO: 4/25/2021 Divide adapter to viewHolders and views

class MainScreenAdapter(
    private val listener: AppHeroClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListHeroesCache = mutableListOf<BaseView>()
    private var mListHolders = mutableListOf<BaseViewHolder>()

    // Set data and check it by DiffUtil
    fun setData(newListMarvelViewCache: MutableList<BaseView>) {

        when (newListMarvelViewCache[0].getTypeOfView()) {
            TYPE_SCREEN_HEROES -> {
                val diffUtil = MyDiffUtil(mListHeroesCache, newListMarvelViewCache)
                val diffResults = DiffUtil.calculateDiff(diffUtil)
                mListHeroesCache = newListMarvelViewCache
                diffResults.dispatchUpdatesTo(this)
            }
            TYPE_SCREEN_ERROR -> {
                mListHeroesCache.clear()
                mListHolders.clear()
                mListHeroesCache = newListMarvelViewCache
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppHolderFactory.getHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).drawMarvelHero(mListHeroesCache[position])
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {

        // Make call to holder for attach
        (holder as BaseViewHolder).onAttach(mListHeroesCache[holder.adapterPosition], listener)
        mListHolders.add(holder)

        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {

        // Make call to holder for detach
        (holder as BaseViewHolder).onDetach()
        mListHolders.remove(holder)

        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return mListHeroesCache[position].getTypeOfView()
    }

    override fun getItemCount(): Int = mListHeroesCache.size

    // For delete references
    fun onDestroy() {
        mListHolders.forEach {
            it.onDetach()
        }
    }
}