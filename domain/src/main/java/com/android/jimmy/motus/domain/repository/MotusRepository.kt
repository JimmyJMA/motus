package com.android.jimmy.motus.domain.repository

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character

interface MotusRepository {
    suspend fun getWord(): State<List<Character>>
    suspend fun findWord(word: String): Boolean
}