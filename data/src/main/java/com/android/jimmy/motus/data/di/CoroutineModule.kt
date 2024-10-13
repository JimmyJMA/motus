package com.android.jimmy.motus.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

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
