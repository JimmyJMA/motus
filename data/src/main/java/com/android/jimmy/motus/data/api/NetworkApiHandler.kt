package com.android.jimmy.motus.data.api

import retrofit2.HttpException
import retrofit2.Response

interface NetworkApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.OnSuccess(response.code(), body)
            } else {
                NetworkResult.OnError(
                    code = response.code(),
                    errorMsg = response.errorBody().toString()
                )
            }
        } catch (e: HttpException) {
            NetworkResult.OnError(e.code(), e.message())
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}
