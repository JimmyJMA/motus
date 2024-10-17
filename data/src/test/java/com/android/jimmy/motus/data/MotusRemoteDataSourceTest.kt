package com.android.jimmy.motus.data

import com.android.jimmy.motus.data.api.MotusRemoteDataSourceImpl
import com.android.jimmy.motus.data.api.MotusService
import com.android.jimmy.motus.data.api.toState
import com.android.jimmy.motus.domain.toSuccessOrNull
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MotusRemoteDataSourceTest: BaseUnitTest() {

    @MockK
    private lateinit var motusService: MotusService

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var motusRemoteDataSource: MotusRemoteDataSource

    @Before
    fun setup() {
        motusRemoteDataSource = MotusRemoteDataSourceImpl(
            coroutineContext = testDispatcher,
            motusService = motusService,
        )
    }

    @Test
    fun `should return error when api is broken`() = runTest(testDispatcher) {
        // given
        val error = Response.error<String>(404, "not found".toResponseBody())
        val expectedResult = Result.failure<String>(Throwable(error.message()))

        coEvery { motusService.getWords() } returns error

        // when
        val actualResult = motusService.getWords()

        // then
        assertEquals(expectedResult.exceptionOrNull()?.message, actualResult.message())
    }

    @Test
    fun `should return data when api is up`() = runTest(testDispatcher) {
        // given
        val expectedResponse = "AAAAAA"

        coEvery { motusService.getWords() } returns Response.success(expectedResponse)

        // when
        val actual = motusRemoteDataSource.bindsMotusRemoteDataSource()

        // then
        assertEquals(expectedResponse, actual.toState().toSuccessOrNull()?.data)
    }

}