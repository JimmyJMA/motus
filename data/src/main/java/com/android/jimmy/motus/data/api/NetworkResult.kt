package com.android.jimmy.motus.data.api

import com.android.jimmy.motus.domain.State

sealed class NetworkResult<T : Any> {
    class OnSuccess<T : Any>(val code: Int, val data: T) : NetworkResult<T>()
    class OnError<T : Any>(val code: Int, val errorMsg: String) : NetworkResult<T>()
    class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()
}

fun <T : Any> NetworkResult<T>.toState(): State<T> {
    val errorFailure3 = "Code erreur 3 - Une erreur est survenue"
    return when (this) {
        is NetworkResult.OnSuccess -> State.Success(this.data)
        is NetworkResult.OnError -> State.Failure(this.errorMsg, this.code)
        is NetworkResult.Exception -> {
            State.Failure(this.e.message ?: errorFailure3)
        }
    }
}
