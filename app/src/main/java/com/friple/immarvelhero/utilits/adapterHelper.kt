package com.friple.immarvelhero.utilits

import android.view.View
import android.widget.RatingBar
import com.friple.immarvelhero.network.entities.MarvelCharacter
import java.util.*


// Set transition names for animation
fun setTransitionNames(mapOfView: Map<String, View>, id: Int) {
    mapOfView.forEach {
        it.value.transitionName = "${it.key}${id}"
    }
}

fun randFloat(): Float {
    val rand = Random()
    return rand.nextFloat() * (0f - 5f) + 0f
}

fun makeRatingString(ratingBar: RatingBar): CharSequence {
    return ratingBar.rating.toString() + " Rating"
}

fun makeString(marvelCharacter: MarvelCharacter): CharSequence {
    return marvelCharacter.stories.available.toString() + " stories" + " | " +
            (0..700).random() + " Review"
}