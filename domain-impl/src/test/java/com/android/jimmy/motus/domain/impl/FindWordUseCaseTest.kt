package com.android.jimmy.motus.domain.impl

import com.android.jimmy.motus.domain.repository.MotusRepository
import com.android.jimmy.motus.domain.usecase.FindWordUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FindWordUseCaseTest: BaseUnitTest() {

    @MockK
    private lateinit var motusRepository: MotusRepository

    private lateinit var findWordUseCase: FindWordUseCase

    @Before
    fun setup() {
        findWordUseCase = FindWordUseCaseImpl(
            motusRepository = motusRepository
        )
    }

    @Test
    fun `should return true when word is finded`() = runTest {
        // given
        val expectedWord = "AAAAAA"

        coEvery { motusRepository.findWord(expectedWord) } returns true

        // when
        val result = findWordUseCase(expectedWord)

        // then
        assertTrue(result)
    }

    @Test
    fun `should return false when word is finded`() = runTest {
        // given
        val expectedWord = "ABAAAA"

        coEvery { motusRepository.findWord(expectedWord) } returns false

        // when
        val result = findWordUseCase(expectedWord)

        // then
        assertFalse(result)
    }

}