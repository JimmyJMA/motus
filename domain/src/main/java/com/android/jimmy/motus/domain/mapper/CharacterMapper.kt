package com.android.jimmy.motus.domain.mapper

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import javax.inject.Inject
import kotlin.random.Random

class CharacterMapper @Inject constructor() {

    fun toCharacterMapper(state: State.Success<String>): State<List<Character>> {
        val randomWord = getRandomWord(state.data.split("\n"))
        val listChars = randomWord?.trim()?.toList()
        return if (listChars?.isNotEmpty() == true) {
            State.Success(listChars.map {
                Character(it, Status.NONE)
            })
        } else {
            State.Failure(ERROR_FAILURE_4)
        }
    }

    private fun getRandomWord(listWords: List<String>): String? {
        if (listWords.isNotEmpty()) {
            val randomIndex = Random.nextInt(listWords.size)
            return listWords[randomIndex]
        }
        return null
    }

    companion object {
        private const val ERROR_FAILURE_4 = "Code erreur 4 - Une erreur est survenue"
    }
}