package com.android.jimmy.motus.data.di

import com.android.jimmy.motus.data.MotusLocalDataSource
import com.android.jimmy.motus.data.api.MotusLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MotusLocalDataSourceModule {
    @Binds
    fun bindsMotusLocalDataSource(
        motusLocalDataSourceImpl: MotusLocalDataSourceImpl
    ): MotusLocalDataSource
}
