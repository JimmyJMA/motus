package com.android.jimmy.motus.data

import com.android.jimmy.motus.data.api.NetworkResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MotusRemoteDataSource {
    @Binds
    suspend fun bindsMotusRemoteDataSource(): NetworkResult<String>
}