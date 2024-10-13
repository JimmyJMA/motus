package com.android.jimmy.motus.data.di

import com.android.jimmy.motus.data.MotusRepositoryImpl
import com.android.jimmy.motus.domain.repository.MotusRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface MotusRepositoryModule {
    @Binds
    @Singleton
    fun bindMotusRepository(impl: MotusRepositoryImpl): MotusRepository
}
