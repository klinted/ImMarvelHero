package com.friple.immarvelhero.utilits.adapter

import android.view.View
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import java.util.*


// Set transition names for animation
fun setTransitionNames(mapOfView: Map<String, View>, id: Int) {
    mapOfView.forEach {
        it.value.transitionName = "${it.key}${id}"
    }
}

fun randFloat(from: Float, to: Float): Float {
    val rand = Random()
    return rand.nextFloat() * (to - from) + from
}

fun makeRatingString(rating: Float): CharSequence {
    return "$rating Rating"
}

fun makeString(string: String): CharSequence {
    return string + " stories" + " | " +
            (0..700).random() + " Review"
}

fun makeRef(view: BaseView) = "${view.thumbnail.path}/standard_fantastic.${view.thumbnail.extension}"