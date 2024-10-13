package com.android.jimmy.motus.data.api

import android.content.Context
import com.android.jimmy.motus.data.MotusLocalDataSource
import com.android.jimmy.motus.data.R
import com.android.jimmy.motus.data.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

internal class MotusLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val coroutineContext: CoroutineContext
) : MotusLocalDataSource, NetworkApiHandler {

    @Suppress("SwallowedException")
    override suspend fun bindsMotusLocalDataSource(): NetworkResult<String> {
        return withContext(coroutineContext) {
            try {
                val data = context.resources.openRawResource(R.raw.words_default).bufferedReader()
                    .use {
                        it.readText()
                    }
                if (data.isNotEmpty()) {
                    NetworkResult.OnSuccess(ERROR_200, data)
                } else {
                    NetworkResult.OnError(
                        ERROR_200,
                        context.getString(R.string.error_local_empty_data)
                    )
                }
            } catch (exception: Exception) {
                NetworkResult.OnError(
                    ERROR_404,
                    context.getString(R.string.error_local_file_not_found)
                )
            }
        }
    }

    companion object {
        private const val ERROR_200 = 200
        private const val ERROR_404 = 404
    }
}
