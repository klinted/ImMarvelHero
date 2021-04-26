package com.friple.immarvelhero.utilits

import android.view.View


// Set transition names for animation
fun setTransitionNames(mapOfView: Map<String, View>, id: Int) {
    mapOfView.forEach {
        it.value.transitionName = "${it.key}${id}"
    }
}