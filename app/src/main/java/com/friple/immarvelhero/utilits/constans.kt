package com.friple.immarvelhero.utilits

import android.graphics.Bitmap
import com.friple.immarvelhero.ui.activity.MainActivity

lateinit var APP_ACTIVITY: MainActivity

const val TYPE_SCREEN_HEROES = 0
const val TYPE_SCREEN_ERROR = 1

var HERO_BITMAP: Bitmap? = null
var ID_FOR_BITMAP: String? = null