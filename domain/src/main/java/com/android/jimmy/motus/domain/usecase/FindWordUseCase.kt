package com.android.jimmy.motus.domain.usecase

interface FindWordUseCase {
    suspend operator fun invoke(word: String): Boolean
}
