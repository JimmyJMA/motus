package com.android.jimmy.motus.data.api

import retrofit2.Response
import retrofit2.http.GET

internal interface MotusService {
    @GET("/raw/iys4katchhd")
    suspend fun getWords(): Response<String>
}
