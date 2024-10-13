package com.android.jimmy.motus.domain

sealed class State<T> {
    data class Success<T>(val data: T) : State<T>()
    data class Failure<T>(val errorMsg: String, val code: Int? = null) : State<T>()
}

fun <T> State<T>.toSuccessOrNull(): State.Success<T>? {
    return if (this is State.Success) {
        this
    } else {
        null
    }
}