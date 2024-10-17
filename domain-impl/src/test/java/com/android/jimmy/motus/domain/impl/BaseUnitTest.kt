package com.android.jimmy.motus.domain.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.TimeZone

@ExperimentalCoroutinesApi
open class BaseUnitTest(private val dispatcher: TestDispatcher = StandardTestDispatcher()): TestWatcher() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    @Before
    fun initSetup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"))
        MockKAnnotations.init(this)
    }

}