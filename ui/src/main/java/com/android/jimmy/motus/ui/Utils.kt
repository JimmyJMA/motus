package com.android.jimmy.motus.ui

import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status

internal fun List<Character>.isCorrect() = all { it.status == Status.CORRECT }

internal fun String.parseStringToLetter(): List<Character> =
    this.trim().toList().map { char -> Character(char.uppercaseChar(), Status.NONE) }

internal fun List<Character>.parseCharacterToString(): String {
    var word = ""
    this.map {
        word += it.char
    }
    return word
}