package com.android.jimmy.motus.ui.model

enum class UiCurrentGameState {
    STARTED, // Current game is started
    WIN, // Win the current game
    LOOSE, // Loose the current game
    NONE, // Current game is stopped or doesn't exist
}