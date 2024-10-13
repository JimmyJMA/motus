package com.android.jimmy.motus.data.di

import com.android.jimmy.motus.data.MotusRemoteDataSource
import com.android.jimmy.motus.data.api.MotusRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MotusRemoteDataSourceModule {
    @Binds
    fun bindsMotusRemoteDataSource(motusRemoteDataSourceImpl: MotusRemoteDataSourceImpl): MotusRemoteDataSource
}