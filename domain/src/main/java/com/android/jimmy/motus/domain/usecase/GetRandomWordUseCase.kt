package com.android.jimmy.motus.domain.usecase

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character

interface GetRandomWordUseCase {
    suspend operator fun invoke(): State<List<Character>>
    fun validate(current: List<Character>, wordTypedStr: List<Character>): List<Character>
}