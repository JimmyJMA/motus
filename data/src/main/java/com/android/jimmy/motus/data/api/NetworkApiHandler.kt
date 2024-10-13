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
                NetworkResult.onSuccess(response.code(), body)
            } else {
                NetworkResult.onError(code = response.code(), errorMsg = response.errorBody().toString())
            }
        } catch (e:HttpException){
            NetworkResult.onError(e.code(),e.message())
        } catch (e:Throwable){
            NetworkResult.Exception(e)
        }
    }
}