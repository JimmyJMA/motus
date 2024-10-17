package com.android.jimmy.motus.ui

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.domain.repository.MotusRepository
import com.android.jimmy.motus.domain.usecase.FindWordUseCase
import com.android.jimmy.motus.domain.usecase.GetRandomWordUseCase
import com.android.jimmy.motus.ui.mapper.RandomWordUiMapper
import com.android.jimmy.motus.ui.model.UiState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest: BaseUnitTest() {

    @MockK
    private lateinit var getRandomWordUseCase: GetRandomWordUseCase

    @MockK
    private lateinit var randomWordUiMapper: RandomWordUiMapper

    @MockK
    private lateinit var findWordUseCase: FindWordUseCase

    @MockK
    private lateinit var motusRepository: MotusRepository

    private lateinit var mainViewModel: MainViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `viewModel init should return success`() = runTest {
        // given
        val expectedStateListCharacter = State.Success(listOf(Character('A', Status.CORRECT)))
        val expectedUiStateListCharcter = UiState.Success(listOf(Character('A', Status.CORRECT)))

        coEvery { motusRepository.getWord() } returns expectedStateListCharacter
        coEvery { getRandomWordUseCase.invoke() } returns expectedStateListCharacter
        coEvery { randomWordUiMapper.randomWordStateToUiState(getRandomWordUseCase()) } returns expectedUiStateListCharcter

        mainViewModel = MainViewModel(getRandomWordUseCase, findWordUseCase, randomWordUiMapper, testDispatcher)

        // when
        mainViewModel.findRandomWord()

        // then
        assertEquals(expectedUiStateListCharcter, mainViewModel.state.value)
    }

    @Test
    fun `viewModel init should return failure`() = runTest {
        // given
        val expectedStateListCharacter: State<List<Character>> = State.Failure("")
        val expectedUiStateListCharacter: UiState.Failure<List<Character>> = UiState.Failure("")

        coEvery { motusRepository.getWord() } returns expectedStateListCharacter
        coEvery { getRandomWordUseCase.invoke() } returns expectedStateListCharacter
        coEvery { randomWordUiMapper.randomWordStateToUiState(getRandomWordUseCase()) } returns expectedUiStateListCharacter

        mainViewModel = MainViewModel(getRandomWordUseCase, findWordUseCase, randomWordUiMapper, testDispatcher)

        // when
        mainViewModel.findRandomWord()

        // then
        assertEquals(expectedUiStateListCharacter, mainViewModel.state.value)
    }

    @Test
    fun `viewModel init should return loading`() = runTest {
        // given
        val expectedStateListCharacter: State<List<Character>> = State.Failure("")
        val expectedUiStateListCharacter: UiState.Loading<List<Character>> = UiState.Loading()

        coEvery { motusRepository.getWord() } returns expectedStateListCharacter
        coEvery { getRandomWordUseCase.invoke() } returns expectedStateListCharacter
        coEvery { randomWordUiMapper.randomWordStateToUiState(getRandomWordUseCase()) } returns expectedUiStateListCharacter

        // when
        mainViewModel = MainViewModel(getRandomWordUseCase, findWordUseCase, randomWordUiMapper, testDispatcher)

        // then
        assertTrue(mainViewModel.state.value is UiState.Loading)
    }
}