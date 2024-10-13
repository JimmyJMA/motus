package com.android.jimmy.motus.domain.model

enum class Status {
    CORRECT, // character is correct and in the correct position
    WRONG_POSITION, // character is correct but in the wrong position
    WRONG, // character is not in the word at all
    NONE // character is just initialized with no status
}
