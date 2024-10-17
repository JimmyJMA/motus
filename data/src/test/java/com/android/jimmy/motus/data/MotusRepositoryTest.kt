package com.android.jimmy.motus.data

import com.android.jimmy.motus.data.api.MotusService
import com.android.jimmy.motus.data.api.NetworkResult
import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.mapper.CharacterMapper
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.domain.repository.MotusRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MotusRepositoryTest: BaseUnitTest() {

    @MockK
    private lateinit var motusRemoteDataSource: MotusRemoteDataSource

    @MockK
    private lateinit var motusLocalDataSource: MotusLocalDataSource

    @MockK
    private lateinit var characterMapper: CharacterMapper

    @MockK
    private lateinit var apiMotusService: MotusService

    private lateinit var motusRepository: MotusRepository

    @Before
    fun setup() {
        motusRepository = MotusRepositoryImpl(
            motusRemoteDataSource,
            motusLocalDataSource,
            characterMapper,
        )
    }

    @Test
    fun `should return data when api is up`() = runTest {
        // given
        val expectedResultStateString = "AAAAAA"
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

        coEvery { apiMotusService.getWords() } returns Response.success(expectedResultStateString)
        coEvery { motusRemoteDataSource.bindsMotusRemoteDataSource() } returns NetworkResult.OnSuccess(200, expectedResultStateString)
        coEvery { characterMapper.toCharacterMapper(State.Success(expectedResultStateString)) } returns expectedResultStateCharacterList

        // when
        val result = motusRepository.getWord()

        // then
        assertEquals(expectedResultStateCharacterList, result)
    }

    @Test
    fun `should return data when api broken`() = runTest {
        // given
        val expectedResultStateString = "AAAAAA"
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

        coEvery { apiMotusService.getWords() } returns Response.success(expectedResultStateString)
        coEvery { motusRemoteDataSource.bindsMotusRemoteDataSource() } returns NetworkResult.OnError(404, "")
        coEvery { motusLocalDataSource.bindsMotusLocalDataSource() } returns NetworkResult.OnSuccess(200, expectedResultStateString)
        coEvery { characterMapper.toCharacterMapper(State.Success(expectedResultStateString)) } returns expectedResultStateCharacterList

        // when
        val result = motusRepository.getWord()

        // then
        assertEquals(expectedResultStateCharacterList, result)
    }

    @Test
    fun `shouldn't return data when api and offline mode is broken`() = runTest {
        // given
        val expectedResultStateString = "AAAAAA"
        val expectedResultStateCharacterList: State<List<Character>> = State.Failure("")

        coEvery { apiMotusService.getWords() } returns Response.success(expectedResultStateString)
        coEvery { motusRemoteDataSource.bindsMotusRemoteDataSource() } returns NetworkResult.OnError(404, "")
        coEvery { motusLocalDataSource.bindsMotusLocalDataSource() } returns NetworkResult.OnError(404, "")
        coEvery { characterMapper.toCharacterMapper(State.Success(expectedResultStateString)) } returns State.Failure("", 404)

        // when
        val result = motusRepository.getWord()

        // then
        assertEquals(expectedResultStateCharacterList, result)
    }

    @Test
    fun `should return true when word is finded`() = runTest {
        // given
        val expectedWord = "AAAAAA"

        coEvery { apiMotusService.getWords() } returns Response.success(expectedWord)
        coEvery { motusRemoteDataSource.bindsMotusRemoteDataSource() } returns NetworkResult.OnSuccess(200, expectedWord)

        // when
        val result = motusRepository.findWord(expectedWord)

        // then
        assertTrue(result)
    }

    @Test
    fun `should return false when word is finded`() = runTest {
        // given
        val expectedWord = "AAAAAA"
        val givenWord = "ABABAB"

        coEvery { apiMotusService.getWords() } returns Response.success(expectedWord)
        coEvery { motusRemoteDataSource.bindsMotusRemoteDataSource() } returns NetworkResult.OnSuccess(200, expectedWord)

        // when
        val result = motusRepository.findWord(givenWord)

        // then
        assertFalse(result)
    }

}