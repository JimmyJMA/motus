package com.android.jimmy.motus.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal class CoroutineModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineContext =
        Dispatchers.IO
}
