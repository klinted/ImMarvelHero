package com.friple.immarvelhero.utilits

import androidx.recyclerview.widget.DiffUtil
import com.friple.immarvelhero.network.entities.MarvelCharacter


class MyDiffUtil(
    private var oldList: List<MarvelCharacter>,
    private var newList: List<MarvelCharacter>

) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    // Check by name and stories of marvel character
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].stories == newList[newItemPosition].stories

    }
}