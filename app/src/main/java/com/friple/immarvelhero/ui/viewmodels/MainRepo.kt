package com.friple.immarvelhero.ui.viewmodels

import android.util.Log
import com.friple.immarvelhero.network.ApiClient
import com.friple.immarvelhero.network.entities.MarvelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepo {

    fun load( offset: Int, limit: Int, function: (MarvelResponse) -> Unit, onError: () -> Unit) {
            val call: Call<MarvelResponse> =
                ApiClient.getApiClient().getHeroes(offset, limit)
            call.enqueue(object : Callback<MarvelResponse> {

                override fun onResponse(
                    call: Call<MarvelResponse>,
                    response: Response<MarvelResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { function(it) }
                    }
                }

                override fun onFailure(call: Call<MarvelResponse>, t: Throwable) {
                    onError()
                }

            })
    }

    companion object {
        val instance = MainRepo()
    }
}