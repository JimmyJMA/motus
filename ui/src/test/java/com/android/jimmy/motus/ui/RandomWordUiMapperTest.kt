package com.android.jimmy.motus.ui

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.mapper.CharacterMapper
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.ui.mapper.RandomWordUiMapper
import com.android.jimmy.motus.ui.model.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RandomWordUiMapperTest: BaseUnitTest() {

    private lateinit var randomWordUiMapper: RandomWordUiMapper

    @Before
    fun setup() {
        randomWordUiMapper = RandomWordUiMapper()
    }

    @Test
    fun `when state is success and data is not empty mapper should return uistate success`() {
        // region given
        val expectedStateSuccess: State<List<Character>> = State.Success(listOf(Character('A', Status.CORRECT)))
        val expectedUiStateSuccess: UiState<List<Character>> = UiState.Success(listOf(Character('A', Status.CORRECT)))
        // endregion
        // when
        val result = randomWordUiMapper.randomWordStateToUiState(expectedStateSuccess)

        // then
        assertEquals(expectedUiStateSuccess, result)
    }

    @Test
    fun `when state is success and data is empty mapper should return uistate failure`() {
        // region given
        val expectedStateSuccess: State<List<Character>> = State.Success(emptyList())
        val expectedUiStateFailure: UiState<List<Character>> = UiState.Failure("Code erreur 4 - Une erreur est survenue")
        // endregion
        // when
        val result = randomWordUiMapper.randomWordStateToUiState(expectedStateSuccess)

        // then
        assertEquals(expectedUiStateFailure, result)
    }

    @Test
    fun `when state is failure mapper should return uistate failure`() {
        // region given
        val expectedStateFailure: State<List<Character>> = State.Failure("")
        val expectedUiStateFailure: UiState<List<Character>> = UiState.Failure("")
        // endregion
        // when
        val result = randomWordUiMapper.randomWordStateToUiState(expectedStateFailure)

        // then
        assertEquals(expectedUiStateFailure, result)
    }

}