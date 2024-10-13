package com.android.jimmy.motus.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.usecase.FindWordUseCase
import com.android.jimmy.motus.domain.usecase.GetRandomWordUseCase
import com.android.jimmy.motus.ui.di.ViewModelCoroutineContext
import com.android.jimmy.motus.ui.mapper.RandomWordUiMapper
import com.android.jimmy.motus.ui.model.UiCurrentGameState
import com.android.jimmy.motus.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomWordUseCase: GetRandomWordUseCase,
    private val findWordUseCase: FindWordUseCase,
    private val randomWordUiMapper: RandomWordUiMapper,
    @ViewModelCoroutineContext private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Character>>>(UiState.Loading())
    val state: StateFlow<UiState<List<Character>>> = _state

    private val _uiCurrentGameState = MutableStateFlow(UiCurrentGameState.NONE)
    val uiCurrentGameState = _uiCurrentGameState

    private val _currentWord = MutableStateFlow<List<Character>?>(null)

    private val _currentWordStr = MutableStateFlow("")
    val currentWordStr = _currentWordStr

    private val _listWordTried = MutableStateFlow<MutableList<List<Character>>>(mutableListOf())
    val listWordTried = _listWordTried

    private val _moveRemaining = MutableStateFlow(NB_MAX_MOVE)
    val moveRemaining = _moveRemaining

    init {
        viewModelScope.launch(dispatcher) {
            findRandomWord()
        }
    }

    fun findRandomWord() {
        viewModelScope.launch(dispatcher) {
            val randomWordUiState = randomWordUiMapper.randomWordStateToUiState(
                getRandomWordUseCase.invoke()
            )
            _state.value = randomWordUiState
            if (randomWordUiState is UiState.Success) {
                _currentWord.value = randomWordUiState.data
                _currentWordStr.value = randomWordUiState.data.parseCharacterToString()
                _listWordTried.value = mutableListOf(randomWordUiState.data)
            }
        }
    }

    fun newGame() {
        _uiCurrentGameState.value = UiCurrentGameState.NONE
        _moveRemaining.value = NB_MAX_MOVE
        _listWordTried.value.clear()
        findRandomWord()
    }

    fun validate(wordTypedStr: String) {
        viewModelScope.launch(dispatcher) {
            _currentWord.value?.let { current ->
                if (!findWordUseCase.invoke(wordTypedStr)) {
                    _moveRemaining.value = 0
                } else {
                    _moveRemaining.value--
                }
                val wordTyped = getRandomWordUseCase.validate(
                    current,
                    wordTypedStr.parseStringToLetter()
                )

                val currentWordTmp = _listWordTried.value.toMutableList()

                when {
                    wordTyped.isCorrect() -> {
                        _uiCurrentGameState.value = UiCurrentGameState.WIN
                        currentWordTmp.removeLast()
                        currentWordTmp.add(wordTyped)
                    }

                    _moveRemaining.value <= 0 -> {
                        _uiCurrentGameState.value = UiCurrentGameState.LOOSE
                        currentWordTmp.removeLast()
                        currentWordTmp.add(wordTyped)
                    }

                    else -> currentWordTmp.add(currentWordTmp.size - 1, wordTyped)
                }

                _listWordTried.value = currentWordTmp
            }
        }
    }

    companion object {
        private const val NB_MAX_MOVE = 7
    }
}
