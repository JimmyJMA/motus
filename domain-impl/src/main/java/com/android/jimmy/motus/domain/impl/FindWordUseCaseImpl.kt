package com.android.jimmy.motus.domain.impl

import com.android.jimmy.motus.domain.repository.MotusRepository
import com.android.jimmy.motus.domain.usecase.FindWordUseCase
import javax.inject.Inject

class FindWordUseCaseImpl @Inject constructor (private val motusRepository: MotusRepository): FindWordUseCase {

    override suspend fun invoke(word: String): Boolean =
        motusRepository.findWord(word)

}