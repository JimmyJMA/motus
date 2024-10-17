package com.android.jimmy.motus.data

import android.content.Context
import com.android.jimmy.motus.data.api.MotusLocalDataSourceImpl
import com.android.jimmy.motus.data.api.NetworkResult
import com.android.jimmy.motus.data.api.toState
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.toSuccessOrNull
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class MotusLocalDataSourceTest: BaseUnitTest() {

    private val testDispatcher = StandardTestDispatcher()

    private val context = mockk<Context>(relaxed = true)

    private lateinit var motusLocalDataSource: MotusLocalDataSource

    @Before
    fun setup() {
        motusLocalDataSource = MotusLocalDataSourceImpl(
            context = context,
            coroutineContext = testDispatcher,
        )

    }

    @Test
    fun `should return success when file offline is present`() = runTest(testDispatcher) {
        // given
        val success = Response.success("AAABBB")
        val expectedResult = NetworkResult.OnSuccess(200, success)

        coEvery { context.resources.openRawResource(R.raw.words_default) } returns "AAABBB".byteInputStream()

        // when
        val actualResult = motusLocalDataSource.bindsMotusLocalDataSource()

        // then
        assertEquals(expectedResult.data.body(), actualResult.toState().toSuccessOrNull()?.data)
    }

    @Test
    fun `should return error when file offline is not here`() = runTest(testDispatcher) {
        // given
        val expectedResult: NetworkResult<List<Character>> = NetworkResult.OnError(404, "")

        coEvery { context.resources.openRawResource(R.raw.words_default) } returns "".byteInputStream()

        // when
        val actualResult = motusLocalDataSource.bindsMotusLocalDataSource()

        // then (state error return null on toSuccessOfNull)
        assertEquals(expectedResult.toState().toSuccessOrNull(), actualResult.toState().toSuccessOrNull())
    }

}