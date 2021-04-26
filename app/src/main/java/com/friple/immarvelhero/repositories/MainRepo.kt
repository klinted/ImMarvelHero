package com.friple.immarvelhero.repositories

import com.friple.immarvelhero.network.ApiClient
import com.friple.immarvelhero.network.entities.MarvelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// REFACTOR: 4/25/2021 I should ref this shit. (Divide)

class MainRepo {

    fun load( offset: Int, limit: Int, function: (MarvelResponse) -> Unit, onError: () -> Unit) {

            val call: Call<MarvelResponse> = ApiClient.getApiClient().getHeroes(offset, limit)
            call.enqueue(object : Callback<MarvelResponse> {

                override fun onResponse(
                    call: Call<MarvelResponse>,
                    response: Response<MarvelResponse>
                ) {
                    // If successful, we make callback
                    if (response.isSuccessful) {
                        response.body()?.let { function(it) }
                    }
                }

                override fun onFailure(call: Call<MarvelResponse>, t: Throwable) {
                    onError()

                    // TODO: 4/25/2021 Make error listener
                }

            })
    }

    companion object {
        val instance = MainRepo()
    }
}