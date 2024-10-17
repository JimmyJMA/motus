package com.android.jimmy.motus.domain.impl

import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.domain.repository.MotusRepository
import com.android.jimmy.motus.domain.usecase.GetRandomWordUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRandomWordUseCaseTest: BaseUnitTest() {

    @MockK
    private lateinit var motusRepository: MotusRepository

    private lateinit var getRandomWordUseCase: GetRandomWordUseCase

    @Before
    fun setup() {
        getRandomWordUseCase = GetRandomWordUseCaseImpl(
            motusRepository = motusRepository
        )
    }

    @Test
    fun `should return character list`() = runTest {
        // given
        val expectedResultStateCharacterList = State.Success(
            listOf(
                Character('A', Status.NONE),
                Character('A', Status.NONE),
                Character('A', Status.NONE),
                Character('A', Status.NONE),
                Character('A', Status.NONE),
                Character('A', Status.NONE),
            )
        )

        coEvery { motusRepository.getWord() } returns expectedResultStateCharacterList

        // when
        val result = getRandomWordUseCase()

        // then
        assertEquals(expectedResultStateCharacterList, result)
    }

    @Test
    fun `should return character list with win`() = runTest {
        val expectedCharacters = listOf(
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
        )
        val characters = listOf(
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
        )

        // when
        val result = getRandomWordUseCase.validate(expectedCharacters, characters)

        // then
        assertEquals(expectedCharacters, result)
    }

    @Test
    fun `should return character list with loose`() = runTest {
        val expectedCharacters = listOf(
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
            Character('A', Status.CORRECT),
        )
        val characters = listOf(
            Character('B', Status.WRONG),
            Character('A', Status.CORRECT),
            Character('B', Status.WRONG),
            Character('A', Status.CORRECT),
            Character('B', Status.WRONG),
            Character('A', Status.CORRECT),
        )

        // when
        val result = getRandomWordUseCase.validate(expectedCharacters, characters)

        // then
        assertNotEquals(expectedCharacters, result)
    }
}