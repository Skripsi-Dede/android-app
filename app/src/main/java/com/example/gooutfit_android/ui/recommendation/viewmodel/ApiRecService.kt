package com.example.gooutfit_android.ui.recommendation.viewmodel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRecService {
    @POST("recommend")
    fun recommend(
        @Body recRequest: RecRequest
    ): Call<RecResponse>
}