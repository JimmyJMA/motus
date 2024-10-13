package com.android.jimmy.motus.data

import com.android.jimmy.motus.data.api.NetworkResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MotusLocalDataSource {
    @Binds
    suspend fun bindsMotusLocalDataSource(): NetworkResult<String>
}
