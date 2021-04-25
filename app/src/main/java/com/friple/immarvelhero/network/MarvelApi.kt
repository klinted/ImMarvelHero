package com.friple.immarvelhero.network

import com.friple.immarvelhero.network.entities.MarvelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getHeroes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ) : Call<MarvelResponse>
}
