package com.friple.data.services

import com.friple.domain.entities.heroes.MarvelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    fun getHeroes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ) : Call<MarvelResponse>
}
