package com.friple.immarvelhero.utilits

import android.graphics.Bitmap
import com.friple.immarvelhero.ui.activity.MainActivity

lateinit var APP_ACTIVITY: MainActivity



var HERO_BITMAP: Bitmap? = null
var ID_FOR_BITMAP: String? = null



// For marvel API

const val BASE_URL = "https://gateway.marvel.com/v1/public/"

const val PUBLIC_KEY = "fca931ad3e7ee0821a879f88b6e29233"
const val PRIVATE_KEY = "9a118b5543b30aa2524eb80e77f83ba0721bff92"