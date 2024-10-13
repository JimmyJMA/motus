package com.android.jimmy.motus.data.api

import retrofit2.Response
import retrofit2.http.GET

internal interface MotusService {
    @GET("/JimmyJMA/2fcab8bf05fb4542ad35f13041a95bd5/raw/ac758258acd4244dee75c88162b6f23a6c3534ef/gistfile1.txt")
    suspend fun getWords(): Response<String>
}
