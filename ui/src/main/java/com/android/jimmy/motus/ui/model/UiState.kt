package com.android.jimmy.motus.ui.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class UiState<T> {
    /**
     * The state was unable to load.
     */
    data class Failure<T>(val errorMsg: String) : UiState<T>()

    /**
     * There is a success state, with the given data.
     */
    data class Success<T>(val data: T) : UiState<T>()

    /**
     * The state is loading.
     */
    class Loading<T> : UiState<T>()
}
