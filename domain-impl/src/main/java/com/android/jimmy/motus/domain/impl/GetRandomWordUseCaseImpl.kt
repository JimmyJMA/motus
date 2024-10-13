package com.android.jimmy.motus.domain.impl

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.domain.repository.MotusRepository
import com.android.jimmy.motus.domain.usecase.GetRandomWordUseCase
import javax.inject.Inject

class GetRandomWordUseCaseImpl @Inject constructor (private val motusRepository: MotusRepository) :
    GetRandomWordUseCase {

    override suspend fun invoke(): State<List<Character>> {
        return motusRepository.getWord()
    }

    override fun validate(
        current: List<Character>,
        wordTypedStr: List<Character>
    ): List<Character> {
        val result = wordTypedStr.toMutableList()

        // Scan correct and wrong position character
        for (i in current.indices) {
            if (current[i].char == result[i].char) {
                result[i] = result[i].copy(status = Status.CORRECT)
            } else {
                for (j in result.indices) {
                    if (result[j].status == Status.NONE && current[i].char == result[j].char) {
                        result[j] = result[j].copy(status = Status.WRONG_POSITION)
                        break
                    }
                }
            }
        }

        // Scan wrong character
        for (i in result.indices) {
            if (result[i].status == Status.NONE) {
                result[i] = result[i].copy(status = Status.WRONG)
            }
        }

        return result
    }
}