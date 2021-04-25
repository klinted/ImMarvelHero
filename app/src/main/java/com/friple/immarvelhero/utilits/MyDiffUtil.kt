package com.friple.immarvelhero.utilits

import androidx.recyclerview.widget.DiffUtil
import com.friple.immarvelhero.network.entities.MarvelCharacter


class MyDiffUtil(
     var oldList: List<MarvelCharacter>,
     var newList: List<MarvelCharacter>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].name != newList[newItemPosition].name -> {
                false
            }
            oldList[oldItemPosition].stories != newList[newItemPosition].stories -> {
                false
            }
            else -> true
        }
    }
}