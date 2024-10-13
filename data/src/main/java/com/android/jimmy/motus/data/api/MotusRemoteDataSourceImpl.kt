package com.android.jimmy.motus.data.api

import com.android.jimmy.motus.data.MotusRemoteDataSource
import com.android.jimmy.motus.data.di.IoDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal class MotusRemoteDataSourceImpl @Inject constructor(
    private val motusService: MotusService,
    @IoDispatcher private val coroutineContext: CoroutineContext,
) : MotusRemoteDataSource, NetworkApiHandler {


    override suspend fun bindsMotusRemoteDataSource(): NetworkResult<String> {
        return withContext(coroutineContext) {
            handleApi { motusService.getWords() }
        }
    }
}