package com.android.jimmy.motus.ui.mapper

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.ui.model.UiState
import javax.inject.Inject

class RandomWordUiMapper @Inject constructor() {

    fun randomWordStateToUiState(randomWordState: State<List<Character>>): UiState<List<Character>> {
        return when (randomWordState) {
            is State.Success -> {
                if (randomWordState.data.isNotEmpty()) {
                    UiState.Success(randomWordState.data)
                } else {
                    UiState.Failure(ERROR_FAILURE_5)
                }
            }
            is State.Failure -> {
                UiState.Failure(randomWordState.errorMsg)
            }
        }
    }

    companion object {
        private const val ERROR_FAILURE_5 = "Code erreur 4 - Une erreur est survenue"
    }
}