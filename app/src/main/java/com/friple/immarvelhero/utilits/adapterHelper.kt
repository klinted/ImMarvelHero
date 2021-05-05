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
    val f = rand.nextFloat() * (5f - 0f) + 0f
    return f
}

fun makeRatingString(ratingBar: RatingBar): CharSequence {
    return ratingBar.rating.toString() + " Rating"
}

fun makeString(string: String): CharSequence {
    return string + " stories" + " | " +
            (0..700).random() + " Review"
}